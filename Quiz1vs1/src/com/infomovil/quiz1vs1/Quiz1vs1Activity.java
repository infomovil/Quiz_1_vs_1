package com.infomovil.quiz1vs1;


import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.infomovil.quiz1vs1.aplicacion.PreguntasActivity;
import com.infomovil.quiz1vs1.aplicacion.ResponderRetoActivity;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicasAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicosAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.UsuariosPendientesAdapter;
import com.infomovil.quiz1vs1.modelo.LoginUsuario;
import com.infomovil.quiz1vs1.modelo.Preferencias;
import com.infomovil.quiz1vs1.modelo.Usuario;

public class Quiz1vs1Activity extends Activity {
	
	private Vector<Usuario> partidasPendientes;
	private Vector<Usuario> partidasRespondidas;
	
	
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
	private ToggleButton notificaciones;
	private ToggleButton sonido;
	
	private Preferencias preferencias; 
	
	private static Handler manejador = new Handler(){
		public void handleMessage(Message msg) {
			vf.setDisplayedChild(msg.what);
		};
	};
	
	private String device_id = "";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);
        
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        
        
        boolean respondido = this.getIntent().getBooleanExtra("respondido", false);
        if(respondido)
        	vf.setDisplayedChild(3);
        	
        final ProgressDialog pd = ProgressDialog.show(this,"","Cargando...",true, false);
    		new Thread(new Runnable(){
	    		public void run(){
		    		try {		    			
						Thread.sleep(2000);
						if(conectarAservidor()){
							manejador.sendEmptyMessage(3);							
						}
						else
							manejador.sendEmptyMessage(1);						
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
        notificaciones = (ToggleButton) findViewById(R.id.toggleButtonNotificaciones);
        sonido = (ToggleButton) findViewById(R.id.toggleButtonSonido);
        
        
        preferencias = new Preferencias(getApplicationContext());
        loadPreferenceValues();
        preferencias.getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenecChangeListener);
        
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
				else{					
		        	int imagenID = (Integer) imagenAvatar.getTag();
		        	String imagen = "" + imagenID;
		        	System.out.println("IMAGEN ID: " + imagen);
		        	LoginUsuario.registroUsuario(editTextEmail.getText().toString(), editTextNombre.getText().toString(),
		        			editTextApellido.getText().toString(), editTextNick.getText().toString(), spinnerPaises.getSelectedItem().toString()
		        			, editTextCiudad.getText().toString(), imagen, device_id);
					vf.showNext();
				}
			}
		});             

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                long iden = parent.getAdapter().getItemId(position);
                System.out.println(iden);
            	imagenAvatar.setBackgroundResource((int)parent.getAdapter().getItemId(position));
                imagenAvatar.setTag((int)parent.getAdapter().getItemId(position));
                
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
        		LoginUsuario.actualizarUsuario(editTextNombre.getText().toString(), editTextApellido.getText().toString(),
        				spinnerPaises.getSelectedItem().toString(), editTextCiudad.getText().toString());				
        	}
        });

        botonNuevaPartida.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), PreguntasActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("final", false);
				i.putExtras(bundle);
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
			/*new Usuario("Maria", R.drawable.chica12),
			new Usuario("Alejandro", R.drawable.chico5)*/
        };

        Usuario usuariosrespondidos[] = new Usuario[] {
			/*new Usuario("Maria", R.drawable.avatar1),
			new Usuario("Alejandro", R.drawable.avatar2),
			new Usuario("Raul", R.drawable.chico13)*/
        };
        final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		device_id = tm.getDeviceId();
		partidasPendientes = LoginUsuario.getPartidasPendientes(device_id);
		partidasRespondidas = LoginUsuario.getPartidasRespondidas(device_id);
		
		if(partidasPendientes != null){
			usuariospendientes = new Usuario[partidasPendientes.size()];
			for(int i = 0; i < partidasPendientes.size(); i++){
				Usuario u = partidasPendientes.get(i);	
				usuariospendientes[i] = partidasPendientes.get(i);
			}
		}
		if(partidasRespondidas != null){
			usuariosrespondidos = new Usuario[partidasRespondidas.size()];
			for(int i = 0; i < partidasRespondidas.size(); i++){
				Usuario u = partidasRespondidas.get(i);				
				usuariosrespondidos[i] = u;
			}
		}
        UsuariosPendientesAdapter adapter_p = new UsuariosPendientesAdapter(this, R.layout.item, usuariospendientes);
        UsuariosPendientesAdapter adapter_r = new UsuariosPendientesAdapter(this, R.layout.item, usuariosrespondidos);
        listaPartidasPendientes.setAdapter(adapter_p);          
        listaPartidasEnviadas.setAdapter(adapter_r);
        
        listaPartidasPendientes.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View view, int arg2,
        			long arg3) {
        		
        		TextView nombre = (TextView) view.findViewById(R.id.nombreUsuario);
        		String nombreUsuario = nombre.getText().toString();
        		String idUsuario = LoginUsuario.getUserId(device_id);
        		String contrincante = LoginUsuario.getUserIdNick(nombreUsuario);
        		String categoria = LoginUsuario.getCategoriaUsuario(idUsuario, contrincante);
        		String idPreguntas = LoginUsuario.getPreguntasReto(idUsuario, contrincante);
        		Intent i = new Intent(getBaseContext(), ResponderRetoActivity.class);
        		System.out.println("IDUSUARIO: " + idUsuario);
        		System.out.println("CONTRINCANTE: " + contrincante);
        		System.out.println("CATEGORIA: " + categoria);
        		System.out.println("IDPREGUNTAS: " + idPreguntas);
        		System.out.println("NOMBREUSUARIO: " + nombreUsuario);
				Bundle bundle = new Bundle();
				bundle.putBoolean("esPrimerReto", false);
				bundle.putString("nombreUsuario", nombreUsuario);
				bundle.putString("categoria", categoria);
				bundle.putString("idpreguntas", idPreguntas);
				bundle.putString("jugador1", idUsuario);
				bundle.putString("jugador2", contrincante);
				bundle.putBoolean("mostrarResultado", false);
				i.putExtras(bundle);
				startActivity(i);
        		//Toast.makeText(getApplicationContext(), nombreUsuario + "\n " + categoria + "\n " + idPreguntas, Toast.LENGTH_SHORT).show();
        	}
		});
    }
	
	/**
	 * Loads the preference values and updates their enable status and summary.
	 */
	private void loadPreferenceValues() {
		Preferencias preferencias = new Preferencias(getApplicationContext());
		notificaciones.setChecked(preferencias.isNotificationEnabled());
		sonido.setChecked(preferencias.isNotificationEnabled());
	}
    
    public boolean conectarAservidor(){
        	final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        	String device_id = tm.getDeviceId();
        	return LoginUsuario.estaUsuario(device_id);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_quiz1vs1, menu);
        return true;
    }
    
    private SharedPreferences.OnSharedPreferenceChangeListener preferenecChangeListener = new OnSharedPreferenceChangeListener() {
		/*
		 * (non-Javadoc)
		 * @see android.content.SharedPreferences.OnSharedPreferenceChangeListener#onSharedPreferenceChanged(android.content.SharedPreferences, java.lang.String)
		 */
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			
		}
	};
		
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
