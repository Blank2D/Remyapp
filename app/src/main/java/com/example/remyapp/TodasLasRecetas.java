package com.example.remyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TodasLasRecetas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaslasrecetas);
    }

    public void onClickvolver(View view){
        Intent intent = new Intent(this, AccesoActivity.class);
        startActivity(intent);
    }
}
