package co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

public final class ConsultarRutinaUseCase implements UseCase<RutinaDomain> {

    private DAOFactory factoria;

    public ConsultarRutinaUseCase(final DAOFactory factoria) {
        setFactoria(factoria);
    }

    @Override
    public final void execute(RutinaDomain domain) {
        RegistrarRutinaValidator.ejecutar(domain);
        validarNoExistenciaRutinaConMismoNombreYEntrenador(domain.getNombre(), domain.getEntrenador());
        domain = obtenerIdentificadorRutina(domain);
        registrarNuevaRutina(domain);
    }

    private void registrarNuevaRutina(final RutinaDomain domain) {
        RutinaEntity entity = RutinaEntityMapper.convertToEntity(domain);
        List<EjercicioEntity> ejercicios = obtenerListaDeEjercicios(domain);
        RutinaDAO rutinaDAO = getRutinaDAO();
        rutinaDAO.crear(entity, ejercicios);
    }

    private List<EjercicioEntity> obtenerListaDeEjercicios(RutinaDomain domain) {
        List<EjercicioDomain> ejerciciosDomain = domain.getEjercicios();

        if (ejerciciosDomain != null) {
            return convertirListaDeEjercicios(ejerciciosDomain);
        } else {
            return new ArrayList<>();
        }
    }

    private List<EjercicioEntity> convertirListaDeEjercicios(List<EjercicioDomain> ejerciciosDomain) {
        return new ArrayList<>(ejerciciosDomain.stream().map(EjercicioEntityMapper::convertToEntity).collect(Collectors.toList()));
    }

    private final void validarNoExistenciaRutinaConMismoNombreYEntrenador(final String nombre, final EntrenadorDomain entrenador) {
        final var domain = RutinaDomain.crear(null, nombre, entrenador, null);
        final var entity = RutinaEntityMapper.convertToEntity(domain);
        var resultados = getRutinaDAO().consultar(entity);

        if (!resultados.isEmpty()) {
            var mensajeUsuario = "Ya existe una rutina de este entrenador con el mismo nombre, por favor asigne otro nombre";
            throw ServiceGestorGimnasioException.crear(mensajeUsuario);
        }
    }

    private final RutinaDomain obtenerIdentificadorRutina(final RutinaDomain domain) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (getRutinaDAO().consultarPorId(uuid).isPresent());
        return RutinaDomain.crear(uuid, domain.getNombre(), domain.getEntrenador(), domain.getEjercicios());
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
