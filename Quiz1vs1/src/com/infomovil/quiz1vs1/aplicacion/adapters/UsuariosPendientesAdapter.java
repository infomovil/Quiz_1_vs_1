package com.infomovil.quiz1vs1.aplicacion.adapters;

import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.Usuario;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UsuariosPendientesAdapter extends ArrayAdapter<Usuario> {

	Context context;
	private int resource_imagen;
	private Usuario[] usuarios = null;
	
	static class UsuariosHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
	
	public UsuariosPendientesAdapter(Context context, int resource_imagen, Usuario[] objects) {
		super(context, resource_imagen, objects);
		this.context = context;
		this.resource_imagen = resource_imagen;
		this.usuarios = objects;		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
        UsuariosHolder holder = null;
        View row = convertView;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource_imagen, parent, false);
            
            holder = new UsuariosHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imagenUsuario);
            holder.txtTitle = (TextView)row.findViewById(R.id.nombreUsuario);
            
            row.setTag(holder);
        } else {
            holder = (UsuariosHolder)row.getTag();
        }
        Usuario usuario = usuarios[position];
        if(usuario != null){
	        holder.txtTitle.setText(usuario.getNombreUsuario());
	        holder.imgIcon.setImageResource(usuario.getResource_image());
        }
        else{
        	holder.txtTitle.setText("No tienes partidas");
        }
        return row;
    }
}