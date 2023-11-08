package co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.ejercicio.EliminarEjercicioUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.ejercicio.EliminarEjercicioValidator;
import co.edu.uco.gestorgimnasio.service.domain.ejercicio.EjercicioDomain;
import co.edu.uco.gestorgimnasio.service.dto.EjercicioDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.EjercicioDTOMapper;


public final class EliminarEjercicioFacade implements Facade<EjercicioDTO> {

	@Override
    public void execute(EjercicioDTO dto) {
        final EjercicioDomain domain = EjercicioDTOMapper.convertirToDomain(dto);
        EliminarEjercicioValidator.ejecutar(domain);

        final DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);

        try {
            daoFactory.iniciarTransaccion();

            var useCase = new EliminarEjercicioUseCase(daoFactory);
            useCase.execute(domain);

            daoFactory.confirmarTransaccion();

        } catch (final GestorGimnasioException excepcion) {
            daoFactory.cancelarTransaccion();
            throw excepcion;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de eliminar un ejercicio";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de eliminar un ejercicio. Verifique la trasa completa ";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}

