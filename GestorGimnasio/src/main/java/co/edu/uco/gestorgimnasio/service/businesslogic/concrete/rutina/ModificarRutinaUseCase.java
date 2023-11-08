package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina;


import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.RutinaDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina.ModificarRutinaValidator;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.RutinaEntityMapper;

public final class ModificarRutinaUseCase implements UseCase<RutinaDomain> {

    private DAOFactory factoria;

    public ModificarRutinaUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(RutinaDomain domain) {
        ModificarRutinaValidator.ejecutar(domain);
        validarExistenciaRutinaConMismoNombre(domain.getNombre());
        domain = actualizarIdentificadorRutina(domain);
        modificarRutina(domain);
    }

    private void modificarRutina(final RutinaDomain domain) {
        var entity = RutinaEntityMapper.convertToEntity(domain);
        getRutinaDAO().modificar(entity, null);
    }

    private final void validarExistenciaRutinaConMismoNombre(final String nombre) {
        var domain = RutinaDomain.crear(null, nombre, null, null);
        var entity = RutinaEntityMapper.convertToEntity(domain);
        var resultados = getRutinaDAO().consultar(entity);

        if (resultados.size() > 1 || (resultados.size() == 1 && !resultados.get(0).getId().equals(domain.getId()))) {
            var mensajeUsuario = "Ya existe una rutina con el nombre " + nombre;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final RutinaDomain actualizarIdentificadorRutina(final RutinaDomain domain) {
        if (domain.getId() == null) {
            var mensajeUsuario = "No se puede actualizar una rutina sin ID";
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

    private final RutinaDAO getRutinaDAO() {
        return getFactoria().obtenerRutinaDAO();
    }
}
