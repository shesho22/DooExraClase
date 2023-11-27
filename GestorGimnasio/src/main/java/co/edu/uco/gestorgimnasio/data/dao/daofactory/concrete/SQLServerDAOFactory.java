package co.edu.uco.gestorgimnasio.data.dao.daofactory.concrete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.CrossCuttingGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.DataGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.messages.CatalogoMensajes;
import co.edu.uco.gestorgimnasio.crosscutting.messages.enumerator.CodigoMensaje;

import co.edu.uco.gestorgimnasio.crosscutting.util.UtilSQL;
import co.edu.uco.gestorgimnasio.data.dao.EjercicioDAO;
import co.edu.uco.gestorgimnasio.data.dao.EntrenadorDAO;
import co.edu.uco.gestorgimnasio.data.dao.RutinaDAO;
import co.edu.uco.gestorgimnasio.data.dao.TipoIdentificacionDAO;
import co.edu.uco.gestorgimnasio.data.dao.concrete.sqlserver.EjercicioSQLServerDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.DAOFactory;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.concrete.sqlserver.EntrenadorSQLServerDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.concrete.sqlserver.RutinaSQLServerDAO;
import co.edu.uco.gestorgimnasio.data.dao.daofactory.concrete.sqlserver.TipoIdentificacionSQLServerDAO;

public class SQLServerDAOFactory extends DAOFactory {

	private Connection conexion;

	public SQLServerDAOFactory() {

		abrirConexion();
	}

	@Override
	protected final void abrirConexion() {

		try {
			Class.forName("org.postgresql.Driver");
			final String url = "jdbc:postgresql://localhost:5432/gestor";
			final String usuario = "postgres";
			final String clave = "";
			UtilSQL.conexionAbierta(conexion);

			conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println("Se ha conectado a la base de datos");
		} catch (final SQLException excepcion) {

			throw DataGestorGimnasioException.crear(excepcion,
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000027));
		} catch (final Exception excepcion) {

			throw DataGestorGimnasioException.crear(excepcion,
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000028));
		}
	}

	@Override
	public final void cerrarConexion() {
		UtilSQL.cerrarConexion(conexion);
	}

	@Override
	public final void iniciarTransaccion() {
		UtilSQL.iniciarTransaccion(conexion);
	}

	@Override
	public final void confirmarTransaccion() {
		UtilSQL.confirmarTransaccion(conexion);
	}

	@Override
	public final void cancelarTransaccion() {
		UtilSQL.cancelarTransaccion(conexion);
	}

	@Override
	public EjercicioDAO obtenerEjercicioDAO() {

		if (!UtilSQL.conexionAbierta(conexion)) {

			throw CrossCuttingGestorGimnasioException.crear(
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000034));
		}

		return new EjercicioSQLServerDAO(conexion);
	}

	@Override
	public EntrenadorDAO obtenerEntrenadorDAO() {
		if (!UtilSQL.conexionAbierta(conexion)) {

			throw CrossCuttingGestorGimnasioException.crear(
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000034));
		}
		return new EntrenadorSQLServerDAO(conexion);
	}

	@Override
	public TipoIdentificacionDAO obtenerTipoIdentificacionDAO() {
		if (!UtilSQL.conexionAbierta(conexion)) {

			throw CrossCuttingGestorGimnasioException.crear(
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000034));
		}
		return new TipoIdentificacionSQLServerDAO(conexion);
	}

	@Override
	public RutinaDAO obtenerRutinaDAO() {
		if (!UtilSQL.conexionAbierta(conexion)) {

			throw CrossCuttingGestorGimnasioException.crear(
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000004),
					CatalogoMensajes.obtenerContenidoMensaje(CodigoMensaje.M0000000034));
		}
		return new RutinaSQLServerDAO(conexion);
	}
}
