package br.edu.ifsul.primeiraapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.adapter.CestaAdapter;
import br.edu.ifsul.primeiraapp.model.ItemPedido;
import br.edu.ifsul.primeiraapp.model.Pedido;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;


public class CestaActivity extends AppCompatActivity {

    public ListView lvCesta;
    private DatabaseReference myRef;
    private static final String TAG = "cestaActivity" ; //string TAG para localizar no Logcat caso de algum erro

    private TextView tvTotalCesta, tvNomeClienteCesta, tvNomeVendedorCesta;
    private Double totalPedido = new Double(0);
    private List<ItemPedido> itens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);

        lvCesta = findViewById(R.id.lvListaCesta);
        tvTotalCesta = findViewById(R.id.tvTotalCesta);
        tvNomeClienteCesta = findViewById(R.id.tvNomeClienteCesta);
        tvNomeVendedorCesta = findViewById(R.id.tvNomeVendedorCesta);
        Log.d(TAG, "vendedor: " + AppSetup.vendedor.getEmail());
        tvNomeVendedorCesta.setText(AppSetup.vendedor.getEmail());

        tvNomeClienteCesta.setText(AppSetup.cliente.getNome());




                //trata o click longo no cartão da Listview
        lvCesta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialogExcluirItem("Atenção", "Você realmente deseja excluir este item?", position);

                return true;
            }
        });


        /* parei aqui 16/11/2018- após inserir o itens nesta classe... preciso comparar o itens do código do Vagner com esse*/

        //trata o click curto no cartão da Listview
        /*lvCesta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CestaActivity.this, DetalheProdutoActivity.class);
                intent.putExtra("produto", AppSetup.cesta.get(position).getProduto());
                startActivity(intent);
                AppSetup.cesta.remove(position);
                finish();
            }
        });*/

        //trata o click curto no cartão da Listview
        lvCesta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //manda a position do produto na lista de produtos da app
                int cont = 0;
                for(Produto produto : AppSetup.produtos){
                    if(!AppSetup.cesta.isEmpty()) {
                        if (itens.get(position).getProduto().getKey().equals(produto.getKey())) {
                            Log.d(TAG, "Objeto clicado: " + AppSetup.produtos.get(cont));
                            Intent intent = new Intent(CestaActivity.this, DetalheProdutoActivity.class);
                            intent.putExtra("position", cont);
                            startActivity(intent);
                            atualizaEstoque(position);
                            AppSetup.cesta.remove(position);
                            Toast.makeText(CestaActivity.this, R.string.toast_produto_removido, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        cont++;
                    }
                }

            }
        });






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_cesta , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuitem_salvar:{
                alertDialogSalvarPedido("Quase lá", "\nTotal do Pedido = " + NumberFormat.getCurrencyInstance().format(totalPedido) + " . Confirmar?");
                break;
            }
            case R.id.menuitem_cancelar:{
                alertDialogCancelarPedido("Ops! Vai cancelar?", "Tem certeza que quer cancelar este pedido?");
                break;
            }
        }

        return true;
    }

    private void atualizaEstoque(final int position) {
        //atualiza estoque no Firebase (Essa atualização é temporária, ao efetivar o pedido isso deverá ser validado.)
        final DatabaseReference myRef = AppSetup.getInstance().child("produtos").child(AppSetup.cesta.get(position).getProduto().getKey()).child("quantidade");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //busca a posição de estoque atual
                long quantidade = (long) dataSnapshot.getValue();
                //atualiza o estoque
                myRef.setValue(itens.get(position).getQuantidadePedido() +quantidade);
                atualizarView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //*
    // Falta atualizar o banco quando exclui o item do carrinho e qdo cancela a compra
    //
    // */

    private void alertDialogSalvarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseReference myRef = AppSetup.getInstance();
                //prepara o pedido para salvá-lo
                Pedido pedido = new Pedido();
                pedido.setFormaPagamento("dinheiro");
                pedido.setEstadoPedido("aberto");
                pedido.setDataCriacao(Calendar.getInstance().getTime());
                pedido.setDataModificacaoPedido(Calendar.getInstance().getTime());
                pedido.setTotalPedido(totalPedido);
                pedido.setSituacaoPedido(true);
                pedido.setItensPedido(AppSetup.cesta);
                pedido.setCliente(AppSetup.cliente);

                //salva o pedido no database
                myRef.child("pedidos").push().setValue(pedido);
                //limpa o setup

                Toast.makeText(CestaActivity.this, "Ótimo! Vendido.", Toast.LENGTH_SHORT).show();
                /*
                 *  persistir a o pedido no Firebase aqui!!!!!!!!!!!! Lembrar de atualizar o estoque
                 *  e controlar as exceções.
                 */
                AppSetup.cesta.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.Não, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(CestaActivity.this, "Ótimo. Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }



    private void alertDialogCancelarPedido(String titulo, String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < itens.size(); i++) {
                    atualizaEstoque(i);
                }
                Toast.makeText(CestaActivity.this, "Que pena! Pedido cancelado.", Toast.LENGTH_SHORT).show();
                AppSetup.cesta.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.Não, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Ótimo! Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }


    private void alertDialogExcluirItem(String titulo, String mensagem, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //add the title and text
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        //add the buttons
        builder.setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppSetup.cesta.remove(position); //remove do carrinho
                Toast.makeText(CestaActivity.this, "Produto removido.", Toast.LENGTH_SHORT).show();
                atualizarView();
            }
        });
        builder.setNegativeButton(R.string.Não, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CestaActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    /*/@Override esse era o meu antes de criar a venda e atualizar o estoque
    protected void onResume() {

        super.onResume();
        atualizarView();

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if(!AppSetup.cesta.isEmpty()){
            atualizarView();
        }
        //faz uma cópia do carrinho para usar na atualização do estoque
        itens = new ArrayList<>();
        itens.addAll(AppSetup.cesta);
    }




    private void atualizarView() {
        lvCesta.setAdapter(new CestaAdapter(CestaActivity.this, AppSetup.cesta));
        //totaliza o pedido
        totalPedido = new Double(0);
        for(ItemPedido itemPedido : AppSetup.cesta){
            totalPedido += itemPedido.getTotalItemPedido();
            Log.d(TAG, "total pedido" + totalPedido);
        }
        tvTotalCesta.setText(NumberFormat.getCurrencyInstance().format(totalPedido));



    }


}
