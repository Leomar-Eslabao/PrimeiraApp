package br.edu.ifsul.primeiraapp.model;

import java.io.Serializable;

public class Cliente implements Serializable{
    private Long codigoDeBarras;
    private Long cpf;
    private String nome;
    private String sobrenome;
    private Boolean situacao;
    private String url_cliente;


    public Cliente() {
    }//tem que criar esse construtor porque o firebase precisa dele... usa a ferramenta para gerar automáticamente.

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public String getUrl_cliente() {
        return url_cliente;
    }

    public void setUrl_cliente(String url_cliente) {
        this.url_cliente = url_cliente;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", situacao=" + situacao +
                ", url_cliente='" + url_cliente + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}
