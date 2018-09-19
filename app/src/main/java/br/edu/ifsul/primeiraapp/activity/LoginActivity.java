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
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity" ;
    //gero na mão o ---  private static final String TAG = "detalheLoginActivity";

    //private Login login1;

    //onCreat  // qual dos onCreate uso?  pq ele gerou um @Nullable junto do comando e acrescentou mais uma linha no import com esse nullable


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        final EditText etmail = findViewById(R.id.etEmail);
        final EditText etsenha = findViewById(R.id.etSenha);
        Button btLogin = findViewById(R.id.btLogin);
        Button btFazerCadastroLogin = findViewById(R.id.btFazerCadastroLogin);



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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");


                            AppSetup.user = mAuth.getCurrentUser();



                            //updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Usuário logado com sucesso.",
                                    Toast.LENGTH_SHORT).show();
                            AppSetup.user = mAuth.getCurrentUser();
                            //startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
                            startActivity(new Intent(LoginActivity.this, ClientesActivity.class));
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
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
