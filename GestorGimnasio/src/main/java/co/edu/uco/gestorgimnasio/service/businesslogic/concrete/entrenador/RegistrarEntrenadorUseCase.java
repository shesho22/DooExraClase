package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador;


import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.EntrenadorDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EntrenadorEntityMapper;


public final class RegistrarEntrenadorUseCase implements UseCase<EntrenadorDomain> {

    private DAOFactory factoria;

    public RegistrarEntrenadorUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(EntrenadorDomain domain) {
    	validarExistenciaEntrenadorConMismoDocumento(domain.getIdentificacion(),domain.getTipoidentificacion());
        domain = registrarEntrenador(domain);
    }

    private EntrenadorDomain registrarEntrenador(final EntrenadorDomain domain) {
        var entity = EntrenadorEntityMapper.convertToEntity(domain);
        getEntrenadorDAO().crear(entity);
        return domain;
    }

    private void validarExistenciaEntrenadorConMismoDocumento(final String identificador, final TipoIdentificacionDomain tipoDocumento) {
        var domain = EntrenadorDomain.crear(null, tipoDocumento, identificador, null, null, null, null);
        var entity = EntrenadorEntityMapper.convertToEntity(domain);
        var resultados = getEntrenadorDAO().consultar(entity);
        if (!resultados.isEmpty()) {
            var mensajeUsuario = "Ya existe un entrenador con el mismo identificador y tipo de documento.";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final EntrenadorDAO getEntrenadorDAO() {
        return getFactoria().obtenerEntrenadorDAO();
    }

    private final DAOFactory getFactoria() {
        return factoria;
    }

    private final void setFactoria(final DAOFactory factoria) {
        if (UtilObjeto.esNulo(factoria)) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo el registro del entrenador";
            var mensajeTecnico = "Se ha presentado un problema en setFactoria";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario, mensajeTecnico);
        }
        this.factoria = factoria;
    }
}



