package co.edu.uco.gestorgimnasio.service.facade.concrete.rutina;

import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;

import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;

import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina.RegistrarRutinaUseCase;

import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina.RegistrarRutinaValidator;

import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;

import co.edu.uco.gestorgimnasio.service.dto.RutinaDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;

import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.RutinaDTOMapper;

public final class EditarRutinaFacade implements Facade<RutinaDTO> {


	@Override
	public void execute(RutinaDTO dto) {
		final RutinaDomain domain = RutinaDTOMapper.convertirToDomain(dto);
        RegistrarRutinaValidator.ejecutar(domain);

        final DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);

        try {
            daoFactory.iniciarTransaccion();

           final  var useCase = new RegistrarRutinaUseCase(daoFactory);
            useCase.execute(domain);

            daoFactory.confirmarTransaccion();

        } catch (final GestorGimnasioException excepcion) {
            daoFactory.cancelarTransaccion();
            throw excepcion;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();

            throw ServiceGestorGimnasioException.crear(exception, "Se ha presentado un error inesperado tratando de registrar una nueva rutina", "Se ha presentado un error inesperado tratando de registrar una nueva rutina. Verifique la traza completa.");
        } finally {
            daoFactory.cerrarConexion();
        }	
	}
}
