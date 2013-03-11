package com.infomovil.quiz1vs1;

import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.infomovil.quiz1vs1.aplicacion.PreguntasActivity;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicasAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicosAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.UsuariosPendientesAdapter;
import com.infomovil.quiz1vs1.modelo.LoginUsuario;
import com.infomovil.quiz1vs1.modelo.Pregunta;
import com.infomovil.quiz1vs1.modelo.Usuario;

public class Quiz1vs1Activity extends Activity {
	
	private static ViewFlipper vf;
	private ListView listaPartidasPendientes;
	private ListView listaPartidasEnviadas;
	
	//REGISTRO1.XML
	private EditText editTextEmail;
	private EditText editTextNombre;
	private EditText editTextApellido;
	private EditText editTextNick;
	private EditText editTextCiudad;
	private Spinner spinnerPaises;
	private Button botonSiguiente;
	
	//REGISTRO2.XML
	private Button botonGuardar;
	private ImageView imagenAvatar;
	
	//AJUSTES.XML Y PERFIL.XML 
	private Button botonAtrasAjustes;
	private Button botonAtrasPerfil;
	private Button botonPerfil;
	private Button botonGuardarPerfil;
	private Button botonGuardarAjustes;
	private ImageButton botonAjustes;	
	private ImageButton botonNuevaPartida;
	private RadioButton radioButtonChicos;
	private RadioButton radioButtonChicas;
	private GridView gridview;
	
