package co.edu.uco.gestorgimnasio.data.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.data.entity.TipoIdentificacionEntity;

public interface TipoIdentificacionDAO {
	
	void crear(TipoIdentificacionEntity entity);
	void modificar(TipoIdentificacionEntity entity);
	void eliminar(TipoIdentificacionEntity entity);
	Optional<TipoIdentificacionEntity> consultarPorId(UUID id);
	List<TipoIdentificacionEntity> consultar(TipoIdentificacionEntity entity);
}
