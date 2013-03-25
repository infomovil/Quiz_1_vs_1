package com.infomovil.quiz1vs1.aplicacion;

import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.aplicacion.adapters.UsuariosPendientesAdapter;
import com.infomovil.quiz1vs1.modelo.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

public class PrincipalActivity extends Activity {
	
	private ListView listaPartidasPendientes;
	//private ListView listaPartidasEnviadas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_layout);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        //listaPartidasEnviadas = (ListView) findViewById(R.id.listPartidasEnviadas);
        listaPartidasPendientes = (ListView) findViewById(R.id.listPartidasPendientes);
        
        Usuario usuarios[] = new Usuario[] {
			new Usuario("Maria", R.drawable.avatar1),
			new Usuario("Alejandro", R.drawable.avatar2)
        };
                
        UsuariosPendientesAdapter adapter = new UsuariosPendientesAdapter(this, R.layout.item, usuarios);
        //View cabecera = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cabecera, null, false);
        //listaPArtidasPendientes.addHeaderView(cabecera);
        listaPartidasPendientes.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onDestroy();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
