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

public class myAdapterNotificaciones extends FirebaseRecyclerAdapter<modelNotificaciones,myAdapterNotificaciones.myviewholder> {


    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    View view;

    public myAdapterNotificaciones(@NonNull FirebaseRecyclerOptions<modelNotificaciones> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull modelNotificaciones model) {
        holder.fechaNot.setText(model.getFecha());
        holder.idNot.setText(model.getIdentificadorCompra());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign_notificaciones, parent, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView fechaNot, idNot;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            fechaNot = itemView.findViewById(R.id.txtfechaNotificacion);
            idNot = itemView.findViewById(R.id.txtidNotificacion);


        }
    }
}
