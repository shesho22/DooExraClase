package co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador.EliminarEntrenadorUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.entrenador.EliminarEntrenadorValidator;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.dto.EntrenadorDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.EntrenadorDTOMapper;


public final class EliminarEntrenadorFacade implements Facade<EntrenadorDTO> {

	@Override
    public void execute(EntrenadorDTO dto) {
        final EntrenadorDomain domain = EntrenadorDTOMapper.convertirToDomain(dto);
        EliminarEntrenadorValidator.ejecutar(domain);

        final DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);

        try {
            daoFactory.iniciarTransaccion();

            var useCase = new EliminarEntrenadorUseCase(daoFactory);
            useCase.execute(domain);

            daoFactory.confirmarTransaccion();

        } catch (final GestorGimnasioException excepcion) {
            daoFactory.cancelarTransaccion();
            throw excepcion;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de eliminar un entrenador";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de eliminar un entrenador. Verifique la trasa completa ";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}

