package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.tipoidentificacion;

import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.TipoIdentificacionDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.entity.TipoIdentificacionEntity;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.tipoidentificacion.RegistrarTipoIdentificacionValidator;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.TipoIdentificacionEntityMapper;

public final class RegistrarTipoIdentificacionUseCase implements UseCase<TipoIdentificacionDomain>{

	private DAOFactory factoria;
	

	public RegistrarTipoIdentificacionUseCase(final DAOFactory factoria) {
		setFatoria(factoria);
	}
	
	

	@Override
	public final void execute(TipoIdentificacionDomain domain) {
		RegistrarTipoIdentificacionValidator.ejecutar(domain);
		validarNoExistenciaTipoIdentificacionConMismoCodigo(domain.getCodigo());
		validarNoExistenciaTipoIdentificacionConMismoNombre(domain.getNombre());
		domain = obtenerIdentificadorTipoIdentificacion(domain);
		registrarNuevoTipoIdentificacion(domain);
	}

	private void registrarNuevoTipoIdentificacion(final TipoIdentificacionDomain domain) {
		var entity = TipoIdentificacionEntityMapper.convertToEntity(domain);
		getTipoIdentificacionDAO().crear(entity);
	}

	private final void validarNoExistenciaTipoIdentificacionConMismoNombre(final String nombre) {
		
		var domain = TipoIdentificacionDomain.crear(null, null, nombre, false);
		var entity=TipoIdentificacionEntityMapper.convertToEntity(domain);
		var resultados = getTipoIdentificacionDAO().consultar(entity);
		
		if(!resultados.isEmpty()) {
			var mensajeUsuario = "Ya existe un tipo de identificacion con el nombre "+nombre;
			throw ServiceGestorGimnasioException.crear(mensajeUsuario);
		}
	}
	
	private final void validarNoExistenciaTipoIdentificacionConMismoCodigo(final String codigo) {
		
		var domain = TipoIdentificacionDomain.crear(null, codigo, null, false);
		var entity=TipoIdentificacionEntityMapper.convertToEntity(domain);
		var resultados = getTipoIdentificacionDAO().consultar(entity);
		
		if(!resultados.isEmpty()) {
			var mensajeUsuario = "Ya existe un tipo de identificacion con el codigo "+codigo;
			throw ServiceGestorGimnasioException.crear(mensajeUsuario);
		}
	}
	
private final TipoIdentificacionDomain obtenerIdentificadorTipoIdentificacion(final TipoIdentificacionDomain domain) {
	Optional<TipoIdentificacionEntity> optional = Optional.empty();
	UUID uuid;
	do {
		uuid = UUID.randomUUID();
		optional = getTipoIdentificacionDAO().consultarPorId(uuid);
	}while(optional.isPresent());
	return TipoIdentificacionDomain.crear(uuid, domain.getCodigo(), domain.getNombre(), domain.isEstado());
}
	

	private final DAOFactory getFactoria() {
		return factoria;
	}

	private final void setFatoria(final DAOFactory factoria) {
		if(UtilObjeto.esNulo(factoria)) {
		var mensajeUsuario= "Se ha presentado un problema tratando de llevar a cabo el resultado";
		var mensajeTecnico = "Se ha presentado un problema en setFactoria";
		throw ServiceGestorGimnasioException.crear(mensajeUsuario,mensajeTecnico);
		}
		this.factoria = factoria;
	}

	private final TipoIdentificacionDAO getTipoIdentificacionDAO() {
		return getFactoria().obtenerTipoIdentificacionDAO();
	}



}
