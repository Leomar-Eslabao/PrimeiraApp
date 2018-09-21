package br.edu.ifsul.primeiraapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Cliente;

public class DetalheClienteActivity extends AppCompatActivity {

    private static final String TAG = "detalheClienteActivity";
    private Cliente cliente1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // cria a conexao com o banco --- tem que importar a classe alt+enter
        DatabaseReference myRef = database.getReference("clientes");

        //myRef.setValue("Hello, World!");
        Log.d(TAG, "Conectou com o banco" + myRef.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        cliente1 = new Cliente();
       /* cliente1.setNome("João da Silva");
        cliente1.setSituacao(true);*/
        cliente1 = (Cliente) getIntent().getSerializableExtra("cliente");

            atualizarView();



        Button btAlterar = findViewById(R.id.btAlterar);
        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DetalheProdutoActivity.this, "Parabéns, você clicou em alterar:" + produto.getQuantidade().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DetalheClienteActivity.this, "Parabéns, você clicou em alterar", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void atualizarView() {
        TextView tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvNomeUsuario.setText(cliente1.getNome());
        TextView tvSituacao = findViewById(R.id.tvSituacao);
        if (cliente1.getSituacao()){
            tvSituacao.setText("Ativo");
        }else{
            tvSituacao.setText("Inativo");
        }

    }
}
