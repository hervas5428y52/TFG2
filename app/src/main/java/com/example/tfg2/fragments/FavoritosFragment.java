package com.example.tfg2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.BD.BaseDatos;
import com.example.tfg2.R;
import com.example.tfg2.modelFavoritos;
import com.example.tfg2.modelRopa;
import com.example.tfg2.myAdapter;
import com.example.tfg2.myAdapterFavoritos;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.security.auth.login.LoginException;

public class FavoritosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public RecyclerView recyclerView;
    public myAdapterFavoritos adapter;
    FirebaseRecyclerOptions<modelFavoritos> options;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FavoritosFragment() {
        // Required empty public constructor
    }

    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        String id = mAuth.getCurrentUser().getUid();
        recyclerView = (RecyclerView) view.findViewById(R.id.recviewFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirebaseRecyclerOptions.Builder<modelFavoritos>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("ProductosFavoritos"), modelFavoritos.class)
                .build();

        adapter = new myAdapterFavoritos(options);
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