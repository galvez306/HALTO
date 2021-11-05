package com.creamcode.halto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

public class PreferencesFragment extends PreferenceFragmentCompat {

    private static final int LENGHT_NOMBRE = 50;

    private  SharedPreferences sharedPreferences;
    private EditTextPreference etNombre, etNumeroUno, etNumeroDos;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        etNombre = (EditTextPreference)findPreference("prfNombre");
        etNumeroUno = (EditTextPreference)findPreference("prfNumeroUno");
        etNumeroDos = (EditTextPreference)findPreference("prfNumeroDos");

        etNumeroUno.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });
        etNumeroDos.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });

        cargarDatos();

        etNombre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (String.valueOf(newValue).length()<LENGHT_NOMBRE) {
                    etNombre.setSummary(String.valueOf(newValue));
                }else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content),R.string.preferences_longitud_nombre,Snackbar.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        });

        etNumeroUno.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (String.valueOf(newValue).length()<15) {
                    etNumeroUno.setSummary(String.valueOf(newValue));
                }else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content),R.string.login_snack_numeros_largos,Snackbar.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        });

        etNumeroDos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (String.valueOf(newValue).length()<15) {
                    etNumeroDos.setSummary(String.valueOf(newValue));
                }else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content),R.string.login_snack_numeros_largos,Snackbar.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        });

    }

    private void cargarDatos() {
        etNombre.setSummary(sharedPreferences.getString("prfNombre",""));
        etNumeroUno.setSummary(sharedPreferences.getString("prfNumeroUno",""));
        etNumeroDos.setSummary(sharedPreferences.getString("prfNumeroDos",""));
    }
}
