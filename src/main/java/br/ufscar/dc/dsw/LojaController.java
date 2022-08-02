package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/loja"})
public class LojaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Statement stmt = null;
    private static Connection con = null;
    private static DataBaseFunctions db = new DataBaseFunctions();
    private static boolean showOffers = false;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("post get");
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // boolean showOffers = false;
        Loja myStore = (Loja) req.getSession().getAttribute("storeLog");
        System.out.println(myStore.getNome() + " chilling with " + myStore.getPropostas().size() + " offers");
        if(req.getParameter("listOffers") != null){
           System.out.println("listing");
            showOffers = true;
        }
        if(req.getParameter("closeOffers") != null){
            showOffers = false;
            System.out.println("here");
        }
        System.out.println("Setting " + showOffers);
        req.setAttribute("showOffers", showOffers);
        
        String url = "/loja.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp); 
    }

	
}
