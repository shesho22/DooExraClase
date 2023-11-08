package co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.ejercicio.ConsultarPorIdEjercicioUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.ejercicio.ConsultarEjercicioValidator;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.dto.EjercicioDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.EjercicioDTOMapper;

public final class ConsultarPorIdEjercicioFacade implements Facade<EjercicioDTO> {

    private final ConsultarPorIdEjercicioUseCase useCase;

    public ConsultarPorIdEjercicioFacade(ConsultarPorIdEjercicioUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void execute(EjercicioDTO dto) {
        final EjercicioDomain domain = EjercicioDTOMapper.convertirToDomain(dto);
        ConsultarEjercicioValidator.ejecutar(domain);

        final DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);

        try {
            daoFactory.iniciarTransaccion();

            useCase.execute(domain);

            daoFactory.confirmarTransaccion();

        } catch (final GestorGimnasioException excepcion) {
            daoFactory.cancelarTransaccion();
            throw excepcion;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de consultar un ejercicio por ID.";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de consultar un ejercicio por ID. Verifique la traza completa.";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

}


