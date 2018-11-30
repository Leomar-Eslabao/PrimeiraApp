

package br.edu.ifsul.primeiraapp.activity;

        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;

        import br.edu.ifsul.primeiraapp.R;
        import br.edu.ifsul.primeiraapp.model.Cliente;
        import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ClienteAdminActivity extends AppCompatActivity {

    private TextView tvCodigoDeBarras;
    private ImageView imvFoto;
    private EditText etNome, etSobrenome, etCPF;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_admin);

        //mapeia os componentes da UI
        tvCodigoDeBarras = findViewById(R.id.tvCodigoDeBarrasClienteTelaAdmin);
        imvFoto = findViewById(R.id.imvFotoClienteTelaAdmin);
        etNome = findViewById(R.id.etNomeClienteTelaAdmin);
        etSobrenome = findViewById(R.id.etSobrenomeClienteTelaAdmin);
        etCPF = findViewById(R.id.etCPFClienteTelaAdmin);
        btSalvar = findViewById(R.id.btSalvarClienteTelaAdmin);

        //trata evento onClick do botão
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNome.getText().toString().isEmpty()
                        || etSobrenome.getText().toString().isEmpty()
                        || etCPF.getText().toString().isEmpty()){
                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_todos_campos_devem_preenchidos ,Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference  myRef = AppSetup.getInstance().child("clientes");
                    Cliente cliente = new Cliente();
                    //pega os dados da tela e os coloca no objeto de modelo
                    cliente.setNome(etNome.getText().toString());
                    cliente.setSobrenome(etSobrenome.getText().toString());
                    cliente.setCpf(etCPF.getText().toString());
                    cliente.setSituacao(true);
                    cliente.setCodigoDeBarras(0L);
                    myRef.push().setValue(cliente)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override

                                /* ARRUMAR OS TOASTS*/
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_otimo_cadastrado, Toast.LENGTH_SHORT).show();
                                    limparTela();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ClienteAdminActivity.this, R.string.toast_operacao_falhou, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void limparTela() {
        etNome.setText(null);
        etSobrenome.setText(null);
        etCPF.setText(null);
    }
}
