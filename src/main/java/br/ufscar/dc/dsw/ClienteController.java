package br.ufscar.dc.dsw;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet
{

    private static final long serialVersionUID = 1L;
    private static Statement stmt = null;
    private static Connection con = null;
    private static DataBaseFunctions db = new DataBaseFunctions();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("post get");
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        Erro erros = new Erro();
        db.checkCreation();
        if(req.getParameter("rewind") != null){
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req, resp); 
            return;
        }

        if (req.getParameter("botaoEnviar") != null)
        {
            Cliente cliente = (Cliente) session.getAttribute("clientLog");
            
            System.out.println("cliente: " + cliente);
            
            try{
                Carros carro = db.getCarByPlate(req.getParameter("placa"));
                if(carro == null){
                    erros.add("Placa inválida!");
                }
                if(req.getParameter("placa") == null){
                    erros.add("Carro não informado! (placa)");
                }
                if(req.getParameter("valorOfertado") == null){
                    erros.add("Valor da oferta não informado!");
                }
                if(req.getParameter("condicoes") == null){
                    erros.add("Condição não informada!");
                }
                Proposta propostaCliente = new Proposta(
                Float.parseFloat(req.getParameter("valorOfertado")),
                req.getParameter("condicoes"),"Aberto", carro.getPlaca(),
                carro.getModelo(), new Date(System.currentTimeMillis()));
                
                boolean podeAbrirProposta = true;
                
                for (Proposta proposta : cliente.getPropostas())
                {
                    if (proposta.getEstado().toLowerCase().equals("aberto") && proposta.getPlaca().equals(propostaCliente.getPlaca()))
                    {
                        erros.add("Proposta para esse carro já está em aberto!");
                        podeAbrirProposta = false;
                    }
                }
                
                if (podeAbrirProposta)
                {
                    String ins = db.insertProposta(propostaCliente, cliente);
                    if(!ins.equals("")){
                        erros.add(ins);
                    }
                    db.refreshOffers(cliente);
                    req.setAttribute("clientLog", cliente);
                }
            }
            catch(Exception e){
                erros.add(e.getMessage());
            }
        }

        req.setAttribute("mensagens", erros);

        String url = "/cliente.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }
}