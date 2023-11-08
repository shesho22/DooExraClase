package co.edu.uco.gestorgimnasio.data.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.data.entity.EjercicioEntity;
import co.edu.uco.gestorgimnasio.data.entity.RutinaEntity;

public interface RutinaDAO {
	
    void crear(RutinaEntity entity, List<EjercicioEntity> ejercicios);

    void modificar(RutinaEntity entity, List<EjercicioEntity> ejercicios);

    void eliminar(UUID id);

    Optional<RutinaEntity> consultarPorId(UUID id);

    List<RutinaEntity> consultar(RutinaEntity entity);

}
