package co.edu.uco.gestorgimnasio.service.mapper.entity.concrete;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.messages.CatalogoMensajes;
import co.edu.uco.gestorgimnasio.crosscutting.messages.enumerator.CodigoMensaje;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.entity.EjercicioEntity;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.EntityMapper;


public final class EjercicioEntityMapper implements EntityMapper<EjercicioEntity, EjercicioDomain>{

	private static final EntityMapper<EjercicioEntity, EjercicioDomain> instancia = new EjercicioEntityMapper();
	
	private EjercicioEntityMapper() {
		super();
	}
	
	@Override
	public final EjercicioDomain toDomain(final EjercicioEntity entity) {
		if(UtilObjeto.esNulo(entity)) {

			throw ServiceGestorGimnasioException.crear( CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),"Se ha presentado un problena en el metodo toDomain .No es posible mapear un cliente diminio a partir de un tipo de identificacion entity nula");
		}
		return EjercicioDomain.crear(entity.getId(),entity.getNombre(),entity.getDescripcion(),entity.getSeries(),entity.getRepeticiones());
	}
	
	@Override
	public final EjercicioEntity toEntity(final EjercicioDomain domain) {
		if(UtilObjeto.esNulo(domain)) {

			throw ServiceGestorGimnasioException.crear(CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),"Se ha presentado un problena en el metodo toEntity .No es posible mapear un cliente diminio a partir de un tipo de identificacion entity nula");
		}
		return EjercicioEntity.crear(domain.getId(),domain.getNombre(),domain.getDescripcion(),domain.getRepeticiones(),domain.getSeries());
	
	}
	
	public static final EjercicioDomain convertToDomain(final EjercicioEntity entity) {
		return instancia.toDomain(entity);
	}
	
	public static final EjercicioEntity convertToEntity(final EjercicioDomain domain) {
		return instancia.toEntity(domain);
	}

	public static List<EjercicioDomain> convertToDomainList(List<EjercicioEntity> ejercicios) {
	 final   List<EjercicioDomain> ejercicioDomainList = new ArrayList<>();

	    ejercicios.stream().map(EjercicioEntityMapper::convertToDomain).forEach(ejercicioDomainList::add);

	    return ejercicioDomainList;
	}


}
