package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.tipoidentificacion;

import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.TipoIdentificacionDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.TipoIdentificacionEntityMapper;

public final class EliminarTipoIdentificacionUseCase implements UseCase<TipoIdentificacionDomain> {

    private DAOFactory factoria;

    public EliminarTipoIdentificacionUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(TipoIdentificacionDomain domain) {
        validarExistenciaTipoIdentificacion(domain.getId());
        eliminarTipoIdentificacion(domain);
    }

    private void eliminarTipoIdentificacion(final TipoIdentificacionDomain domain) {
        var entity = TipoIdentificacionEntityMapper.convertToEntity(domain);
        getTipoIdentificacionDAO().eliminar(entity);
    }

    private final void validarExistenciaTipoIdentificacion(final UUID id) {
        var resultado = getTipoIdentificacionDAO().consultarPorId(id);
        if (resultado.isEmpty()) {
            var mensajeUsuario = "No existe un tipo de identificación con el ID " + id;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final DAOFactory getFactoria() {
        return factoria;
    }

    private final void setFactoria(final DAOFactory factoria) {
        if (UtilObjeto.esNulo(factoria)) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo el resultado";
            var mensajeTecnico = "Se ha presentado un problema en setFactoria";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario, mensajeTecnico);
        }
        this.factoria = factoria;
    }

    private final TipoIdentificacionDAO getTipoIdentificacionDAO() {
        return getFactoria().obtenerTipoIdentificacionDAO();
    }
}


