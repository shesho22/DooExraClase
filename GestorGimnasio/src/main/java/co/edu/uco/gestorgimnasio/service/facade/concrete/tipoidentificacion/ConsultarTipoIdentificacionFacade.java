package co.edu.uco.gestorgimnasio.service.facade.concrete.tipoidentificacion;


import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ServiceGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.tipoidentificacion.ConsultarTipoIdentificacionUseCase;
import co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.tipoidentificacion.ConsultarTipoIdentificacionValidator;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.dto.TipoIdentificacionDTO;
import co.edu.uco.gestorgimnasio.service.facade.Facade;
import co.edu.uco.gestorgimnasio.service.mapper.dto.concrete.TipoIdentificacionDTOMapper;

public final class ConsultarTipoIdentificacionFacade implements Facade<TipoIdentificacionDTO> {

    @Override
    public void execute(TipoIdentificacionDTO dto) {
        final TipoIdentificacionDomain domain = TipoIdentificacionDTOMapper.convertirToDomain(dto);
        ConsultarTipoIdentificacionValidator.ejecutar(domain);

        final DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);

        try {
            daoFactory.iniciarTransaccion();

            var useCase = new ConsultarTipoIdentificacionUseCase(daoFactory);
            useCase.execute(domain);

            daoFactory.confirmarTransaccion();

        } catch (final GestorGimnasioException excepcion) {
            daoFactory.cancelarTransaccion();
            throw excepcion;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de consultar un tipo de identificación";
            var mensajeTecnico = "Se ha presentado un error inesperado tratando de consultar un tipo de identificación. Verifique la trasa completa ";
            throw ServiceGestorGimnasioException.crear(exception, mensajeUsuario, mensajeTecnico);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}

