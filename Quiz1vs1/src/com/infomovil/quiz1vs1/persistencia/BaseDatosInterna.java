package com.infomovil.quiz1vs1.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BaseDatosInterna extends SQLiteOpenHelper{
	//Sentencia SQL para crear la tabla de Usuarios
    
    private static final String DATABASE_NAME = "quizChampion.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USUARIOS = "Usuarios";
    
    String sqlCreate = "CREATE TABLE " + TABLE_USUARIOS + "(codigo INTEGER, nombre TEXT)";
    
    public BaseDatosInterna(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public BaseDatosInterna(Context contexto, String nombre,
                               CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente la opci�n de
        //      eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este m�todo deber�a ser m�s elaborado.
 
        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
 
        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }
    
    
    
}