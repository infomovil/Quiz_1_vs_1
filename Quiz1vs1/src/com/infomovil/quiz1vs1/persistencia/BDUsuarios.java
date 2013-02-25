package com.infomovil.quiz1vs1.persistencia;

import com.infomovil.quiz1vs1.modelo.Usuario;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BDUsuarios {
	
	  private SQLiteDatabase database;
	  private BaseDatosInterna dbHelper;
	  
	  public BDUsuarios(Context context) {
	    dbHelper = new BaseDatosInterna(context);
	  }
	
	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	
	  public void close() {
	    dbHelper.close();
	  }
	
	  /*public Comment createComment(String comment) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Comment newComment = cursorToComment(cursor);
	    cursor.close();
	    return newComment;
	  }*/
	
	  public void deleteUsuario(Usuario u) {
	    String email = u.getEmail();
	    System.out.println("Usuario deleted with email: " + email);
	    database.delete(BaseDatosInterna.TABLE_USUARIOS,"email = " + email, null);
	  }
	
	  /*public List<Comment> getAllComments() {
	    List<Comment> comments = new ArrayList<Comment>();
	
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, null, null, null, null, null);
	
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return comments;
	  }*/
	
	  private Usuario cursorToUsuario(Cursor cursor) {
	    Usuario u = new Usuario();
	    u.setEmail(cursor.getString(0));
	    u.setNombreUsuario(cursor.getString(1));
	    u.setApellidoUsuario(cursor.getString(2));
	    return u;
	  }
	
	  public boolean estaRegistrado(String email){
		
		  String[] args = new String[] {"usu1"};
		  Cursor c = database.rawQuery(" SELECT usuario,email FROM Usuarios WHERE usuario=? ", args);
		  
		  //Nos aseguramos de que existe al menos un registro
		  /*if (c.moveToFirst()) {
	         	//Recorremos el cursor hasta que no haya más registros
	         	do {
	              	String usuario = c.getString(0);
	              	String email = c.getString(1);
	         	} while(c.moveToNext());
	    	}*/
		  return c.moveToFirst();
	  }
}
