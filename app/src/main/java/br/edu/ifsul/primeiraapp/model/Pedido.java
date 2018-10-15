package br.edu.ifsul.primeiraapp.model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private Long totalPedido;
    private Long idPedido;
    private String estadoPedido;
    private Date dataCriacao;
    private boolean situacaoPedido;
    private Date dataModificacaoPedido;
    private String formaPagamento;
    private List<ItemPedido> itensPedido;// crio um objeto ItemPedido para criar a relação Pedido com ItemPedido
    //depois preciso gerar um construtor para o objeto ItemPedido (click direito/generator/constructor)
    // depois tem q criar um toString, no final da classe, para converter
    private Cliente cliente;

    public Pedido(Long totalPedido, Long idPedido, String estadoPedido, Date dataCriacao, boolean situacaoPedido, Date dataModificacaoPedido, String formaPagamento, List<ItemPedido> itensPedido, Cliente cliente) {
        this.totalPedido = totalPedido;
        this.idPedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.dataCriacao = dataCriacao;
        this.situacaoPedido = situacaoPedido;
        this.dataModificacaoPedido = dataModificacaoPedido;
        this.formaPagamento = formaPagamento;
        this.itensPedido = itensPedido;
        this.cliente = cliente;
    }

    public Pedido(){} //construtor vazio p o android

    public Pedido(Long totalPedido, Long idPedido, String estadoPedido, Date dataCriacao, boolean situacaoPedido, Date dataModificacaoPedido, String formaPagamento, List <ItemPedido> itensPedido) {
        this.totalPedido = totalPedido;
        this.idPedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.dataCriacao = dataCriacao;
        this.situacaoPedido = situacaoPedido;
        this.dataModificacaoPedido = dataModificacaoPedido;
        this.formaPagamento = formaPagamento;
        this.itensPedido = itensPedido; // cria a composição
    }

    //getters e seters
    public Long getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Long totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isSituacaoPedido() {
        return situacaoPedido;
    }

    public void setSituacaoPedido(boolean situacaoPedido) {
        this.situacaoPedido = situacaoPedido;
    }

    public Date getDataModificacaoPedido() {
        return dataModificacaoPedido;
    }

    public void setDataModificacaoPedido(Date dataModificacaoPedido) {
        this.dataModificacaoPedido = dataModificacaoPedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // criação do toString


    @Override
    public String toString() {
        return "Pedido{" +
                "totalPedido=" + totalPedido +
                ", idPedido=" + idPedido +
                ", estadoPedido='" + estadoPedido + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", situacaoPedido=" + situacaoPedido +
                ", dataModificacaoPedido=" + dataModificacaoPedido +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", itensPedido=" + itensPedido +
                ", cliente=" + cliente +
                '}';
    }
}
