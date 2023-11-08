package co.edu.uco.gestorgimnasio.service.facade.concrete.rutina;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina.ConsultarPorIdRutinaUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina.ConsultarRutinaValidator;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.dto.RutinaDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.RutinaDTOMapper;

public final class ConsultarPorIdRutinaFacade implements Facade<RutinaDTO> {

	private final ConsultarPorIdRutinaUseCase useCase;

    public ConsultarPorIdRutinaFacade(ConsultarPorIdRutinaUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void execute(RutinaDTO dto) {
        final RutinaDomain domain = RutinaDTOMapper.convertirToDomain(dto);
        ConsultarRutinaValidator.ejecutar(domain);

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
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de consultar una rutina por ID.";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de consultar una rutina por ID. Verifique la traza completa.";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

}


