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
        //Long id, float valor, float valor_original, String condicoes, String estado, String contraproposta, String placa, String modelo, Date data_proposta

        HttpSession session = req.getSession();
        
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        Proposta propostaCliente = new Proposta((Long) session.getAttribute("id"), 
        (float) session.getAttribute("valorOfertado"), (float) session.getAttribute("valorOriginal"), (String) session.getAttribute("condicoes"),
        (String) session.getAttribute("estado"), (String) session.getAttribute("contraProposta"), (String) session.getAttribute("placa"),
        (String) session.getAttribute("modelo"), (Date) session.getAttribute("dataProposta"));

        boolean podeAbrirProposta = true;

        for (Proposta proposta : cliente.getPropostas())
        {
            if (proposta.getEstado() == "Aberto")
            {
                podeAbrirProposta = false;
            }
        }

        if (podeAbrirProposta)
        {
            db.refreshOffers(cliente);
            String url = "/cliente.jsp";
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req, resp);
        }
    }
}