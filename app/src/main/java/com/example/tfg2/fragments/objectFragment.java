package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg2.R;
import com.example.tfg2.valueSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class objectFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    valueSpinner valorSpinner = new valueSpinner();
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Spinner tallas;
    NavigationView navigationView;
    ImageView imageView;
    String nombre, precio, descripcion, url;


    public objectFragment() {
    }


    public objectFragment(String nombre, String descripcion, String precio, String url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.url = url;
    }


    public static objectFragment newInstance(String param1, String param2) {
        objectFragment fragment = new objectFragment();
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
        View view = inflater.inflate(R.layout.fragment_object, container, false);
        TextView nombreHold, descripcionHold, precioHold;
        ImageView imagenHold;
        Button añadirCesta;
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        añadirCesta = view.findViewById(R.id.btnAñadirAlCarrito);
        añadirCesta.setOnClickListener(this);
        nombreHold = view.findViewById(R.id.nombreholder);
        descripcionHold = view.findViewById(R.id.descripcionholder);
        precioHold = view.findViewById(R.id.precioholder);
        imagenHold = view.findViewById(R.id.imagenholder);
        tallas = view.findViewById(R.id.spinnerTallas);
        nombreHold.setText(nombre);
        descripcionHold.setText(descripcion);
        precioHold.setText(precio);
        Glide.with(getContext()).load(url).into(imagenHold);
        Log.i("url IMAGEN", url);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.talla, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        tallas.setAdapter(adapter);


        tallas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           valorSpinner.setValorSpinner(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        Map<String, Object> map = new HashMap<>();
        map.put("nombreProductoCarrito", nombre);
        map.put("precioProductoCarrito", precio);
        map.put("descripcionProductoCarrito", descripcion);
        map.put("urlProductoCarrito", url);
        map.put("tallaProductoCarrito", valorSpinner.getValorSpinner());
        String id = mAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(id).child("ProductosCarrito").child(nombre).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("INFO Carrito", "Se ha añadido perfectamente al carrito");
                } else {
                    Log.i("INFO Carrito", "No se ha añadido perfectamente al carrito");

                }
            }
        });

    }

/*
    public void onBackPressed(int keyCode, KeyEvent event) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new TiendaFragment())
                .addToBackStack(null).commit();


    }*/


}