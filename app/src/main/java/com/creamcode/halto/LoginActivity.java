package com.creamcode.halto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText etNombre, etNumeroUno, etNumeroDos;
    private Button btnConfirmar;

    private SharedPreferences sharedPreferences;

    String[] PERMISOS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNombre = findViewById(R.id.etNombre);
        etNumeroUno = findViewById(R.id.etNumeroUno);
        etNumeroDos = findViewById(R.id.etNumeroDos);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ActivityCompat.requestPermissions(this, PERMISOS, 1);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String numUno = etNumeroUno.getText().toString().trim();
                String numDos = etNumeroDos.getText().toString().trim();
                //Condiciones: Ninguno vacio, Nombre<50, numUno!= numDos, ambos numeros < 15
                if(nombre.equals("") || numUno.equals("") || numDos.equals("")){
                    Snackbar.make(findViewById(R.id.contenedorLogin),R.string.login_snack_campos_vacios,Snackbar.LENGTH_LONG).show();
                }else if(nombre.length()>50){
                    Snackbar.make(findViewById(R.id.contenedorLogin),R.string.login_snack_nombre_largo,Snackbar.LENGTH_LONG).show();
                }else if(numUno.equals(numDos)){
                    Snackbar.make(findViewById(R.id.contenedorLogin),R.string.login_snack_numeros_diferentes,Snackbar.LENGTH_LONG).show();
                }else if (numUno.length()>15 || numDos.length()>15){
                    Snackbar.make(findViewById(R.id.contenedorLogin),R.string.login_snack_numeros_diferentes,Snackbar.LENGTH_LONG).show();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("prfNombre", nombre);
                    editor.putString("prfNumeroUno", numUno);
                    editor.putString("prfNumeroDos", numDos);
                    editor.putBoolean("firstLogin", false);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}