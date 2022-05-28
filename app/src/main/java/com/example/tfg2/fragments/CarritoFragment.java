package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.R;
import com.example.tfg2.modelCarrito;
import com.example.tfg2.modelFavoritos;
import com.example.tfg2.myAdapterCarrito;
import com.example.tfg2.myAdapterFavoritos;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class CarritoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public RecyclerView recyclerView;
    public myAdapterCarrito adapter;
    FirebaseRecyclerOptions<modelCarrito> options;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        String id = mAuth.getCurrentUser().getUid();
        recyclerView = (RecyclerView) view.findViewById(R.id.recviewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<modelCarrito>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("ProductosCarrito"), modelCarrito.class)
                .build();

        adapter = new myAdapterCarrito(options);
        recyclerView.setAdapter(adapter);

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
}