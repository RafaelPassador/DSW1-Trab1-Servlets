package br.ufscar.dc.dsw;

import java.sql.Date;
import java.util.ArrayList;

public class Cliente {
    private Long id;
    private String email, pass, cpf, nome, telefone, sexo;
    private Date nascimento;
    private ArrayList<Proposta> propostas;

    public Cliente(Long id, String email, String pass, String cpf, String nome, String telefone, String sexo, Date nascimento, ArrayList<Proposta> propostas) {
        this.cpf = cpf;
        this.email = email;
        this.id = id;
        this.nascimento = nascimento;
        this.nome = nome;
        this.pass = pass;
        this.sexo = sexo;
        this.telefone = telefone;
        this.propostas = propostas;
    }

    
    public ArrayList<Proposta> getPropostas() {
        return propostas;
    }
    public String getCpf() {
        return cpf;
    }
    public Date getNascimento() {
        return nascimento;
    }
    public String getNome() {
        return nome;
    }
    public String getSexo() {
        return sexo;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }
    public Long getId() {
        return id;
    }
    public String getPass() {
        return pass;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
