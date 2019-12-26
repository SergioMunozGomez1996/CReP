package com.example.crep;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

// TODO: añadir los permisos en el Manifest

// TODO: la clase debe implementar la interfaz SurfaceHolder.Callback
public class CapturaVideoActivity extends Activity implements SurfaceHolder.Callback{

    SurfaceView superficie;
    Button parar, grabar;
    SurfaceHolder m_holder;


    private static final int REQUEST_CAMERA_PERMISSION = 200;

    boolean preparado = false;

    // TODO: añadimos un objeto privado MediaRecorder
    private MediaRecorder mediaRecorder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_video);
        inicializarInterfaz();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                }, REQUEST_CAMERA_PERMISSION);
                return;
            }
        }

        // TODO: inicializamos el objeto mediaRecorder
        mediaRecorder = new MediaRecorder();

        // TODO: obtenemos el holder de la superficie y añadimos el manejador
        m_holder = superficie.getHolder();
        m_holder.addCallback(this);
        m_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    private void inicializarInterfaz() {
        superficie = (SurfaceView)findViewById(R.id.superficie);
        parar = (Button)findViewById(R.id.parar);
        grabar = (Button)findViewById(R.id.grabar);

        grabar.setOnClickListener(new ManejadorBotonGrabar());
        parar.setOnClickListener(new ManejadorBotonParar());
    }

    private class ManejadorBotonParar implements View.OnClickListener {
        public void onClick(View v) {
            parar.setEnabled(false);
            grabar.setEnabled(true);

            // TODO: detener la grabación
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();

        }
    };

    private class ManejadorBotonGrabar implements View.OnClickListener {
        public void onClick(View v) {

            if (preparado) {
                parar.setEnabled(true);
                grabar.setEnabled(false);

                // TODO: iniciar la grabación
                configurar(m_holder);
                mediaRecorder.start();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaRecorder.setPreviewDisplay(m_holder.getSurface());
        preparado = true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mediaRecorder.release();
    }

    private void configurar(SurfaceHolder holder) {
        // TODO: configurar mediaRecorder
        try {
            mediaRecorder = new MediaRecorder();

            // Inicializando el objeto MediaRecorder
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"video.mp4");

            mediaRecorder.setOutputFile(file.getPath());
            mediaRecorder.setPreviewDisplay(holder.getSurface());
            mediaRecorder.prepare();

        } catch (IllegalArgumentException e) {
            Log.d("MEDIA_PLAYER", e.getMessage());
        } catch (IllegalStateException e) {
            Log.d("MEDIA_PLAYER", e.getMessage());
        } catch (IOException e) {
            Log.d("MEDIA_PLAYER", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
