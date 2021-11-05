package com.creamcode.halto;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AudioFragment extends Fragment {

    private TestInterfaz testInterfaz;

    private final static int META = 3;
    private int aciertos = 0;

    private TextView tvUno, tvDos, tvTres, tvCuatro, tvCinco;
    private ImageView btnRepetir;

    private String palbraActual;
    private MediaPlayer sonidoAleatorio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        tvUno = view.findViewById(R.id.tvUno);
        tvDos = view.findViewById(R.id.tvDos);
        tvTres = view.findViewById(R.id.tvTres);
        tvCuatro = view.findViewById(R.id.tvCuatro);
        tvCinco = view.findViewById(R.id.tvCinco);
        btnRepetir = view.findViewById(R.id.btnRepetir);

        generarPalabras();

        tvUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        tvDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        tvTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        tvCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        tvCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespuesta(v);
            }
        });
        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoAleatorio.start();
                v.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void generarPalabras(){
        String[] arrayPalabrasUno = getResources().getStringArray(R.array.test_audio_palabras_uno);
        String[] arrayPalabrasDos = getResources().getStringArray(R.array.test_audio_palabras_dos);
        String[] arrayPalabrasTres = getResources().getStringArray(R.array.test_audio_palabras_tres);

        String[][] arreglos = {arrayPalabrasUno,arrayPalabrasDos,arrayPalabrasTres};

        int randomArray = (int) ((Math.random() * (3)) );
        int randomPalabra = (int) ((Math.random() * (5)) );
        int randomTextview = (int) ((Math.random() * (5)) );

        TextView [] arrayTextViews = {tvUno,tvDos,tvTres,tvCuatro,tvCinco};

        for (int i = 0; i < arreglos[randomArray].length; i++){
            arrayTextViews[i].setText(arreglos[randomArray][i]);
            arrayTextViews[i].setBackgroundResource(R.drawable.background_textview_audio);
        }

        palbraActual = arreglos[randomArray][randomPalabra].toLowerCase();
        arrayTextViews[randomTextview].setBackgroundResource(R.drawable.background_textview_audio_random);

        //Generar el mediaplayer a partir de la palabra aleatoria
        int resID=getResources().getIdentifier(palbraActual, "raw", getActivity().getPackageName());
        sonidoAleatorio=MediaPlayer.create(getContext(),resID);
        sonidoAleatorio.start();

    }

    public void checarRespuesta(View view){
        TextView t = (TextView)view;
        String textTextview = t.getText().toString().toLowerCase();
        if(textTextview.equals(palbraActual)){
            aciertos++;
            if(aciertos==META){
                testInterfaz.resultado(true);
            }else {
                generarPalabras();
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