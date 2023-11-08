package co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.tipoidentificacion;


import co.edu.uco.gestorgimnasio.service.businesslogic.validator.Validator;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.TipoIdentificacionDomain;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.rules.IdTipoIdentificacionValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.tipoidentificacion.rules.TipoIdentificacionValidationRule;


public final class ConsultarPorIdTipoIdentificacionValidator implements Validator<TipoIdentificacionDomain>{


	private static final Validator<TipoIdentificacionDomain> instancia = new ConsultarPorIdTipoIdentificacionValidator();
	
	private ConsultarPorIdTipoIdentificacionValidator() {
		super();
	}
	
	public static final void ejecutar(final TipoIdentificacionDomain data) {
		instancia.execute(data);
	}
	
	@Override
	public void execute(TipoIdentificacionDomain data) {
		TipoIdentificacionValidationRule.ejecutarValidacion(data);
		IdTipoIdentificacionValidationRule.ejecutarValidacion(data.getId());		
	}	

}
