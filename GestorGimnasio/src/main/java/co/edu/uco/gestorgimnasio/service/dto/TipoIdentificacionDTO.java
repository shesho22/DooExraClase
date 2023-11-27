package co.edu.uco.gestorgimnasio.service.dto;

import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.util.UtilTexto;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilUUID;

public final class TipoIdentificacionDTO {
	
	private UUID id;
	private String codigo;
	private String nombre;
	private boolean estado;
	
	
	public TipoIdentificacionDTO() {
		setId(UtilUUID.getNewUUID()); 
		setCodigo(UtilTexto.VACIO);
		setNombre(UtilTexto.VACIO);
		setEstado(false);
	}
	
	public TipoIdentificacionDTO(final UUID id,final String codigo,final String nombre,final boolean estado) {
		setId(id);
		setCodigo(codigo);
		setNombre(nombre);
		setEstado(estado);
	}
	
	public static final TipoIdentificacionDTO crear() {
		return new TipoIdentificacionDTO();
	}
	
	public final UUID getId() {
		return id;
	}
	public final TipoIdentificacionDTO setId(final UUID id) {
		this.id = id;
		return this;
	}
	public final String getCodigo() {
		return codigo;
	}
	public final TipoIdentificacionDTO setCodigo(final String codigo) {
		this.codigo = codigo;
		return this;
	}
	public final String getNombre() {
		return nombre;
	}
	public final TipoIdentificacionDTO setNombre(final String nombre) {
		this.nombre = nombre;
		return this;
	}
	public final boolean isEstado() {
		return estado;
	}
	public final TipoIdentificacionDTO setEstado(final boolean estado) {
		this.estado = estado;
		return this;
	}
	public void copy(TipoIdentificacionDTO other) {
		this.id = other.id;
		this.codigo = other.codigo;
		this.nombre = other.nombre;
		this.estado = other.estado;
	}
	
}
