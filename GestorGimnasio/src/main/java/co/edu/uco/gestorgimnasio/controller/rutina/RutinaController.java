package co.edu.uco.gestorgimnasio.controller.rutina;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.uco.gestorgimnasio.controller.support.response.Respuesta;

import co.edu.uco.gestorgimnasio.service.dto.RutinaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RutinaAPI", description = "Ofrece las api de consumo para todas las operaciones con el Ejercicio")
public interface RutinaController {
	@Operation(summary="Obtener dummy", description="Servicio encargadao de obtener la estructura de todos los ti de JSON basica para todas las operaciones de Tipo Identificacion")
	RutinaDTO obtenerDummy();
		
	
	
	
	@Operation(summary = "Consultar", description = "Servicio encargadao de obtener la informacion de todos los tipo de Identificacion ue cumplen los parametros de filtrado enviados")
	 ResponseEntity<Respuesta<RutinaDTO>> consultar(@RequestBody RutinaDTO dto);
	
	@Operation(summary="ConsultarPorId", description="Obtener la informacion del tipo de identificacion asociado a la Id enviado como filtro de consulta")
	ResponseEntity<Respuesta<UUID>> consultarPorId(@PathVariable("id") UUID id) ;
	@Operation(summary="Registrar", description="Servicio encargado de registrar l informacion del nuevo tipo de informacion enviado a la Id enviado como parametro")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tipo identificacion registrado exitosamente") ,
			@ApiResponse(responseCode = "400", description = "Tipo identificacion no registrado por algun problema conocido") ,
			@ApiResponse(responseCode = "500", description = "Tipo identificacion no registrado por algun problema inesperado") })
	
	ResponseEntity<Respuesta<RutinaDTO>> registrar(@RequestBody RutinaDTO dto);
	@Operation(summary="Modificar", description="Servicio encargado de modificar la informacion correspondiente a la Id enviado como parametro")
	 ResponseEntity<Respuesta<RutinaDTO>> modificar(@PathVariable("id") UUID id,@RequestBody RutinaDTO dto);
		
	
	@Operation(summary="Eliminar", description="Servicio encargado de eliminar de forma definitiva la informacion correspondiente a la Id enviado como parametro")
	ResponseEntity<Respuesta<UUID>> eliminar(@PathVariable("id") UUID id);
}
