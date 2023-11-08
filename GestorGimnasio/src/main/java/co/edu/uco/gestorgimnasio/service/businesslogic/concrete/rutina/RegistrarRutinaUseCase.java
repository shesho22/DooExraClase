package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina;


import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.RutinaDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.entity.RutinaEntity;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina.RegistrarRutinaValidator;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.RutinaEntityMapper;


public final class RegistrarRutinaUseCase implements UseCase<RutinaDomain> {
    private DAOFactory factoria;

    public RegistrarRutinaUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(RutinaDomain domain) {
        RegistrarRutinaValidator.ejecutar(domain);
        validarNoExistenciaRutinaConMismoNombre(domain.getNombre());
        domain = obtenerIdentificadorRutina(domain);
        registrarNuevaRutina(domain);
    }

    private void registrarNuevaRutina(final RutinaDomain domain) {
        var entity = RutinaEntityMapper.convertToEntity(domain);
        getRutinaDAO().crear(entity, null);
    }

    private final void validarNoExistenciaRutinaConMismoNombre(final String nombre) {
        var domain = RutinaDomain.crear(null, nombre, null,null);
        var entity = RutinaEntityMapper.convertToEntity(domain);
        var resultados = getRutinaDAO().consultar(entity);

        if (!resultados.isEmpty()) {
            var mensajeUsuario = "Ya existe una rutina con el nombre " + nombre;
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final RutinaDomain obtenerIdentificadorRutina(final RutinaDomain domain) {
        Optional<RutinaEntity> optional;
        UUID uuid;

        do {
            uuid = UUID.randomUUID();
            optional = getRutinaDAO().consultarPorId(uuid);
        } while (optional.isPresent());

        return RutinaDomain.crear(uuid, domain.getNombre(), domain.getEntrenador(), domain.getEjercicios());
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