	private Button botonAtrasElegirContrincante;
	private Button botonAleatorio;
	private Button botonAmigo;
	
	
	private static Handler manejador = new Handler(){
		public void handleMessage(Message msg) {
			vf.setDisplayedChild(msg.what);
		};
	};
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);
        
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        vf.setFlipInterval(2000);
        vf.setFadingEdgeLength(200);
        vf.startFlipping();        
        final ProgressDialog pd = ProgressDialog.show(this,"","Cargando...",true, false);
    		new Thread(new Runnable(){
	    		public void run(){
		    		try {		    			
						Thread.sleep(2500);
						//Intent i = new Intent(getBaseContext(), PrincipalActivity.class);
						//startActivity(i);
						//Conectarse al servidor
						conectarAservidor();
						vf.stopFlipping();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		pd.dismiss();	    		
	    		}
    	}).start();
    		
		listaPartidasEnviadas = (ListView) findViewById(R.id.listPartidasEnviadas);
        listaPartidasPendientes = (ListView) findViewById(R.id.listPartidasPendientes);
        
        //REGISTRO1.XML
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellido = (EditText) findViewById(R.id.editTextApellido);
        editTextNick = (EditText) findViewById(R.id.editTextNick);
        editTextCiudad = (EditText) findViewById(R.id.editTextCiudad);
        spinnerPaises = (Spinner) findViewById(R.id.spinnerPaises);
        botonSiguiente = (Button) findViewById(R.id.botonSiguiente);
        
        //REGISTRO2.XML
        botonGuardar = (Button) findViewById(R.id.botonGuardar);
        imagenAvatar = (ImageView) findViewById(R.id.imagenAvatar);
        radioButtonChicas = (RadioButton) findViewById(R.id.radioButtonChicas);
        radioButtonChicos = (RadioButton) findViewById(R.id.radioButtonChicos);
        gridview = (GridView) findViewById(R.id.gridview);
        
        //AJUSTES.XML Y PERFIL.XML
        botonAtrasAjustes = (Button) findViewById(R.id.botonAtrasAjustes);
        botonAtrasPerfil = (Button) findViewById(R.id.botonAtrasPerfil);
        botonPerfil = (Button) findViewById(R.id.botonPerfil);
        botonAjustes = (ImageButton) findViewById(R.id.ajustes);
        botonGuardarPerfil = (Button) findViewById(R.id.botonGuardarPerfil);
        botonGuardarAjustes = (Button) findViewById(R.id.botonGuardarAjustes);
        botonNuevaPartida = (ImageButton) findViewById(R.id.nueva_partida);
        
        //ELEGIRCONTRINCANTE.XML
        botonAtrasElegirContrincante = (Button) findViewById(R.id.botonAtrasElegirContrincante);
        botonAleatorio = (Button) findViewById(R.id.botonAleatorio);
        botonAmigo = (Button) findViewById(R.id.botonAmigo);
        
        botonSiguiente.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(editTextEmail.getText().length()==0)
					Toast.makeText(getApplicationContext(), "El email es obligatorio", Toast.LENGTH_SHORT).show();
				else if(editTextApellido.getText().length()==0)
					Toast.makeText(getApplicationContext(), "El apellido es obligatorio", Toast.LENGTH_SHORT).show();
				else if(editTextNick.getText().length()==0)
					Toast.makeText(getApplicationContext(), "El nick es obligatorio", Toast.LENGTH_SHORT).show();
				else if(editTextNombre.getText().length()==0)
					Toast.makeText(getApplicationContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
				else
					vf.showNext();				
			}
		});
        
        botonGuardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(imagenAvatar.getBackground() == null)
					Toast.makeText(getApplicationContext(), "Seleccione un sexo y luego una imagen como avatar", Toast.LENGTH_SHORT).show();
				else
					vf.showNext();				
			}
		});             

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                imagenAvatar.setBackgroundResource((int)parent.getAdapter().getItemId(position));
            }
        });
        
        radioButtonChicas.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				gridview.setAdapter(new ChicasAdapter(getApplicationContext()));				
			}
		});
        
        radioButtonChicos.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				gridview.setAdapter(new ChicosAdapter(getApplicationContext()));
				
			}
		});
        
        botonAjustes.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(4);			
			}
		});
        
        botonGuardarPerfil.setOnClickListener(new OnClickListener() {        		
        	@Override
        	public void onClick(View v) {
        		//vf.setDisplayedChild(5);				
        	}
        });

        botonNuevaPartida.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Vector<Pregunta> preguntas = LoginUsuario.getPreguntas("Animales");
				for(int i=0; i<preguntas.size();i++){
					Pregunta p = preguntas.get(i);
					System.out.println("PREGUNTA: " + p.getPregunta());
					System.out.println("RESPUESTA1: " + p.getRespuestaCorrecta());
					System.out.println("RESPUESTA2: " + p.getRespuestaIncorrecta1());
					System.out.println("RESPUESTA3: " + p.getRespuestaIncorrecta2());
					System.out.println("RESPUESTA4: " + p.getRespuestaIncorrecta3());
					System.out.println("----------------------------");
				}
				
				Intent i = new Intent(getBaseContext(), PreguntasActivity.class);
				startActivity(i);
			}
		});
        
        botonGuardarAjustes.setOnClickListener(new OnClickListener() {        	
        	@Override
        	public void onClick(View v) {
        		//vf.setDisplayedChild(5);				
        	}
        });        
        
		botonAtrasAjustes.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(3);			
			}
		});

		botonAtrasPerfil.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(4);			
			}
		});
        
		botonPerfil.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(5);				
			}
		});               
        
        Usuario usuariospendientes[] = new Usuario[] {
			new Usuario("Maria", R.drawable.chica12),
			new Usuario("Alejandro", R.drawable.chico5)
        };

        Usuario usuariosrespondidos[] = new Usuario[] {
			new Usuario("Maria", R.drawable.avatar1),
			new Usuario("Alejandro", R.drawable.avatar2),
			new Usuario("Raul", R.drawable.chico13)
        };
                
        UsuariosPendientesAdapter adapter_p = new UsuariosPendientesAdapter(this, R.layout.item, usuariospendientes);
        UsuariosPendientesAdapter adapter_r = new UsuariosPendientesAdapter(this, R.layout.item, usuariosrespondidos);
        listaPartidasPendientes.setAdapter(adapter_p);               
        listaPartidasEnviadas.setAdapter(adapter_r);
    }

    public void conectarAservidor(){
        	final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	String device_id = tm.getDeviceId();
        	if(LoginUsuario.EstaUsuario(device_id))
        		manejador.sendEmptyMessage(3);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_quiz1vs1, menu);
        return true;
    }
    
}
