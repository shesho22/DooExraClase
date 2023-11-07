package co.edu.uco.gestorgimnasio.controller.ejercicio.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import co.edu.uco.gestorgimnasio.controller.ejercicio.EjercicioController;
import co.edu.uco.gestorgimnasio.controller.support.response.Respuesta;
import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;

import co.edu.uco.gestorgimnasio.service.dto.EjercicioDTO;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.ConsultarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.EditarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.EliminarEjercicioFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.ejercicio.RegistrarEjercicioFacade;

@RestController
@RequestMapping("/api/v1/ejercicio")
public final class EjercicioControllerImpl implements EjercicioController {

	public static final Logger lOGGER = LoggerFactory.getLogger(EjercicioControllerImpl.class);

	@Override
	@GetMapping("/dummy")
	public final EjercicioDTO obtenerDummy() {
		lOGGER.info("El dummy se ha creado correctamente");
		return EjercicioDTO.crear();
	}

	@Override
	@GetMapping
	public final ResponseEntity<Respuesta<EjercicioDTO>> consultar(@RequestBody EjercicioDTO dto) {
		final Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {
			final ConsultarEjercicioFacade facade = new ConsultarEjercicioFacade();
			facade.execute(dto);
			codigoHttpStatus = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificacion fue consultado exitosamente");
			lOGGER.info("El tipo de identificacion fue consultado exitosamente");
		} catch (final GestorGimnasioException excepcion) {
			respuesta.getMensajes().add(excepcion.getMensajeUsuario());
			lOGGER.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
		} catch (final Exception excepcion) {
			codigoHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			respuesta.getMensajes().add(
					"Se ha presentado un problema inesperado tratando de registrar el tipo de identificacion deseado2 ");
			lOGGER.error(
					"Se ha presentado un problema inesperado tratando de consultar el tipo de identificacion deseado ",
					excepcion);
		}
		return new ResponseEntity<>(respuesta, codigoHttpStatus);

	}

	@Override
	@GetMapping("/{id}")
	public final ResponseEntity<Respuesta<UUID>> consultarPorId(@PathVariable("id") UUID id) {

		final Respuesta<UUID> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {

			codigoHttpStatus = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificacion fue eliminado exitosamente");
			lOGGER.info("El tipo de identificacion fue registrado exitosamente");
		} catch (final GestorGimnasioException excepcion) {
			respuesta.getMensajes().add(excepcion.getMensajeUsuario());
			lOGGER.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
		} catch (final Exception excepcion) {
			codigoHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			respuesta.getMensajes().add(
					"Se ha presentado un problema inesperado tratando de eliminar el tipo de identificacion deseado ");
			lOGGER.error(
					"Se ha presentado un problema inesperado tratando de registrar el tipo de identificacion deseado ",
					excepcion);
		}
		return new ResponseEntity<>(respuesta, codigoHttpStatus);

	}

	@Override
	@PostMapping
	public final ResponseEntity<Respuesta<EjercicioDTO>> registrar(@RequestBody EjercicioDTO dto) {
		final Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
		try {
			final RegistrarEjercicioFacade facade = new RegistrarEjercicioFacade();
			facade.execute(dto);
			codigoHttp = HttpStatus.OK;
			respuesta.getMensajes().add("El ejercicio fue registrado existosamente...");
			lOGGER.info("El ejercicio fue consultado exitosamente");
		} catch (final GestorGimnasioException exception) {
			respuesta.getMensajes().add(exception.getMensajeUsuario());
			System.err.println(exception.getMensajeTecnico());
			System.err.println(exception.getLugar());
			lOGGER.error(exception.getMensajeTecnico(), exception.getExcepcionRaiz());
		} catch (final Exception exception) {
			codigoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			respuesta.getMensajes().add("Se ha presentado un problema inesperado tratando de registrar el ejercicio");

			lOGGER.error("Se ha presentado un problema inesperado tratando de registrar el ejercicio deseado ",
					exception);
		}

		return new ResponseEntity<>(respuesta, codigoHttp);
	}

	@PutMapping("/{id}")
	@Override
	public final ResponseEntity<Respuesta<EjercicioDTO>> modificar(@PathVariable("id") UUID id,
			@RequestBody EjercicioDTO dto) {

		dto.setId(id);
		final Respuesta<EjercicioDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {
			final EditarEjercicioFacade facade = new EditarEjercicioFacade();
			facade.execute(dto);
			codigoHttpStatus = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificacion fue modifcado exitosamente");
			lOGGER.info("El tipo de identificacion fue registrado exitosamente");
		} catch (final GestorGimnasioException excepcion) {
			respuesta.getMensajes().add(excepcion.getMensajeUsuario());
			lOGGER.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
		} catch (final Exception excepcion) {
			codigoHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			respuesta.getMensajes().add(
					"Se ha presentado un problema inesperado tratando de modificar el tipo de identificacion deseado ");
			lOGGER.error(
					"Se ha presentado un problema inesperado tratando de registrar el tipo de identificacion deseado ",
					excepcion);
		}
		return new ResponseEntity<>(respuesta, codigoHttpStatus);

	}

	@DeleteMapping("/{id}")
	@Override
	public final ResponseEntity<Respuesta<UUID>> eliminar(@PathVariable("id") UUID id, @RequestBody EjercicioDTO dto) {
		final Respuesta<UUID> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {
			final EliminarEjercicioFacade facade = new EliminarEjercicioFacade();
			facade.execute(dto);
			codigoHttpStatus = HttpStatus.OK;
			respuesta.getMensajes().add("El tipo de identificacion fue eliminado exitosamente");
			lOGGER.info("El tipo de identificacion fue registrado exitosamente");
		} catch (final GestorGimnasioException excepcion) {
			respuesta.getMensajes().add(excepcion.getMensajeUsuario());
			lOGGER.error(excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());
		} catch (final Exception excepcion) {
			codigoHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			respuesta.getMensajes().add(
					"Se ha presentado un problema inesperado tratando de eliminar el tipo de identificacion deseado ");
			lOGGER.error(
					"Se ha presentado un problema inesperado tratando de registrar el tipo de identificacion deseado ",
					excepcion);
		}
		return new ResponseEntity<>(respuesta, codigoHttpStatus);

	}
}
