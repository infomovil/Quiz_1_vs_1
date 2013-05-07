package com.infomovil.quiz1vs1;

import java.util.Vector;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import com.infomovil.quiz1vs1.aplicacion.PreguntasActivity;
import com.infomovil.quiz1vs1.aplicacion.ResponderRetoActivity;
import com.infomovil.quiz1vs1.aplicacion.ServicioNotificaciones;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicasAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.ChicosAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.LogrosAdapter;
import com.infomovil.quiz1vs1.aplicacion.adapters.UsuariosPendientesAdapter;
import com.infomovil.quiz1vs1.modelo.Logro;
import com.infomovil.quiz1vs1.modelo.Preferencias;
import com.infomovil.quiz1vs1.modelo.PullToRefreshListView;
import com.infomovil.quiz1vs1.modelo.PullToRefreshListView.OnRefreshListener;
import com.infomovil.quiz1vs1.modelo.Usuario;
import com.infomovil.quiz1vs1.persistencia.AccesoBDLogros;
import com.infomovil.quiz1vs1.persistencia.AccesoBDpreguntas;
import com.infomovil.quiz1vs1.persistencia.AccesoBDresultado;
import com.infomovil.quiz1vs1.persistencia.AccesoBDusuario;

public class Quiz1vs1Activity extends Activity {

	private Vector<Usuario> partidasPendientes;
	private Vector<Usuario> partidasRespondidas;

	private static ViewFlipper vf;
	private PullToRefreshListView listaPartidasPendientes;
	private ListView listaPartidasEnviadas;

	// REGISTRO1.XML
	private EditText editTextEmail;
	private EditText editTextNombre;
	private EditText editTextApellido;
	private EditText editTextNick;
	private EditText editTextCiudad;
	private Spinner spinnerPaises;
	private Button botonSiguiente;

	// REGISTRO2.XML
	private Button botonGuardar;
	private ImageView imagenAvatar;

	// AJUSTES.XML Y PERFIL.XML
	private Button botonAtrasAjustes;
	private Button botonAtrasPerfil;
	private Button botonPerfil;
	private Button botonGuardarPerfil;
	private Button botonGuardarAjustes;
	private RadioButton radioButtonChicos;
	private RadioButton radioButtonChicas;
	private GridView gridview;
	private ToggleButton notificaciones;

	// PERFIL.XML
	private EditText editTextNombrePerfil;
	private EditText editTextApellidoPerfil;
	private Spinner spinnerPaisesPerfil;
	private EditText editTextCiudadPerfil;
	private ImageView imagenAvatarPerfil;

	private Preferencias preferencias;

	// PRINCIPAL_LAYOUT.XML
	private ImageButton botonNuevaPartida;
	private ImageButton botonAjustes;
	private ImageButton botonLogros;
	private ImageButton botonRanking;

	// LOGROS Y RANKING
	private Button botonAtrasLogros;
	private Button botonAtrasRanking;
	private TableLayout tablaPuntuaciones;
	private GridView gridViewLogros;

	private static ProgressDialog pd;

	private static Activity actividad;

	private static Context context = null;

