package com.infomovil.quiz1vs1.modelo;



import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferencias {
	
	private Context context;
	
	public static final String ENABLE_NOTIFICATIONS = "notificaciones";
	public static final String ENABLE_SOUND = "sonido";
	public static final boolean ENABLE_NOTIFICATIONS_DEFAULT = true;
	public static final boolean ENABLE_SOUND_DEFAULT = false;
	
	public Preferencias(Context context) {
		this.context = context;
	}
	
	public SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public boolean isNotificationEnabled() {
		return getSharedPreferences().getBoolean(ENABLE_NOTIFICATIONS, ENABLE_NOTIFICATIONS_DEFAULT);
	}

	public void setNotificationEnabled(boolean enabled) {
		System.out.println("ESTABLECIENDO NOTIFICACION: " + enabled);
		getSharedPreferences().edit().putBoolean(ENABLE_NOTIFICATIONS, enabled);
		getSharedPreferences().edit().commit();
	}
	
	public boolean isSoundEnabled() {
		return getSharedPreferences().getBoolean(ENABLE_SOUND, ENABLE_SOUND_DEFAULT);
	}

	public void setSoundEnabled(boolean enabled) {
		System.out.println("ESTABLECIENDO SONIDO: " + enabled);
		getSharedPreferences().edit().putBoolean(ENABLE_SOUND, enabled);
		getSharedPreferences().edit().commit();
	}
}