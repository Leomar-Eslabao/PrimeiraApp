package br.edu.ifsul.primeiraapp.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private long id;
    private String nome;
    private String descricao;
    private  Double valor;
    private  Integer quantidade;
    private boolean situacao;
    private String url_foto;

    public Produto(){} // tem que criar esse construtor porque o firebase precisa dele para fazer o envio de dados

    public long getId() {
        return id;
    }

    public void setId(long codigoDeBarras) {
        this.id = codigoDeBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigoDeBarras=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", situacao=" + situacao +
                ", url_foto='" + url_foto + '\'' +
                '}';
    }
}
