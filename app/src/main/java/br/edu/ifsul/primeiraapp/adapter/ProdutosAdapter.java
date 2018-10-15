package br.edu.ifsul.primeiraapp.adapter;

import android.content.Context;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Produto;

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    //
    private Context context;
    private List<Produto> produtos;
    public ProdutosAdapter(@NonNull Context context, @NonNull List<Produto> produtos) {
        super(context, 0 , produtos);
        this.produtos = produtos;
        this.context=context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Produto produto = produtos.get(position);

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_produtos_adapter, parent, false);
        }

        TextView tvNomeProduto = convertView.findViewById(R.id.tvNomeProdutoAdapter);
        tvNomeProduto.setText(produto.getNome());
        TextView tvEstoque = convertView.findViewById(R.id.tvEstoqueProdutoAdapter);
        tvEstoque.setText(produto.getQuantidade().toString());
        TextView tvPrecoProduto = convertView.findViewById(R.id.tvPrecoProdutoAdapter);
       // tvPrecoProduto.setText(produto.getValor().toString());
        tvPrecoProduto.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        ImageView fotoProduto = convertView.findViewById(R.id.imvFotoProdutoAdapter);
        if(produto.getUrl_foto()!=null){
           fotoProduto.setImageURI(Uri.parse(produto.getUrl_foto()));
        }else{
            fotoProduto.setImageResource(R.mipmap.ic_launcher_round);
        }
        //tvEstoqueProdutoAdapter

        return convertView;
    }
}
