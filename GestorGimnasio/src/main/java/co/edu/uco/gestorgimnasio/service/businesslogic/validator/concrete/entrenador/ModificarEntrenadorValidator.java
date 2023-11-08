package co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.entrenador;

import co.edu.uco.gestorgimnasio.service.businesslogic.validator.Validator;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.EntrenadorDomain;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.CorreoElectronicoValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.FechaNacimientoValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.EntrenadorValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.IdentificacionValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.NombreCompletoValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.entrenador.rules.NumeroTelefonoMovilValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.rules.TipoIdentificacionValidationRule;

public class ModificarEntrenadorValidator implements Validator<EntrenadorDomain>{
	private static final Validator<EntrenadorDomain> instancia = new ModificarEntrenadorValidator();
	
	private ModificarEntrenadorValidator() {
		super();
	}
	
	public static final void ejecutar(final EntrenadorDomain data) {
		instancia.execute(data);
	}

	@Override
	public void execute(EntrenadorDomain data) {
		EntrenadorValidationRule.ejecutarValidacion(data);
	    TipoIdentificacionValidationRule.ejecutarValidacion(data.getTipoidentificacion());
	    IdentificacionValidationRule.ejecutarValidacion(data.getIdentificacion());
	    NombreCompletoValidationRule.ejecutarValidacion(data.getNombreCompleto());
	    CorreoElectronicoValidationRule.ejecutarValidacion(data.getCorreoElectornico());
	    NumeroTelefonoMovilValidationRule.ejecutarValidacion(data.getNumeroTelefonoMovil());
	    FechaNacimientoValidationRule.ejecutarValidacion(data.getFechaNacimiento());
	}
	
}
