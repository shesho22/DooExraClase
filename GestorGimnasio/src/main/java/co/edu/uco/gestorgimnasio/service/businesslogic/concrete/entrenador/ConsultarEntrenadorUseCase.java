package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador;

import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.EntrenadorDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EntrenadorEntityMapper;

public final class ConsultarEntrenadorUseCase implements UseCase<EntrenadorDomain> {

    private DAOFactory factoria;

    public ConsultarEntrenadorUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public void execute(EntrenadorDomain domain) {
        if (domain == null || domain.getId() == null) {
            var mensajeUsuario = "Se requiere un objeto EntrenadorDomain con un ID válido";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        consultarEntrenadorPorId(domain.getId());
    }

    private EntrenadorDomain consultarEntrenadorPorId(UUID id) {
        var resultado = getEntrenadorDAO().consultarPorId(id);

        if (resultado.isEmpty()) {
            var mensajeUsuario = "No existe un entrenador con el ID " + id;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        var entity = resultado.get();
        return EntrenadorEntityMapper.convertToDomain(entity);
    }

    private final DAOFactory getFactoria() {
        return factoria;
    }

    private final void setFactoria(final DAOFactory factoria) {
        if (UtilObjeto.esNulo(factoria)) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo la consulta del entrenador";
            var mensajeTecnico = "Se ha presentado un problema en setFactoria";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario, mensajeTecnico);
        }
        this.factoria = factoria;
    }

    private final EntrenadorDAO getEntrenadorDAO() {
        return getFactoria().obtenerEntrenadorDAO();
    }
}

