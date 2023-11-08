package co.edu.uco.gestorgimnasio.service.businesslogic.validator.concrete.rutina;


import co.edu.uco.gestorgimnasio.service.businesslogic.validator.Validator;
import co.edu.uco.gestorgimnasio.service.domain.rutina.RutinaDomain;
import co.edu.uco.gestorgimnasio.service.domain.rutina.rules.ListaEjerciciosValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.rutina.rules.NombreRutinaValidationRule;
import co.edu.uco.gestorgimnasio.service.domain.rutina.rules.IdRutinaValidationRule;



public final class RegistrarRutinaValidator implements Validator<RutinaDomain> {

    private static final Validator<RutinaDomain> instancia = new RegistrarRutinaValidator();

    private RegistrarRutinaValidator() {
        super();
    }

    public static final void ejecutar(final RutinaDomain data) {
        instancia.execute(data);
    }

    @Override
    public void execute(RutinaDomain data) {
        IdRutinaValidationRule.ejecutarValidacion(data.getId());
        NombreRutinaValidationRule.ejecutarValidacion(data.getNombre());
        ListaEjerciciosValidationRule.ejecutarValidacion(data.getEjercicios());
    }
}

