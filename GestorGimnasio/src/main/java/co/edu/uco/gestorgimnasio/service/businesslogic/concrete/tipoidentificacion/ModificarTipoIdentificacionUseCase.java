package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.tipoidentificacion;


import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.TipoIdentificacionDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.tipoidentificacion.RegistrarTipoIdentificacionValidator;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.TipoIdentificacionEntityMapper;

public final class ModificarTipoIdentificacionUseCase implements UseCase<TipoIdentificacionDomain> {

    private DAOFactory factoria;

    public ModificarTipoIdentificacionUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(TipoIdentificacionDomain domain) {
        RegistrarTipoIdentificacionValidator.ejecutar(domain);
        validarExistenciaTipoIdentificacionConMismoCodigo(domain.getCodigo());
        validarExistenciaTipoIdentificacionConMismoNombre(domain.getNombre());
        domain = actualizarIdentificadorTipoIdentificacion(domain);
        modificarTipoIdentificacion(domain);
    }

    private void modificarTipoIdentificacion(final TipoIdentificacionDomain domain) {
        var entity = TipoIdentificacionEntityMapper.convertToEntity(domain);
        getTipoIdentificacionDAO().modificar(entity);
    }

    private final void validarExistenciaTipoIdentificacionConMismoNombre(final String nombre) {
        var domain = TipoIdentificacionDomain.crear(null, null, nombre, false);
        var entity = TipoIdentificacionEntityMapper.convertToEntity(domain);
        var resultados = getTipoIdentificacionDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe un tipo de identificación con el nombre " + nombre;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final void validarExistenciaTipoIdentificacionConMismoCodigo(final String codigo) {
        var domain = TipoIdentificacionDomain.crear(null, codigo, null, false);
        var entity = TipoIdentificacionEntityMapper.convertToEntity(domain);
        var resultados = getTipoIdentificacionDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe un tipo de identificación con el código " + codigo;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final TipoIdentificacionDomain actualizarIdentificadorTipoIdentificacion(final TipoIdentificacionDomain domain) {
        if (domain.getId() == null) {
            var mensajeUsuario = "No se puede actualizar un tipo de identificación sin ID";
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

    private final TipoIdentificacionDAO getTipoIdentificacionDAO() {
        return getFactoria().obtenerTipoIdentificacionDAO();
    }
}
