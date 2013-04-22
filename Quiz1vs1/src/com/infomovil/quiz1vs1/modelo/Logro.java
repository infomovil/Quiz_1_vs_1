package com.infomovil.quiz1vs1.modelo;

public class Logro {

	private int imagen;
	private int id;
	private String descripcion;
	private String nombreLogro;
	
	public Logro() {
	}

	public Logro (String nombreLogro, int imagen_id){
		this.setNombreLogro(nombreLogro);
		this.imagen = imagen_id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getImagen() {
		return imagen;
	}

	public void setImagen(int imagen) {
		this.imagen = imagen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreLogro() {
		return nombreLogro;
	}

	public void setNombreLogro(String nombreLogro) {
		this.nombreLogro = nombreLogro;
	}
}
