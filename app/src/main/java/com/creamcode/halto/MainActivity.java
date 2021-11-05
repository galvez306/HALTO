package com.creamcode.halto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.snackbar.Snackbar;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView btnSettings;
    private GifImageView gfIniciar;
    private TextView tvPrueba;

    String[] PERMISOS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings = findViewById(R.id.btnSettings);
        gfIniciar = findViewById(R.id.gfIniciar);
        tvPrueba = findViewById(R.id.tvPrueba);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean firstLogin = sharedPreferences.getBoolean("firstLogin",true);
        Boolean tutorial = sharedPreferences.getBoolean("tutorial",true);

        if (firstLogin) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        if (tutorial) {
            TapTargetView.showFor(this,
                    TapTarget.forView(findViewById(R.id.tvPrueba), getString(R.string.main_prueba_titulo), getString(R.string.main_prueba_descripcion))
                            .outerCircleColor(R.color.tema_azul)
                            .outerCircleAlpha(.99f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(30)
                            .titleTextColor(R.color.white)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(false)
                            .tintTarget(true)
                            .transparentTarget(false)
                            .targetRadius(60),
                    new TapTargetView.Listener() {
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("tutorial", false);
                            editor.apply();
                        }
                    });
        }

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
            }
        });

        gfIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checarPermisos(MainActivity.this,PERMISOS)) {
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("prueba", false);
                    startActivity(intent);
                }
            }
        });

        tvPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checarPermisos(MainActivity.this,PERMISOS)) {
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("prueba",true);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean checarPermisos(Context context, String... permissions) {
        //Validar permisos
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISOS, 1);
                return false;
            }
        }
        //validar ubicacion
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean activadoGps = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!activadoGps){
            Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.main_gps_desactivado, Snackbar.LENGTH_LONG);
            snackBar.setAction(R.string.main_gps_activar, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            snackBar.show();
            return false;
        }
        AudioManager am =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        return true;
    }

}