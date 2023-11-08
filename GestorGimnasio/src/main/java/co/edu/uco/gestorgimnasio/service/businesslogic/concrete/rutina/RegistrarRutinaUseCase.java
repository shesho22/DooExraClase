package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.data.dao.RutinaDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.entity.EjercicioEntity;
import co.edu.uco.gestorgimnasio.data.entity.RutinaEntity;
import co.edu.uco.gestorgimnasio.service.businesslogic.UseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina.RegistrarRutinaValidator;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.EjercicioEntityMapper;
import co.edu.uco.gestorgimnasio.service.mapper.entity.concrete.RutinaEntityMapper;

public final class RegistrarRutinaUseCase implements UseCase<RutinaDomain> {
    private DAOFactory factoria;

    public RegistrarRutinaUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(RutinaDomain domain) {
        RegistrarRutinaValidator.ejecutar(domain);
        validarNoExistenciaRutinaConMismoNombre(domain.getNombre(), domain.getEntrenador());
        domain = obtenerIdentificadorRutina(domain);
        registrarNuevaRutina(domain);
    }

    private void registrarNuevaRutina(final RutinaDomain domain) {
        var entity = RutinaEntityMapper.convertToEntity(domain);
        final List<EjercicioEntity> ejercicios = obtenerListaDeEjercicios(domain);
        final RutinaDAO rutinaDAO = getRutinaDAO();
        rutinaDAO.crear(entity, ejercicios);
    }

    private final void validarNoExistenciaRutinaConMismoNombre(final String nombre, final EntrenadorDomain entrenador) {
        final var domain = RutinaDomain.crear(null, nombre, entrenador, null);
        final var entity = RutinaEntityMapper.convertToEntity(domain);
        final var resultados = getRutinaDAO().consultar(entity);
        if (!resultados.isEmpty()) {
            throw ServiceGestorGimnasioException.crear("Ya existe una rutina con el nombre " + nombre);
        }
    }

    private List<EjercicioEntity> obtenerListaDeEjercicios(RutinaDomain domain) {
        final List<EjercicioDomain> ejerciciosDomain = domain.getEjercicios();

        if (ejerciciosDomain != null) {
            return convertirListaDeEjercicios(ejerciciosDomain);
        } else {
            return new ArrayList<>();
        }
    }

    private List<EjercicioEntity> convertirListaDeEjercicios(List<EjercicioDomain> ejerciciosDomain) {
        final List<EjercicioEntity> ejerciciosEntity = new ArrayList<>();
        ejerciciosDomain.stream().map(EjercicioEntityMapper::convertToEntity).forEach(ejerciciosEntity::add);
        return ejerciciosEntity;
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
            throw ServiceGestorGimnasioException.crear("Se ha presentado un problema tratando de llevar a cabo el resultado", "Se ha presentado un problema en setFactoria");
        }
        this.factoria = factoria;
    }

    private final RutinaDAO getRutinaDAO() {
        return getFactoria().obtenerRutinaDAO();
    }
}
