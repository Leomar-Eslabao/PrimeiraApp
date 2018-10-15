package br.edu.ifsul.primeiraapp.setup;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.primeiraapp.model.Cliente;
import br.edu.ifsul.primeiraapp.model.ItemPedido;

public class AppSetup {

    public static FirebaseUser vendedor = null;
    private static DatabaseReference myRef = null;
    public static Cliente cliente = null;

    public static List<ItemPedido> cesta=new ArrayList<>();//tem q ser publico p ser acessivel por qualquer classe

   public static DatabaseReference getInstance() {
       if (myRef == null) {
           FirebaseDatabase database = FirebaseDatabase.getInstance();
           myRef = database.getReference("vendas");
           return myRef;
       }
       return myRef;
   }
}
