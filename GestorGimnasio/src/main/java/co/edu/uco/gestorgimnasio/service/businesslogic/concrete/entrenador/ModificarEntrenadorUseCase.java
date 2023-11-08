package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador;


import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.EntrenadorDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.entrenador.ModificarEntrenadorValidator;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.support.NombreCompletoClienteDomain;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EntrenadorEntityMapper;

public final class ModificarEntrenadorUseCase implements UseCase<EntrenadorDomain> {

    private DAOFactory factoria;

    public ModificarEntrenadorUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(EntrenadorDomain domain) {
        ModificarEntrenadorValidator.ejecutar(domain);
        validarExistenciaEntrenadorConMismoNombre(domain.getNombreCompleto());
        validarExistenciaEntrenadorConMismaIdentificacion(domain.getTipoidentificacion(), domain.getIdentificacion());
        domain = actualizarIdentificadorEntrenador(domain);
        modificarEntrenador(domain);
    }

    private void modificarEntrenador(final EntrenadorDomain domain) {
        var entity = EntrenadorEntityMapper.convertToEntity(domain);
        getEntrenadorDAO().modificar(entity);
    }

    private final void validarExistenciaEntrenadorConMismoNombre(final NombreCompletoClienteDomain nombre) {
        var domain = EntrenadorDomain.crear(null, null, null, nombre, null, null, null);
        var entity = EntrenadorEntityMapper.convertToEntity(domain);
        var resultados = getEntrenadorDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe un entrenador con el mismo nombre";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final void validarExistenciaEntrenadorConMismaIdentificacion(
            final TipoIdentificacionDomain tipoIdentificacion, final String identificacion) {
        var domain = EntrenadorDomain.crear(null, tipoIdentificacion, identificacion, null, null, null, null);
        var entity = EntrenadorEntityMapper.convertToEntity(domain);
        var resultados = getEntrenadorDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe un entrenador con la misma identificación";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final EntrenadorDomain actualizarIdentificadorEntrenador(final EntrenadorDomain domain) {
        if (domain.getId() == null) {
            var mensajeUsuario = "No se puede actualizar un entrenador sin ID";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }

        return domain;
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

    private final EntrenadorDAO getEntrenadorDAO() {
        return getFactoria().obtenerEntrenadorDAO();
    }
}
