package co.edu.uco.gestorgimnasio.controller.rutina;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.gestorgimnasio.controller.support.response.Respuesta;
import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.ControllerGestorGimnasioException;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.TipoDAOFactory;
import co.edu.uco.gestorgimnasio.service.businesslogic.concrete.rutina.ConsultarPorIdRutinaUseCase;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.dto.RutinaDTO;
import co.edu.uco.gestorgimnasio.service.dto.TipoIdentificacionDTO;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.ConsultarRutinaFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.EliminarRutinaFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.ModificarRutinaFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.RegistrarRutinaFacade;

@RestController
@RequestMapping("/api/v1/rutina")
public final class RutinaController {
	
	
	@GetMapping("/saludo")
	public String saludo() {
		return "hola";
	}
	
	@GetMapping("/dummy")
	public final TipoIdentificacionDTO obtenerDummy() {
		return TipoIdentificacionDTO.crear();
	}
	
	@GetMapping
	public ResponseEntity<Respuesta<RutinaDTO>> consultar(@RequestBody RutinaDTO dto) {
	    Respuesta<RutinaDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

	    try {
	        ConsultarRutinaFacade facade = new ConsultarRutinaFacade();
	        facade.execute(dto);
	        List<RutinaDTO> resultados = new ArrayList<>();
	        resultados.add(dto);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La consulta de tipo de identificación se realizó exitosamente.");
	        respuesta.setDatos(resultados);
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del tipo de identificación.";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultar de la clase ConsultarTipoIdentificacionFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta, codigoHttp);
	}




	@GetMapping("/{id}")
	public ResponseEntity<Respuesta<RutinaDTO>> consultarPorId(@PathVariable("id") UUID id) {
	    Respuesta<RutinaDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.OK;
	    try {
	        DAOFactory daoFactory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);
	        ConsultarPorIdRutinaUseCase useCase = new ConsultarPorIdRutinaUseCase(daoFactory);
	        RutinaDomain tipoIdentificacionDomain = new RutinaDomain(id, null, null, null);
	        useCase.execute(tipoIdentificacionDomain); 
	        respuesta.getMensajes().add("La consulta de rutina por ID se realizó exitosamente.");
	    } catch (final GestorGimnasioException exception) {
	        codigoHttp = HttpStatus.NOT_FOUND; 
	        respuesta.getMensajes().add("No se encontró ningúna rutina con el ID proporcionado.");
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del rutina por ID.";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultarPorIdRutina. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }
	    return new ResponseEntity<>(respuesta, codigoHttp);
	}












	
	
	@PostMapping
	public final RutinaDTO registrar(@RequestBody RutinaDTO dto) {
	    Respuesta<RutinaDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        RegistrarRutinaFacade facade = new RegistrarRutinaFacade();
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La rutina fue registrado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de registrar la información de la nueva rutina...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método crear de la clase RutinaSQLServerDAO tratando de llevar a cabo el registro del nueva rutina. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	
	@PutMapping
	public final RutinaDTO modificar(@PathVariable("id") UUID id, @RequestBody RutinaDTO dto) {
	    Respuesta<RutinaDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        ModificarRutinaFacade facade = new ModificarRutinaFacade();
	        dto.setId(id); 
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La rutina fue modificado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información de la rutina...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método modificar de la clase ModificarRutinaFacade tratando de llevar a cabo la modificación de rutina. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	

	@DeleteMapping ("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") UUID id) {
	    Respuesta<String> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        EliminarRutinaFacade facade = new EliminarRutinaFacade();
	        RutinaDTO tipoIdentificacionDTO = new RutinaDTO();
	        tipoIdentificacionDTO.setId(id);

	        facade.execute(tipoIdentificacionDTO);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La rutina fue eliminado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información de la rutina...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método execute de la clase EliminarRutinaFacade tratando de llevar a cabo la eliminación de rutina. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta.toString(), codigoHttp);
	}










	
}
