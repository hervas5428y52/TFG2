package com.example.tfg2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tfg2.R;
import com.example.tfg2.login.Registro;
import com.example.tfg2.login.inicioSesion;

public class FalsoConfiguracionFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FalsoConfiguracionFragment() {
    }


    public static FalsoConfiguracionFragment newInstance(String param1, String param2) {
        FalsoConfiguracionFragment fragment = new FalsoConfiguracionFragment();
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
        View view = inflater.inflate(R.layout.fragment_falso_configuracion, container, false);


        Button btnIniciarSesion = view.findViewById(R.id.buttonIniciodeSesionConfiguracion);
        Button btnRegistro = view.findViewById(R.id.buttonRegistroConfiguracion);

        btnIniciarSesion.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonRegistroConfiguracion:
                Intent intent = new Intent(getContext(), Registro.class);
                startActivity(intent);
                break;
            case R.id.buttonIniciodeSesionConfiguracion:
                Intent intsent = new Intent(getContext(), inicioSesion.class);
                startActivity(intsent);
                break;
        }

    }
}