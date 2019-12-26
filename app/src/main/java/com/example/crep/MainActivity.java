package com.example.crep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button audioButton;
    private Button captura;
    private Button habla;
    private Button sintesis;
    private Button video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioButton = findViewById(R.id.audioButton);
        captura = findViewById(R.id.capturaButton);
        habla = findViewById(R.id.hablaButton);
        sintesis = findViewById(R.id.sintesisButton);
        video = findViewById(R.id.videoButton);

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AudioActivity.class);
                startActivity(intent);
            }
        });

        captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CapturaVideoActivity.class);
                startActivity(intent);
            }
        });

        habla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sintesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
