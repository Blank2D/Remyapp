package com.example.remyapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;


public class DataHelper extends SQLiteOpenHelper{
    public DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version+1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE recetas(id INTEGER PRIMARY KEY, nombre TEXT, precio TEXT, tipoDePlato TEXT, nota INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS recetas ");
        db.execSQL("CREATE TABLE recetas(id INTEGER PRIMARY KEY, nombre TEXT, precio TEXT, tipoDePlato TEXT, nota INTEGER)");
    }
}
