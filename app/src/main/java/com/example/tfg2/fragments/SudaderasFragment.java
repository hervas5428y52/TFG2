package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.R;
import com.example.tfg2.modelRopa;
import com.example.tfg2.myAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class SudaderasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public RecyclerView recyclerView;
    public myAdapter adapter;
    FirebaseRecyclerOptions<modelRopa> options;
    View view;


    public SudaderasFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static SudaderasFragment newInstance(String param1, String param2) {
        SudaderasFragment fragment = new SudaderasFragment();
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
        view = inflater.inflate(R.layout.fragment_sudaderas, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recviewSudaderas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirebaseRecyclerOptions.Builder<modelRopa>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ropa").orderByChild("categoria").equalTo("sudadera"), modelRopa.class)
                .build();


        adapter = new myAdapter(options);
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