package br.edu.ifsul.primeiraapp.model;

public class ItemPedido {
    private Integer quantidadePedido;
    private Double totalItemPedido;
    private  boolean situacaoItemPedido = true;// guarda a situação do item do pedido, pois o cliente pode cancelar o item

    private Produto produto;

    public ItemPedido(){}

    public ItemPedido(Integer quantidadePedido, Produto produto) {
        this.quantidadePedido = quantidadePedido;
        this.totalItemPedido = quantidadePedido * produto.getValor();
        this.produto = produto;
    }

    public Integer getQuantidadePedido() {
        return quantidadePedido;
    }

    public void setQuantidadePedido(Integer quantidadePedido) {
        this.quantidadePedido = quantidadePedido;
    }

    public Double getTotalItemPedido() {
        return totalItemPedido;
    }

    public void setTotalItemPedido(Double totalItemPedido) {
        this.totalItemPedido = totalItemPedido;
    }

    public boolean isSituacaoItemPedido() {
        return situacaoItemPedido;
    }

    public void setSituacaoItemPedido(boolean situacaoItemPedido) {
        this.situacaoItemPedido = situacaoItemPedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "quantidadePedido=" + quantidadePedido +
                ", totalItemPedido=" + totalItemPedido +
                ", situacaoItemPedido=" + situacaoItemPedido +
                ", produto=" + produto +
                '}';
    }
}
