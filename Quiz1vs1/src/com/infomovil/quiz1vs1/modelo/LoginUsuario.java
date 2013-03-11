package com.infomovil.quiz1vs1.modelo;

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

public class LoginUsuario {
	
	//private static final String IP_SERVER = "192.168.2.105";
	private static final String IP_SERVER = "192.168.117.1";
	private static InputStream is;
	
	public static void RegistroUsuario(String email, String nombre, String apellidos, String nick, String pais, String ciudad,String device_id){
		String result = "";
		//the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email",email));
		nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
		nameValuePairs.add(new BasicNameValuePair("apellidos",apellidos));
		nameValuePairs.add(new BasicNameValuePair("nick",nick));
		nameValuePairs.add(new BasicNameValuePair("pais",pais));
		nameValuePairs.add(new BasicNameValuePair("ciudad",ciudad));
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		
		
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.117.1/quizchampion/login.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}catch(Exception e){
		        Log.e("log_tag", "Error in http connection "+e.toString());
		}
		//convert response to string
		/*try{
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
		 
		//parse json data
		try{
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                Log.i("log_tag","id: "+json_data.getInt("campo"));
		        }
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}*/
	}
	
	public static boolean EstaUsuario(String device_id){
		boolean esta = false;
		String result = "";
		//the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("device_id",device_id));
		
		
		//http post
		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://" + IP_SERVER + "/quizchampion/estaUsuario.php");
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
		 
		//parse json data
		try{
				System.out.println("RESULT: " + result);
				if(result!=null){
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);
			                String deviceID = json_data.getString("device_id");
			                if(deviceID.equalsIgnoreCase(device_id))
			                	esta = true;
			        }
				}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return esta;
	}
	
	public static Vector<Pregunta> getPreguntas(String categoria){
		Vector<Pregunta> preguntas = new Vector<Pregunta>();
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
				System.out.println("RESULT: " + result);
				if(result!=null){
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);
			                JSONObject archivo =  json_data.getJSONObject("archivo");			                
			                String pregunta = json_data.getString("pregunta");
			                String respuestaCorrecta = json_data.getString("respuestacorrecta");
			                String respuesta2 = json_data.getString("respuestaincorrecta1");
			                String respuesta3 = json_data.getString("respuestaincorrecta2");
			                String respuesta4 = json_data.getString("respuestaincorrecta3");
			                Pregunta p = new Pregunta(pregunta, respuestaCorrecta, respuesta2, respuesta3, respuesta4);
			                preguntas.add(p);
			        }
				}
		} catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return preguntas;
	}
	
}
