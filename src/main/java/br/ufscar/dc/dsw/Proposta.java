package br.ufscar.dc.dsw;

import java.sql.Date;

public class Proposta {
    private Long id;
    private float valor;
    private String condicoes, estado, contraproposta;
    private Date data_proposta;

    public Proposta(Long id, float valor, String condicoes, String estado, String contraproposta, Date data_proposta){
        this.condicoes = condicoes;
        this.estado = estado;
        this.valor = valor;
        this.id = id;
        this.contraproposta = contraproposta;
        this.data_proposta = data_proposta;
    }

    public String getCondicoes() {
        return condicoes;
    }
    public String getEstado() {
        return estado;
    }
    public float getValor() {
        return valor;
    }
    public Long getId() {
        return id;
    }
    public String getContraproposta() {
        return contraproposta;
    }
    public Date getData_proposta() {
        return data_proposta;
    }
    public void setContraproposta(String contraproposta) {
        this.contraproposta = contraproposta;
    }
    public void setData_proposta(Date data_proposta) {
        this.data_proposta = data_proposta;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setCondicoes(String condicoes) {
        this.condicoes = condicoes;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }

    
}
