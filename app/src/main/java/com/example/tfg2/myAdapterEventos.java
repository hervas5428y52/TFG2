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

import java.util.List;

public class myAdapterEventos extends FirebaseRecyclerAdapter<modelEventos, myAdapterEventos.myviewholder> {
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    View view;

    public myAdapterEventos(@NonNull FirebaseRecyclerOptions<modelEventos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull modelEventos model) {

        holder.nombreEvento.setText(model.getNombreEvento());
        holder.fechaEvento.setText(model.getFecha());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign_eventos, parent, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference  = FirebaseDatabase.getInstance().getReference();
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView nombreEvento, fechaEvento;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nombreEvento = itemView.findViewById(R.id.txtNombreEvento);
            fechaEvento = itemView.findViewById(R.id.txtTiempoEvento);
        }
    }
}
