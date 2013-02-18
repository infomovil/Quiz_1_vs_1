package com.infomovil.quiz1vs1;

import com.infomovil.quiz1vs1.aplicacion.adapters.UsuariosPendientesAdapter;
import com.infomovil.quiz1vs1.modelo.Usuario;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ViewFlipper;

public class Quiz1vs1Activity extends Activity {
	
	private ViewFlipper vf;
	private ListView listaPartidasPendientes;
	private ListView listaPartidasEnviadas;
	
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
        
        Usuario usuarios[] = new Usuario[] {
			new Usuario("Maria", R.drawable.avatar1),
			new Usuario("Alejandro", R.drawable.avatar2)
        };
                
        UsuariosPendientesAdapter adapter = new UsuariosPendientesAdapter(this, R.layout.item, usuarios);
        View cabecera_p = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cabecera_pendientes, null, false);
        View cabecera_r = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cabecera_respondidos, null, false);
        listaPartidasPendientes.addHeaderView(cabecera_p);
        listaPartidasPendientes.setAdapter(adapter);
        
        listaPartidasEnviadas.addHeaderView(cabecera_r);
        listaPartidasEnviadas.setAdapter(adapter);
    }

    public void conectarAservidor(){
    	
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_quiz1vs1, menu);
        return true;
    }
    
}
