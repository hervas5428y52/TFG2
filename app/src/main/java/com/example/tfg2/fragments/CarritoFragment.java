package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg2.R;
import com.example.tfg2.modelCarrito;
import com.example.tfg2.modelFavoritos;
import com.example.tfg2.myAdapterCarrito;
import com.example.tfg2.myAdapterFavoritos;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CarritoFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private myAdapterCarrito adapter;
    private FirebaseRecyclerOptions<modelCarrito> options;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private View view;
    private String id;
    private Button Comprar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Map<String, Object> map;
    Date d = new Date();
    CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
    TextView CestaFull;

    public CarritoFragment() {
        // Required empty public constructor
    }

    public static CarritoFragment newInstance(String param1, String param2) {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_carrito, container, false);
        id = mAuth.getCurrentUser().getUid();
        recyclerView = (RecyclerView) view.findViewById(R.id.recviewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<modelCarrito>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("ProductosCarrito"), modelCarrito.class)
                .build();
        adapter = new myAdapterCarrito(options);
        recyclerView.setAdapter(adapter);

        comprobarCesta();
        CestaFull = view.findViewById(R.id.txtCestaVacia);

        Comprar = view.findViewById(R.id.btnComprar);
        Comprar.setOnClickListener(this);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public void comprobarCesta() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    Comprar.setVisibility(View.VISIBLE);
                    CestaFull.setVisibility(View.INVISIBLE);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    Comprar.setVisibility(View.INVISIBLE);
                    CestaFull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child("Users").child(id).child("ProductosCarrito").addValueEventListener(valueEventListener);
    }

    @Override
    public void onClick(View view) {

        id = mAuth.getCurrentUser().getUid();

        map = new HashMap<>();
        map.put("fecha", s);
        map.put("identificadorCompra", generateRandomUUID());
        Log.i("INFO", s.toString());
        databaseReference.child("Users").child(id).child("Compras").child(generateRandomUUID()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    databaseReference.child("Users").child(id).child("ProductosCarrito").removeValue();
                } else {
                    Log.i("INFO Carrito", "No se ha añadido perfectamente al carrito");

                }
            }
        });

    }
}