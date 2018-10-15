package br.edu.ifsul.primeiraapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.text.NumberFormat;
import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.adapter.CestaAdapter;
import br.edu.ifsul.primeiraapp.model.ItemPedido;
import br.edu.ifsul.primeiraapp.setup.AppSetup;


public class CestaActivity extends AppCompatActivity {

    public ListView lvCesta;
    private DatabaseReference myRef;
    private static final String TAG = "cestaActivity" ; //string TAG para localizar no Logcat caso de algum erro

    private TextView tvTotalCesta, tvNomeClienteCesta, tvNomeVendedorCesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);

        lvCesta = findViewById(R.id.lvListaCesta);
        tvTotalCesta = findViewById(R.id.tvTotalCesta);
        tvNomeClienteCesta = findViewById(R.id.tvNomeClienteCesta);
        tvNomeVendedorCesta = findViewById(R.id.tvNomeVendedorCesta);




    }

    @Override
    protected void onResume() {
        super.onResume();
     lvCesta.setAdapter(new CestaAdapter(this, AppSetup.cesta));
     Double acumulador= new Double(0);
     for (ItemPedido item : AppSetup.cesta) {
         acumulador += item.getTotalItemPedido();
        }

        //tvTotalCesta.setText(acumulador.toString());
        tvTotalCesta.setText(NumberFormat.getCurrencyInstance().format(acumulador));
        tvNomeClienteCesta.setText(AppSetup.cliente.getNome()+" "+AppSetup.cliente.getSobrenome());
        tvNomeVendedorCesta.setText(AppSetup.vendedor.getEmail());

    }




}
