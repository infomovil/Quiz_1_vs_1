package com.infomovil.quiz1vs1.persistencia;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
 
public class BaseDatosActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BaseDatosInterna usdbh =
            new BaseDatosInterna(this, "DBUsuarios", null, 1);
 
        SQLiteDatabase db = usdbh.getWritableDatabase();
 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Insertamos 5 usuarios de ejemplo
            for(int i=1; i<=5; i++)
            {
                //Generamos los datos
                int codigo = i;
                String nombre = "Usuario" + i;
 
                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                           "VALUES (" + codigo + ", '" + nombre +"')");
            }
 
            //Cerramos la base de datos
            db.close();
        }
    }
}
