package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.ejercicio;


import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.EjercicioDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EjercicioEntityMapper;

public final class ConsultarPorIdEjercicioUseCase implements UseCase<EjercicioDomain> {

    private DAOFactory factoria;

    public ConsultarPorIdEjercicioUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public void execute(EjercicioDomain domain) {
        if (domain == null || domain.getId() == null) {
            var mensajeUsuario = "Se requiere un objeto EjercicioDomain con un ID válido";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        consultarEjercicioPorId(domain.getId());
    }

    private EjercicioDomain consultarEjercicioPorId(UUID id) {
        var resultado = getEjercicioDAO().consultarPorId(id);

        if (resultado.isEmpty()) {
            var mensajeUsuario = "No existe un ejercicio con el ID " + id;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        var entity = resultado.get();
        return EjercicioEntityMapper.convertToDomain(entity);
    }

    private final DAOFactory getFactoria() {
        return factoria;
    }

    private final void setFactoria(final DAOFactory factoria) {
        if (UtilObjeto.esNulo(factoria)) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo la consulta del ejercicio";
            var mensajeTecnico = "Se ha presentado un problema en setFactoria";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario, mensajeTecnico);
        }
        this.factoria = factoria;
    }

    private final EjercicioDAO getEjercicioDAO() {
        return getFactoria().obtenerEjercicioDAO();
    }
}




