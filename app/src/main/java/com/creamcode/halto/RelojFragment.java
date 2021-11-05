package com.creamcode.halto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class RelojFragment extends Fragment {

    private RelojInterfaz relojInterfaz;

    private ProgressBar pgReloj;

    private int tiempoActual;
    private int tiempoBarra;

    private Timer timer = new Timer();
    TimerTask tarea;


    public interface RelojInterfaz {
        void tiempoTermino();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_reloj, container, false);

        pgReloj = view.findViewById(R.id.pbReloj);

        Bundle bundle = getArguments();
        tiempoActual = bundle.getInt("tiempo")/1000;
        tiempoBarra = tiempoActual;
        ejecutarTarea();

        return view;
    }

    public void ejecutarTarea() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if(tiempoActual ==0){
                            pgReloj.setProgress(0);
                            relojInterfaz.tiempoTermino();
                            timer.cancel();
                        }else{
                            pgReloj.setProgress(tiempoActual *100/tiempoBarra);
                            tiempoActual--;
                        }
                    }
                });
            }
        };
        timer.schedule(tarea,0,1000);
    }

    public void cambiarTiempo(int newTime){
        tiempoActual = newTime/1000;
        tiempoBarra = tiempoActual;
    }

    public void cancelTimer(){
        timer.cancel();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RelojInterfaz){
            relojInterfaz = (RelojInterfaz)context;
        }else{
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        relojInterfaz = null;
    }
}