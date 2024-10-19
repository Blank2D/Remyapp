package com.example.remyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
public class AccesoActivity  extends AppCompatActivity{

    //delcaramos variables para enlazar los id
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        //enlazamos los ID
        textView = findViewById(R.id.txtl1);
        progressBar = findViewById(R.id.barraProgreso);
        linearLayout = findViewById(R.id.layout2);
        // Creamos y ejecutamos el thread para simular la carga de imagen y texto

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //simulamos una espera de 5s
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                //actualizamos el UI desde el hilo una vez pasen los 5s
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ocultamos la barra de progreso
                        progressBar.setVisibility(View.GONE);
                        //ocultamos el texto
                        textView.setVisibility(View.GONE);
                        //aactualizamos el layout 2 para que sea visible
                        linearLayout.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

        //INICIAMOS EL HILO CREADO
        thread.start();

    }


    public void onClickrdd(View view){
        Intent intent = new Intent(this, RecetaDelDia.class);
        startActivity(intent);
    }

    public void onClicktlr(View view){
        Intent intent = new Intent(this, TodasLasRecetas.class);
        startActivity(intent);
    }

    public void onClickcs(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickMapa(View view){
        Intent intent = new Intent(this, Mapa.class);
        startActivity(intent);
    }
    public void onClicksensor(View view){
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

}
