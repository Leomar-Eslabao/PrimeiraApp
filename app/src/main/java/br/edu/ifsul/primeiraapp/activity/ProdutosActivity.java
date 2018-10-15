package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
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
import br.edu.ifsul.primeiraapp.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity {

    private static final String TAG = "produtosActivity" ;
    private static final int RC_BARCODE_CAPTURE = 0; //final define q a variável é uma constante
    private List<Produto> produtos;
    public ListView lvProdutos;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);


      lvProdutos = findViewById(R.id.lvProdutos);
      lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Log.d(TAG,"Objeto Clicado" + produtos.get(position));
             // Toast.makeText(ProdutosActivity.this, "clioou no indice = " + position, Toast.LENGTH_SHORT).show();
             // Toast.makeText(ProdutosActivity.this, produtos.get(position).toString(), Toast.LENGTH_SHORT).show();// Toast para verificar se o objeto foi criado e o seu valor
              Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);//define o controlador q vai chamar
              intent.putExtra("produto",produtos.get(position));//pega o produto da posição position e coloca na intent com o nome "produto"
              startActivity(intent); //inicia a DetalaheProdutoActivity e passada pela a intent
          }
      });
        produtos= new ArrayList<>();
        /*Produto produto = new Produto();
        produto.setNome("Meu produto");
        produto.setQuantidade(100);
        produtos.add(produto);
        lvProdutos.setAdapter(new ProdutosAdapter(this, produtos));*/

        // Write a message to the database

       myRef = AppSetup.getInstance().child("produtos");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_produtos, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do produto");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //uma lista para nova camada da RecyclerView
                List<Produto> produtosFilter = new ArrayList<>();

                //um forEatch na camada de modelo atual
                for(Produto produto:produtos){
                    //se o nome do produto começa com o texto digitado
                    if(produto.getNome().contains(newText)){
                        //adiciona o produto na nova lista
                        produtosFilter.add(produto);
                    }
                }
                //coloca a nova lista como fonte de dados do novo adaptador da RecyclerView
                //(Context, fonte de dados)
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this,produtosFilter));
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_barcode:{
                Intent intent = new Intent(this,BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus,true); //true liga a funcionalidade autofocus
                intent.putExtra(BarcodeCaptureActivity.UseFlash,false); //true liga a lanterna
                startActivityForResult(intent,RC_BARCODE_CAPTURE);//STAT A ACTIVITY PASSANDO RC_BARCODE_CAPTURE
                break;
            }
        }
        return true;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    //localiza o produto na lista (ou não)
                    boolean flag = true;
                    for (Produto produto : produtos){
                        if(String.valueOf(produto.getCodigoDeBarras()).equals(barcode.displayValue)){
                            flag = false;
                            Intent intent = new Intent(ProdutosActivity.this, DetalheProdutoActivity.class);
                            intent.putExtra("produto", produto);
                            startActivity(intent);
                            break;
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "Produto não cadastrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this, String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void atualizarView() {
        lvProdutos.setAdapter(new ProdutosAdapter(this,produtos));
    }
}
