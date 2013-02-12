package com.infomovil.quiz1vs1.modelo;

public class Usuario {
	
	String nombreUsuario;
	int resource_image;
	
	public Usuario(String nombreUsuario, int resource_image) {
		this.nombreUsuario = nombreUsuario;
		this.resource_image = resource_image;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getResource_image() {
		return resource_image;
	}

	public void setResource_image(int resource_image) {
		this.resource_image = resource_image;
	}
}
