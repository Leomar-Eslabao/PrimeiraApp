package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.ItemPedido;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class DetalheProdutoActivity extends AppCompatActivity {

    private static final String TAG = "detalheProdutoActivity";
    private Produto produto;
    private EditText etQuantidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //myRef.setValue("Hello, World!");
        //Log.d(TAG, "Conectou com o banco" + myRef.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        etQuantidade = findViewById(R.id.etQuantidade);


         produto = new Produto();
         //código para criar um objeto no BD
         /*produto.setCodigoDeBarras(2L);//código de barras do produto
         produto.setNome("mouse");
         produto.setDescricao("mouse usb");
         produto.setValor(35.00);
         produto.setQuantidade(new Integer ( 100));//como estou usando o Double posso criar um construtor para informar o valor
         myRef.child("produtos").child(String.valueOf(produto.getCodigoDeBarras())).setValue(produto);*/

        produto = (Produto) getIntent().getSerializableExtra("produto"); //cria um novo produto com os dados que recebeu da intent (vindo do ProdutosActivity

        atualizarView();





        Button btComprar = findViewById(R.id.btComprarProduto);
        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    //DÚVIDA: QUANDO CLICO NO COMPRAR PARA ONDE DEVO MANDAR OS DADOS DO PRODUTO COMPRADO,
                    // PARA QUE POSSA SER INSERIDO NA CESTA?  VAI PARA O ITEMPEDIDO? preciso dar um start activity para a cestaactivity
                   if (AppSetup.cliente==null){
                       Toast.makeText(DetalheProdutoActivity.this, "Selecione o cliente.", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(DetalheProdutoActivity.this, ClientesActivity.class));//
                   }
                   else {
                       if (!etQuantidade.getText().toString().isEmpty()) {
                           ItemPedido item = new ItemPedido(Integer.valueOf(etQuantidade.getText().toString()), produto);
                           AppSetup.cesta.add(item);
                           startActivity(new Intent(DetalheProdutoActivity.this, CestaActivity.class));

                           // Toast.makeText(DetalheProdutoActivity.this, "Parabéns, você comprou:" + produto.getQuantidade().toString(), Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(DetalheProdutoActivity.this, "Digite a quantidade a ser comprada", Toast.LENGTH_SHORT).show();
                       }

                   }
            }
        });


    }

    private void atualizarView() {
        TextView tvNome = findViewById(R.id.tvNomeProdutoAdapter);
        tvNome.setText(produto.getNome());
        TextView tvDescricao= findViewById(R.id.tvDescricaoProduto);
        tvDescricao.setText (produto.getDescricao());
        TextView tvValor = findViewById(R.id.tvValorProduto);
        tvValor.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        //tvValor.setText(produto.getValor().toString());
        TextView tvQuantidade = findViewById(R.id.tvQuantidadeProduto);
        tvQuantidade.setText(produto.getQuantidade().toString());
    }


}
