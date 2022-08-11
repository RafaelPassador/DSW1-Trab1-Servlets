package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

//this is a DAO

public class DataBaseFunctions {

    private static Statement stmt = null;
    private static String database = "";    

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        stmt.close();
        super.finalize();
    }

    public Connection getConnection(){
        Connection con = null;
        try{
            String url = "jdbc:mysql://db:3306"; //+ database;
            con = (Connection) DriverManager.getConnection(System.getenv("DATABASE_URI"), "root", "password");
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
            System.out.println("kk pra variar " + e.getMessage());
        }
        return con;
    }

    public void checkCreation(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = con.getMetaData().getCatalogs();
            //rs é o result set com todos os databases do sql
            boolean exists = false;
            while(rs.next()){
                if(rs.getString(1).toLowerCase().equals("sistemaveiculos"))
                    exists = true;
            }
            //n pode esquecer de fechar
            rs.close();

            // caso nao exista a base devemos criar!
            if(!exists) 
                criar();
            else{
                database = "/SistemaVeiculos";
            }
            
        }
        catch(ClassNotFoundException e){
            //tratar erros preguicinha aqui é de driver
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query

        }
    }

    public void criar(){
        try{
            System.out.println("creating");
            String query = "create database SistemaVeiculos;";
            stmt.executeUpdate(query);
            query = "use SistemaVeiculos;";
            stmt.executeUpdate(query);
            query = "create table Cliente(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(1200) not null, cpf char(11) not null, nome varchar(256) not null, telefone varchar(14) not null, sexo varchar(1) not null, nascimento date not null, primary key(id));";
            stmt.executeUpdate(query);
            query = "create table Loja(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(1200) not null, cnpj char(14) not null, nome varchar(256) not null, descricao varchar(1024) not null, primary key(id));";
            stmt.executeUpdate(query);
            query = "create table Carros(placa char(7) not null, modelo varchar(128) not null, chassi varchar(64) not null, ano bigint not null, quilometragem bigint not null, descricao varchar(1024) not null, valor float not null, loja_id bigint not null, primary key(placa), foreign key(loja_id) references Loja(id));";
            stmt.executeUpdate(query);
            query = "create table Imagens(id bigint not null auto_increment, carro_id char(7) not null, link text not null, primary key(id), foreign key(carro_id) references Carros(placa));";
            stmt.executeUpdate(query);
            query = "create table Proposta(id bigint not null auto_increment, loja_id bigint not null, cliente_id bigint not null, carro_id char(7) not null, valor float not null, condicoes varchar(1200) not null, estado varchar(11) not null, contraproposta varchar(1200), data_proposta date not null, primary key(id, cliente_id, carro_id), foreign key(cliente_id) references Cliente(id), foreign key(loja_id) references Loja(id), foreign key(carro_id) references Carros(placa));";
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
            System.out.println(e.getMessage() + " Mas quem diria");
        }
    }

    public Cliente getClientByCpf(String cpf){
        Cliente myClient = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Cliente where cpf = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cpf);
        

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                Date nascimento = resultSet.getDate("nascimento");

                ArrayList<Proposta> propostas = getOffers(id, id, true);
                myClient = new Cliente(id, email, pass, cpf, nome, telefone, sexo, nascimento, propostas);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            //tratar erros preguicinha aqui é de Query
        }
        return myClient;
    }



    public Cliente getClientByLogin(String login, String password){
        Cliente myClient = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Cliente where email = ? and pass = md5(?)";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
        

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                Date nascimento = resultSet.getDate("nascimento");

                ArrayList<Proposta> propostas = getOffers(id, id, true);
                myClient = new Cliente(id, email, pass, cpf, nome, telefone, sexo, nascimento, propostas);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            //tratar erros preguicinha aqui é de Query
        }
        return myClient;
    }

    public Loja getStoreByCnpj(String cnpj){
        Loja myStore = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Loja where cnpj = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cnpj);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                ArrayList<Carros> carros = getStoreCars(id);
                ArrayList<Proposta> propostas = getOffers(id, id, false);
                myStore = new Loja(id, email, pass, cnpj, nome, descricao, carros, propostas);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage() + " Oh my goodness");
        }
        return myStore;
    }

    public Loja getStoreByLogin(String login, String password){
        Loja myStore = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Loja where email = ? and pass = md5(?)";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                ArrayList<Carros> carros = getStoreCars(id);
                ArrayList<Proposta> propostas = getOffers(id, id, false);
                myStore = new Loja(id, email, pass, cnpj, nome, descricao, carros, propostas);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage() + " Oh my goodness");
        }
        return myStore;
    }

    public String getStoreCnpj(long id){
        //Pode dar erro (encriptacao)
        String sql = "select * from Loja where id = ?";
        String cnpj = null;

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()){ // se tiver algo resultado da consulta
                cnpj = resultSet.getString("cnpj");
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage() + " Oh my goodness");
        }
        return cnpj;
    }

    public Carros getCarByPlate(String placa){
        Carros car = null;
        
        String sql = "select * from Carros where placa = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, placa);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){ // se tiver algo resultado da consulta
                Long lojaId = resultSet.getLong("loja_id");
                Long ano = resultSet.getLong("ano");
                Long quilometragem = resultSet.getLong("quilometragem");
                String modelo = resultSet.getString("modelo");
                String chassi = resultSet.getString("chassi");
                String descricao = resultSet.getString("descricao");
                Float valor = resultSet.getFloat("valor");
                String cnpj = getStoreCnpj(lojaId);

                ArrayList<String> imagens = getCarImages(placa);
                car = new Carros(lojaId, ano, quilometragem, placa, modelo, chassi, descricao, valor, imagens, cnpj);
            }
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
            System.out.println("car error " + e.getMessage());
        }
        return car;
    }

    public ArrayList<Carros> getStoreCars(Long id){
        ArrayList<Carros> myStore = new ArrayList<>();

        
        String sql = "select * from Carros where loja_id = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta
                String carId = resultSet.getString("placa");
                Long ano = resultSet.getLong("ano");
                Long quilometragem = resultSet.getLong("quilometragem");
                String placa = resultSet.getString("placa");
                String modelo = resultSet.getString("modelo");
                String chassi = resultSet.getString("chassi");
                String descricao = resultSet.getString("descricao");
                Float valor = resultSet.getFloat("valor");
                String cnpj = getStoreCnpj(id);

                ArrayList<String> imagens = getCarImages(carId);

                myStore.add(new Carros(id, ano, quilometragem, placa, modelo, chassi, descricao, valor, imagens, cnpj));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
        }
        return myStore;
    }
    
    public ArrayList<Carros> getCars() {
        ArrayList<Carros> carros = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "select id from Loja";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carros.addAll(getStoreCars(resultSet.getLong("id")));
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            //tratar erros preguicinha aqui é de Query
        }
        return carros;
    }

    public ArrayList<String> getCarImages(String id){
        ArrayList<String> myImages = new ArrayList<>();

        
        String sql = "select * from Imagens where carro_id = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta

                String endereco = resultSet.getString("link");

                myImages.add(endereco);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
        }
        return myImages;
    }


    // seria tender
    public ArrayList<Proposta> getOffers(Long clienteId, Long lojaId, boolean type){
        ArrayList<Proposta> myOffer = new ArrayList<>();

        String sql = type ? "select * from Proposta where cliente_id = ?" : "select * from Proposta where loja_id = ?";

        try{
            Connection conn = getConnection();
            System.out.println("preparing " + sql);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, type ? clienteId : lojaId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                Float valor = resultSet.getFloat("valor");
                String condicoes = resultSet.getString("condicoes");
                String estado = resultSet.getString("estado");
                String contraproposta = resultSet.getString("contraproposta");
                Date data_proposta = resultSet.getDate("data_proposta");
                String placa = resultSet.getString("carro_id");
                Carros car = getCarByPlate(placa);
                Proposta offer = new Proposta(id, valor, car.getValor(), condicoes, estado, contraproposta, placa, car.getModelo(), data_proposta);

                myOffer.add(offer);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage());
        }
        return myOffer;
    }

    public void replyOffer(Proposta offer, String contraproposta, String novo_estado){
        try{
            offer.setEstado(novo_estado);
            offer.setContraproposta(contraproposta);

            String sql = "UPDATE Proposta SET contraproposta = ?, estado = ? WHERE id = ?";

            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setString(1, contraproposta);
            statement.setString(2, novo_estado);
            statement.setLong(3, offer.getId());

            statement.executeUpdate();
            statement.close();
            con.close();
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage());
        }
    }

    public void insertCars(Carros carro1){
        try{

            String sql = "INSERT into Carros (placa, modelo, chassi, ano, quilometragem, descricao, valor, loja_id) values (?, ?, ?, ?, ?, ?, ?, ?)" ;

            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setString(1, carro1.getPlaca());
            statement.setString(2, carro1.getModelo());
            statement.setString(3, carro1.getChassi());
            statement.setLong(4, carro1.getAno());
            statement.setLong(5, carro1.getQuilometragem());
            statement.setString(6, carro1.getDescricao());
            statement.setFloat(7, carro1.getValor());
            statement.setLong(8, carro1.getLoja_id());

            statement.executeUpdate();
            statement.close();
            con.close();
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage());
        }
    }

    public void insertImage(String link, String placa){
        try{

            String sql = "INSERT into Imagens (carro_id, link) values (?, ?)" ;

            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, placa);
            statement.setString(2, link);
        

            statement.executeUpdate();
            statement.close();
            con.close();
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
           System.out.println(e.getMessage());
        }
    }

    public void insertProposta(Proposta proposta, Cliente cliente){
        try{
    
            String sql = "INSERT into Proposta (loja_id, cliente_id, carro_id, valor, condicoes, estado, data_proposta) values (?, ?, ?, ?, ?, ?, ?)";
    
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setLong(1, getCarByPlate(proposta.getPlaca()).getLoja_id());
            statement.setLong(2, cliente.getId());
            statement.setString(3, proposta.getPlaca());
            statement.setFloat(4, proposta.getValor());
            statement.setString(5, proposta.getCondicoes());
            statement.setString(6, proposta.getEstado());
            statement.setDate(7, proposta.getData_proposta());
    
            statement.executeUpdate();
            statement.close();
            con.close();
        }
        catch(SQLException e){
            //erro de query
           System.out.println(e.getMessage());
        }
    }

    public void refreshOffers(Loja store){
        store.setPropostas(getOffers(store.getId(), store.getId(), false));
    }

    public void refreshOffers(Cliente client){
        client.setPropostas(getOffers(client.getId(), client.getId(), true));
    }

    public void refreshCars(Loja store){
        store.setCarrosLoja(getStoreCars(store.getId()));
    }

    public String createStore(Loja store){
        String sql = "insert into Loja(email, pass, cnpj, nome, descricao) values (?, md5(?), ?, ?, ?)";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, store.getEmail());
            statement.setString(2, store.getSenha());
            statement.setString(3, store.getCnpj());
            statement.setString(4, store.getNome());
            statement.setString(5, store.getDescricao());

            statement.executeUpdate();


            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";

    }

    public String createClient(Cliente client){
        String sql = "insert into Cliente(email, pass, cpf, nome, telefone, sexo, nascimento) values (?, md5(?), ?, ?, ?, ?, ?)";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPass());
            statement.setString(3, client.getCpf());
            statement.setString(4, client.getNome());
            statement.setString(5, client.getTelefone());
            statement.setString(6, client.getSexo());
            statement.setDate(7, client.getNascimento());

            statement.executeUpdate();


            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";
    }

    public String updateClient(Cliente client, boolean newPassword){
        String sql = newPassword?
        "update Cliente set email = ?, pass = md5(?), cpf = ?, nome = ?, telefone = ?, sexo = ?, nascimento = ? where id = ?":
        "update Cliente set email = ?, pass = ?, cpf = ?, nome = ?, telefone = ?, sexo = ?, nascimento = ? where id = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPass());
            statement.setString(3, client.getCpf());
            statement.setString(4, client.getNome());
            statement.setString(5, client.getTelefone());
            statement.setString(6, client.getSexo());
            statement.setDate(7, client.getNascimento());
            statement.setLong(8, client.getId());

            statement.executeUpdate();


            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";
    }

    public String updateStore(Loja store, boolean newPassword){
        String sql = newPassword ? 
        "update Loja set email = ?, pass = md5(?), cnpj = ?, nome = ?, descricao = ? where id = ?" : 
        "update Loja set email = ?, pass = ?, cnpj = ?, nome = ?, descricao = ? where id = ?";
        System.out.println("sql = " + sql);

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, store.getEmail());
            statement.setString(2, store.getSenha());
            statement.setString(3, store.getCnpj());
            statement.setString(4, store.getNome());
            statement.setString(5, store.getDescricao());
            statement.setLong(6, store.getId());

            statement.executeUpdate();


            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";
    }

    public String deleteStore(Loja store){
        String sql = "delete from Loja where id = ?";
        try{
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, store.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            // tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";
    }
    public String deleteClient(Cliente client){
        String sql = "delete from Cliente where id = ?";
        try{
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, client.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            // tratar erros preguicinha aqui é de Query
            return e.getMessage();
        }
        return "";
    }

}
