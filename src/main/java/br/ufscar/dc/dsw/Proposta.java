package br.ufscar.dc.dsw;

public class Proposta {
    private Long id;
    private float valor;
    private String condicoes, estado;

    public Proposta(Long id, float valor, String condicoes, String estado){
        this.condicoes = condicoes;
        this.estado = estado;
        this.valor = valor;
        this.id = id;
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
