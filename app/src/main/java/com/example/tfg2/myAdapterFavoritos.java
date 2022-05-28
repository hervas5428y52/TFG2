package com.example.tfg2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg2.fragments.objectFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class myAdapterFavoritos extends FirebaseRecyclerAdapter<modelFavoritos, myAdapterFavoritos.myviewholder> {

    List<modelFavoritos> ropaList;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    AppCompatActivity activity;
    String id;

    public myAdapterFavoritos(@NonNull FirebaseRecyclerOptions<modelFavoritos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myAdapterFavoritos.myviewholder holder, int position, @NonNull modelFavoritos model) {

        holder.nombreProductoFav.setText(model.getNombreProducto());
        holder.precioProductoFav.setText(model.getPrecioProducto());

        holder.cardViewFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new objectFragment(model.getNombreProducto(),model.getDescripcionProducto(), model.getPrecioProducto(), model.getUrlProducto())).addToBackStack(null).commit();
               // Log.i("info nombre fav", model2.getNombre());
            }
        });

        holder.eliminarFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = mAuth.getCurrentUser().getUid();

                databaseReference.child("Users").child(id).child("ProductosFavoritos").child(model.getNombreProducto()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                           Log.i("INFO ELIMINAR FAV", "Se elimino correctamente");
                       else
                           Log.i("INFO ELIMINAR FAV", "No se elimino correctamente");

                    }
                });
            }
        });


    }

    @NonNull
    @Override
    public myAdapterFavoritos.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign_favoritos, parent, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView nombreProductoFav, precioProductoFav, eliminarFavorito;
        CardView cardViewFavoritos;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            cardViewFavoritos = itemView.findViewById(R.id.row_favoritos);
            nombreProductoFav = itemView.findViewById(R.id.txtNombreFavoritos);
            precioProductoFav = itemView.findViewById(R.id.txtPrecioFavoritos);
            eliminarFavorito = itemView.findViewById(R.id.eliminarProductoFavorito);
        }
    }
}
