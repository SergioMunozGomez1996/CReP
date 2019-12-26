package com.example.crep;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class AudioActivity extends Activity {

    Button reproducir, pausa, detener;
    MediaPlayer mediaPlayer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // TODO: inicializar el objeto MediaPlayer

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zelda_nes);

        //mediaPlayer = new MediaPlayer();
        /*mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String archivo = "android.resource://"+  getPackageName() + R.raw.zelda_nes;

        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(archivo));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        inicializarBotones();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // TODO Rellena con tu código
                // TODO habilitar el botón Reproducir, deshabilitar los botones Pausa y Detener
                reproducir.setEnabled(true);
                pausa.setEnabled(false);
                detener.setEnabled(false);
                // TODO volver a poner como texto del botón de pausa la cadena Pausa
                pausa.setText("Pausa");

                mediaPlayer.stop();

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void inicializarBotones() {
        reproducir = (Button)findViewById(R.id.reproducir);
        pausa = (Button)findViewById(R.id.pausa);
        detener = (Button)findViewById(R.id.detener);

        reproducir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO habilitar los botones Pausa y Detener, deshabilitar el botón Reproducir
                reproducir.setEnabled(false);
                pausa.setEnabled(true);
                detener.setEnabled(true);
                // TODO iniciar la reproducción del clip de audio
                mediaPlayer.start();

            }
        });

        pausa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO pausar o reanudar la reproducción del audio
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pausa.setText("Reanudar");
                }else{
                    mediaPlayer.start();
                    pausa.setText("Pausa");
                }
                // TODO cambiar el texto del botón a Reanudar o Pausa según corresponda


            }
        });

        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO habilitar el botón Reproducir, deshabilitar los botones Pausa y Detener
                reproducir.setEnabled(true);
                pausa.setEnabled(false);
                detener.setEnabled(false);
                // TODO volver a poner como texto del botón de pausa la cadena Pausa
                pausa.setText("Pausa");

                mediaPlayer.stop();

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO liberar los recursos asociados al objeto MediaPlayer
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

