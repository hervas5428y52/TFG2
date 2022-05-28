package com.example.tfg2;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfg2.fragments.TiendaFragment;
import com.example.tfg2.fragments.objectFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class myAdapter extends FirebaseRecyclerAdapter<modelRopa, myAdapter.myviewholder> {

    private List<modelRopa> ropaList;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String id;
    private View view;
    private AppCompatActivity activity;

    public myAdapter(@NonNull FirebaseRecyclerOptions<modelRopa> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull modelRopa model) {
        holder.nombre.setText(model.getNombre());
        //holder.descripcion.setText(model.getDescripcion());
        holder.precio.setText(model.getPrecio() + " €");
        Glide.with(holder.imagen.getContext()).load(model.getUrl()).into(holder.imagen);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new objectFragment(model.getNombre(), model.getDescripcion(), model.getPrecio() + " €", model.getUrl()))
                        .addToBackStack(null).commit();
            }
        });

        holder.meGustaOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("nombreProducto", model.getNombre());
                map.put("precioProducto", model.getPrecio());
                map.put("descripcionProducto", model.getDescripcion());
                map.put("urlProducto", model.getUrl());

                id = mAuth.getCurrentUser().getUid();

                Toast.makeText(view.getContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show();

                databaseReference.child("Users").child(id).child("ProductosFavoritos").child(model.getNombre()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (databaseReference.child("Users").child(id).child("ProductosFavoritos").child(model.getNombre()).equals(databaseReference.child("Users").child(id).child("ProductosFavoritos").child(model.getNombre()))) {
                                holder.meGustaOFF.setVisibility(View.INVISIBLE);
                                Log.i("INFO ME GUSTA", "El ME GUSTA YA ESTA DADO");
                            } else {
                                Log.i("INFO NO ME GUSTA", "El ME GUSTA NO ESTA DADO");
                            }
                        } else {
                            Log.i("WFEFE2G2EGE2", "No Funciono");
                        }
                    }
                });
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign, parent, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView nombre, /*descripcion,*/
                precio;
        ImageView imagen;
        CardView cardView;
        ImageButton meGustaON, meGustaOFF;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txtNombre);
            //descripcion = itemView.findViewById(R.id.txtDescripcion);
            precio = itemView.findViewById(R.id.txtPrecio);
            cardView = itemView.findViewById(R.id.row);
            imagen = itemView.findViewById(R.id.imgRopa);
            meGustaOFF = itemView.findViewById(R.id.iB_meGustaOFF);
            meGustaON = itemView.findViewById(R.id.iB_meGustaON);
        }
    }


}
