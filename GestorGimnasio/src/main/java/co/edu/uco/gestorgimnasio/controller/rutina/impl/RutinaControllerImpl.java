package co.edu.uco.gestorgimnasio.controller.rutina.impl;

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


import co.edu.uco.gestorgimnasio.controller.rutina.RutinaController;
import co.edu.uco.gestorgimnasio.controller.support.response.Respuesta;
import co.edu.uco.gestorgimnasio.crosscutting.exception.GestorGimnasioException;

import co.edu.uco.gestorgimnasio.service.dto.RutinaDTO;

import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.ConsultarRutinaFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.EditarRutinaFacade;
import co.edu.uco.gestorgimnasio.service.facade.concrete.rutina.RegistrarRutinaFacade;

@RestController
@RequestMapping("/api/v1/rutina")
public final class RutinaControllerImpl implements RutinaController{
	public static final Logger lOGGER = LoggerFactory.getLogger(RutinaControllerImpl.class);
	
	@Override
	@GetMapping("/dummy")
	public final RutinaDTO obtenerDummy() {
		lOGGER.info("El dummy se ha creado correctamente");
		return RutinaDTO.crear();
	}
	@GetMapping
	public final ResponseEntity<Respuesta<RutinaDTO>> consultar(@RequestBody RutinaDTO dto) {
		final Respuesta<RutinaDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {
			final ConsultarRutinaFacade facade = new ConsultarRutinaFacade();
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
	public final ResponseEntity<Respuesta<UUID>> consultarPorId(@PathVariable("id")UUID id) {
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
	
	
	@PostMapping
	public final ResponseEntity<Respuesta<RutinaDTO>> registrar(@RequestBody RutinaDTO dto) {
		final Respuesta<RutinaDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;
		try {
			final RegistrarRutinaFacade facade = new RegistrarRutinaFacade();
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
	public final ResponseEntity<Respuesta<RutinaDTO>> modificar(@PathVariable("id") UUID id,@RequestBody RutinaDTO dto) {
		dto.setId(id);
		final Respuesta<RutinaDTO> respuesta = new Respuesta<>();
		HttpStatus codigoHttpStatus = HttpStatus.BAD_REQUEST;
		try {
			final EditarRutinaFacade facade = new EditarRutinaFacade();
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
	public final ResponseEntity<Respuesta<UUID>> eliminar(@PathVariable("id")UUID id) {
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
}