	private static Handler manejador = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 99:
				Toast.makeText(context, "Su perfil ha sido modificado",
						Toast.LENGTH_SHORT).show();
				break;
			case 9:
				pd.cancel();
				vf.setDisplayedChild(0);
				Builder alertDialog = new AlertDialog.Builder(context);
				alertDialog.setTitle("Error al iniciar conexion ");
				alertDialog
						.setMessage("Lo sentimos, no se ha podido conectar con el servidor.");
				alertDialog.setPositiveButton("Reintentar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								int conexion = conectarAservidor();
								switch (conexion) {
								case AccesoBDusuario.EXITO:
									manejador.sendEmptyMessage(3);
									break;
								case AccesoBDusuario.USUARIO_NUEVO:
									manejador.sendEmptyMessage(1);
									break;
								case AccesoBDusuario.CONEXION_FALLIDA:
									pd.cancel();
									manejador.sendEmptyMessage(9);
									break;
								default:
									break;
								}
							}
						});
				alertDialog.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								actividad.finish();
							}
						});
				alertDialog.setIcon(R.drawable.error);
				alertDialog.show();
				break;
			default:
				vf.setDisplayedChild(msg.what);
				break;
			}
		};
	};

	private String device_id = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantalla_inicio);
		context = this;
		actividad = this;
		vf = (ViewFlipper) findViewById(R.id.viewFlipper);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		final Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			boolean atras = bundle.getBoolean("atras");
			if (atras)
				vf.setDisplayedChild(3);
		}

		boolean respondido = this.getIntent().getBooleanExtra("respondido",
				false);
		if (respondido)
			vf.setDisplayedChild(3);

		pd = ProgressDialog.show(this, "", "Cargando...", true, false);
		new Thread(new Runnable() {
			public void run() {
				int conexion = conectarAservidor();
				switch (conexion) {
				case AccesoBDusuario.EXITO:
					manejador.sendEmptyMessage(3);
					break;
				case AccesoBDusuario.USUARIO_NUEVO:
					manejador.sendEmptyMessage(1);
					break;
				case AccesoBDusuario.CONEXION_FALLIDA:
					pd.cancel();
					manejador.sendEmptyMessage(9);
					break;
				default:
					break;
				}
				pd.dismiss();
			}
		}).start();

		listaPartidasEnviadas = (ListView) findViewById(R.id.listPartidasEnviadas);
		listaPartidasPendientes = (PullToRefreshListView) findViewById(R.id.listPartidasPendientes);

		// REGISTRO1.XML
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextNombre = (EditText) findViewById(R.id.editTextNombre);
		editTextApellido = (EditText) findViewById(R.id.editTextApellido);
		editTextNick = (EditText) findViewById(R.id.editTextNick);
		editTextCiudad = (EditText) findViewById(R.id.editTextCiudad);
		spinnerPaises = (Spinner) findViewById(R.id.spinnerPaises);
		botonSiguiente = (Button) findViewById(R.id.botonSiguiente);

		// REGISTRO2.XML
		botonGuardar = (Button) findViewById(R.id.botonGuardar);
		imagenAvatar = (ImageView) findViewById(R.id.imagenAvatar);
		radioButtonChicas = (RadioButton) findViewById(R.id.radioButtonChicas);
		radioButtonChicos = (RadioButton) findViewById(R.id.radioButtonChicos);
		gridview = (GridView) findViewById(R.id.gridview);

		// AJUSTES.XML Y PERFIL.XML
		botonAtrasAjustes = (Button) findViewById(R.id.botonAtrasAjustes);
		botonAtrasPerfil = (Button) findViewById(R.id.botonAtrasPerfil);
		botonPerfil = (Button) findViewById(R.id.botonPerfil);
		botonGuardarPerfil = (Button) findViewById(R.id.botonGuardarPerfil);
		botonGuardarAjustes = (Button) findViewById(R.id.botonGuardarAjustes);
		notificaciones = (ToggleButton) findViewById(R.id.toggleButtonNotificaciones);

		// PERFIL.XML
		editTextNombrePerfil = (EditText) findViewById(R.id.editTextNombrePerfil);
		editTextApellidoPerfil = (EditText) findViewById(R.id.editTextApellidoPerfil);
		editTextCiudadPerfil = (EditText) findViewById(R.id.editTextCiudadPerfil);
		spinnerPaisesPerfil = (Spinner) findViewById(R.id.spinnerPaisesPerfil);
		imagenAvatarPerfil = (ImageView) findViewById(R.id.imagenAvatarPerfil);

		// PRINCIPAL_LAYOUT.XML
		botonAjustes = (ImageButton) findViewById(R.id.ajustes);
		botonNuevaPartida = (ImageButton) findViewById(R.id.nueva_partida);
		botonLogros = (ImageButton) findViewById(R.id.logros);
		botonRanking = (ImageButton) findViewById(R.id.ranking);

		// LOGROS Y RANKING
		botonAtrasLogros = (Button) findViewById(R.id.botonAtrasLogros);
		botonAtrasRanking = (Button) findViewById(R.id.atrasRanking);
		tablaPuntuaciones = (TableLayout) findViewById(R.id.tablaPuntuaciones);
		gridViewLogros = (GridView) findViewById(R.id.gridviewLogros);

		preferencias = new Preferencias(getApplicationContext());
		loadPreferenceValues();
		preferencias.getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenecChangeListener);
		
		final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		device_id = tm.getDeviceId();
		if (device_id == null) {
			device_id = Secure.getString(getApplicationContext()
					.getContentResolver(), Secure.ANDROID_ID);
		}
		String idUsuario = AccesoBDusuario.getUserId(device_id);
		AccesoBDLogros.checkLogos(idUsuario);
		
		Intent service = new Intent(getApplicationContext(),
				ServicioNotificaciones.class);
		startService(service);

		botonSiguiente.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editTextEmail.getText().length() == 0)
					Toast.makeText(getApplicationContext(),
							"El email es obligatorio", Toast.LENGTH_SHORT)
							.show();
				else if (editTextApellido.getText().length() == 0)
					Toast.makeText(getApplicationContext(),
							"El apellido es obligatorio", Toast.LENGTH_SHORT)
							.show();
				else if (editTextNick.getText().length() == 0)
					Toast.makeText(getApplicationContext(),
							"El nick es obligatorio", Toast.LENGTH_SHORT)
							.show();
				else if (editTextNombre.getText().length() == 0)
					Toast.makeText(getApplicationContext(),
							"El nombre es obligatorio", Toast.LENGTH_SHORT)
							.show();
				else
					vf.showNext();
			}
		});

		botonGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (imagenAvatar.getBackground() == null)
					Toast.makeText(
							getApplicationContext(),
							"Seleccione un sexo y luego una imagen como avatar",
							Toast.LENGTH_SHORT).show();
				else {
					int imagenID = (Integer) imagenAvatar.getTag();
					String imagen = "" + imagenID;
					AccesoBDusuario.registroUsuario(editTextEmail.getText()
							.toString(), editTextNombre.getText().toString(),
							editTextApellido.getText().toString(), editTextNick
									.getText().toString(), spinnerPaises
									.getSelectedItem().toString(),
							editTextCiudad.getText().toString(), imagen,
							device_id);
					vf.showNext();
				}
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				imagenAvatar.setBackgroundResource((int) parent.getAdapter()
						.getItemId(position));
				imagenAvatar.setTag((int) parent.getAdapter().getItemId(
						position));
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
				AccesoBDusuario.actualizarUsuario(editTextNombrePerfil
						.getText().toString(), editTextApellidoPerfil.getText()
						.toString(), spinnerPaisesPerfil.getSelectedItem()
						.toString(), editTextCiudadPerfil.getText().toString(),
						device_id);
				manejador.sendEmptyMessage(99);
			}
		});

		botonNuevaPartida.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), PreguntasActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("final", false);
				bundle.putBoolean("esPrimerReto", true);
				bundle.putBoolean("respondiendo", false);
				i.putExtras(bundle);
				startActivity(i);
			}
		});

		botonLogros.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cargarLogros();
				vf.setDisplayedChild(6);
			}
		});

		botonRanking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cargarPuntuaciones();
				vf.setDisplayedChild(7);
			}
		});

		botonGuardarAjustes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// vf.setDisplayedChild(5);
			}
		});

		botonAtrasAjustes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(3);
			}
		});

		botonAtrasLogros.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vf.setDisplayedChild(3);
			}
		});

		botonAtrasRanking.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tablaPuntuaciones.removeViews(0,
						tablaPuntuaciones.getChildCount());
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
				cargarPerfilUsuario();
				vf.setDisplayedChild(5);
			}
		});

		notificaciones.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				preferencias.setNotificationEnabled(notificaciones.isChecked());
			}
		});
		
		Usuario usuariospendientes[] = new Usuario[] {
		/*
		 * new Usuario("Maria", R.drawable.chica12), new Usuario("Alejandro",
		 * R.drawable.chico5)
		 */
		};

		Usuario usuariosrespondidos[] = new Usuario[] {
		/*
		 * new Usuario("Maria", R.drawable.avatar1), new Usuario("Alejandro",
		 * R.drawable.avatar2), new Usuario("Raul", R.drawable.chico13)
		 */
		};
		/*final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		device_id = tm.getDeviceId();
		if (device_id == null) {
			device_id = Secure.getString(getApplicationContext()
					.getContentResolver(), Secure.ANDROID_ID);
		}*/
		partidasPendientes = AccesoBDresultado.getPartidasPendientes(device_id);
		partidasRespondidas = AccesoBDresultado
				.getPartidasRespondidas(device_id);

		if (partidasPendientes != null) {
			usuariospendientes = new Usuario[partidasPendientes.size()];
			for (int i = 0; i < partidasPendientes.size(); i++) {
				Usuario u = partidasPendientes.get(i);
				usuariospendientes[i] = u;
			}
		}
		if (partidasRespondidas != null) {
			usuariosrespondidos = new Usuario[partidasRespondidas.size()];
			for (int i = 0; i < partidasRespondidas.size(); i++) {
				Usuario u = partidasRespondidas.get(i);
				usuariosrespondidos[i] = u;
			}
		}
		UsuariosPendientesAdapter adapter_p = new UsuariosPendientesAdapter(
				this, R.layout.item, usuariospendientes);
		UsuariosPendientesAdapter adapter_r = new UsuariosPendientesAdapter(
				this, R.layout.item, usuariosrespondidos);
		listaPartidasPendientes.setAdapter(adapter_p);
		listaPartidasEnviadas.setAdapter(adapter_r);

		listaPartidasPendientes.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				System.out.println("REFRESCANDO");
				partidasPendientes = AccesoBDresultado
						.getPartidasPendientes(device_id);
				partidasRespondidas = AccesoBDresultado
						.getPartidasRespondidas(device_id);
				Usuario usuariospendientes[] = new Usuario[partidasPendientes
						.size()];
				for (int i = 0; i < partidasPendientes.size(); i++) {
					Usuario u = partidasPendientes.get(i);
					usuariospendientes[i] = u;
				}
				Usuario usuariosrespondidos[] = new Usuario[partidasRespondidas.size()];
				for (int i = 0; i < partidasRespondidas.size(); i++) {
					Usuario u = partidasRespondidas.get(i);
					usuariosrespondidos[i] = u;
				}
				UsuariosPendientesAdapter adapter_pen = new UsuariosPendientesAdapter(context, R.layout.item,
						usuariospendientes);
				listaPartidasPendientes.setAdapter(adapter_pen);
				
				UsuariosPendientesAdapter adapter_res = new UsuariosPendientesAdapter(context, R.layout.item,
						usuariosrespondidos);
				listaPartidasEnviadas.setAdapter(adapter_res);
				
				listaPartidasPendientes.postDelayed(new Runnable() {

					@Override
					public void run() {
						listaPartidasPendientes.onRefreshComplete();
					}
				}, 2000);
			}

		});

		listaPartidasPendientes
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {

						TextView nombre = (TextView) view
								.findViewById(R.id.nombreUsuario);
						String nombreUsuario = nombre.getText().toString();
						String idUsuario = AccesoBDusuario.getUserId(device_id);
						String contrincante = AccesoBDusuario
								.getUserIdNick(nombreUsuario);
						String categoria = AccesoBDpreguntas
								.getCategoriaUsuario(idUsuario, contrincante);
						String idPreguntas = AccesoBDpreguntas
								.getPreguntasReto(idUsuario, contrincante);
						Intent i = new Intent(getBaseContext(),
								ResponderRetoActivity.class);
						System.out.println("IDUSUARIO: " + idUsuario);
						System.out.println("CONTRINCANTE: " + contrincante);
						System.out.println("CATEGORIA: " + categoria);
						System.out.println("IDPREGUNTAS: " + idPreguntas);
						System.out.println("NOMBREUSUARIO: " + nombreUsuario);
						int idPartida = AccesoBDresultado
								.tieneResultadoPendiente(idUsuario,
										contrincante);
						Bundle bundle = new Bundle();
						bundle.putBoolean("esPrimerReto", false);
						bundle.putString("nombreUsuario", nombreUsuario);
						bundle.putString("categoria", categoria);
						bundle.putString("idpreguntas", idPreguntas);
						bundle.putString("jugador1", idUsuario);
						bundle.putString("jugador2", contrincante);
						bundle.putBoolean("mostrarResultado", false);
						if (idPartida != 0) {
							bundle.putBoolean("tieneResultadoPendiente", true);
							bundle.putString("idMarcador",
									String.valueOf(idPartida));
						} else
							bundle.putBoolean("tieneResultadoPendiente", false);
						i.putExtras(bundle);
						startActivity(i);
					}
				});
	}

	protected void cargarLogros() {
		Logro[] listaLogros = null;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		Vector<Integer> logros = AccesoBDLogros.getLogros(device_id);
		Vector<Logro> logrosNormales = new Vector<Logro>();
		logrosNormales.add(new Logro("100000 puntos", R.drawable.logro1_trans));
		logrosNormales.add(new Logro("1000000 puntos", R.drawable.logro2_trans));
		logrosNormales.add(new Logro("10000000 puntos", R.drawable.logro3_trans));
		logrosNormales.add(new Logro("5 ganadas", R.drawable.logro4_trans));
		logrosNormales.add(new Logro("50 ganadas", R.drawable.logro5_trans));
		logrosNormales.add(new Logro("500 ganadas", R.drawable.logro6_trans));
		logrosNormales.add(new Logro("Nivel 5", R.drawable.logro7_trans));
		logrosNormales.add(new Logro("Nivel 25", R.drawable.logro8_trans));
		logrosNormales.add(new Logro("Nivel 50", R.drawable.logro9_trans));
		logrosNormales.add(new Logro("100 retos", R.drawable.logro10_trans));
		logrosNormales.add(new Logro("500 retos", R.drawable.logro11_trans));
		logrosNormales.add(new Logro("1000 retos", R.drawable.logro12_trans));
		if(logros != null){
			for(int i = 0; i < logros.size(); i++){
				int j=1;
				boolean encontrado = false;
				while(j<=logrosNormales.size() && !encontrado){
					int idLogro = logros.get(i);
					System.out.println(idLogro + ":" + j);
					Logro l = logrosNormales.get(j-1);
					if(idLogro == j){
						encontrado = true;
						switch (j) {
						case 1:
							l.setImagen(R.drawable.logro1);
							break;
						case 2:
							l.setImagen(R.drawable.logro2);
							break;
						case 3:
							l.setImagen(R.drawable.logro3);
							break;
						case 4:
							l.setImagen(R.drawable.logro4);
							break;
						case 5:
							l.setImagen(R.drawable.logro5);
							break;
						case 6:
							l.setImagen(R.drawable.logro6);
							break;
						case 7:
							l.setImagen(R.drawable.logro7);
							break;
						case 8:
							l.setImagen(R.drawable.logro8);
							break;
						case 9:
							l.setImagen(R.drawable.logro9);
							break;
						case 10:
							l.setImagen(R.drawable.logro10);
							break;
						case 11:
							l.setImagen(R.drawable.logro11);
							break;
						case 12:
							l.setImagen(R.drawable.logro12);
							break;
						default:
							break;
						}						
					}
					j++;
				}
			}
		}
		listaLogros = new Logro[logrosNormales.size()];
		for(int k=0;k<logrosNormales.size();k++)
			listaLogros[k] = logrosNormales.get(k);
		gridViewLogros.setAdapter(new LogrosAdapter(this, R.layout.item_logro, listaLogros));
	}

	protected void cargarPuntuaciones() {
		Vector<Usuario> usuarios = AccesoBDusuario.getRanking();
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			tr.setOrientation(1);
			TextView usuario = new TextView(this);
			usuario.setText(u.getNick());
			usuario.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			usuario.setTextColor(Color.BLACK);
			usuario.setBackgroundResource(R.layout.borde);
			tr.addView(usuario);
			TextView puntuacion = new TextView(this);
			puntuacion.setText("    " + u.getPuntuaciontotal());
			puntuacion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			puntuacion.setTextColor(Color.BLACK);
			puntuacion.setBackgroundResource(R.layout.borde);
			tr.addView(puntuacion);
			tablaPuntuaciones.addView(tr);
		}
	}

	private void loadPreferenceValues() {
		notificaciones.setChecked(preferencias.isNotificationEnabled());
	}

	public static int conectarAservidor() {
		int conectado;
		System.out.println("CONECTANDO CON SERVIDOR");
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = tm.getDeviceId();
		if (device_id == null) {
			device_id = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		}
		conectado = AccesoBDusuario.estaUsuario(device_id);
		System.out.println("DEVOLVIENDO CONEXION: " + conectado);
		return conectado;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.layout_quiz1vs1, menu);
		return true;
	}

	private SharedPreferences.OnSharedPreferenceChangeListener preferenecChangeListener = new OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			System.out.println("CAMBIANDO PREFERENCIAS: " + key);
		}
	};

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			actividad.finish();
		return true;
	};

	@SuppressWarnings("unchecked")
	protected void cargarPerfilUsuario() {
		Usuario usuario = AccesoBDusuario.getPerfilUsuario(device_id);
		editTextNombrePerfil.setText(usuario.getNombreUsuario());
		editTextApellidoPerfil.setText(usuario.getApellidoUsuario());
		editTextCiudadPerfil.setText(usuario.getCiudad());
		ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spinnerPaises
				.getAdapter();
		int posicion = myAdap.getPosition(usuario.getPais());
		spinnerPaisesPerfil.setSelection(posicion);
		imagenAvatarPerfil.setBackgroundResource(usuario.getResource_image());
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
