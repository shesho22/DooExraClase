package co.edu.uco.gestorgimnasio.service.businesslogic;


public interface UseCase<D> {
	void execute(D domain);
}
