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

import com.infomovil.quiz1vs1.modelo.Usuario;

public class AccesoBDresultado {

	// private static final String IP_SERVER = "192.168.2.107";
	// private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";
	private static InputStream is;
	
	public static Vector<Usuario> getPartidasPendientes(String device_id) {
		Vector<Usuario> pendientes = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/partidasPendientes.php");
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

			if (!result.equals("null\n")) {
				System.out.println("RESULT GET PARTIDAS PENDIENTES: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					String nombreUsu = json_data.getString("nick");
					String avatar = json_data.getString("avatar");
					System.out.println("AVATAR: " + avatar);
					Usuario u = new Usuario(nombreUsu, Integer.parseInt(avatar));
					pendientes.add(u);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return pendientes;
	}

	public static Vector<Usuario> getPartidasRespondidas(String device_id) {
		Vector<Usuario> respondidas = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/partidasRespondidas.php");
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
			if (!result.equals("null\n")) {
				System.out
						.println("RESULT GET PARTIDAS RESPONDIDAS: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					String nombreUsu = json_data.getString("nick");
					String avatar = json_data.getString("avatar");
					System.out.println("AVATAR: " + avatar);
					Usuario u = new Usuario(nombreUsu, Integer.parseInt(avatar));
					respondidas.add(u);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return respondidas;
	}
	
	public static void registrarResultadoPartida(String idmarcador,
			String idusuario1, String puntuacion1, String idusuario2,
			String puntuacion2, String preguntas, String categoria,
			String enviada, String respondida) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idmarcador", idmarcador));
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idusuario1));
		nameValuePairs.add(new BasicNameValuePair("puntuacion1", puntuacion1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idusuario2));
		nameValuePairs.add(new BasicNameValuePair("puntuacion2", puntuacion2));
		nameValuePairs.add(new BasicNameValuePair("preguntas", preguntas));
		nameValuePairs.add(new BasicNameValuePair("categoria", categoria));
		nameValuePairs.add(new BasicNameValuePair("enviada", enviada));
		nameValuePairs.add(new BasicNameValuePair("respondida", enviada));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/registrarResultadoPartida.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			System.out.println("RESULTADO REGISTRADO");
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
			System.out.println("RESULT: " + result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
	}

	public static void actualizarResultadoPartida(String idusuario1,
			String idusuario2, String puntuacion2) {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("puntuacion2", puntuacion2));
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idusuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idusuario2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/actualizarResultadoPartida.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			System.out.println("RESULTADO ACTUALIZADO");
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
			System.out.println("RESULT: " + result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
	}

	public static Vector<Object> getResultadoPartida(String idUsuario1,
			String idUsuario2) {
		String result = "";
		Vector<Object> puntuaciones = new Vector<Object>();

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idUsuario2", idUsuario2));
		System.out.println(idUsuario1);
		System.out.println(idUsuario2);
		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/mostrarPuntuacionReto.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
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
			System.out.println("RESULTADO MOSTRAR RESUL: " + result);
			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int idResultado = json_data.getInt("id");
					int puntuacion1 = json_data.getInt("puntuacion1");
					int puntuacion2 = json_data.getInt("puntuacion2");
					String nick1 = json_data.getString("nick1");
					String nick2 = json_data.getString("nick2");

					System.out.println(idResultado);
					System.out.println(puntuacion1);
					System.out.println(puntuacion2);
					System.out.println(nick1);
					System.out.println(nick2);

					puntuaciones.add(idResultado);
					puntuaciones.add(puntuacion1);
					puntuaciones.add(puntuacion2);
					puntuaciones.add(nick1);
					puntuaciones.add(nick2);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return puntuaciones;
	}
	
	public static void setRespondida(String idUsuario1, String idUsuario2) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idUsuario2", idUsuario2));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setRespondida.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}

	public static Vector<String> getResultadoPendiente(String idMarcador) {
		String result = "";
		Vector<String> marcadores = new Vector<String>();
		System.out.println("MARCADOR: " + idMarcador);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/getResultadoPendiente.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
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
					int marcadorJ1 = json_data.getInt("puntuacion1");
					int marcadorJ2 = json_data.getInt("puntuacion2");
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
	
	public static int tieneResultadoPendiente(String idUsuario,
			String contrincante) {
		int partida = 0;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario1", idUsuario));
		nameValuePairs.add(new BasicNameValuePair("idUsuario2", contrincante));
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
			System.out.println("RESULT LOGIN: " + result);
			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int total = json_data.getInt("total");
					if (total > 0)
						partida = json_data.getInt("id");
				}
				System.out.println("USUARIO EXISTENTE");
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return partida;
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
			if (!result.equals("null\n")) {
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
	
	public static void setEnviada(String idResultado) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		System.out.println("IDRESULTADO: " + idResultado);
		nameValuePairs.add(new BasicNameValuePair("idResultado", idResultado));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setEnviada.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
}
