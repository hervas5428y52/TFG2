package com.example.tfg2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myAdapterCarrito extends FirebaseRecyclerAdapter<modelCarrito, myAdapterCarrito.myviewholder> {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private View view;

    public myAdapterCarrito(@NonNull FirebaseRecyclerOptions<modelCarrito> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull modelCarrito model) {
        holder.nombre.setText(model.getNombreProductoCarrito());
        holder.precio.setText(model.getPrecioProductoCarrito());
        holder.talla.setText("Talla: " + model.getTallaProductoCarrito());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign_carrito, parent, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView nombre, precio, talla;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txtNombreCarrito);
            precio = itemView.findViewById(R.id.txtPrecioCarrito);
            talla = itemView.findViewById(R.id.txtTallaCarrito);
        }
    }
}
