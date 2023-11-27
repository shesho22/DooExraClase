package co.edu.uco.gestorgimnasio.controller.tipoidentificacion;

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
import co.edu.uco.gestorgimnasio.service.dto.TipoIdentificacionDTO;
import co.edu.uco.gestorgimnasio.service.facade.concrete.tipoidentificacion.ConsultarTipoIdentificacionFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.tipoidentificacion.EliminarTipoIdentificacionFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.tipoidentificacion.ModificarTipoIdentificacionFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.tipoidentificacion.RegistrarTipoIdentificacionFacade;

@RestController
@RequestMapping("gestor/api/v1/tipoidentificacion")
public final class TipoIdentificacionController {

	@GetMapping("/saludo")
	public String saludo() {
		return "hola";
	}

	@GetMapping("/dummy")
	public final TipoIdentificacionDTO obtenerDummy() {
		return TipoIdentificacionDTO.crear();
	}

	@GetMapping
	public ResponseEntity<Respuesta<TipoIdentificacionDTO>> consultar(@RequestBody TipoIdentificacionDTO dto) {
		Respuesta<TipoIdentificacionDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

		try {
			ConsultarTipoIdentificacionFacade facade = new ConsultarTipoIdentificacionFacade();
			facade.execute(dto);
			List<TipoIdentificacionDTO> resultados = new ArrayList<>();
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
	public ResponseEntity<Respuesta<TipoIdentificacionDTO>> consultarPorId(@PathVariable("id") UUID id) {
		Respuesta<TipoIdentificacionDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

		try {
			TipoIdentificacionDTO tipoIdentificacion = new TipoIdentificacionDTO();
			tipoIdentificacion.setId(id);

			ConsultarTipoIdentificacionFacade facade = new ConsultarTipoIdentificacionFacade();
			facade.execute(tipoIdentificacion);
			if (tipoIdentificacion.getId() != null) {
				codigoHttp = HttpStatus.OK;
				respuesta.getMensajes().add("La consulta de tipo de identificación por ID se realizó exitosamente.");
				respuesta.getDatos().add(tipoIdentificacion);
			} else {
				respuesta.getMensajes().add("No se encontró ningún Tipo de Identificación con el ID proporcionado.");
			}
		} catch (final GestorGimnasioException exception) {
			respuesta.getMensajes().add(exception.getMensajeUsuario());
			
			exception.getExcepcionRaiz().printStackTrace();
		} catch (final Exception excepcion) {
			final  String mensajeUsuario = "Se ha presentado un problema tratando de consultar la información del tipo de identificación por ID.";
			final String mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método consultarPorId de la clase ConsultarTipoIdentificacionFacade. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió.";
			throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}

		return new ResponseEntity<>(respuesta, codigoHttp);
	}

	@PostMapping
	public final ResponseEntity<Respuesta<TipoIdentificacionDTO>> registrar(@RequestBody TipoIdentificacionDTO dto) {
		Respuesta<TipoIdentificacionDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
		try {
			RegistrarTipoIdentificacionFacade facade = new RegistrarTipoIdentificacionFacade();
			facade.execute(dto);
			codigoHttp = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificación fue registrado exitosamente...");
		} catch (final GestorGimnasioException exception) {
			respuesta.getMensajes().add(exception.getMensajeUsuario());
			
			exception.getExcepcionRaiz().printStackTrace();
		} catch (final Exception excepcion) {
			final var mensajeUsuario = "Se ha presentado un problema tratando de registrar la información del nuevo ejercicio...";
			final 	var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método crear de la clase EjercicioSQLServerDAO tratando de llevar a cabo el registro del nuevo ejercicio. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
			throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
		return new ResponseEntity<>(respuesta, codigoHttp);
	}

	@PutMapping
	public final ResponseEntity<Respuesta<TipoIdentificacionDTO>> modificar(@PathVariable("id") UUID id,
			@RequestBody TipoIdentificacionDTO dto) {
		Respuesta<TipoIdentificacionDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
		try {
			ModificarTipoIdentificacionFacade facade = new ModificarTipoIdentificacionFacade();
			dto.setId(id);
			facade.execute(dto);
			codigoHttp = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificación fue modificado exitosamente...");
		} catch (final GestorGimnasioException exception) {
			respuesta.getMensajes().add(exception.getMensajeUsuario());
			
			exception.getExcepcionRaiz().printStackTrace();
		} catch (final Exception excepcion) {
			final var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información del tipo de identificación...";
			final var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método modificar de la clase ModificarTipoIdentificacionFacade tratando de llevar a cabo la modificación del tipo de identificación. Por favor revise la trasa completa del problema presentado para así poder identificar qué sucedió...";
			throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
		return new ResponseEntity<>(respuesta, codigoHttp);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") UUID id) {
		Respuesta<String> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
		try {
			EliminarTipoIdentificacionFacade facade = new EliminarTipoIdentificacionFacade();

			TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
			tipoIdentificacionDTO.setId(id);

			facade.execute(tipoIdentificacionDTO);

			codigoHttp = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificación fue eliminado exitosamente...");
		} catch (final GestorGimnasioException exception) {
			respuesta.getMensajes().add(exception.getMensajeUsuario());
			System.err.println(exception.getMensajeTecnico());
			System.err.println(exception.getLugar());
			exception.getExcepcionRaiz().printStackTrace();
		} catch (final Exception excepcion) {
			var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información del tipo de identificación...";
			var mensajeTecnico = "Se ha presentado un problema inesperado de tipo Exception en el método execute de la clase EliminarTipoIdentificacionFacade tratando de llevar a cabo la eliminación del tipo de identificación. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
			throw ControllerGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTecnico);
		}
		return new ResponseEntity<>(respuesta.toString(), codigoHttp);
	}

}
