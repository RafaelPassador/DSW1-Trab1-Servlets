package br.ufscar.dc.dsw;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/menu/*")
public class Menu extends HttpServlet { // classe para reposicionar para botões

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = (String) //request.getParameter("typeLog");
        request.getSession().getAttribute("typeLog");
        
        //responsavel pelo direcionamento ao servlet de cada usuario
        if(type.equals("cliente")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente");
            dispatcher.forward(request, response);
            return;
            
        }
        else if(type.equals("loja")){
            // response.sendRedirect("/Servlet-Trab/loja");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loja");
            dispatcher.forward(request, response);
            return;
            
        }
        else if(type.equals("admin")){
            // response.sendRedirect("/admin.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin");
            dispatcher.forward(request, response);
            return;

        }
        else{
            // aqui n e pra chegar, se chegou ferrou
        }

        System.out.println("MEDOOO");
            
        
    }
}