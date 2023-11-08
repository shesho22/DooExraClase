package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.ejercicio;


import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.EjercicioDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.ejercicio.ModificarEjercicioValidator;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EjercicioEntityMapper;

public final class ModificarEjercicioUseCase implements UseCase<EjercicioDomain> {

    private DAOFactory factoria;

    public ModificarEjercicioUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(EjercicioDomain domain) {
        ModificarEjercicioValidator.ejecutar(domain);
        validarExistenciaEjercicioConMismoNombre(domain.getNombre());
        domain = actualizarIdentificadorEjercicio(domain);
        modificarEjercicio(domain);
    }

    private void modificarEjercicio(final EjercicioDomain domain) {
        var entity = EjercicioEntityMapper.convertToEntity(domain);
        getEjercicioDAO().modificar(entity);
    }

    private final void validarExistenciaEjercicioConMismoNombre(final String nombre) {
        var domain = EjercicioDomain.crear(null, nombre, null, null, null);
        var entity = EjercicioEntityMapper.convertToEntity(domain);
        var resultados = getEjercicioDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe un ejercicio con el nombre " + nombre;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final EjercicioDomain actualizarIdentificadorEjercicio(final EjercicioDomain domain) {
        if (domain.getId() == null) {
            var mensajeUsuario = "No se puede actualizar un ejercicio sin ID";
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

    private final EjercicioDAO getEjercicioDAO() {
        return getFactoria().obtenerEjercicioDAO();
    }
}
