package br.edu.ifsul.primeiraapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.primeiraapp.R;
import br.edu.ifsul.primeiraapp.model.Cliente;

public class ClienteAdapter extends ArrayAdapter<Cliente> {
    private static final String TAG = "ClienteAdapter" ;
    private Context context;
    private List<Cliente> clientes;
    public ClienteAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0 , clientes);
        this.clientes = clientes;
        this.context=context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Cliente cliente = clientes.get(position);
        Log.d(TAG,cliente.toString());

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_clientes_adapter, parent, false);
        }

        TextView tvNomeCliente = convertView.findViewById(R.id.tvNomeClienteAdapter);
        tvNomeCliente.setText(cliente.getNome());

        TextView tvCodCliente = convertView.findViewById(R.id.tvCodigoClienteAdapter);
        tvCodCliente.setText(cliente.getCodigoDeBarras().toString());

        TextView tvCPF = convertView.findViewById(R.id.tvCPFClienteAdapter);
        tvCPF.setText(cliente.getCpf().toString());

        ImageView fotoCliente = convertView.findViewById(R.id.imvClienteItemClientesAdapter);
        if(cliente.getUrl_cliente()!=null){
            fotoCliente.setImageURI(Uri.parse(cliente.getUrl_cliente()));
        }else{
            fotoCliente.setImageResource(R.mipmap.ic_launcher_round);
        }
        return convertView;
    }


}

