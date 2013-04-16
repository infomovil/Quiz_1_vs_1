package com.infomovil.quiz1vs1.aplicacion;

import java.util.ArrayList;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
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
	private boolean mostrarResultado;
	
	//MOSTRAR_PUNTUACION_RETO.XML
	private TextView nombreJugador1;
	private TextView puntuacionJugador1;
	private TextView nombreJugador2;
	private TextView puntuacionJugador2;
	private TextView textResultado;
	private Button botonContinuar;
	
	//MOSTRAR_MARCADOR.XML
	private TextView jugador1;
	private TextView jugador2;
	private TextView marcador1;
	private TextView marcador2;
	private Button botonOtraCategoria;
	
	private String nombreJ1;
	private String nombreJ2;
	private int puntuacion;
	private String idMarcador;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper_responder_reto);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
		bundle = this.getIntent().getExtras();
		nombreUsuario = bundle.getString("nombreUsuario");
		categoriaUsuario = bundle.getString("categoria");
		idPreguntas = bundle.getString("idpreguntas");
		idUsuario = bundle.getString("jugador1");
		contrincante = bundle.getString("jugador2");
		puntuacion = bundle.getInt("puntuacion");
		pantallasResponderReto = (ViewFlipper) findViewById(R.id.pantallasResponderReto);
		btnResponderReto = (Button) findViewById(R.id.btnResponderReto);
		textoUsuario = (TextView) findViewById(R.id.txtUsuario);
		categoria = (TextView) findViewById(R.id.txtNombreCategoria);
		textoUsuario.setText(nombreUsuario + " ha elegido:");
		categoria.setText(categoriaUsuario);
		mostrarResultado = bundle.getBoolean("mostrarResultado");
		idMarcador = bundle.getString("idMarcador");
		
		nombreJugador1 = (TextView)findViewById(R.id.nombreJugador1);
		puntuacionJugador1 = (TextView)findViewById(R.id.puntuacionJugador1);
		nombreJugador2 = (TextView)findViewById(R.id.nombreJugador2);
		puntuacionJugador2 = (TextView)findViewById(R.id.puntuacionJugador2);
		textResultado = (TextView)findViewById(R.id.textResultado);
		botonContinuar = (Button)findViewById(R.id.botonContinuar);
		
		jugador1 = (TextView)findViewById(R.id.jugador1);
		jugador2 = (TextView)findViewById(R.id.jugador2);
		marcador1 = (TextView)findViewById(R.id.marcador1);
		marcador2 = (TextView)findViewById(R.id.marcador2);
		botonOtraCategoria = (Button)findViewById(R.id.botonOtraCategoria);
		
		if(!mostrarResultado)
			pantallasResponderReto.setDisplayedChild(0);
		else
			pantallasResponderReto.setDisplayedChild(1);
		
		
		if(mostrarResultado){
			Vector<Object> puntuaciones = new Vector<Object>();
			System.out.println("ID1: " + idUsuario);
			System.out.println("ID2: " + contrincante);
			puntuaciones = LoginUsuario.getResultadoPartida(idUsuario, contrincante);
			int puntosJ1 = (Integer)puntuaciones.get(1);
			int puntosJ2 = (Integer)puntuaciones.get(2);
			nombreJ1 = (String)puntuaciones.get(3);
			nombreJ2 = (String)puntuaciones.get(4);
			
			nombreJugador1.setText(nombreJ1);
			nombreJugador2.setText(nombreJ2);
			puntuacionJugador1.setText(""+puntosJ1);
			puntuacionJugador2.setText(""+puntuacion);
			
			int idResultado = (Integer)puntuaciones.get(0);
			final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			if(device_id == null){
				device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
			}

			boolean esJugador1 = LoginUsuario.esJugador1(idMarcador, idUsuario);
			if(puntosJ2>puntosJ1 && esJugador1){
				textResultado.setText("HAS GANADO!!!");										
				LoginUsuario.actualizarMarcadorJ1(idMarcador);
			} else{
				textResultado.setText("HAS GANADO!!!");
				LoginUsuario.actualizarMarcadorJ2(idMarcador);					
			}
			if(puntosJ1>puntosJ2 && esJugador1)
				textResultado.setText("HAS PERDIDO :(");
				LoginUsuario.actualizarMarcadorJ2(idMarcador);
			}
			else{
				textResultado.setText("HAS PERDIDO :(");
				LoginUsuario.actualizarMarcadorJ1(idMarcador);
			}				
			
		botonContinuar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				pantallasResponderReto.setDisplayedChild(2);
				
				Vector<String> marcadores = new Vector<String>();
				marcadores = LoginUsuario.getMarcadorPartida(idMarcador);							
				
				int marcadorJ1 = Integer.parseInt(marcadores.get(0));
				int marcadorJ2 = Integer.parseInt(marcadores.get(1));
				String J1 = marcadores.get(2);
				String J2 = marcadores.get(3);
				
				marcador1.setText(""+marcadorJ1);
				marcador2.setText(""+marcadorJ2);
				jugador1.setText(J1);
				jugador2.setText(J2);
				
				LoginUsuario.setRespondida(idMarcador);
			}
		});
		
		botonOtraCategoria.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
				i.putExtra("fin", false);
				i.putExtra("respondiendo", true);
				i.putExtra("esPrimerReto", false);
				startActivity(i);
			}
		});
		
		btnResponderReto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("categoria", categoriaUsuario);
				bundle.putInt("numPregunta", 0);
				bundle.putInt("resultado", 0);
				bundle.putInt("combo", 0);
				bundle.putString("jugador1", idUsuario);
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
