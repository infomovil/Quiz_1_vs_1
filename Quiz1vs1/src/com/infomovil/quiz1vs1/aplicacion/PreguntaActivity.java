package com.infomovil.quiz1vs1.aplicacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.Pregunta;

public class PreguntaActivity extends Activity {
	
	private ArrayList<Pregunta> preguntas;
	private int numPregunta;
	private String categoria;
	private int resultado;
	private int combo;
	private String jugador1;
	private String jugador2;
	private String marcador;
	private String idPreguntas;
	private boolean esPrimerReto;
	public static final long PUNTUACION_TOTAL = 5000;
	
	private TextView textoCategoria;
	private ImageView imagenPregunta;
	private Chronometer cronometro;
	private Button respuesta1;
	private Button respuesta2;
	private Button respuesta3;
	private Button respuesta4;
	private Bundle bundle;
	
	private Pregunta p;
	
	private DecimalFormat df;
	private long millis;
	private boolean pararCrono;
	
	private Thread contadorCrono;
	
	private Handler manejador = new Handler(){
		public void handleMessage(Message msg) {
			Bundle b = msg.getData();
			boolean correcto = b.getBoolean("correcto");
			int combo = b.getInt("combo");
			mostrarCombo(correcto,combo);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pregunta);
	    bundle = this.getIntent().getExtras();
		categoria = bundle.getString("categoria");
		numPregunta = bundle.getInt("numPregunta");
		preguntas = getIntent().getParcelableArrayListExtra("preguntas");
		resultado = bundle.getInt("resultado");
		combo = bundle.getInt("combo");
		jugador1 = bundle.getString("jugador1");
		jugador2 = bundle.getString("jugador2");
		marcador = bundle.getString("marcador");
		idPreguntas = bundle.getString("idPreguntas");
		esPrimerReto = bundle.getBoolean("esPrimerReto");
		p = preguntas.get(numPregunta);
		System.out.println("RESPONDIENDO PREGUNTA " + numPregunta);
		Vector<String> respuestas = p.getRespuestas();
		byte[] imageAsBytes = Base64.decode(p.getArchivo().getBytes(), Base64.DEFAULT);		
		textoCategoria = (TextView) findViewById(R.id.textoCategoria);
		imagenPregunta = (ImageView) findViewById(R.id.imagenPregunta);
		cronometro = (Chronometer) findViewById(R.id.cronometro);
		respuesta1 = (Button) findViewById(R.id.respuesta1);
		respuesta2 = (Button) findViewById(R.id.respuesta2);
		respuesta3 = (Button) findViewById(R.id.respuesta3);
		respuesta4 = (Button) findViewById(R.id.respuesta4);
		
	    df = new DecimalFormat("0.00"); 
		
	    imagenPregunta.setImageBitmap(
	            BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
	    );
		
		textoCategoria.setText(categoria);
		respuesta1.setText(respuestas.get(0));
		respuesta2.setText(respuestas.get(1));
		respuesta3.setText(respuestas.get(2));
		respuesta4.setText(respuestas.get(3));
		cronometro.setBase(SystemClock.elapsedRealtime());	
		cronometro.start();			
		
		contadorCrono = new Thread(new Runnable() {			
			@Override
			public void run() {
				millis = 0;
				while(millis<5000 && !pararCrono){
					millis = SystemClock.elapsedRealtime() - cronometro.getBase();
				}
				if(millis == 5000){
					String respuesta = "respuesta" + (numPregunta);
					String correcta = "esCorrecta" + (numPregunta);
					String tiempo = "tiempo" + (numPregunta);
					System.out.println(respuesta + " " + correcta + " " + tiempo);
					bundle.putString("categoria", categoria);
					numPregunta++;
					bundle.putInt("numPregunta", numPregunta);
					bundle.putString(respuesta, "Tiempo excedido");
					bundle.putBoolean(correcta, false);
					double time = (double) millis / 1000d;
					bundle.putString(tiempo, df.format(time));
					int puntuacion = (int) (PUNTUACION_TOTAL - millis);
					bundle.putInt("resultado", resultado + puntuacion);
					bundle.putInt("combo", 0);
					bundle.putString("jugador1",jugador1);
					bundle.putString("jugador2", jugador2);
					bundle.putString("marcador", marcador);
					bundle.putString("idPreguntas", idPreguntas);
					bundle.putBoolean("esPrimerReto", esPrimerReto);
					System.out.println("NUMPREGUNTA: " + numPregunta);
					if(numPregunta != 5){
						bundle.putBoolean("final", false);
						Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
						i.putParcelableArrayListExtra("preguntas", preguntas);
						i.putExtras(bundle);
						startActivity(i);
					}
					else{
						bundle.putBoolean("final", true);
						Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
						i.putExtras(bundle);
						startActivity(i);
					}
				}
			}
		});
		contadorCrono.start();		
		respuesta1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cronometro.stop();
				pararCrono = true;
				long miliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
				boolean esCorrecta = p.esCorrecta((String) respuesta1.getText());
				if (esCorrecta) {
					respuesta1.setBackgroundColor(Color.GREEN);
					combo++;
				} else {
					respuesta1.setBackgroundColor(Color.RED);
					combo = 0;
				}
				Bundle b = new Bundle();
				b.putBoolean("correcto", esCorrecta);
				b.putInt("combo", combo);
				Message msg = new Message();
				msg.setData(b);
				manejador.sendMessage(msg);
				String respuesta = "respuesta" + (numPregunta);
				String correcta = "esCorrecta" + (numPregunta);
				String tiempo = "tiempo" + (numPregunta);
				System.out.println(respuesta + " " + correcta + " " + tiempo);
				bundle.putString("categoria", categoria);
				bundle.putInt("numPregunta", numPregunta+1);
				bundle.putString(respuesta, respuesta1.getText().toString());
				bundle.putBoolean(correcta, esCorrecta);
				double time = (double) miliseconds / 1000d;
				int puntuacion = (int) (PUNTUACION_TOTAL - miliseconds);
				bundle.putInt("resultado", resultado + puntuacion * combo);
				bundle.putString(tiempo, df.format(time));
				bundle.putInt("combo", combo);
				bundle.putString("jugador1",jugador1);
				bundle.putString("jugador2", jugador2);
				bundle.putString("marcador", marcador);
				bundle.putString("idPreguntas", idPreguntas);
				bundle.putBoolean("esPrimerReto", esPrimerReto);
				numPregunta++;
				System.out.println("NUMPREGUNTA: " + numPregunta);
				if(numPregunta == 5){
					bundle.putBoolean("final", true);
					Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
					i.putExtras(bundle);
					startActivity(i);
				}
				else{
					bundle.putBoolean("final", false);
					Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
					i.putParcelableArrayListExtra("preguntas", preguntas);
					i.putExtras(bundle);
					startActivity(i);
				}
			}
		});

		respuesta2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cronometro.stop();
				pararCrono = true;
				long miliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
				boolean esCorrecta = p.esCorrecta((String) respuesta2.getText());
				if (esCorrecta) {
					respuesta2.setBackgroundColor(Color.GREEN);
					combo++;
				} else {
					respuesta2.setBackgroundColor(Color.RED);
					combo = 0;
				}
				Bundle b = new Bundle();
				b.putBoolean("correcto", esCorrecta);
				b.putInt("combo", combo);
				Message msg = new Message();
				msg.setData(b);
				manejador.sendMessage(msg);
				String respuesta = "respuesta" + (numPregunta);
				String correcta = "esCorrecta" + (numPregunta);
				String tiempo = "tiempo" + (numPregunta);
				System.out.println(respuesta + " " + correcta + " " + tiempo);
				bundle.putString("categoria", categoria);
				bundle.putInt("numPregunta", numPregunta+1);
				bundle.putString(respuesta, respuesta2.getText().toString());
				bundle.putBoolean(correcta, esCorrecta);
				double time = (double) miliseconds / 1000d;
				bundle.putString(tiempo, df.format(time));
				int puntuacion = (int) (PUNTUACION_TOTAL - miliseconds);
				bundle.putInt("resultado", resultado + puntuacion * combo);
				bundle.putInt("combo", combo);
				bundle.putString("jugador1",jugador1);
				bundle.putString("jugador2", jugador2);
				bundle.putString("marcador", marcador);
				bundle.putString("idPreguntas", idPreguntas);
				bundle.putBoolean("esPrimerReto", esPrimerReto);
				numPregunta++;
				System.out.println("NUMPREGUNTA: " + numPregunta);
				if(numPregunta == 5){
					bundle.putBoolean("final", true);
					Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
					i.putExtras(bundle);
					startActivity(i);
				}
				else{
					bundle.putBoolean("final", false);
					Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
					i.putParcelableArrayListExtra("preguntas", preguntas);
					i.putExtras(bundle);
					startActivity(i);
				}
			}
		});

		respuesta3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cronometro.stop();
				pararCrono = true;
				long miliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
				boolean esCorrecta = p.esCorrecta((String) respuesta3.getText());
				if (esCorrecta) {
					respuesta3.setBackgroundColor(Color.GREEN);
					combo++;
				} else {
					respuesta3.setBackgroundColor(Color.RED);
					combo = 0;
				}
				Bundle b = new Bundle();
				b.putBoolean("correcto", esCorrecta);
				b.putInt("combo", combo);
				Message msg = new Message();
				msg.setData(b);
				manejador.sendMessage(msg);
				String respuesta = "respuesta" + (numPregunta);
				String correcta = "esCorrecta" + (numPregunta);
				String tiempo = "tiempo" + (numPregunta);
				System.out.println(respuesta + " " + correcta + " " + tiempo);
				bundle.putString("categoria", categoria);
				bundle.putInt("numPregunta", numPregunta+1);
				bundle.putString(respuesta, respuesta3.getText().toString());
				bundle.putBoolean(correcta, esCorrecta);
				double time = (double) miliseconds / 1000d;
				bundle.putString(tiempo, df.format(time));
				int puntuacion = (int) (PUNTUACION_TOTAL - miliseconds);
				bundle.putInt("resultado", resultado + puntuacion * combo);
				bundle.putInt("combo", combo);
				bundle.putString("jugador1",jugador1);
				bundle.putString("jugador2", jugador2);
				bundle.putString("marcador", marcador);
				bundle.putString("idPreguntas", idPreguntas);
				bundle.putBoolean("esPrimerReto", esPrimerReto);
				numPregunta++;
				System.out.println("NUMPREGUNTA: " + numPregunta);
				if(numPregunta == 5){
					bundle.putBoolean("final", true);
					Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
					i.putExtras(bundle);
					startActivity(i);
				}
				else{
					bundle.putBoolean("final", false);
					Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
					i.putParcelableArrayListExtra("preguntas", preguntas);
					i.putExtras(bundle);
					startActivity(i);
				}
			}
		});

		respuesta4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cronometro.stop();
				pararCrono = true;
				long miliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
				boolean esCorrecta = p.esCorrecta((String) respuesta4.getText());
				if (esCorrecta) {
					respuesta4.setBackgroundColor(Color.GREEN);
					combo++;
				} else {
					respuesta4.setBackgroundColor(Color.RED);
					combo = 0;
				}
				Bundle b = new Bundle();
				b.putBoolean("correcto", esCorrecta);
				b.putInt("combo", combo);
				Message msg = new Message();
				msg.setData(b);
				manejador.sendMessage(msg);
				String respuesta = "respuesta" + (numPregunta);
				String correcta = "esCorrecta" + (numPregunta);
				String tiempo = "tiempo" + (numPregunta);
				System.out.println(respuesta + " " + correcta + " " + tiempo);
				bundle.putString("categoria", categoria);
				bundle.putInt("numPregunta", numPregunta+1);
				bundle.putString(respuesta, respuesta4.getText().toString());
				bundle.putBoolean(correcta, esCorrecta);
				double time = (double) miliseconds / 1000d;
				bundle.putString(tiempo, df.format(time));				
				int puntuacion = (int) (PUNTUACION_TOTAL - miliseconds);
				bundle.putInt("resultado", resultado + puntuacion * combo);
				bundle.putInt("combo", combo);
				bundle.putString("jugador1",jugador1);
				bundle.putString("jugador2", jugador2);
				bundle.putString("marcador", marcador);
				bundle.putString("idPreguntas", idPreguntas);
				bundle.putBoolean("esPrimerReto", esPrimerReto);
				numPregunta++;
				System.out.println("NUMPREGUNTA: " + numPregunta);
				if(numPregunta == 5){
					bundle.putBoolean("final", true);
					Intent i = new Intent(getApplicationContext(), PreguntasActivity.class);
					i.putExtras(bundle);
					startActivity(i);
				}
				else{
					bundle.putBoolean("final", false);
					Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
					i.putParcelableArrayListExtra("preguntas", preguntas);
					i.putExtras(bundle);
					startActivity(i);
				}
			}
		});
	}
	
	
	private void mostrarCombo(boolean correcto, int combo){
		String texto = "";
		if(correcto){
			switch (combo) {
			case 1:
				texto = "　ACERTASTE!!";
				break;
			case 2:
				texto = "　BIEN!! 2 SEGUIDAS!!";
				break;
			case 3:
				texto = "　ENORME!! 3 SEGUIDAS!!";
				break;
			case 4:
				texto = "　PERFECTO!!";
				break;
			default:
				break;
			}
		}
		else{
			texto = "OH QUE PENA...";
		}
		View layout_combo = getLayoutInflater().inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
		TextView txtCombo = (TextView) layout_combo.findViewById(R.id.txtCombo);
		txtCombo.setText(texto);
		Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout_combo);
        toast.show();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
