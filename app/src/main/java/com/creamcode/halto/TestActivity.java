package com.creamcode.halto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements TestInterfaz, RelojFragment.RelojInterfaz {

    private boolean prueba;
    private int[] ordenTiempos = {6000,7000,10000,9000};
    Fragment ordenFragments[] = {new ColoresFragment(), new AudioFragment(), new VozFragment(), new EquilibrioFragment()};

    private int indice = 0;

    private final static int DELAY = 1400;

    private RelojFragment relojFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        prueba = getIntent().getExtras().getBoolean("prueba");

        relojFragment = new RelojFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tiempo",ordenTiempos[indice]);
        relojFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flReloj,relojFragment).replace(R.id.flPrueba, ordenFragments[indice]).commit();

    }

    @Override
    public void resultado(boolean resultadoDelTest) {
        if(resultadoDelTest){
            mostrarTransicion("correcto");
            if(indice == ordenFragments.length-1){
                relojFragment.cancelTimer();
                new CountDownTimer(DELAY, 1000) {
                    public void onFinish() {
                        Intent intent = new Intent(getApplicationContext(),ResultadoActivity.class);
                        intent.putExtra("prueba",prueba);
                        intent.putExtra("resultado",true);
                        startActivity(intent);
                    }
                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }else {
                indice++;
                relojFragment.cambiarTiempo(ordenTiempos[indice] + DELAY);
                new CountDownTimer(DELAY, 1000) {
                    public void onFinish() {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.flPrueba, ordenFragments[indice]).commit();
                    }
                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        }else{
            relojFragment.cancelTimer();
            mostrarTransicion("incorrecto");
            new CountDownTimer(DELAY, 1000) {
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(),ResultadoActivity.class);
                    intent.putExtra("prueba",prueba);
                    intent.putExtra("resultado",false);
                    startActivity(intent);
                }
                public void onTick(long millisUntilFinished) {
                }
            }.start();
        }
    }

    @Override
    public void tiempoTermino() {
        mostrarTransicion("tiempo");
        new CountDownTimer(DELAY, 1000) {
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),ResultadoActivity.class);
                intent.putExtra("prueba",prueba);
                intent.putExtra("resultado",false);
                startActivity(intent);
            }
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    public void mostrarTransicion(String tipo){
        TrancisionFragment trancisionFragment = new TrancisionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("opcion",tipo);
        trancisionFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flPrueba,trancisionFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relojFragment.cancelTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Care with this
        relojFragment.cancelTimer();
        finish();
    }
}
