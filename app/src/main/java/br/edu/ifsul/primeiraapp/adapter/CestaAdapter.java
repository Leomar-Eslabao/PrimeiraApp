package br.edu.ifsul.primeiraapp.adapter;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.activity.CestaActivity;

import br.edu.ifsul.primeiraapp.model.ItemPedido;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class CestaAdapter extends ArrayAdapter<ItemPedido> {
    private static final String TAG = "CestaAdapter";
    private Context context;
    private List<ItemPedido> itensPedido;


    public CestaAdapter(@NonNull Context context, @NonNull List<ItemPedido> itens) {
        super(context, 0, itens);

        this.context = context;
        this.itensPedido = itens;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ItemPedido itens = itensPedido.get(position); //cria  um objeto itens e atribui a posição do itensPedido

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cesta_adapter, parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.ivProdutoCesta);
            if (itens.getProduto().getUrl_foto()!=null) {//se existir a imagem do produto exibe na view
                //não sei como carregar a imagem do BD


            }
            else{
                imageView.setImageResource(R.drawable.carrinho_de_compras);//caso não tenha imagem do produto carrega uma imagem padrão
            }

        TextView tvNomeProdutoCesta = convertView.findViewById(R.id.tvNomeProdutoCesta);
        tvNomeProdutoCesta.setText(itens.getProduto().getNome());

      //  tvTotalCesta.setText(NumberFormat.getCurrencyInstance().format(acumulador));

        TextView tvTotalItemCesta = convertView.findViewById(R.id.tvTotalItemCesta);
        //tvTotalItemCesta.setText(itens.getTotalItemPedido().toString());// pega o valor total de itempedido -- sem colocar a máscara para moeda regional
        tvTotalItemCesta.setText(NumberFormat.getCurrencyInstance().format(itens.getTotalItemPedido()));

        TextView quantidadeCesta = convertView.findViewById(R.id.tvQuantidadeProdutoCesta);
        quantidadeCesta.setText(itens.getQuantidadePedido().toString());


        return convertView; // retorna o objeto convertView q foi inflado
    }
}
