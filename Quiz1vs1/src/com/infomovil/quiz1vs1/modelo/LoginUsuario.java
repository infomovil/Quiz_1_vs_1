package com.infomovil.quiz1vs1.modelo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class LoginUsuario {
	
	//private static final String IP_SERVER = "192.168.2.107";
	//private static final String IP_SERVER = "192.168.117.1";
	private static final String IP_SERVER = "quizchampion.zz.mu";
	
	private static InputStream is;
	
	public static void registroUsuario(String email, String nombre, String apellidos, String nick, String pais, String ciudad, String avatar, String device_id){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email",email));
		nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
		nameValuePairs.add(new BasicNameValuePair("apellidos",apellidos));
		nameValuePairs.add(new BasicNameValuePair("nick",nick));
		nameValuePairs.add(new BasicNameValuePair("pais",pais));
		nameValuePairs.add(new BasicNameValuePair("ciudad",ciudad));
		nameValuePairs.add(new BasicNameValuePair("avatar",avatar));
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/login.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
	}
	
	public static String getUserId(String device_id){
		int id = 0;
		String result = "";
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/userID.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                id = json_data.getInt("id");
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return String.valueOf(id);
	}
	
	public static String getUserIdNick(String nick){
		int id = 0;
		String result = "";
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nick",nick));
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/userIDnick.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			System.out.println("RESULTADO: " + result);
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                id = json_data.getInt("id");
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return String.valueOf(id);
	}
	
	public static void actualizarUsuario(String nombre, String apellidos, String pais, String ciudad, String device_id){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
		nameValuePairs.add(new BasicNameValuePair("apellidos",apellidos));
		nameValuePairs.add(new BasicNameValuePair("pais",pais));
		nameValuePairs.add(new BasicNameValuePair("ciudad",ciudad));
		nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/modificarPerfil.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        System.out.println("USUARIO REGISTRADO");
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		
	}
		
	public static boolean estaUsuario(String device_id) throws HttpHostConnectException{
		boolean esta = false;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/estaUsuario.php");
	        HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), 3000);
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}catch(HttpHostConnectException conn){
	        throw new HttpHostConnectException(conn.getHost(), (ConnectException) conn.getCause());
		}
		catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			System.out.println("RESULT LOGIN: " + result);
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);
	                String deviceID = json_data.getString("device_id");
	                if(deviceID.equalsIgnoreCase(device_id))
	                	esta = true;
		        }
		        System.out.println("USUARIO EXISTENTE");
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return esta;
	}
	
	public static ArrayList<Pregunta> getPreguntas(String categoria){
		System.out.println("OBTENIENDO PREGUNTAS");
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("categoria",categoria));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/preguntas.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	 
	        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{			
			if(result!=null){
				
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String id =  json_data.getString("id");
		                String archivo =  json_data.getString("archivo");
		                String pregunta = json_data.getString("pregunta");
		                String respuestaCorrecta = json_data.getString("respuestacorrecta");
		                String respuesta2 = json_data.getString("respuestaincorrecta1");
		                String respuesta3 = json_data.getString("respuestaincorrecta2");
		                String respuesta4 = json_data.getString("respuestaincorrecta3");
		                Pregunta p = new Pregunta(pregunta, respuestaCorrecta, respuesta2, respuesta3, respuesta4,archivo);
		                p.setId(id);
		                preguntas.add(p);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return preguntas;
	}
	
	public static ArrayList<Pregunta> getPreguntasDeId(String preguntasReto){
		System.out.println("OBTENIENDO PREGUNTAS RETO");
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();		
		nameValuePairs.add(new BasicNameValuePair("preguntas",preguntasReto));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/preguntasDeID.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	 
	        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{			
			if(result!=null){
				System.out.println("RESULTADO PREGUNTAS: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String archivo =  json_data.getString("archivo");
		                String pregunta = json_data.getString("pregunta");
		                String respuestaCorrecta = json_data.getString("respuestacorrecta");
		                String respuesta2 = json_data.getString("respuestaincorrecta1");
		                String respuesta3 = json_data.getString("respuestaincorrecta2");
		                String respuesta4 = json_data.getString("respuestaincorrecta3");
		                Pregunta p = new Pregunta(pregunta, respuestaCorrecta, respuesta2, respuesta3, respuesta4,archivo);
		                preguntas.add(p);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return preguntas;
	}
	
	public static String getPreguntasReto(String idUsuario1, String idUsuario2){
		String result = "";
		String preguntas="";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();		
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idUsuario2));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/preguntasReto.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	 
	        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{			
			if(result!=null){
				System.out.println("RESULT: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                preguntas =  json_data.getString("preguntas");		                
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return preguntas;
	}
	
	public static String getCategoriaUsuario(String idUsuario1, String idUsuario2){
		String categoria = "";
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();		
		nameValuePairs.add(new BasicNameValuePair("idusuario1", idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2", idUsuario2));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/categoriaUsuario.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	 
	        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{			
			if(result!=null){
				System.out.println("RESULT: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                categoria =  json_data.getString("categoria");		                
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		System.out.println("PREGUNTAS OBTENIDAS");
		return categoria;
	}
	
	public static String buscarAleatorio(String device_id){
		int id=0;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/buscarAleatorio.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{			
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                id = json_data.getInt("id");
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return String.valueOf(id);
	}
	
	public static Vector<String> getCategorias(){
		System.out.println("OBTENIENDO CATEGORIAS");
		Vector<String> categorias = new Vector<String>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("categoria",categoria));
		
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/categorias.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);		                
		                String cat = json_data.getString("categoria");			                
		                categorias.add(cat);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		System.out.println("CATEGORIAS OBTENIDAS");
		return categorias;
	}
	
	public static Vector<Usuario> getPartidasPendientes(String device_id){
		Vector<Usuario> pendientes = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/partidasPendientes.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			
			if(result!=null){
				System.out.println("RESULT GET PARTIDAS PENDIENTES: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String nombreUsu = json_data.getString("nick");
		                String avatar = json_data.getString("avatar");
		                System.out.println("AVATAR: " + avatar);
		                Usuario u = new Usuario(nombreUsu, Integer.parseInt(avatar));
		                pendientes.add(u);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return pendientes;
	}
	
	public static Vector<Usuario> getPartidasRespondidas(String device_id){
		Vector<Usuario> respondidas = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/partidasRespondidas.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			if(result!=null){
				System.out.println("RESULT GET PARTIDAS RESPONDIDAS: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String nombreUsu = json_data.getString("nick");
		                String avatar = json_data.getString("avatar");
		                System.out.println("AVATAR: " + avatar);
		                Usuario u = new Usuario(nombreUsu, Integer.parseInt(avatar));
		                respondidas.add(u);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return respondidas;
	}
	
	public static String registrarPartida(String idusuario1, String idusuario2, String partidasGanadasJ1, String partidasGanadasJ2){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1",idusuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2",idusuario2));
		nameValuePairs.add(new BasicNameValuePair("partidasganadasjugador1",partidasGanadasJ1));
		nameValuePairs.add(new BasicNameValuePair("partidasganadasjugador2",partidasGanadasJ2));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/registrarPartida.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		return result;
	}
	
	public static void registrarResultadoPartida(String idmarcador, String idusuario1, String puntuacion1, String idusuario2, String puntuacion2, String preguntas, String categoria, /*String fechahora,*/String enviada, String respondida){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idmarcador",idmarcador));
		nameValuePairs.add(new BasicNameValuePair("idusuario1",idusuario1));
		nameValuePairs.add(new BasicNameValuePair("puntuacion1",puntuacion1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2",idusuario2));
		nameValuePairs.add(new BasicNameValuePair("puntuacion2",puntuacion2));
		nameValuePairs.add(new BasicNameValuePair("preguntas",preguntas));
		nameValuePairs.add(new BasicNameValuePair("categoria",categoria));
		nameValuePairs.add(new BasicNameValuePair("enviada",enviada));
		nameValuePairs.add(new BasicNameValuePair("respondida",enviada));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/registrarResultadoPartida.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        System.out.println("RESULTADO REGISTRADO");
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        result=sb.toString();
	        System.out.println("RESULT: " + result);
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
	}
	
	public static void actualizarResultadoPartida(String idusuario1, String idusuario2, String puntuacion2){
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("puntuacion2",puntuacion2));
		nameValuePairs.add(new BasicNameValuePair("idusuario1",idusuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2",idusuario2));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/actualizarResultadoPartida.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        System.out.println("RESULTADO ACTUALIZADO");
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        result=sb.toString();
	        System.out.println("RESULT: " + result);
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
	}
	
	public static Vector<Object> getResultadoPartida(String idUsuario1, String idUsuario2){
		String result = "";
		Vector<Object> puntuaciones = new Vector<Object>();
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario1",idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idUsuario2",idUsuario2));
		System.out.println(idUsuario1);
		System.out.println(idUsuario2);
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/mostrarPuntuacionReto.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			System.out.println("RESULTADO MOSTRAR RESUL: " + result);
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
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
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return puntuaciones;
	}
	
	public static boolean esJugador1(String idMarcador, String idUsuario){
		boolean esJugador1 = false;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		nameValuePairs.add(new BasicNameValuePair("idUsuario",idUsuario));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/esJugador1.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
				if(result!=null){
					System.out.println("RESULT ES JUGADOR: " + result);
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                int marcador = json_data.getInt("id");
		                if(marcador!= 0)
		                	esJugador1 = true;
			        }
				}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return esJugador1;
	}
	public static void actualizarMarcadorJ1(String idMarcador){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/actualizarMarcadorJ1.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		
	}
	public static void actualizarMarcadorJ2(String idMarcador){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/actualizarMarcadorJ2.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		
	}
	
	public static void setRespondida(String idMarcador){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/setRespondida.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
	}
	
	public static String getIDMarcador(String idUsuario1, String idUsuario2){
		String idmarcador = "";
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idusuario1",idUsuario1));
		nameValuePairs.add(new BasicNameValuePair("idusuario2",idUsuario2));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/getIDMarcador.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{			
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                idmarcador = json_data.getString("idmarcador");
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return idmarcador;
	}
	
	public static Vector<String> getMarcadorPartida(String idMarcador){
		String result = "";
		Vector<String> marcadores = new Vector<String>();
		System.out.println("MARCADOR: " + idMarcador);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/mostrarMarcador.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			if(result!=null){
				System.out.println("RESULTADO MOSTRAR MARCADOR: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                int marcadorJ1 = json_data.getInt("partidasganadasjugador1");
		                int marcadorJ2 = json_data.getInt("partidasganadasjugador2");
		                String nickJ1 = json_data.getString("nickj1");
		                String nickJ2 = json_data.getString("nickj2");
		                marcadores.add(String.valueOf(marcadorJ1));
		                marcadores.add(String.valueOf(marcadorJ2));
		                marcadores.add(nickJ1);
		                marcadores.add(nickJ2);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data " + e.toString());
		}
		
		return marcadores;
	}
	
	public static Vector<String> getResultadoPendiente(String idMarcador){
		String result = "";
		Vector<String> marcadores = new Vector<String>();
		System.out.println("MARCADOR: " + idMarcador);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idMarcador",idMarcador));
		
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/getResultadoPendiente.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			if(result!=null){
				System.out.println("RESULTADO MOSTRAR MARCADOR: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
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
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return marcadores;
	}
	
	public static Vector<Usuario> buscarAdversario(String campo_busqueda, String valor_busqueda){
		Vector<Usuario> usuarios = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("campo_busqueda",campo_busqueda.toLowerCase()));
		nameValuePairs.add(new BasicNameValuePair("valor_busqueda",valor_busqueda));
		System.out.println("campo "+campo_busqueda);
		System.out.println("valor "+valor_busqueda);
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/buscarAdversario.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		        System.out.println("resultado buscar adversario "+result);
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{			
			
			if(result!=null){
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
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
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return usuarios;
	}
	
	public static Vector<Usuario> getPartidasEnviadas(String device_id){
		Vector<Usuario> pendientes = new Vector<Usuario>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/partidasEnviadas.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			
			if(result!=null){
				System.out.println("RESULT GET PARTIDAS NOTIFICACION: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
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
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return pendientes;
	}
	
	public static void setEnviada(String idResultado){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		System.out.println("IDRESULTADO: " + idResultado);
		nameValuePairs.add(new BasicNameValuePair("idResultado",idResultado));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/setEnviada.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
	}
	
	public static Vector<Usuario> getRanking(){
		Vector<Usuario> usuarios = new Vector<Usuario>();
		String result = "";
		//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/ranking.php");
		        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			
			if(result!=null){
				System.out.println("RESULT RANKING: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String nick = json_data.getString("nick");
		                int puntuaciontotal = json_data.getInt("puntuaciontotal");
		                Usuario u = new Usuario();
		                u.setNick(nick);
		                u.setPuntuaciontotal(puntuaciontotal);
		                usuarios.add(u);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return usuarios;
	}
	
	public static Vector<Logro> getLogros(String device_id){
		Vector<Logro> logros = new Vector<Logro>();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/getLogros.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			
			if(result!=null){
				System.out.println("RESULT GET PARTIDAS NOTIFICACION: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                String logro = json_data.getString("logro");
		                int imagenLogro = json_data.getInt("imagen_id");
		                Logro l = new Logro(logro, imagenLogro);
		                logros.add(l);
		        }
			}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return logros;
	}
	
	public static Usuario getPerfilUsuario(String device_id){
		Usuario usuario = new Usuario();
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/recuperarPerfil.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}		
		try{
			
			if(result!=null){
				System.out.println("RESULT GET PERFIL NOTIFICACION: " + result);
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
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
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return usuario;
	}
	
	public static int tieneResultadoPendiente(String idUsuario, String contrincante) {
		int partida = 0;
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("idUsuario1",idUsuario));
		nameValuePairs.add(new BasicNameValuePair("idUsuario2",contrincante));
		try{
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/getPartidasTotales.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
		} catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		try{
			System.out.println("RESULT LOGIN: " + result);
				if(result!=null){
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                int total = json_data.getInt("total");
		                if(total > 0)
		                	partida = json_data.getInt("id");
			        }
			        System.out.println("USUARIO EXISTENTE");
				}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return partida;
	}
}
