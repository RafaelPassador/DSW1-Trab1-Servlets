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
        db.checkCreation();
        
        Cliente cliente = (Cliente) session.getAttribute("clientLog");
        Proposta propostaCliente = new Proposta((Long) session.getAttribute("id"),
        Float.parseFloat(req.getParameter("valorOfertado")), Float.parseFloat(req.getParameter("valorOriginal")),
        req.getParameter("condicoes"),
        req.getParameter("estado"), req.getParameter("contraProposta"), req.getParameter("placa"),
        req.getParameter("modelo"), new Date(System.currentTimeMillis()));

        boolean podeAbrirProposta = true;

        for (Proposta proposta : cliente.getPropostas())
        {
            if (proposta.getEstado().toLowerCase().equals("Aberto") && proposta.getPlaca().equals(propostaCliente.getPlaca()))
            {
                podeAbrirProposta = false;
            }
        }

        if (podeAbrirProposta)
        {
            db.insertProposta(propostaCliente, cliente);
            String url = "/cliente.jsp";
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req, resp);
        }
    }
}