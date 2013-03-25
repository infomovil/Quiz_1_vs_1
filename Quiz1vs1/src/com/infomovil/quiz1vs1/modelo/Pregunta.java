package com.infomovil.quiz1vs1.modelo;

import java.util.Collections;
import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

public class Pregunta implements Parcelable{

	private String pregunta;
	private String respuestaCorrecta;
	private String respuestaIncorrecta1;
	private String respuestaIncorrecta2;
	private String respuestaIncorrecta3;
	private String archivo;
	private String id;

	public Pregunta(String pregunta, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3, String archivo) {
		this.pregunta = pregunta;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestaIncorrecta1 = respuestaIncorrecta1;
		this.respuestaIncorrecta2 = respuestaIncorrecta2;
		this.respuestaIncorrecta3 = respuestaIncorrecta3;
		this.archivo = archivo;
	}

	public Pregunta (Parcel source){
		pregunta = source.readString();
		respuestaCorrecta = source.readString();
		respuestaIncorrecta1 = source.readString();
		respuestaIncorrecta2 = source.readString();
		respuestaIncorrecta3 = source.readString();
		archivo = source.readString();
	}
	
	public boolean esCorrecta(String respuesta){
		return respuesta.equalsIgnoreCase(this.respuestaCorrecta);
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getRespuestaIncorrecta1() {
		return respuestaIncorrecta1;
	}

	public void setRespuestaIncorrecta1(String respuestaIncorrecta1) {
		this.respuestaIncorrecta1 = respuestaIncorrecta1;
	}

	public String getRespuestaIncorrecta2() {
		return respuestaIncorrecta2;
	}

	public void setRespuestaIncorrecta2(String respuestaIncorrecta2) {
		this.respuestaIncorrecta2 = respuestaIncorrecta2;
	}

	public String getRespuestaIncorrecta3() {
		return respuestaIncorrecta3;
	}

	public void setRespuestaIncorrecta3(String respuestaIncorrecta3) {
		this.respuestaIncorrecta3 = respuestaIncorrecta3;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	public Vector<String> getRespuestas(){
		Vector<String> v = new Vector<String>();
		v.add(respuestaCorrecta);
		v.add(respuestaIncorrecta1);
		v.add(respuestaIncorrecta2);
		v.add(respuestaIncorrecta3);
		
		Collections.shuffle(v);
		
		return v;
	}

	public static final Parcelable.Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
		
		@Override
		public Pregunta[] newArray(int size) {
			return new Pregunta[size];
		}
		
		@Override
		public Pregunta createFromParcel(Parcel source) {
			return new Pregunta(source);
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(pregunta);
		dest.writeString(respuestaCorrecta);
		dest.writeString(respuestaIncorrecta1);
		dest.writeString(respuestaIncorrecta2);
		dest.writeString(respuestaIncorrecta3);
		dest.writeString(archivo);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
