package com.infomovil.quiz1vs1.aplicacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.infomovil.quiz1vs1.Quiz1vs1Activity;
import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.LoginUsuario;
import com.infomovil.quiz1vs1.modelo.Pregunta;
import com.infomovil.quiz1vs1.modelo.Usuario;

public class PreguntasActivity extends Activity {

	private Button botonAleatorio;
	private Button botonAtrasElegirContrincante;
	private Button botonAmigo;
	
	
	
	private ViewFlipper pantallasPreguntas;
	private ListView listViewCategorias;
	private int puntuacion;
	private String categoria;
	private String contrincante;
	private String idUsuario;
	private String idPreguntas;
	
	private Button enviarReto;
	private TextView puntuacionTotal;	
	private TextView textoPregunta1;
	private ImageView correcta1;
	private TextView tiempo1;
	private TextView textoPregunta2;
	private ImageView correcta2;
	private TextView tiempo2;
	private TextView textoPregunta3;
	private ImageView correcta3;
	private TextView tiempo3;
	private TextView textoPregunta4;
	private ImageView correcta4;
	private TextView tiempo4;
	private TextView textoPregunta5;
	private ImageView correcta5;
	private TextView tiempo5;
		
	
	private String txtPregunta1;
	private String txtPregunta2;
	private String txtPregunta3;
	private String txtPregunta4;
	private String txtPregunta5;
	private boolean esCorrecta1;
	private boolean esCorrecta2;
	private boolean esCorrecta3;
	private boolean esCorrecta4;
	private boolean esCorrecta5;
	private String txtTiempo1;
	private String txtTiempo2;
	private String txtTiempo3;
	private String txtTiempo4;
	private String txtTiempo5;
	
	private String marcador = "";
	
	private Bundle bundle;
	
	private Spinner spinnerBusqueda;
	private TextView valorBusqueda;
	private Button botonBuscarContrincante;
	private ListView listaAdversarios;
	
	private boolean fin = false;
	private boolean esPrimerReto;
	private boolean respondiendo;
	
