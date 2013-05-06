package com.infomovil.quiz1vs1.persistencia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AccesoBDmarcador {
	
	// private static final String IP_SERVER = "192.168.2.107";
	// private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";

	private static InputStream is;
	
	public static String registrarPartida(String idusuario1, String idusuario2,
			String partidasGanadasJ1, String partidasGanadasJ2) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idusuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idusuario2));
		nameValuePairs.add(new BasicNameValuePair("partidasganadasjugador1",
				partidasGanadasJ1));
		nameValuePairs.add(new BasicNameValuePair("partidasganadasjugador2",
				partidasGanadasJ2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/registrarPartida.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		return result;
	}
	
	public static void actualizarMarcadorJ1(String idMarcador) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/actualizarMarcadorJ1.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

	}
	
	public static void actualizarMarcadorJ2(String idMarcador) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/actualizarMarcadorJ2.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

	}

	public static String getIDMarcador(String idUsuario1, String idUsuario2) {
		String idmarcador = "";
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idUsuario2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/getIDMarcador.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					idmarcador = json_data.getString("idmarcador");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return idmarcador;
	}

	public static Vector<String> getMarcadorPartida(String idMarcador) {
		String result = "";
		Vector<String> marcadores = new Vector<String>();
		System.out.println("MARCADOR: " + idMarcador);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/mostrarMarcador.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			if (result != null) {
				System.out.println("RESULTADO MOSTRAR MARCADOR: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int marcadorJ1 = json_data
							.getInt("partidasganadasjugador1");
					int marcadorJ2 = json_data
							.getInt("partidasganadasjugador2");
					String nickJ1 = json_data.getString("nickj1");
					String nickJ2 = json_data.getString("nickj2");
					marcadores.add(String.valueOf(marcadorJ1));
					marcadores.add(String.valueOf(marcadorJ2));
					marcadores.add(nickJ1);
					marcadores.add(nickJ2);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return marcadores;
	}
	
	public static boolean partidaFinalizada(String idMarcador){
		boolean finalizada = false;
		String result ="";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/getPartidasTotales.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			if (result != null) {
				System.out.println("RESULTADO MOSTRAR PARTIDA FINALIZADA: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int partidasj1 = json_data.getInt("partidasganadasjugador1");
					int partidasj2 = json_data.getInt("partidasganadasjugador2");
					System.out.println(partidasj1 + ":" + partidasj2);
					if(partidasj1 == 6 || partidasj2 == 6)
						finalizada = true;
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return finalizada;
	}
	
	public static boolean haGanadoPartida(String idMarcador, String idUsuario){
		boolean ganada = false;
		String result ="";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/haGanadoPartida.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			if (result != null) {
				System.out.println("RESULTADO MOSTRAR MARCADOR FINAL: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int ganadasj1 = json_data.getInt("partidasganadasjugador1");
					int ganadasj2 = json_data.getInt("partidasganadasjugador1");
					int idj1 = json_data.getInt("partidasganadasjugador1");
					int idj2 = json_data.getInt("partidasganadasjugador1");
					int usuario = Integer.parseInt(idUsuario);
					System.out.println("usu:" + usuario);
					if(ganadasj1 == 6)
						ganada = idj1 == usuario;
					if(ganadasj2 == 6)
						ganada = idj2 == usuario;
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return ganada;
	}
	
}
