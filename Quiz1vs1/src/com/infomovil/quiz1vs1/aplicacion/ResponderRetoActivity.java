package com.infomovil.quiz1vs1.aplicacion;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		mostrarResultado = bundle.getBoolean("mostrarResultado");
		if(!mostrarResultado)
			pantallasResponderReto.setDisplayedChild(0);
		else
			pantallasResponderReto.setDisplayedChild(1);
		
		
		if(mostrarResultado){
			Vector<Object> puntuaciones = new Vector<Object>();
			puntuaciones = LoginUsuario.getResultadoPartida(idUsuario, contrincante);
			int puntosJ1 = (Integer)puntuaciones.get(1);
			int puntosJ2 = (Integer)puntuaciones.get(2);
			nombreJ1 = (String)puntuaciones.get(3);
			nombreJ2 = (String)puntuaciones.get(4);
			
			nombreJugador1 = (TextView)findViewById(R.id.nombreJugador1);
			puntuacionJugador1 = (TextView)findViewById(R.id.puntuacionJugador1);
			nombreJugador2 = (TextView)findViewById(R.id.nombreJugador2);
			puntuacionJugador2 = (TextView)findViewById(R.id.puntuacionJugador2);
			textResultado = (TextView)findViewById(R.id.textResultado);
			botonContinuar = (Button)findViewById(R.id.botonContinuar);
			
			nombreJugador1.setText(nombreJ1);
			nombreJugador2.setText(nombreJ2);
			puntuacionJugador1.setText(puntosJ1);
			puntuacionJugador2.setText(puntosJ2);
			
			int idResultado = (Integer)puntuaciones.get(0);
			final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			boolean esJugador1 = LoginUsuario.esJugador1(device_id, ""+idResultado);
			if(esJugador1){
				if(puntosJ1>puntosJ2){
					textResultado.setText("HAS GANADO!!!");
					LoginUsuario.actualizarMarcadorJ1(idUsuario, contrincante);
				}
				else{
					textResultado.setText("HAS PERDIDO :(");
					LoginUsuario.actualizarMarcadorJ2(idUsuario, contrincante);
				}
			}
			else{
				if(puntosJ2>puntosJ1){
					textResultado.setText("HAS GANADO!!!");
					LoginUsuario.actualizarMarcadorJ2(idUsuario, contrincante);
				}
				else{
					textResultado.setText("HAS PERDIDO :(");
					LoginUsuario.actualizarMarcadorJ1(idUsuario, contrincante);
				}
			}
				
		}
		botonContinuar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pantallasResponderReto.setDisplayedChild(2);
				
				Vector<Integer> marcadores = new Vector<Integer>();
				marcadores = LoginUsuario.getMarcadorPartida(idUsuario, contrincante);
				
				jugador1 = (TextView)findViewById(R.id.jugador1);
				jugador2 = (TextView)findViewById(R.id.jugador2);
				marcador1 = (TextView)findViewById(R.id.marcador1);
				marcador2 = (TextView)findViewById(R.id.marcador2);
				botonOtraCategoria = (Button)findViewById(R.id.botonOtraCategoria);
				
				marcador1.setText(marcadores.get(0));
				marcador2.setText(marcadores.get(1));
				jugador1.setText(nombreJ1);
				jugador2.setText(nombreJ2);
				
			}
		});
		
		botonOtraCategoria.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
