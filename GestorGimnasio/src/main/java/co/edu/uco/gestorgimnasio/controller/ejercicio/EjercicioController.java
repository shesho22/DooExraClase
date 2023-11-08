package co.edu.uco.gestorgimnasio.controller.ejercicio;

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
import co.edu.uco.gestorgimnasio.service.dto.EjercicioDTO;
import co.edu.uco.gestorgimnasio.service.dto.TipoIdentificacionDTO;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.ConsultarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.EliminarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.ModificarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.RegistrarEjercicioFacade;

@RestController
@RequestMapping("/api/v1/ejercicio")
public final class EjercicioController {
	
	
	@GetMapping("/saludo")
	public String saludo() {
		return "hola";
	}
	
	@GetMapping("/dummy")
	public final TipoIdentificacionDTO obtenerDummy() {
		return TipoIdentificacionDTO.crear();
	}
	
	@GetMapping
	public ResponseEntity<Respuesta<EjercicioDTO>> consultar(@RequestBody EjercicioDTO dto) {
	    Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

	    try {
	        ConsultarEjercicioFacade facade = new ConsultarEjercicioFacade();
	        facade.execute(dto);
	        List<EjercicioDTO> resultados = new ArrayList<>();
	        resultados.add(dto);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La consulta del ejercicio se realizó exitosamente.");
	        respuesta.setDatos(resultados);
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del ejercicio.";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultar de la clase ConsultarEjercicioFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta, codigoHttp);
	}




	
	@GetMapping("/{id}")
	public ResponseEntity<Respuesta<EjercicioDTO>> consultarPorId(@PathVariable("id") UUID id) {
	    Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

	    try {
	    	EjercicioDTO ejercicio = new EjercicioDTO();
	    	ejercicio.setId(id);

	        ConsultarEjercicioFacade facade = new ConsultarEjercicioFacade();
	        facade.execute(ejercicio);
	        if (ejercicio.getId() != null) {
	            codigoHttp = HttpStatus.OK;
	            respuesta.getMensajes().add("La consulta de ejercicio por ID se realizó exitosamente.");
	            respuesta.getDatos().add(ejercicio);
	        } else {
	            respuesta.getMensajes().add("No se encontró ningún ejercicio con el ID proporcionado.");
	        }
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        String mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del ejercicio por ID.";
	        String mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultarPorId de la clase ConsultarEjercicioFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion,mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta, codigoHttp);
	}






	
	
	@PostMapping
	public final EjercicioDTO registrar(@RequestBody EjercicioDTO dto) {
	    Respuesta<TipoIdentificacionDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        RegistrarEjercicioFacade facade = new RegistrarEjercicioFacade();
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El ejercicio fue registrado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de registrar la información del nuevo ejercicio...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método crear de la clase EjercicioSQLServerDAO tratando de llevar a cabo el registro del nuevo ejercicio. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	
	@PutMapping
	public final EjercicioDTO modificar(@PathVariable("id") UUID id, @RequestBody EjercicioDTO dto) {
	    Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        ModificarEjercicioFacade facade = new ModificarEjercicioFacade();
	        dto.setId(id); 
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El ejercicio fue modificado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información del ejercicio...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método modificar de la clase ModificarEjercicioFacade tratando de llevar a cabo la modificación del ejercicio. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	

	@DeleteMapping ("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") UUID id) {
	    Respuesta<String> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        EliminarEjercicioFacade facade = new EliminarEjercicioFacade();

	        EjercicioDTO ejercicioDTO = new EjercicioDTO();
	        ejercicioDTO.setId(id);

	        facade.execute(ejercicioDTO);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El ejercicio fue eliminado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información del ejercicio...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método execute de la clase EliminarEjercicioFacade tratando de llevar a cabo la eliminación del ejercicio. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta.toString(), codigoHttp);
	}










	
}
