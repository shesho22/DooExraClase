package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina;


import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.RutinaDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.RutinaEntityMapper;

public final class ConsultarPorIdRutinaUseCase implements UseCase<RutinaDomain> {

    private DAOFactory factoria;

    public ConsultarPorIdRutinaUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public void execute(RutinaDomain domain) {
        if (domain == null || domain.getId() == null) {
            var mensajeUsuario = "Se requiere un objeto RutinaDomain con un ID válido";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        consultarRutinaPorId(domain.getId());
    }

    private RutinaDomain consultarRutinaPorId(UUID id) {
        var resultado = getRutinaDAO().consultarPorId(id);

        if (resultado.isEmpty()) {
            var mensajeUsuario = "No existe una rutina con el ID " + id;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        var entity = resultado.get();
        return RutinaEntityMapper.convertToDomain(entity);
    }

    private final DAOFactory getFactoria() {
        return factoria;
    }

    private final void setFactoria(final DAOFactory factoria) {
        if (UtilObjeto.esNulo(factoria)) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo la consulta de la rutina";
            var mensajeTecnico = "Se ha presentado un problema en setFactoria";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario, mensajeTecnico);
        }
        this.factoria = factoria;
    }

    private final RutinaDAO getRutinaDAO() {
        return getFactoria().obtenerRutinaDAO();
    }
}




