package com.infomovil.quiz1vs1.aplicacion;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.infomovil.quiz1vs1.Quiz1vs1Activity;
import com.infomovil.quiz1vs1.R;
import com.infomovil.quiz1vs1.modelo.LoginUsuario;
import com.infomovil.quiz1vs1.modelo.Usuario;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class ServicioNotificaciones extends Service {

	/*private Thread hiloNotificaciones;
	private boolean corriendo;
	private Vector<Usuario> partidasPendientes;
	private Timer timer;
	private String device_id;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		System.out.println("ONCREATE SERVICIO");
		timer = new Timer();
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {
				final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				device_id = tm.getDeviceId();
				if(device_id == null){
					device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
				}
				partidasPendientes = LoginUsuario.getPartidasPendientes(device_id);
				if(partidasPendientes.size()>0){
					for(int i=0; i < partidasPendientes.size(); i++){
						notifyUp(partidasPendientes.get(i).getNick());
					}
				}
			}
		};
		super.onCreate();
		hiloNotificaciones = new Thread(new Runnable() {
			@Override
			public void run() {
				corriendo = true;
				while(corriendo){
					System.out.println("RECUPERANDO PARTIDAS");
					timer.schedule(task, 10000);
				}
			}
		});
		hiloNotificaciones.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		pararServicio();
	}
	
	@SuppressWarnings("deprecation")
	public void notifyUp(String nick){
		NotificationManager mgr= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.logo_aplicacion, "Quiz Champion", System.currentTimeMillis());
		n.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,Quiz1vs1Activity.class),0);
		n.setLatestEventInfo(this, "Service notification", nick + " te ha enviado una partida", pi);
		mgr.notify(1337, n);
	}
	
	private void pararServicio() {
		corriendo = false;
	}*/

	public static final long NOTIFY_INTERVAL = 10000;
	private Vector<Usuario> partidasPendientes;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
    @Override
    public void onCreate() {
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }
 
    class TimeDisplayTimerTask extends TimerTask {
 
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                	final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    				String device_id = tm.getDeviceId();
    				if(device_id == null){
    					device_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
    				}
    				partidasPendientes = LoginUsuario.getPartidasEnviadas(device_id);
    				if(partidasPendientes.size()>0){
    					System.out.println("hay partidas pendientes");
    					for(int i=0; i < partidasPendientes.size(); i++){
    						Usuario u = partidasPendientes.get(i);
    						notifyUp(u.getNick());
    						LoginUsuario.setEnviada(u.getIdResultado());
    					}
    				}
                }
 
            });
        }
    }
    
    @SuppressWarnings("deprecation")
	public void notifyUp(String nick){
		NotificationManager mgr= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.logo_aplicacion, "Quiz Champion", System.currentTimeMillis());
		n.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,Quiz1vs1Activity.class),0);
		n.setLatestEventInfo(this, "Quiz Champion", nick + " te ha enviado una partida", pi);
		mgr.notify(1337, n);
	}
	
}
