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

import com.infomovil.quiz1vs1.modelo.Pregunta;
import com.infomovil.quiz1vs1.modelo.Usuario;

public class AccesoBDpreguntas {
	
	// private static final String IP_SERVER = "192.168.2.107";
	// private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";

	private static InputStream is;
	
	public static ArrayList<Pregunta> getPreguntas(String categoria) {
		System.out.println("OBTENIENDO PREGUNTAS");
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("categoria", categoria));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/preguntas.php");
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
					String id = json_data.getString("id");
					String archivo = json_data.getString("archivo");
					String pregunta = json_data.getString("pregunta");
					String respuestaCorrecta = json_data
							.getString("respuestacorrecta");
					String respuesta2 = json_data
							.getString("respuestaincorrecta1");
					String respuesta3 = json_data
							.getString("respuestaincorrecta2");
					String respuesta4 = json_data
							.getString("respuestaincorrecta3");
					Pregunta p = new Pregunta(pregunta, respuestaCorrecta,
							respuesta2, respuesta3, respuesta4, archivo);
					p.setId(id);
					preguntas.add(p);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return preguntas;
	}
	
	public static ArrayList<Pregunta> getPreguntasDeId(String preguntasReto) {
		System.out.println("OBTENIENDO PREGUNTAS RETO");
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("preguntas", preguntasReto));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/preguntasDeID.php");
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
				System.out.println("RESULTADO PREGUNTAS: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					String archivo = json_data.getString("archivo");
					String pregunta = json_data.getString("pregunta");
					String respuestaCorrecta = json_data
							.getString("respuestacorrecta");
					String respuesta2 = json_data
							.getString("respuestaincorrecta1");
					String respuesta3 = json_data
							.getString("respuestaincorrecta2");
					String respuesta4 = json_data
							.getString("respuestaincorrecta3");
					Pregunta p = new Pregunta(pregunta, respuestaCorrecta,
							respuesta2, respuesta3, respuesta4, archivo);
					preguntas.add(p);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return preguntas;
	}

	public static String getPreguntasReto(String idUsuario1, String idUsuario2) {
		String result = "";
		String preguntas = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idUsuario2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/preguntasReto.php");
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
				System.out.println("RESULT: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					preguntas = json_data.getString("preguntas");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return preguntas;
	}
	
	public static Vector<String> getCategorias() {
		System.out.println("OBTENIENDO CATEGORIAS");
		Vector<String> categorias = new Vector<String>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// nameValuePairs.add(new BasicNameValuePair("categoria",categoria));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/categorias.php");
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
					String cat = json_data.getString("categoria");
					categorias.add(cat);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		System.out.println("CATEGORIAS OBTENIDAS");
		return categorias;
	}
	
	public static String getCategoriaUsuario(String idUsuario1,
			String idUsuario2) {
		String categoria = "";
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idUsuario2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/categoriaUsuario.php");
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
				System.out.println("RESULT: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					categoria = json_data.getString("categoria");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return categoria;
	}
	
	public static Vector<Usuario> getPartidasEnviadas(String device_id) {
		Vector<Usuario> pendientes = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/partidasEnviadas.php");
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
					String nick = json_data.getString("nick");
					int avatar = json_data.getInt("avatar");
					String idResultado = json_data.getString("id");
					System.out.println("AVATAR: " + avatar);
					Usuario u = new Usuario(nick, avatar);
					u.setIdResultado(idResultado);
					u.setNick(nick);
					pendientes.add(u);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return pendientes;
	}

}
