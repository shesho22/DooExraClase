package co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador.ConsultarPorIdEntrenadorUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.entrenador.ConsultarEntrenadorValidator;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.dto.EntrenadorDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.EntrenadorDTOMapper;

public final class ConsultarPorIdEntrenadorFacade implements Facade<EntrenadorDTO> {

    private final ConsultarPorIdEntrenadorUseCase useCase;

    public ConsultarPorIdEntrenadorFacade(ConsultarPorIdEntrenadorUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void execute(EntrenadorDTO dto) {
        final EntrenadorDomain domain = EntrenadorDTOMapper.convertirToDomain(dto);
        ConsultarEntrenadorValidator.ejecutar(domain);

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
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de consultar un entrenador por ID.";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de consultar un entrenador por ID. Verifique la traza completa.";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

}


