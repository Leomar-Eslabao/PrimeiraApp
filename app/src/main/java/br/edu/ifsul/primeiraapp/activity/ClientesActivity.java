package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import br.edu.ifsul.primeiraapp.adapter.ClienteAdapter;
import br.edu.ifsul.primeiraapp.adapter.ProdutosAdapter;
import br.edu.ifsul.primeiraapp.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.Produto;
import br.edu.ifsul.primeiraapp.setup.AppSetup;

public class ClientesActivity extends AppCompatActivity {

    private static final String TAG = "clientesActivity" ;
    private static final int RC_BARCODE_CAPTURE = 0; //final define q a variável é uma constante
    private List<Cliente> clientes;
    public ListView lvClientes;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        lvClientes = findViewById(R.id.lvActivityClientes);
        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"Objeto Clicado" + clientes.get(position));
               // Toast.makeText(ClientesActivity.this, clientes.get(position).toString(), Toast.LENGTH_SHORT).show();// Toast para verificar se o objeto foi criado e o seu valor
                //
                Intent intent = new Intent(ClientesActivity.this, DetalheClienteActivity.class);
                intent.putExtra("cliente",clientes.get(position));
                startActivity(intent);
                finish();//não coloca esta ativit na lista de activits
            }
        });
        clientes=new ArrayList<>();
        // Read from the database

        myRef = AppSetup.getInstance();
        myRef.child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<Cliente>> type = new GenericTypeIndicator<List<Cliente>>() {
                };
                clientes = dataSnapshot.getValue(type);
                clientes.remove(null);
                Log.d(TAG, "Value is: " + clientes);
                atualizarView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_clientes, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint("Digite o nome do Cliente");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Cliente> clientesFilter = new ArrayList<>();

                for (Cliente cliente : clientes) {
                    if (cliente.getNome().contains(newText)) {
                        clientesFilter.add(cliente);
                    }
                }
                lvClientes.setAdapter(new ClienteAdapter(ClientesActivity.this, clientesFilter));
                return true;
            }
        });

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
                    for (Cliente cliente : clientes){
                        if(String.valueOf(cliente.getCodigoDeBarras()).equals(barcode.displayValue)){
                            flag = false;
                            Intent intent = new Intent(ClientesActivity.this, DetalheClienteActivity.class);
                            intent.putExtra("cliente", cliente);
                            startActivity(intent);
                            break;
                        }
                    }
                    if(flag){
                        Toast.makeText(this, "Cliente não cadastrado.", Toast.LENGTH_SHORT).show();
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




    private void atualizarView(){
           lvClientes.setAdapter(new ClienteAdapter(this,clientes));
    }
}


