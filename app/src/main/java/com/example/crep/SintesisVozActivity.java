package com.example.crep;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class SintesisVozActivity extends Activity {
    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts = null;
    private boolean ttsIsInit = false;
    private RadioButton radioEnglish, radioSpanish;
    private TextView texto;
    private Button leer;
    private Locale loc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintesis_voz);
        inicializarControles();
        initTextToSpeech();
    }

    private void inicializarControles() {
        radioEnglish = (RadioButton)findViewById(R.id.radioEnglish);
        radioSpanish = (RadioButton)findViewById(R.id.radioSpanish);
        texto = (TextView)findViewById(R.id.texto);
        leer = (Button)findViewById(R.id.butRead);
        radioSpanish.setChecked(true);

        leer.setOnClickListener(butReadListener);
        radioSpanish.setOnClickListener(radioSpanishListener);
        radioEnglish.setOnClickListener(radioEnglishListener);
    }

    private View.OnClickListener butReadListener = new View.OnClickListener() {
        public void onClick(View v) {
            speak(texto.getText().toString());
        }
    };

    private View.OnClickListener radioSpanishListener = new View.OnClickListener() {

        public void onClick(View v) {
            radioSpanish.setChecked(true);
            radioEnglish.setChecked(false);

            //TODO cambiar el idioma a español
            loc = new Locale("es","","");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                if (tts.isLanguageAvailable(loc)
                        >= TextToSpeech.LANG_AVAILABLE)
                    tts.setLanguage(loc);
            }

        }

    };

    private View.OnClickListener radioEnglishListener = new View.OnClickListener() {

        public void onClick(View v) {
            radioSpanish.setChecked(false);
            radioEnglish.setChecked(true);

            //TODO cambiar el idioma a inglés
            loc = new Locale("en","","");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                if (tts.isLanguageAvailable(loc)
                        >= TextToSpeech.LANG_AVAILABLE)
                    tts.setLanguage(loc);
            }
        }

    };

    private void initTextToSpeech() {
        //TODO inicializar motor text to speech
        Intent intent = new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_DATA_CHECK);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO inicialización del motor text to speech
        if (requestCode == TTS_DATA_CHECK) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                ttsIsInit = true;
                                loc = new Locale("es","","");
                                if (tts.isLanguageAvailable(loc)
                                        >= TextToSpeech.LANG_AVAILABLE)
                                    tts.setLanguage(loc);
                                tts.setPitch(0.8f);
                                tts.setSpeechRate(1.1f);
                            }
                        }
                    });
                }
            } else {
                Intent installVoice = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installVoice);
            }
        }

    }

    private void speak(String texto) {
        //TODO invocar al método speak del objeto TextToSpeech para leer el texto del EditText
        if (tts != null && ttsIsInit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                tts.speak(texto, TextToSpeech.QUEUE_ADD, null);
            }
        }

    }

    @Override
    public void onDestroy() {
        //TODO liberar los recursos de Text to Speech
        if (tts != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                tts.stop();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                tts.shutdown();
            }
        }

        super.onDestroy();
    }

}