package co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.entrenador.RegistrarEntrenadorUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.entrenador.RegistrarEntrenadorValidator;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.dto.EntrenadorDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.EntrenadorDTOMapper;

public final class RegistrarEntrenadorFacade implements Facade<EntrenadorDTO>{

	@Override
	public final void execute(EntrenadorDTO dto) {
		final EntrenadorDomain domain = EntrenadorDTOMapper.convertirToDomain(dto);
		RegistrarEntrenadorValidator.ejecutar(domain);
		
		final DAOFactory daoFactory =DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);
		
		try {
			daoFactory.iniciarTransaccion();
			
			var useCase = new RegistrarEntrenadorUseCase(daoFactory);
			useCase.execute(domain);
			
			daoFactory.confirmarTransaccion();
			
		}catch (final GestorGimnasioException excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		}catch (Exception exception) {
			daoFactory.cancelarTransaccion();
			var mensajeUsuario ="Se ha presentado un error inesperado tratando de registrar un nuevo entrenador";
			var mensajeTecnico = "Se ha presentado un error inesperado tratando de registrar un nuevo entrenador. verigue la trasa completa ";
			throw ServiceGestorGimnasioException.crear(exception,mensajeUsuario,mensajeTecnico);
		}
		finally {
			daoFactory.cerrarConexion();
		}
	}

	
}
