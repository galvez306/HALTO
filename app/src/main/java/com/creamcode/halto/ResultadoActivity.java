package com.creamcode.halto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import pl.droidsonroids.gif.GifImageView;

public class ResultadoActivity extends AppCompatActivity {

    private GifImageView gfFace;
    private TextView tvResultado;
    private ImageView ivUber, ivCabify, ivEmergencias;

    private FusedLocationProviderClient fusedLocationClient;

    private boolean prueba, resultado;
    private String ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        tvResultado = findViewById(R.id.tvResultado);
        gfFace = findViewById(R.id.gfFace);
        ivUber = findViewById(R.id.ivUber);
        ivCabify = findViewById(R.id.ivCaify);
        ivEmergencias = findViewById(R.id.ivEmergencias);

        prueba = getIntent().getExtras().getBoolean("prueba");
        resultado = getIntent().getExtras().getBoolean("resultado");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        if (!resultado) {
            gfFace.setImageResource(R.drawable.gif_bad);
            tvResultado.setText(R.string.resultado_resultado_bad);
            if (!prueba) {
                getLocation();
            }
        }


        ivUber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ubercab");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(ResultadoActivity.this, R.string.resultado_error_abrir_app, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivCabify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.cabify.rider");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(ResultadoActivity.this, R.string.resultado_error_abrir_app, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivEmergencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:911"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ResultadoActivity.this, R.string.resultado_error_abrir_llamadas, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Double latitud = location.getLatitude();
                Double longitud = location.getLongitude();
                ubicacion = ubicacion = "http://www.google.com/maps/place/"+latitud+","+longitud;
                enviarMensajes();
            }
        });
    }

    private void enviarMensajes(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        String nombre = datos.getString("prfNombre","");
        String numUno = datos.getString("prfNumeroUno","");
        String numDos = datos.getString("prfNumeroDos","");

        String smsUno =getString(R.string.resultado_sms_saludo)+" " +nombre;
        String smsDos =getString(R.string.resultado_sms)+" " +ubicacion;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numUno, null, smsUno, null, null);
        smsManager.sendTextMessage(numUno, null, smsDos, null, null);
        smsManager.sendTextMessage(numDos, null, smsUno, null, null);
        smsManager.sendTextMessage(numDos, null, smsDos, null, null);


        Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
    }
}