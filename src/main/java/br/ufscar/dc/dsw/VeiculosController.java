package br.ufscar.dc.dsw;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/index.html")
public class VeiculosController extends HttpServlet {
    private static DataBaseFunctions db = new DataBaseFunctions();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db.checkCreation();
        req.getSession().setAttribute("cars", db.getCars());
        String url = "/veiculos.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp); 
    }
}
