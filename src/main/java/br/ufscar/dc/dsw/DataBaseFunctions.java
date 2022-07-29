package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//this is a DAO

public class DataBaseFunctions {

    private static Statement stmt = null;
    

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        stmt.close();
        super.finalize();
    }

    public Connection getConnection(){
        Connection con = null;
        try{
            String url = "jdbc:mysql://localhost:3306";
            con = (Connection) DriverManager.getConnection(url, "root", "password");
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
                if(rs.getString(1).toLowerCase() == "sistemaveiculos")
                    exists = true;
            }
            //n pode esquecer de fechar
            rs.close();

            // caso nao exista a base devemos criar!
            if(!exists) 
                criar();
            else
                stmt.execute("use SistemaVeiculos;");
            
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
            String query = "create database SistemaVeiculos;";
            stmt.execute(query);
            query = "use SistemaVeiculos;";
            stmt.execute(query);
            query = "create table Cliente(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(21844) not null, cpf char(11) not null, nome varchar(256) not null, telefone varchar(14) not null, sexo varchar(1) not null, nascimento date not null, primary key(email));";
            stmt.executeUpdate(query);
            query = "create table Loja(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(21844) not null, cnpj char(14) not null, nome varchar(256) not null, descricao varchar(1024) not null, primary key(email));";
            stmt.executeUpdate(query);
            query = "create table Carros(placa char(7) not null, modelo varchar(128) not null, chassi varchar(64) not null, ano bigint not null, quilometragem bigint not null, descricao varchar(1024) not null, valor float not null, loja_id bigint not null, primary key(placa), foreign key(loja_id) references Loja(id));";
            stmt.executeUpdate(query);
            query = "create table Imagens(id bigint not null auto_increment, carro_id char(7) not null, primary key(id), foreign key(carro_id) references Carros(id));";
            stmt.executeUpdate(query);
            query = "create table Proposta(id bigint not null auto_increment, loja_id bigint not null, cliente_id bigint not null, valor float not null, condicoes varchar(21844) not null, estado varchar(11) not null, primary key(id), foreign key(cliente_id) references Cliente(id), foreign key(loja_id) references Loja(id));";
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            //tratar erros preguicinha aqui é de Query
        }
    }

    public Cliente getClientByLogin(String login, String password){
        Cliente myClient = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Cliente where email = ? and pass = password(?)";

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

    public Loja getStoreByLogin(String login, String password){
        Loja myStore = null;

        //Pode dar erro (encriptacao)
        String sql = "select * from Loja where email = ? and pass = password(?)";

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
        }
        return myStore;
    }

    public ArrayList<Carros> getStoreCars(Long id){
        ArrayList<Carros> myStore = new ArrayList<>();

        //Pode dar erro (encriptacao)
        String sql = "select * from Carros where loja_id = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta
                Long carId = resultSet.getLong("id");
                Long ano = resultSet.getLong("ano");
                Long quilometragem = resultSet.getLong("quilometragem");
                String placa = resultSet.getString("placa");
                String modelo = resultSet.getString("modelo");
                String chassi = resultSet.getString("chassi");
                String descricao = resultSet.getString("descricao");
                Float valor = resultSet.getFloat("valor");

                ArrayList<String> imagens = getCarImages(carId);

                myStore.add(new Carros(carId, id, ano, quilometragem, placa, modelo, chassi, descricao, valor, imagens));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
        }
        return myStore;
    }

    public ArrayList<String> getCarImages(Long id){
        ArrayList<String> myImages = new ArrayList<>();

        //Pode dar erro (encriptacao)
        String sql = "select * from Imagens where carro_id = ?";

        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta

                String endereco = resultSet.getString("endereco");

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
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, type ? clienteId : lojaId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){ // se tiver algo resultado da consulta
                Long id = resultSet.getLong("id");
                Float valor = resultSet.getFloat("valor");
                String condicoes = resultSet.getString("condicoes");
                String estado = resultSet.getString("estado");

                myOffer.add(new Proposta(id, valor, condicoes, estado));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
           //tratar erros preguicinha aqui é de Query
        }
        return myOffer;
    }



	// public static void main(String[] args) {
	// 	try {

	// 		ResultSet rs = stmt.executeQuery("select * from Livro");
	// 		while (rs.next()) {
	// 			System.out.print(rs.getString("Titulo"));
	// 			System.out.print(", " + rs.getString("Autor"));
	// 			System.out.print(", " + rs.getInt("Ano"));
	// 			System.out.println(" (R$ " + rs.getFloat("Preco") + ")");
	// 		}
	// 		stmt.close();
	// 		con.close();
	// 	} catch (ClassNotFoundException e) {
	// 		System.out.println("A classe do driver de conexão não foi encontrada!");
	// 	} catch (SQLException e) {
	// 		System.out.println(e);
	// 		System.out.println("O comando SQL não pode ser executado!");
	// 	}
	// }
}
