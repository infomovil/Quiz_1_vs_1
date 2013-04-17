package com.infomovil.quiz1vs1.modelo;

public class Usuario {
	
	private String nombreUsuario;
	private String email;
	private String apellidoUsuario;
	private String pais;
	private String ciudad;
	private String nick;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario() {
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

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
