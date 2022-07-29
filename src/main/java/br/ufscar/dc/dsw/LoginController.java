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

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Statement stmt = null;
    private static Connection con = null;
    private static DataBaseFunctions db = new DataBaseFunctions();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Erro erros = new Erro();
        // System.out.println("Checking creation...");
        db.checkCreation();
        if (req.getParameter("bOK") != null) {
            System.out.println("ENTROU");
			String login = req.getParameter("login");
			String password = req.getParameter("senha");
            String type = req.getParameter("user");
			if (login == null || login.isEmpty()) {
				erros.add("Login não informado!");
			}
			if (password == null || password.isEmpty()) {
				erros.add("Senha não informada!");
			}
			if (!erros.isExisteErros()) {
                //req.getSession().setAttribute("typeLog", type);
                boolean logged = false;
                if(type.equals("admin")){
                    System.out.println("okay admin");
                    if(login.equals("admin") && password.equals("admin")){
                        // resp.sendRedirect("admin/");
                        logged = true;
                    }
                    else
                        erros.add("Algo errado!");
                }
                else if(type.equals("cliente")){
                    Cliente client = db.getClientByLogin(login, password);
                    if(client != null){
                        req.getSession().setAttribute("clientLog", client);
                        // resp.sendRedirect("client/");
                        logged = true;
                    }
                    else
                        erros.add("Algo errado!");
                }
                else if(type.equals("loja")){
                    Loja store = db.getStoreByLogin(login, password);
                    if(store != null){
                        req.getSession().setAttribute("storeLog", store);
                        // resp.sendRedirect("store/");
                        logged = true;
                    }
                    else
                        erros.add("Algo errado!");

                }
                else{
                    // n e pra vir aqui nunca erro
                    erros.add("XIIII deu ruim");
                }
                if(logged){
                    System.out.println("ta pra sair");
                    req.getSession().setAttribute("typeLog", type);
                    // resp.sendRedirect("menu/");
                    RequestDispatcher rd = req.getRequestDispatcher("/menu");
                    rd.forward(req, resp); 
                    return;
                }

			}
		}
		req.getSession().invalidate();
        req.setAttribute("mensagens", erros);
       
       String url = "/login.jsp";
       RequestDispatcher rd = req.getRequestDispatcher(url);
       rd.forward(req, resp); 
    }

	
}
