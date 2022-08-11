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
    private static boolean showCars = false;
    private static boolean insertCars = false;



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
        boolean reply = false;
        String replyType = null;
        Erro erros = new Erro();
        // checa a criação do banco de dados caso nao exista, cria um novo
        db.checkCreation();
        Loja myStore = (Loja) req.getSession().getAttribute("storeLog");
        System.out.println(myStore.getNome() + " chilling with " + myStore.getPropostas().size() + " offers");
        if(req.getParameter("rewind") != null){
            RequestDispatcher rd = req.getRequestDispatcher("/Login");
            rd.forward(req, resp); 
            return;
        }
        if(req.getParameter("listOffers") != null && insertCars == false && showCars == false){
            db.refreshOffers(myStore);
            showOffers = true;
        }
        if(req.getParameter("closeOffers") != null){
            showOffers = false;
        }
        if(req.getParameter("listCars") != null && insertCars == false && showOffers == false){
            db.refreshCars(myStore);
            showCars = true;
        }
        if(req.getParameter("closeCars") != null){
            showCars = false;
        }
        
        if(req.getParameter("regCar") != null && showCars == false && showOffers == false){
            System.out.println("registering");
            insertCars = true;
        }

        if(req.getParameter("bCLOSE") != null){
            insertCars = false;
        }

        if(req.getParameter("acceptOffer") != null){
            reply = true;
            replyType = "ACEITO";
        }
        if(req.getParameter("declineOffer") != null){
            reply = true;
            replyType = "NÂO ACEITO";
        }

        if(reply){
            String id = req.getParameter("offerId");
            String valor = req.getParameter("counterOfferValue");
            String condition = req.getParameter("counterOfferCondition");

            String placa = null;
            Proposta replying = null;
            
            if(id == null || id.isEmpty())
                erros.add("Proposta nao informada");
            else{
               
                for(Proposta offer : myStore.getPropostas()){ //buscando a oferta do id passado
                    
                    System.out.println(offer.getId() + " aqui tem id " + id + " estado = " + offer.getEstado().toLowerCase());

                    if(offer.getId() == Long.parseLong(id)){
                        System.out.println("Entrei");
                        if(offer.getEstado().toLowerCase().equals("aberto")){
                            placa = offer.getPlaca();
                            replying = offer;
                        }
                        else{
                            erros.add("Oferta não está em aberto");
                        }
                    }
                }

                if(placa == null && !erros.isExisteErros()) // caso nao ache a placa nao achou a proposta, entao da erro
                    erros.add("Oferta " + id + " nao existente");
            }
            if(!erros.isExisteErros()){
                if(valor == null)
                    valor = "...";
                if(condition == null)
                    condition = "...";
                String counterOffer = replyType.toLowerCase().equals("ACEITO") ? "" : "R$ " + valor + " " + condition;
                db.replyOffer(replying, counterOffer, replyType);
            }            
            showOffers = false;

        }


        if(req.getParameter("bSAVE") != null){ //Registrar carro!

            try{

            
            String imagem = req.getParameter("Imagem");
            String cnpj = req.getParameter("CNPJ");
			String placa = req.getParameter("Placa");
            String modelo = req.getParameter("Modelo");
			String chassi = req.getParameter("Chassi");
			String ano = req.getParameter("Ano");
            String quilometragem = req.getParameter("Quilometragem");
			String descricao = req.getParameter("Descricao");
			String valor = req.getParameter("Valor");
            ArrayList<String> listaimagens = null;

            if(imagem != null || ! imagem.isEmpty()){
                System.out.println(imagem.split(","));
                listaimagens = new ArrayList<String>(Arrays.asList(imagem.split(",")));
            }


            if (listaimagens.size() > 10)
            {
                erros.add("Número máximo de imagens atingido!");
            }

			if (cnpj == null || cnpj.isEmpty()) {// caso falte dados
				erros.add("CNPJ não informado!");
			}
            else if(! cnpj.equals(myStore.getCnpj())){
                erros.add("CNPJ incorreto!");
            }
			if (placa == null || placa.isEmpty()) {
				erros.add("Placa não informada!");
			}
            if (modelo == null || modelo.isEmpty()) {
				erros.add("Modelo não informado!");
			}
            if (chassi == null || chassi.isEmpty()) {
				erros.add("Chassi não informado!");
			}
            if (ano == null || ano.isEmpty()) {
				erros.add("Ano não informado!");
			}
            if (quilometragem == null || quilometragem.isEmpty()) {
				erros.add("Quilometragem não informada!");
			}
            if (descricao == null || descricao.isEmpty()) {
				erros.add("Descrição não informada!");
			}
            if (valor == null || valor.isEmpty()) {
				erros.add("Valor não informado!");
			}



            if (!(erros.isExisteErros())){

                Loja newLoja1 = (Loja)req.getSession().getAttribute("storeLog");
                Carros newCar1 = new Carros(newLoja1.getId(), Long.parseLong(ano), Long.parseLong(quilometragem), placa, modelo, chassi, descricao, Float.parseFloat(valor), listaimagens, cnpj);
                //adicionar imagem aqui
                db.insertCars(newCar1);

                for (String link:listaimagens)
                    db.insertImage(link, placa);
                
                insertCars = false;

            }

        } catch(Exception e){
            erros.add(e.getMessage());
        }


        }
        req.setAttribute("showOffers", showOffers);
        req.setAttribute("showCars", showCars);
        req.setAttribute("insertCars", insertCars);
        req.setAttribute("storeLog", myStore);
        req.setAttribute("mensagens", erros);

        String url = "/loja.jsp";
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp); 
    }

	
}
