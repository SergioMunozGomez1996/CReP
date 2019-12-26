package com.example.crep;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class ReproduceVideoActivity extends Activity {

    VideoView superficie;
    TextView tiempo;
    Button reproducir, detener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduce_video);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        superficie = (VideoView)findViewById(R.id.superficie);
        tiempo = (TextView)findViewById(R.id.tiempo);
        reproducir = (Button)findViewById(R.id.reproducir);
        detener = (Button)findViewById(R.id.detener);
        tiempo.setText("Duración 0");

        reproducir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Habilitar y deshabilitar botones
                reproducir.setEnabled(false);
                detener.setEnabled(true);

                // TODO Inicializar la reproducción de video

                superficie.setVideoURI(Uri.parse("android.resource://"+ getPackageName()+ "/" + R.raw.tetris));
            }
        });

        // TODO Manejador del evento OnPreparedListener para comenzar
        // la reproducción y poder mostrar la duración del video
        superficie.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                superficie.start();

                int duracion = superficie.getDuration() / 1000;
                int segundos = duracion & 60;
                int minutos = duracion / 60;

                tiempo.setText(String.format("%d:%2d", minutos, segundos));
            }
        });

        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Habilitar y deshabilitar botones
                reproducir.setEnabled(true);
                detener.setEnabled(false);

                // TODO Detener la ejecución del video
                superficie.stopPlayback();

                tiempo.setText("Duración 0");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}