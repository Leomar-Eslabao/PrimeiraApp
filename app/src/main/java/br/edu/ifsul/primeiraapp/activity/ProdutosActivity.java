package br.edu.ifsul.primeiraapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.adapter.ProdutosAdapter;
import br.edu.ifsul.primeiraapp.model.Produto;

public class ProdutosActivity extends AppCompatActivity {

    private static final String TAG = "produtosActivity" ;
    private List<Produto> produtos;
    public ListView lvProdutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);


      lvProdutos = findViewById(R.id.lvProdutos);
        produtos= new ArrayList<>();
        /*Produto produto = new Produto();
        produto.setNome("Meu produto");
        produto.setQuantidade(100);
        produtos.add(produto);
        lvProdutos.setAdapter(new ProdutosAdapter(this, produtos));*/

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas").child("produtos");

        // Read from the database

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Produto>> type =new GenericTypeIndicator<List<Produto>>() {};
                produtos = dataSnapshot.getValue(type);
                produtos.remove(null);
                Log.d(TAG, "Value is: " + produtos);
                atualizarView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void atualizarView() {
        lvProdutos.setAdapter(new ProdutosAdapter(this,produtos));
    }
}
