package co.edu.uco.gestorgimnasio.controller.entrenador;

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
import co.edu.uco.gestorgimnasio.service.dto.EntrenadorDTO;
import co.edu.uco.gestorgimnasio.service.dto.TipoIdentificacionDTO;
import co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador.ConsultarEntrenadorFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador.EliminarEntrenadorFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador.ModificarEntrenadorFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.entrenador.RegistrarEntrenadorFacade;

@RestController
@RequestMapping("/api/v1/entrenador")
public final class EntrenadorController {
	
	
	@GetMapping("/saludo")
	public String saludo() {
		return "hola";
	}
	
	@GetMapping("/dummy")
	public final TipoIdentificacionDTO obtenerDummy() {
		return TipoIdentificacionDTO.crear();
	}
	
	@GetMapping
	public ResponseEntity<Respuesta<EntrenadorDTO>> consultar(@RequestBody EntrenadorDTO dto) {
	    Respuesta<EntrenadorDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

	    try {
	        ConsultarEntrenadorFacade facade = new ConsultarEntrenadorFacade();
	        facade.execute(dto);
	        List<EntrenadorDTO> resultados = new ArrayList<>();
	        resultados.add(dto);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("La consulta de tipo del entrenador se realizó exitosamente.");
	        respuesta.setDatos(resultados);
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del entrenador.";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultar de la clase ConsultarEntrenadorFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta, codigoHttp);
	}




	
	@GetMapping("/{id}")
	public ResponseEntity<Respuesta<EntrenadorDTO>> consultarPorId(@PathVariable("id") UUID id) {
	    Respuesta<EntrenadorDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

	    try {
	    	EntrenadorDTO entrenador = new EntrenadorDTO();
	    	entrenador.setId(id);

	    	ConsultarEntrenadorFacade facade = new ConsultarEntrenadorFacade();
	        facade.execute(entrenador);
	        if (entrenador.getId() != null) {
	            codigoHttp = HttpStatus.OK;
	            respuesta.getMensajes().add("La consulta de entrenador por ID se realizó exitosamente.");
	            respuesta.getDatos().add(entrenador);
	        } else {
	            respuesta.getMensajes().add("No se encontró ningún entrenador con el ID proporcionado.");
	        }
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        String mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del entrenador por ID.";
	        String mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultarPorId de la clase ConsultarEntrenadorFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
	        throw ControllerGestorGimnasioException.crear(excepcion,mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta, codigoHttp);
	}






	
	
	@PostMapping
	public final EntrenadorDTO registrar(@RequestBody EntrenadorDTO dto) {
	    Respuesta<EntrenadorDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        RegistrarEntrenadorFacade facade = new RegistrarEntrenadorFacade();
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El entrenador fue registrado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de registrar la información del nuevo entrenador...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método crear de la clase EjercicioSQLServerDAO tratando de llevar a cabo el registro del nuevo entrenador. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	
	@PutMapping
	public final EntrenadorDTO modificar(@PathVariable("id") UUID id, @RequestBody EntrenadorDTO dto) {
	    Respuesta<EntrenadorDTO> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        ModificarEntrenadorFacade facade = new ModificarEntrenadorFacade();
	        dto.setId(id); 
	        facade.execute(dto);
	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El entrenador fue modificado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información del entrenador...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método modificar de la clase ModificarEntrenadorFacade tratando de llevar a cabo la modificación del entrenador. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return dto;
	}

	

	@DeleteMapping ("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") UUID id) {
	    Respuesta<String> respuesta = new Respuesta<>();
	    HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
	    try {
	        EliminarEntrenadorFacade facade = new EliminarEntrenadorFacade();

	        EntrenadorDTO entrenadorDTO = new EntrenadorDTO();
	        entrenadorDTO.setId(id);

	        facade.execute(entrenadorDTO);

	        codigoHttp = HttpStatus.OK;
	        respuesta.getMensajes().add("El entrenador fue eliminado exitosamente...");
	    } catch (final GestorGimnasioException exception) {
	        respuesta.getMensajes().add(exception.getMensajeUsuario());
	        System.err.println(exception.getMensajeTecnico());
	        System.err.println(exception.getLugar());
	        exception.getExcepcionRaiz().printStackTrace();
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información del entrenador...";
	        var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método execute de la clase EliminarEntrenadorFacade tratando de llevar a cabo la eliminación del entrenador. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
	    }

	    return new ResponseEntity<>(respuesta.toString(), codigoHttp);
	}










	
}
