package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin" })
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Statement stmt = null;
    private static Connection con = null;
    private static DataBaseFunctions db = new DataBaseFunctions();
    private static boolean rudLoja = false;
    private static boolean rudClient = false;
    private static boolean editingStore = false;
    private static boolean editingClient = false;
    private static boolean showStoreForm = false;
    private static boolean showClientForm = false;
    private static Cliente editableClient = null;
    private static Loja editableStore = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Erro erros = new Erro();
        db.checkCreation();
        if (req.getParameter("rewind") != null) {
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req, resp);
            return;
        }
        if (req.getParameter("regLoja") != null) {
            showStoreForm = true;
        }
        if (req.getParameter("regClient") != null) {
            showClientForm = true;
        }

        if (req.getParameter("closeStore") != null) {
            editingStore = false;
            showStoreForm = false;
        }
        if (req.getParameter("closeClient") != null) {
            editingClient = false;
            showClientForm = false;
        }
        if (req.getParameter("editClient") != null) {
            rudClient = true;
        }
        if (req.getParameter("editLoja") != null) {
            rudLoja = true;
        }
        if (req.getParameter("closeRud") != null) {
            rudClient = false;
            rudLoja = false;
        }
        if (req.getParameter("sendIdLoja") != null) {
            String cnpj = req.getParameter("idLoja");
            editableStore = db.getStoreByCnpj(cnpj);
            if (editableStore == null) {
                erros.add("Cnpj não pertence a nenhuma loja");
            } else {
                rudLoja = false;
                editingStore = true;
                System.out.println("password is " + editableStore.getSenha());
                req.setAttribute("editableStore", editableStore);
            }
        }
        if (req.getParameter("sendIdClient") != null) {
            String cpf = req.getParameter("idClient");
            editableClient = db.getClientByCpf(cpf);
            if (editableClient == null) {
                erros.add("Cpf não pertence a nenhum cliente");
            } else {
                rudClient = false;
                editingClient = true;
                req.setAttribute("editableClient", editableClient);
            }
        }

        if(req.getParameter("createClient") != null){
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String cpf = req.getParameter("cpf");
            String nome = req.getParameter("nome");
            String telefone = req.getParameter("telefone");
            String sexo = req.getParameter("sexo");
            String nascimento = req.getParameter("nascimento");
            if(email == null || email.isEmpty()){
                erros.add("Email vazio, por favor insira algo");
            }
            if(pass == null || pass.isEmpty()){
                erros.add("Senha vazia, por favor insira algo");
            }
            if(cpf == null || cpf.isEmpty()){
                erros.add("cpf vazio, por favor insira algo");
            }
            else if(cpf.length() != 11){
                erros.add("Cpf não possui 11 digitos");
            }
            if(nome == null || nome.isEmpty()){
                erros.add("nome vazio, por favor insira algo");
            }
            if(telefone == null || telefone.isEmpty()){
                erros.add("telefone vazio, por favor insira algo");
            }
            if(sexo == null || sexo.isEmpty()){
                erros.add("sexo vazio, por favor insira algo");
            }
            if(nascimento == null || nascimento.isEmpty()){
                erros.add("nascimento vazio, por favor insira algo");
            }
            if(!erros.isExisteErros()){
                Cliente newClient = new Cliente(email, pass, cpf, nome, telefone, sexo, Date.valueOf(nascimento));

                showClientForm = false;
                String cr = db.createClient(newClient);
                if(!cr.equals(""))
                    erros.add(cr);
            }
        }

        if(req.getParameter("createStore") != null){
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String cnpj = req.getParameter("cnpj");
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            if(email == null || email.isEmpty()){
                erros.add("Email vazio, por favor insira algo");
            }
            if(pass == null || pass.isEmpty()){
                erros.add("Senha vazio, por favor insira algo");
            }
            if(cnpj == null || cnpj.isEmpty()){
                erros.add("cnpj vazio, por favor insira algo");
            }
            else if(cnpj.length() != 14){
                erros.add("Cnpj não possui 14 digitos");
            }
            if(nome == null || nome.isEmpty()){
                erros.add("nome vazio, por favor insira algo");
            }
            if(descricao == null || descricao.isEmpty()){
                erros.add("descricao vazio, por favor insira algo");
            }
            if(!erros.isExisteErros()){
                Loja newStore = new Loja(email, pass, cnpj, nome, descricao);

                String cr = db.createStore(newStore);
                showStoreForm = false;
                if(!cr.equals(""))
                    erros.add(cr);
            }
        }

        if (req.getParameter("confirmClientEdit") != null) {
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String cpf = req.getParameter("cpf");
            String nome = req.getParameter("nome");
            String telefone = req.getParameter("telefone");
            String sexo = req.getParameter("sexo");
            String nascimento = req.getParameter("nascimento");
            if(email == null || email.isEmpty()){
                erros.add("Email vazio, por favor insira algo");
            }
            if(!pass.isEmpty()){
                editableClient.setPass(pass);
            }
            if(cpf == null || cpf.isEmpty()){
                erros.add("cpf vazio, por favor insira algo");
            }
            if(nome == null || nome.isEmpty()){
                erros.add("nome vazio, por favor insira algo");
            }
            if(telefone == null || telefone.isEmpty()){
                erros.add("telefone vazio, por favor insira algo");
            }
            if(sexo == null || sexo.isEmpty()){
                erros.add("sexo vazio, por favor insira algo");
            }
            if(nascimento == null || nascimento.isEmpty()){
                erros.add("nascimento vazio, por favor insira algo");
            }

            if(!erros.isExisteErros()){
                editableClient.setEmail(email);
                editableClient.setCpf(cpf);
                editableClient.setNome(nome);
                editableClient.setTelefone(telefone);
                editableClient.setSexo(sexo);
                editableClient.setNascimento(Date.valueOf(nascimento));

                String up = db.updateClient(editableClient, !pass.isEmpty());
                editingClient = false;
                if(!up.equals(""))
                    erros.add(up);
            }
        }
        if (req.getParameter("confirmStoreEdit") != null) {
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");
            String cnpj = req.getParameter("cnpj");
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            if(email == null || email.isEmpty()){
                erros.add("Email vazio, por favor insira algo");
            }
            if(!pass.isEmpty()){
                editableStore.setSenha(pass);
            }
            if(cnpj == null || cnpj.isEmpty()){
                erros.add("cnpj vazio, por favor insira algo");
            }
            if(nome == null || nome.isEmpty()){
                erros.add("nome vazio, por favor insira algo");
            }
            if(descricao == null || descricao.isEmpty()){
                erros.add("descricao vazio, por favor insira algo");
            }
            if(!erros.isExisteErros()){
                editableStore.setEmail(email);
                editableStore.setCnpj(cnpj);;
                editableStore.setNome(nome);
                editableStore.setDescricao(descricao);

                String up = db.updateStore(editableStore, !pass.isEmpty());
                editingStore = false;
                if(!up.equals(""))
                    erros.add(up);
            }
        }
        if(req.getParameter("deleteStore") != null){
            db.deleteStore(editableStore);
            editingStore = false;
        }
        if(req.getParameter("deleteClient") != null){
            db.deleteClient(editableClient);
            editingClient = false;
        }

        req.setAttribute("editingStore", editingStore);
        req.setAttribute("editingClient", editingClient);
        req.setAttribute("showClientForm", showClientForm);
        req.setAttribute("showStoreForm", showStoreForm);
        req.setAttribute("rudLoja", rudLoja);
        req.setAttribute("rudClient", rudClient);
        req.setAttribute("mensagens", erros);

        String url = "/admin.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

}
