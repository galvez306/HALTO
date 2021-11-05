package com.creamcode.halto;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class EquilibrioFragment extends Fragment {

    private TestInterfaz testInterfaz;

    private final static int META = 4;
    private int aciertos = 0;
    private boolean orientacion = true;

    private ImageView ivAnillo, ivCheto;
    private MediaPlayer successSound;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_equilibrio, container, false);

        ivAnillo = view.findViewById(R.id.ivAnillo);
        ivCheto = view.findViewById(R.id.ivCheto);

        successSound = MediaPlayer.create(getContext(),R.raw.anillofull);

        generarAnillo();

        usarSensor();

        return view;
    }

    private void generarAnillo(){

        int[] anillos = new int[]{ R.drawable.img_anillo1,R.drawable.img_anillo2,R.drawable.img_anillo3,R.drawable.img_anillo4};
        int[] chetos = new int[] {R.drawable.img_cheto1,R.drawable.img_cheto2, R.drawable.img_cheto3, R.drawable.img_cheto4};

        ivAnillo.setImageResource(anillos[aciertos]);
        ivCheto.setImageResource(chetos[aciertos]);
    }

    public void usarSensor(){
        sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Cambio del valor del sensor
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                ivAnillo.setRotation((x*18.34f));
                //Parametro en donde el cheto embona con el anillo
                if (orientacion ==true && x>6.72 && x<7.35){
                    orientacion =!orientacion;
                    aciertos++;
                    successSound.start();
                }if (orientacion ==false && x<-6.72 && x>-7.35){
                    orientacion =!orientacion;
                    aciertos++;
                    successSound.start();
                    }
                if(aciertos==META){
                    sensorManager.unregisterListener(sensorEventListener);
                    testInterfaz.resultado(true);
                }else {
                    generarAnillo();
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_FASTEST);
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