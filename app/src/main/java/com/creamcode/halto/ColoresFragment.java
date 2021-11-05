package com.creamcode.halto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ColoresFragment extends Fragment {

    private TestInterfaz testInterfaz;

    private final static int META = 5;

    private TextView tvColor;
    private Button btnMorado, btnNegro, btnAmarillo, btnRojo, btnVerde, btnGris, btnRosa, btnCafe;
    private int aciertos = 0;
    private String colorActual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colores, container, false);

        tvColor = view.findViewById(R.id.tvColor);
        btnMorado = view.findViewById(R.id.btnMorado);
        btnNegro = view.findViewById(R.id.btnNegro);
        btnAmarillo = view.findViewById(R.id.btnAmarillo);
        btnRojo = view.findViewById(R.id.btnRojo);
        btnVerde = view.findViewById(R.id.btnVerde);
        btnGris = view.findViewById(R.id.btnGris);
        btnRosa = view.findViewById(R.id.btnRosa);
        btnCafe = view.findViewById(R.id.btnCafe);

        btnMorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnNegro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnAmarillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnGris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });

        generarColorAleatorio();

        return view;
    }

    private void generarColorAleatorio(){
        int [] coloresString =  new int[]{
                R.string.test_colores_morado,
                R.string.test_colores_negro,
                R.string.test_colores_amarillo,
                R.string.test_colores_rojo,
                R.string.test_colores_verde,
                R.string.test_colores_gris,
                R.string.test_colores_rosa,
                R.string.test_colores_cafe,
        };
        int [] coloresInt =  new int[]{
                R.color.boton_morado,
                R.color.boton_negro,
                R.color.boton_amarillo,
                R.color.boton_rojo,
                R.color.boton_verde,
                R.color.boton_gris,
                R.color.boton_rosa,
                R.color.boton_cafe,
        };

        int randomString = (int) ((Math.random() * (8)) );
        int randomColorFondo = (int) ((Math.random() * (8)) );

        while (randomString == randomColorFondo){
            randomColorFondo = (int) ((Math.random() * (8)) );
        }

        colorActual = getResources().getString(coloresString[randomString]);

        tvColor.setText(colorActual);
        tvColor.setBackgroundColor(ContextCompat.getColor(getContext(),coloresInt[randomColorFondo]));

    }
    public void checarRespuesta(View view){
        Button b = (Button)view;
        String textBoton = b.getText().toString();
        if(textBoton.equals(colorActual)){
            aciertos++;
            if(aciertos==META){
                testInterfaz.resultado(true);
            }else {
                generarColorAleatorio();
            }
        }else{
            testInterfaz.resultado(false);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TestInterfaz){
            testInterfaz = (TestInterfaz)context;
        }else{
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        testInterfaz = null;
    }

}