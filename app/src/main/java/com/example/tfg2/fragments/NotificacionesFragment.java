package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.R;
import com.example.tfg2.modelFavoritos;
import com.example.tfg2.modelNotificaciones;
import com.example.tfg2.myAdapterFavoritos;
import com.example.tfg2.myAdapterNotificaciones;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class NotificacionesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private myAdapterNotificaciones adapter;
    private FirebaseRecyclerOptions<modelNotificaciones> options;
    private View view;
    private String id;

    public NotificacionesFragment() {
        // Required empty public constructor
    }


    public static NotificacionesFragment newInstance(String param1, String param2) {
        NotificacionesFragment fragment = new NotificacionesFragment();
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
        view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        id = mAuth.getCurrentUser().getUid();

        recyclerView = (RecyclerView) view.findViewById(R.id.recviewNotificaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<modelNotificaciones>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Compras"), modelNotificaciones.class)
                .build();

        adapter = new myAdapterNotificaciones(options);
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