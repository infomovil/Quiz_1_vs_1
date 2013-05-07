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

public class AccesoBDLogros {
	
	//private static final String IP_SERVER = "192.168.2.107";
	//private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";
	private static InputStream is;
	
	public static Vector<Integer> getLogros(String device_id) {
		Vector<Integer> logros = new Vector<Integer>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/getLogros.php");
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
				System.out.println("RESULT GET PARTIDAS NOTIFICACION: "
						+ result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int idLogro = json_data.getInt("id");
					System.out.println("Logro conseguido: " + idLogro);
					logros.add(idLogro);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return logros;
	}
	
	public static void nuevoLogro(String idLogro, String idUsuario) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idLogro", idLogro));
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/nuevoLogro.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
	
	public static void checkLogos(String idUsuario){
		Vector<Integer> logros = new Vector<Integer>();
		checkLogrosPuntuacion(idUsuario, logros);
		checkLogrosPartidasGanadas(idUsuario, logros);
		checkLogrosNivel(idUsuario, logros);
		checkLogrosRetosGanados(idUsuario, logros);		
		for(int i=0;i<logros.size();i++){
			int idLogro = logros.get(i);
			nuevoLogro(String.valueOf(idLogro), idUsuario);
		}
	}
	
	private static void checkLogrosPuntuacion(String idUsuario, Vector<Integer> logros){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/checkLogros.php");
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
					int puntuacion = json_data.getInt("puntuaciontotal");
					if(puntuacion>100000)
						logros.add(1);
					if(puntuacion>1000000)
						logros.add(2);
					if(puntuacion>10000000)
						logros.add(3);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}
	
	private static void checkLogrosNivel(String idUsuario, Vector<Integer> logros){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/checkLogros.php");
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
					int nivel = json_data.getInt("nivel");
					if(nivel>5)
						logros.add(7);
					if(nivel>25)
						logros.add(8);
					if(nivel>50)
						logros.add(9);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}
	
	private static void checkLogrosRetosGanados(String idUsuario, Vector<Integer> logros){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/checkLogros.php");
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
					int retosganados = json_data.getInt("totalretosganados");
					if(retosganados>100)
						logros.add(1);
					if(retosganados>500)
						logros.add(2);
					if(retosganados>1000)
						logros.add(3);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}
	
	private static void checkLogrosPartidasGanadas(String idUsuario, Vector<Integer> logros){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/checkLogros.php");
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
					int partidasganadas = json_data.getInt("totalpartidasganadas");
					if(partidasganadas>5)
						logros.add(4);
					if(partidasganadas>50)
						logros.add(5);
					if(partidasganadas>500)
						logros.add(6);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}
	
}
