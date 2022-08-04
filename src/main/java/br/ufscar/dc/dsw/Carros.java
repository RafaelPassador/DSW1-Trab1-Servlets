package br.ufscar.dc.dsw;

import java.util.ArrayList;

public class Carros {
    private Long loja_id, ano, quilometragem;
    private String placa, modelo, chassi, descricao, cnpj;
    private float valor;
    private ArrayList<String> imagens;

    public Carros(Long loja_id, Long ano, Long quilometragem, String placa, String modelo, String chassi, String descricao, float valor, ArrayList<String> imagens,String cnpj){
        this.chassi = chassi;
        this.descricao = descricao;
        this.imagens = imagens;
        this.modelo = modelo;
        this.placa = placa;
        this.valor = valor;
        this.ano = ano;
        this.loja_id = loja_id;
        this.quilometragem = quilometragem;
        this.cnpj = cnpj;
    }

    public String getChassi() {
        return chassi;
    }
    public String getCnpj() {
        return cnpj;
    }
    public Long getAno() {
        return ano;
    }
    public Long getLoja_id() {
        return loja_id;
    }
    public Long getQuilometragem() {
        return quilometragem;
    }
    public String getDescricao() {
        return descricao;
    }
    public ArrayList<String> getImagens() {
        return imagens;
    }
    public String getModelo() {
        return modelo;
    }
    public String getPlaca() {
        return placa;
    }
    public float getValor() {
        return valor;
    }
    public void setChassi(String chassi) {
        this.chassi = chassi;
    }
    public void setAno(Long ano) {
        this.ano = ano;
    }
    public void setLoja_id(Long loja_id) {
        this.loja_id = loja_id;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean addImage(String image) {
        if (imagens.size() >= 10)
        return false;

        imagens.add(image);
        return true;
    }
}
