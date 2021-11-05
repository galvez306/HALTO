package com.creamcode.halto;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.droidsonroids.gif.GifImageView;


public class TrancisionFragment extends Fragment {

    private GifImageView gifTransicion;
    private MediaPlayer transSound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trancision, container, false);

        gifTransicion = view.findViewById(R.id.gfTransicion);

        Bundle bundle = getArguments();

        switch (bundle.getString("opcion")){
            case "correcto":
                gifTransicion.setImageResource(R.drawable.gif_correcto);
                transSound = MediaPlayer.create(getContext(),R.raw.correcto);
                break;
            case "incorrecto":
                gifTransicion.setImageResource(R.drawable.gif_incorrecto);
                transSound = MediaPlayer.create(getContext(),R.raw.incorrecto);
                break;
            case "tiempo":
                gifTransicion.setImageResource(R.drawable.gif_sintiempo);
                transSound = MediaPlayer.create(getContext(),R.raw.incorrecto);
                break;
        }
        transSound.start();

        return view;
    }
}