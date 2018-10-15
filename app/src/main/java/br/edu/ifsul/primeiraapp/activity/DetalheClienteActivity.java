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
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class DetalheClienteActivity extends AppCompatActivity {

    private static final String TAG = "detalheClienteActivity";
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // cria a conexao com o banco --- tem que importar a classe alt+enter
        DatabaseReference myRef = database.getReference("clientes");

        //myRef.setValue("Hello, World!");
        Log.d(TAG, "Conectou com o banco" + myRef.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

            atualizarView();



        Button btAlterar = findViewById(R.id.btAlterar);
        btAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cliente!=null){
                    AppSetup.cliente=cliente;
                    Toast.makeText(DetalheClienteActivity.this, "Cliente Selecionado", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(DetalheClienteActivity.this, "Não foi possível selecionar o cliente", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private void atualizarView() {
        TextView tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvNomeUsuario.setText(cliente.getNome());
        TextView tvSituacao = findViewById(R.id.tvSituacao);
        if (cliente.getSituacao()){
            tvSituacao.setText("Ativo");
        }else{
            tvSituacao.setText("Inativo");
        }

    }
}
