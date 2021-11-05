package com.creamcode.halto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class VozFragment extends Fragment {

    private TestInterfaz testInterfaz;

    private TextView tvOracion;
    private ImageView ivOracionImagen, btnMic;

    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;

    private String oracionActual;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voz, container, false);

        tvOracion = view.findViewById(R.id.tvOracion);
        ivOracionImagen = view.findViewById(R.id.ivOracionImagen);
        btnMic = view.findViewById(R.id.btnMic);

        generarOracion();
        iniciarReconocedor();

        btnMic.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
                btnMic.setImageResource(R.drawable.ic_mic);
                speechRecognizer.stopListening();
            }
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                speechRecognizer.startListening(speechIntent);
                btnMic.setImageResource(R.drawable.ic_mic_on);
                btnMic.requestLayout();
            }
            return true;
        }
        });

        return view;
    }

    private void generarOracion(){
        String[] arrayOraciones = getResources().getStringArray(R.array.test_voz_oraciones);
        int[] arrayImagenes = {
                R.drawable.img_crocodile,R.drawable.img_vacations,R.drawable.img_books,
                R.drawable.img_door,R.drawable.img_sleeping};
        int randomOracion = (int) ((Math.random() * (5)) );

        tvOracion.setText(arrayOraciones[randomOracion]);
        oracionActual = arrayOraciones[randomOracion];
        ivOracionImagen.setImageResource(arrayImagenes[randomOracion]);
    }

    private void iniciarReconocedor(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }
            @Override
            public void onBeginningOfSpeech() {
            }
            @Override
            public void onRmsChanged(float rmsdB) {
            }
            @Override
            public void onBufferReceived(byte[] buffer) {
            }
            @Override
            public void onEndOfSpeech() {
            }
            @Override
            public void onError(int error) {
            }
            @Override
            public void onPartialResults(Bundle partialResults) {
            }
            @Override
            public void onEvent(int eventType, Bundle params) {
            }
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> arrayList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String respuesta =arrayList.get(0);
                if(oracionActual.equals(respuesta) || oracionActual.toLowerCase().equals(respuesta)){
                    testInterfaz.resultado(true);
                }else{
                    testInterfaz.resultado(false);
                }
            }
        });
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