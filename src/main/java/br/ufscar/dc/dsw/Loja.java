package br.ufscar.dc.dsw;

import java.util.ArrayList;

public class Loja {
    private Long id;
    private String email, senha, cnpj, nome, descricao;
    private ArrayList<Carros> carrosLoja;
    private ArrayList<Proposta> propostas;

    public Loja(Long id, String email, String senha, String cnpj, String nome, String descricao, ArrayList<Carros> carrosLoja, ArrayList<Proposta> propostas){
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.senha = senha;
        this.email = email;
        this.nome = nome;
        this.id = id;
        this.carrosLoja = carrosLoja;
        this.propostas = propostas;
    }
    public void teste(Proposta a){
        propostas.add(a);
    }

    public ArrayList<Carros> getCarrosLoja() {
        return carrosLoja;
    }
    public Long getId() {
        return id;
    }
    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }
    public String getCnpj() {
        return cnpj;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getEmail() {
        return email;
    }
    public String getNome() {
        return nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setPropostas(ArrayList<Proposta> propostas) {
        this.propostas = propostas;
    }

}
