package com.example.remyapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//Librerias de SQLite
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class CRUD extends AppCompatActivity {

    //Declarar variables
    Spinner spSpinner;
    String[] tiposdePLato = new String[]{"Desayuno", "Entrada", "Fondo", "Postre", "Cena"};
    EditText edtId, edtNom, edtDir;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        //Defino los campos del formulario
        edtId = (EditText) findViewById(R.id.edtid);
        edtNom = (EditText) findViewById(R.id.edtNom);
        edtDir = (EditText) findViewById(R.id.edtDir);
        spSpinner = (Spinner) findViewById(R.id.spSpinner);
        lista = (ListView) findViewById(R.id.lstLista);

        //Poblar Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposdePLato);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);
        CargarLista();

    }

    public void CargarLista(){
        DataHelper dh = new DataHelper(this, "recetas.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        Cursor c = bd.rawQuery("Select id, nombre, precio, tipoDePlato from recetas", null);
        String[] arr = new String[c.getCount()];
        if(c.moveToFirst() == true){
            int i = 0;
            do{
                String linea = "||" + c.getInt(0) + "||" + c.getString(1)
                        + "||" + c.getString(2) + "||" + c.getString(3) + "||";
                arr[i] = linea;
                i++;
            }while (c.moveToNext() == true);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_expandable_list_item_1, arr);
            lista.setAdapter(adapter);
            bd.close();
        }
    }

    public void onClickAgregar (View view){
        DataHelper dh=new DataHelper(this, "recetas.db", null, 1);
        SQLiteDatabase bd= dh.getWritableDatabase();
        ContentValues reg= new ContentValues();
        reg.put("id", edtId.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("precio", edtDir.getText().toString());
        reg.put("tipoDePlato", spSpinner.getSelectedItem().toString());
        long resp = bd.insert("recetas", null, reg);
        bd.close();
        if(resp==-1){
            Toast.makeText(this,"No se ´puede ingresar la receta", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Registro Agregado", Toast.LENGTH_LONG).show();
        }
        CargarLista();
        limpiar();
    }

    //Metodo para limpiar los campos de texto
    public void limpiar(){
        edtId.setText("");
        edtNom.setText("");
        edtDir.setText("");
    }

    //Metodo para modificar un campo
    public void onClickModificar(View view){
        //Conexion a la BDD para manipular los registros
        DataHelper dh = new DataHelper(this, "recetas.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();
        //Envio los datos a modificar
        reg.put("id", edtId.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("precio", edtDir.getText().toString());
        reg.put("tipoDePlato", spSpinner.getSelectedItem().toString());
        //Actualizo el registro de la BBD por el RUT
        long respuesta = bd.update("receta", reg, "id=?", new String[]{edtId.getText().toString()});
        bd.close();
        //Envio la respuesta al usuario
        if (respuesta == -1){
            Toast.makeText(this,"Dato No Modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Dato Modificado", Toast.LENGTH_LONG).show();
        }
        limpiar();
        CargarLista();
    }

    //Metodo para Eliminar un Registro
    public void onClickEliminar(View view){
        //Conecto la BDD para eliminar un registro de tabla alumno
        DataHelper dh = new DataHelper(this, "recetas.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        //Ingreso el rut del registro a modificar
        String idEliminar = edtId.getText().toString();
        //Query para eliminar el registro
        long respuesta = bd.delete("recetas", "id=" + idEliminar, null);
        bd.close();
        //Verifico que el registro se elimine
        if (respuesta == -1){
            Toast.makeText(this,"Dato No Eliminado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Dato Eliminado", Toast.LENGTH_LONG).show();
        }
        limpiar();
        CargarLista();

    }

    public void onClickvolver(View view){
        Intent intent = new Intent(this, AccesoActivity.class);
        startActivity(intent);
    }
}