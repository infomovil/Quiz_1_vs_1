package com.infomovil.quiz1vs1.aplicacion;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.LoginUsuario;
import com.infomovil.quiz1vs1.modelo.Pregunta;

public class ResponderRetoActivity extends Activity {
	
	private ViewFlipper pantallasResponderReto;
	private Button btnResponderReto;
	private TextView textoUsuario;
	private TextView categoria;
	private Bundle bundle;
	private String nombreUsuario;
	private String categoriaUsuario;
	private String idPreguntas;
	private String idUsuario;
	private String contrincante;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper_responder_reto);
		bundle = this.getIntent().getExtras();
		nombreUsuario = bundle.getString("nombreUsuario");
		categoriaUsuario = bundle.getString("categoria");
		idPreguntas = bundle.getString("idpreguntas");
		idUsuario = bundle.getString("jugador1");
		contrincante = bundle.getString("jugador2");
		pantallasResponderReto = (ViewFlipper) findViewById(R.id.pantallasResponderReto);
		btnResponderReto = (Button) findViewById(R.id.btnResponderReto);
		textoUsuario = (TextView) findViewById(R.id.txtUsuario);
		categoria = (TextView) findViewById(R.id.txtNombreCategoria);
		textoUsuario.setText(nombreUsuario + " ha elegido:");
		categoria.setText(categoriaUsuario);
		pantallasResponderReto.setDisplayedChild(0);
		
		
		
		btnResponderReto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("categoria", categoriaUsuario);
				bundle.putInt("numPregunta", 0);
				bundle.putInt("resultado", 0);
				bundle.putInt("combo", 0);
				bundle.putString("jugador1",idUsuario);
				bundle.putString("jugador2", contrincante);
				//bundle.putString("marcador", marcador);
				Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
				ArrayList<Pregunta> preguntas = LoginUsuario.getPreguntasDeId(idPreguntas);
				System.out.println("TAMAÑO: " + preguntas.size());
				bundle.putString("idPreguntas", idPreguntas);
				i.putParcelableArrayListExtra("preguntas", preguntas);
				i.putExtras(bundle);
				startActivity(i);
			}
		});
		
	}
}