	private Vector<Usuario> usuarios = new Vector<Usuario>();
	/*private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			pantallasPreguntas.setDisplayedChild(msg.what);
			rellenarPregunta();
		};
	};*/
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper_preguntas);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		bundle = this.getIntent().getExtras();
		fin = bundle.getBoolean("final");
		esPrimerReto = bundle.getBoolean("esPrimerReto");
		respondiendo = bundle.getBoolean("respondiendo");
		System.out.println("RESPONDIENDO: " + respondiendo);
		//ELEGIRCONTRINCANTE.XML
        botonAtrasElegirContrincante = (Button) findViewById(R.id.botonAtrasElegirContrincante);
        botonAmigo = (Button) findViewById(R.id.botonAmigo);
		botonAleatorio = (Button) findViewById(R.id.botonAleatorio);
		
		pantallasPreguntas = (ViewFlipper) findViewById(R.id.pantallasPreguntas);		
		listViewCategorias = (ListView) findViewById(R.id.listViewCategorias);

		puntuacionTotal = (TextView) findViewById(R.id.puntuacionTotal);
		textoPregunta1 = (TextView) findViewById(R.id.textoPregunta1);
		textoPregunta2 = (TextView) findViewById(R.id.textoPregunta2);
		textoPregunta3 = (TextView) findViewById(R.id.textoPregunta3);
		textoPregunta4 = (TextView) findViewById(R.id.textoPregunta4);
		textoPregunta5 = (TextView) findViewById(R.id.textoPregunta5);
		correcta1 = (ImageView) findViewById(R.id.correcta1);
		correcta2 = (ImageView) findViewById(R.id.correcta2);
		correcta3 = (ImageView) findViewById(R.id.correcta3);
		correcta4 = (ImageView) findViewById(R.id.correcta4);
		correcta5 = (ImageView) findViewById(R.id.correcta5);
		tiempo1 = (TextView) findViewById(R.id.tiempo1);
		tiempo2 = (TextView) findViewById(R.id.tiempo2);
		tiempo3 = (TextView) findViewById(R.id.tiempo3);
		tiempo4 = (TextView) findViewById(R.id.tiempo4);
		tiempo5 = (TextView) findViewById(R.id.tiempo5);
		enviarReto = (Button) findViewById(R.id.enviarReto);
		
		//BUSCAR_ADVERSARIO.XML
		spinnerBusqueda = (Spinner) findViewById(R.id.spinnerBusqueda);
		valorBusqueda = (TextView) findViewById(R.id.valorBusqueda);
		botonBuscarContrincante = (Button) findViewById(R.id.botonBuscarContrincante);
		listaAdversarios = (ListView) findViewById(R.id.listaAdversarios);
		
		
		if(fin){
			cargarDatosPuntuacion(bundle);
			pantallasPreguntas.setDisplayedChild(2);
		} 
		if(respondiendo && !fin){
			System.out.println("entro por respondiendo");
			pantallasPreguntas.setDisplayedChild(1);
			Vector<String> listaCategorias = LoginUsuario.getCategorias();
			listViewCategorias.setAdapter(new ArrayAdapter<String>(this,
					R.layout.item_categorias, listaCategorias));
			listViewCategorias.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int arg2,
						long arg3) {								
					categoria = ((TextView) view).getText().toString();
					if(categoria.equals("Animales") || categoria.equals("Calculo")){						
						Bundle bundle2 = new Bundle();
						bundle2.putString("categoria", categoria);
						bundle2.putInt("numPregunta", 0);
						bundle2.putInt("resultado", 0);
						bundle2.putInt("combo", 0);
						idUsuario = bundle.getString("jugador1");
						contrincante = bundle.getString("jugador2");
						System.out.println("idusuario: " + idUsuario + " contrincante: " + contrincante);
						bundle2.putString("jugador1",idUsuario);
						bundle2.putString("jugador2", contrincante);
						bundle2.putString("marcador", marcador);
						bundle2.putBoolean("esPrimerReto", esPrimerReto);
						bundle2.putBoolean("respondiendo", respondiendo);
						Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
						//ArrayList<Pregunta> preguntas = LoginUsuario.getPreguntas(categoria);
						//idPreguntas = getIdPreguntas(preguntas);
						//bundle2.putString("idPreguntas", idPreguntas);
						//i.putParcelableArrayListExtra("preguntas", preguntas);
						i.putExtras(bundle2);
						startActivity(i);
					}
					else
						Toast.makeText(getApplicationContext(), "No hay preguntas para esta categoría todavía", Toast.LENGTH_SHORT).show();
				}
			});
		}
		if (esPrimerReto){
			System.out.println("entro por primer reto");
			Vector<String> listaCategorias = LoginUsuario.getCategorias();
			listViewCategorias.setAdapter(new ArrayAdapter<String>(this,
					R.layout.item_categorias, listaCategorias));
			listViewCategorias.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int arg2,
						long arg3) {								
					categoria = ((TextView) view).getText().toString();
					if(categoria.equals("Animales") || categoria.equals("Calculo")){						
						Bundle bundle = new Bundle();
						bundle.putString("categoria", categoria);
						bundle.putInt("numPregunta", 0);
						bundle.putInt("resultado", 0);
						bundle.putInt("combo", 0);
						bundle.putString("jugador1",idUsuario);
						bundle.putString("jugador2", contrincante);
						bundle.putString("marcador", marcador);
						bundle.putBoolean("esPrimerReto", esPrimerReto);
						bundle.putBoolean("respondiendo", false);
						Intent i = new Intent(getApplicationContext(), PreguntaActivity.class);
						//ArrayList<Pregunta> preguntas = LoginUsuario.getPreguntas(categoria);
						//System.out.println("sigo despues de recoger preguntas");
						//idPreguntas = getIdPreguntas(preguntas);
						//bundle.putString("idPreguntas", idPreguntas);
						//i.putParcelableArrayListExtra("preguntas", preguntas);
						i.putExtras(bundle);
						startActivity(i);
					}
					else
						Toast.makeText(getApplicationContext(), "No hay preguntas para esta categoría todavía", Toast.LENGTH_SHORT).show();
				}
			});
		}

		botonAleatorio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String device_id = tm.getDeviceId();
				if(device_id == null){
					device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
				}
				contrincante = LoginUsuario.buscarAleatorio(device_id);
				idUsuario = LoginUsuario.getUserId(device_id);				
				marcador = LoginUsuario.registrarPartida(String.valueOf(idUsuario), String.valueOf(contrincante), String.valueOf(0), String.valueOf(0));
				pantallasPreguntas.setDisplayedChild(1);				
			}
		});
		
		botonAmigo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pantallasPreguntas.setDisplayedChild(3);
				
			}
		});
		
		botonAtrasElegirContrincante.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), Quiz1vs1Activity.class);
				i.putExtra("atras", true);
				startActivity(i);
			}
		});
		
		botonBuscarContrincante.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String campo_busqueda = spinnerBusqueda.getSelectedItem().toString();
				String valor_busqueda = valorBusqueda.getText().toString();
				usuarios = LoginUsuario.buscarAdversario(campo_busqueda, valor_busqueda);
				Vector<String> adversarios = new Vector<String>();
				for(int i=0; i<usuarios.size(); i++){
					adversarios.add(usuarios.get(i).getNick());
				}
				
				listaAdversarios.setAdapter(new ArrayAdapter<String>
				(getApplicationContext(),R.layout.item_categorias, adversarios));	
			}
		});
		
		listaAdversarios.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String device_id = tm.getDeviceId();
				if(device_id == null){
					device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
				}
				int idAdversario = usuarios.get(position).getId(); 
				contrincante = String.valueOf(idAdversario);
				idUsuario = LoginUsuario.getUserId(device_id);				
				marcador = LoginUsuario.registrarPartida(String.valueOf(idUsuario), String.valueOf(contrincante), String.valueOf(0), String.valueOf(0));
				pantallasPreguntas.setDisplayedChild(1);
			}
		});
		
		enviarReto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				idUsuario = bundle.getString("jugador1");
				contrincante = bundle.getString("jugador2");
				marcador = bundle.getString("marcador");
				idPreguntas = bundle.getString("idPreguntas");
				categoria = bundle.getString("categoria");
				boolean esPrimerReto = bundle.getBoolean("esPrimerReto");
				boolean respondiendo = bundle.getBoolean("respondiendo");
				System.out.println(idUsuario + "\n" + contrincante + "\n" + String.valueOf(puntuacion) + "\n" + esPrimerReto + "\n" + respondiendo);
				if(esPrimerReto){
					System.out.println("es el primer reto");
					LoginUsuario.registrarResultadoPartida(marcador, idUsuario, String.valueOf(puntuacion), contrincante, "0", idPreguntas, categoria, "0", "0");
					Intent i = new Intent(getApplicationContext(), Quiz1vs1Activity.class);
					i.putExtra("respondido", true);
					startActivity(i);
				}
				else if(respondiendo){
					System.out.println("es el primer reto");
					LoginUsuario.registrarResultadoPartida(marcador, idUsuario, String.valueOf(puntuacion), contrincante, "0", idPreguntas, categoria, "0", "0");
					Intent i = new Intent(getApplicationContext(), Quiz1vs1Activity.class);
					i.putExtra("respondido", true);
					startActivity(i);
				}
				else{
					Intent i = new Intent(getApplicationContext(), ResponderRetoActivity.class);
					i.putExtra("jugador1", idUsuario);
					i.putExtra("jugador2", contrincante);
					i.putExtra("puntuacion", puntuacion);
					i.putExtra("mostrarResultado", true);
					String idMarcador = LoginUsuario.getIDMarcador(idUsuario, contrincante);
					System.out.println("ID MARCADOR: " + idMarcador);
					i.putExtra("idMarcador", idMarcador);
					LoginUsuario.actualizarResultadoPartida(idUsuario, contrincante, String.valueOf(puntuacion));
					startActivity(i);
				}
				
			}
		});
	}

	private void cargarDatosPuntuacion(Bundle bundle) {
		puntuacion = bundle.getInt("resultado");
		txtPregunta1 = bundle.getString("respuesta0");
		txtPregunta2 = bundle.getString("respuesta1");
		txtPregunta3 = bundle.getString("respuesta2");
		txtPregunta4 = bundle.getString("respuesta3");
		txtPregunta5 = bundle.getString("respuesta4");
		esCorrecta1 = bundle.getBoolean("esCorrecta0");
		esCorrecta2 = bundle.getBoolean("esCorrecta1");
		esCorrecta3 = bundle.getBoolean("esCorrecta2");
		esCorrecta4 = bundle.getBoolean("esCorrecta3");
		esCorrecta5 = bundle.getBoolean("esCorrecta4");
		txtTiempo1 = bundle.getString("tiempo0");
		txtTiempo2 = bundle.getString("tiempo1");
		txtTiempo3 = bundle.getString("tiempo2");
		txtTiempo4 = bundle.getString("tiempo3");
		txtTiempo5 = bundle.getString("tiempo4");
		
		puntuacionTotal.setText(puntuacion + " puntos");
		textoPregunta1.setText(txtPregunta1);
		textoPregunta2.setText(txtPregunta2);
		textoPregunta3.setText(txtPregunta3);
		textoPregunta4.setText(txtPregunta4);
		textoPregunta5.setText(txtPregunta5);
		if(esCorrecta1)
			correcta1.setImageResource(R.drawable.correcto);
		else
			correcta1.setImageResource(R.drawable.error);
		if(esCorrecta2)
			correcta2.setImageResource(R.drawable.correcto);
		else
			correcta2.setImageResource(R.drawable.error);
		if(esCorrecta3)
			correcta3.setImageResource(R.drawable.correcto);
		else
			correcta3.setImageResource(R.drawable.error);
		if(esCorrecta4)
			correcta4.setImageResource(R.drawable.correcto);
		else
			correcta4.setImageResource(R.drawable.error);
		if(esCorrecta5)
			correcta5.setImageResource(R.drawable.correcto);
		else
			correcta5.setImageResource(R.drawable.error);
		tiempo1.setText(txtTiempo1);
		tiempo2.setText(txtTiempo2);
		tiempo3.setText(txtTiempo3);
		tiempo4.setText(txtTiempo4);
		tiempo5.setText(txtTiempo5);
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
