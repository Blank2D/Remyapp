package com.example.remyapp;

import android.content.Intent;
import android.os.Bundle;
//Agrego librerias
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//Definir una clase MainActivity que hereda de la clase AppCompactActivity implementamos Sensor para gestion de eventos
public class SensorActivity extends AppCompatActivity  implements SensorEventListener{

    //Defino variables para manipular la interfaz
    private View mainLayout;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        //Refenrencio cada variable con la interfaz
        mainLayout = findViewById(R.id.main_layout);
        //Inicializo el sensor del dispositivo para interactuar
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //Obtengo el tipo de sensor a utilizar
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    //Metodo llamado cuando la actividad se reanude
    @Override
    protected void onResume(){
        super.onResume();
        //Verifico si el sensor esta disponible en el telefono
        if (accelerometer != null){
            //Registro un listener para recibir eventos
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    //Metodo llamado cuando la actividad se pause
    @Override
    protected  void onPause(){
        super.onPause();
        //Se desregistra el listener para ahorrar bateria
        //cuando la actividad no este activa
        sensorManager.unregisterListener(this);

    }
    //Metodo llamado cuando cambio los valores del sensor
    @Override
    public void onSensorChanged(SensorEvent event){
        //Definimos un metodo cuando se registren los cambios del sensor
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //Obtengo los valores del sensor
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            //Comparo la inclinacion del sensor
            if (Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)){
                //Comparo la inclinacion del eje X
                if(x > 2){
                    //Incline el sensor a la derecha
                    mainLayout.setBackgroundColor(Color.RED);
                } else if (x < -2) {
                    //Incline el sensor a la izquierda
                    mainLayout.setBackgroundColor(Color.BLUE);
                }
            } else if (Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z) ) {
                //Comparo la inclicacion del eje Y
                if (y > 2){
                    //Inclinacion es Arriba
                    mainLayout.setBackgroundColor(Color.GREEN);
                }else if(y < -2){
                    //Inclinacion hacia abajo
                    mainLayout.setBackgroundColor(Color.YELLOW);
                }

            }
        }
    }
    //Metodo llamado cuando cambie la precision del sensor (no lo utilizo)
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }
    public void onClickvolver(View view){
        Intent intent = new Intent(this, AccesoActivity.class);
        startActivity(intent);
    }

}