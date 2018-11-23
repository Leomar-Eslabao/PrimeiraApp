package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifsul.primeiraapp.setup.AppSetup;

import static br.edu.ifsul.primeiraapp.R.*;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity" ;
    //gero na mão o ---  private static final String TAG = "detalheLoginActivity";

    //private Login login1;

    //onCreat  // qual dos onCreate uso?  pq ele gerou um @Nullable junto do comando e acrescentou mais uma linha no import com esse nullable


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        final EditText etmail = findViewById(id.etEmail);
        final EditText etsenha = findViewById(id.etSenha);
        Button btLogin = findViewById(id.btLogin);
        Button btFazerCadastroLogin = findViewById(id.btFazerCadastroLogin);



        btFazerCadastroLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(etmail.getText().toString(),etsenha.getText().toString());

            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn (etmail.getText().toString(), etsenha.getText().toString());
            }
        });


    }


    private void signUp (String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in vendedor's information
                            Log.d(TAG, "createUserWithEmail:success");


                            AppSetup.vendedor = mAuth.getCurrentUser();



                            //updateUI(vendedor);

                        } else {
                            // If sign in fails, display a message to the vendedor.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void  signIn(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in vendedor's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Usuário logado com sucesso.",
                                    Toast.LENGTH_SHORT).show();
                            AppSetup.vendedor = mAuth.getCurrentUser();
                            Log.d(TAG, "Vendedor logado: " +  AppSetup.vendedor.getEmail());
                            //startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
                            startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
                            // updateUI(vendedor);
                            finish(); //não salva a activity anterior na lista de BACKSTACK para retornar quando  activity é fechada.
                        } else {
                            // If sign in fails, display a message to the vendedor.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });


    }
}
