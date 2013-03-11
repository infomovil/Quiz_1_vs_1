package com.infomovil.quiz1vs1.modelo;

import java.io.InputStream;

public class Pregunta {

	private String pregunta;
	private String respuestaCorrecta;
	private String respuestaIncorrecta1;
	private String respuestaIncorrecta2;
	private String respuestaIncorrecta3;
	//private InputStream archivo;

	public Pregunta(String pregunta, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3/*, InputStream archivo*/) {
		//this.archivo = archivo;
		this.pregunta = pregunta;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestaIncorrecta1 = respuestaIncorrecta1;
		this.respuestaIncorrecta2 = respuestaIncorrecta2;
		this.respuestaIncorrecta3 = respuestaIncorrecta3;
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

//	public InputStream getArchivo() {
//		return archivo;
//	}
//
//	public void setArchivo(InputStream archivo) {
//		this.archivo = archivo;
//	}
	
	
}
