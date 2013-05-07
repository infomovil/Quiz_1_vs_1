package com.infomovil.quiz1vs1.persistencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.infomovil.quiz1vs1.modelo.Logro;
import com.infomovil.quiz1vs1.modelo.Pregunta;
import com.infomovil.quiz1vs1.modelo.Usuario;

import android.util.Log;

public class AccesoBDusuario {

	// private static final String IP_SERVER = "192.168.2.107";
	//private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";

	private static final long TIMEOUT = 3000;
	
	public static final int USUARIO_NUEVO = 0;
	public static final int EXITO = 1;
	public static final int CONEXION_FALLIDA = 2;
	
	private static InputStream is;

	public static void registroUsuario(String email, String nombre,
			String apellidos, String nick, String pais, String ciudad,
			String avatar, String device_id) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("nombre", nombre));
		nameValuePairs.add(new BasicNameValuePair("apellidos", apellidos));
		nameValuePairs.add(new BasicNameValuePair("nick", nick));
		nameValuePairs.add(new BasicNameValuePair("pais", pais));
		nameValuePairs.add(new BasicNameValuePair("ciudad", ciudad));
		nameValuePairs.add(new BasicNameValuePair("avatar", avatar));
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/login.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}

	public static String getUserId(String device_id) {
		int id = 0;
		String result = "";

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/userID.php");
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
					id = json_data.getInt("id");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return String.valueOf(id);
	}

	public static String getUserIdNick(String nick) {
		int id = 0;
		String result = "";

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nick", nick));
		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/userIDnick.php");
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
			System.out.println("RESULTADO: " + result);
			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					id = json_data.getInt("id");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return String.valueOf(id);
	}

	public static void actualizarUsuario(String nombre, String apellidos,
			String pais, String ciudad, String device_id) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nombre", nombre));
		nameValuePairs.add(new BasicNameValuePair("apellidos", apellidos));
		nameValuePairs.add(new BasicNameValuePair("pais", pais));
		nameValuePairs.add(new BasicNameValuePair("ciudad", ciudad));
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/modificarPerfil.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			System.out.println("USUARIO REGISTRADO");
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

	}

	public static int estaUsuario(String device_id) {
		int estado = USUARIO_NUEVO;
		String result = "";
		System.out.println("ENTRANDO...");
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try{
			is = conecta("estaUsuario.php" , nameValuePairs);
			System.out.println("DESPUES DE PRIMERA CONEXION");
			if(is == null)
				return CONEXION_FALLIDA;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (HttpHostConnectException conn) {
			is = null;
		} 
		catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			System.out.println("RESULT LOGIN: " + result);
			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					String deviceID = json_data.getString("device_id");
					if (deviceID.equalsIgnoreCase(device_id))
						estado = EXITO;
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return estado;
	}

	public static String buscarAleatorio(String device_id) {
		int id = 0;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/buscarAleatorio.php");
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
					id = json_data.getInt("id");
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return String.valueOf(id);
	}

	public static boolean esJugador1(String idMarcador, String idUsuario) {
		boolean esJugador1 = false;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador", idMarcador));
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/esJugador1.php");
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
				System.out.println("RESULT ES JUGADOR: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int marcador = json_data.getInt("id");
					if (marcador != 0)
						esJugador1 = true;
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return esJugador1;
	}

	public static Vector<Usuario> buscarAdversario(String campo_busqueda,
			String valor_busqueda) {
		Vector<Usuario> usuarios = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("campo_busqueda",
				campo_busqueda.toLowerCase()));
		nameValuePairs.add(new BasicNameValuePair("valor_busqueda",
				valor_busqueda));
		System.out.println("campo " + campo_busqueda);
		System.out.println("valor " + valor_busqueda);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/buscarAdversario.php");
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
			System.out.println("resultado buscar adversario " + result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {

			if (result != null) {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					Usuario u = new Usuario();
					String email = json_data.getString("email");
					String nick = json_data.getString("nick");
					int id = json_data.getInt("id");
					u.setId(id);
					u.setEmail(email);
					u.setNick(nick);
					usuarios.add(u);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return usuarios;
	}

	public static void setPuntuacionTotal(String puntuacionTotal, String idUsuario) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("puntuacionTotal", puntuacionTotal));
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setPuntuacionTotal.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}

	public static Vector<Usuario> getRanking() {
		Vector<Usuario> usuarios = new Vector<Usuario>();
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/ranking.php");
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
				System.out.println("RESULT RANKING: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					String nick = json_data.getString("nick");
					int puntuaciontotal = json_data.getInt("puntuaciontotal");
					Usuario u = new Usuario();
					u.setNick(nick);
					u.setPuntuaciontotal(puntuaciontotal);
					usuarios.add(u);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return usuarios;
	}

	public static Usuario getPerfilUsuario(String device_id) {
		Usuario usuario = new Usuario();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/recuperarPerfil.php");
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
				System.out.println("RESULT GET PERFIL NOTIFICACION: " + result);
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					int id = json_data.getInt("id");
					String email = json_data.getString("email");
					String nombre = json_data.getString("nombre");
					String apellidos = json_data.getString("apellidos");
					String nick = json_data.getString("nick");
					String pais = json_data.getString("pais");
					String ciudad = json_data.getString("ciudad");
					int avatar = json_data.getInt("avatar");

					usuario.setId(id);
					usuario.setEmail(email);
					usuario.setNombreUsuario(nombre);
					usuario.setApellidoUsuario(apellidos);
					usuario.setNick(nick);
					usuario.setPais(pais);
					usuario.setCiudad(ciudad);
					usuario.setResource_image(avatar);

				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return usuario;
	}

	private static InputStream conecta(String direccion, ArrayList<NameValuePair> nameValuePairs) throws HttpHostConnectException{
		InputStream is = null;
		try {	
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/" + direccion);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response;
			HttpParams params = httpclient.getParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(5000));
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.valueOf(5000));
			response = httpclient.execute(httppost);				
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (HttpHostConnectException conn) {
			throw new HttpHostConnectException(conn.getHost(),(ConnectException) conn.getCause());
		}
		catch (ClientProtocolException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
		System.out.println("TIME: " + Calendar.getInstance().getTimeInMillis());
		System.out.println("TERMINANDO CONEXION");
		return is;
	}
	
	public static void setPartidasGanadas( String idUsuario) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setPartidasGanadas.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
	
	public static void setPartidasJugadas(String idUsuario) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setPartidasJugadas.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
	
	public static void setRetosGanados(String idUsuario) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario", idUsuario));
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://" + IP_SERVER
					+ "/quizchampion/setRetosGanados.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
	
}
