package com.example.remyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEditText;
    private EditText contrasenaEditText;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioEditText = findViewById(R.id.usuario);
        contrasenaEditText = findViewById(R.id.contrasena);
        spinner = findViewById(R.id.spinnerRoles);

        String[] roles = {"Dieta Normal" , "Vegano", "Celiaco"};

        ArrayAdapter<String > adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void onClickAcceder(View view){
        String user = usuarioEditText.getText().toString().trim();
        String pass = contrasenaEditText.getText().toString().trim();
        String rol = spinner.getSelectedItem().toString();

        if (user.isEmpty()){
            Toast.makeText(this, "Ingrese su usuario >:(", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()){
            Toast.makeText(this, "Ingrese su contraseña >:(", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.equals("cris") && pass.equals("1234") && rol.equals("Dieta Normal")){
            Intent intent = new Intent(this, AccesoActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Credenciales Incorrectas >:(", Toast.LENGTH_SHORT).show();

        }
    }
}