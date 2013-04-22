package com.infomovil.quiz1vs1.aplicacion.adapters;

import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.Logro;
import com.infomovil.quiz1vs1.modelo.Usuario;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LogrosAdapter extends ArrayAdapter<Logro> {

	Context context;
	private int resource_imagen;
	private Logro[] logros = null;
	
	static class LogrosHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
	
	public LogrosAdapter(Context context, int resource_imagen, Logro[] objects) {
		super(context, resource_imagen, objects);
		this.context = context;
		this.resource_imagen = resource_imagen;
		this.logros = objects;		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LogrosHolder holder = null;
        View row = convertView;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource_imagen, parent, false);
            
            holder = new LogrosHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imagenUsuario);
            holder.txtTitle = (TextView)row.findViewById(R.id.nombreUsuario);
            
            row.setTag(holder);
        } else {
            holder = (LogrosHolder)row.getTag();
        }
        Logro logro = logros[position];
        if(logro != null){
	        holder.txtTitle.setText(logro.getNombreLogro());
	        holder.imgIcon.setImageResource(logro.getImagen());
        }
        else{
        }
        return row;
    }
}
