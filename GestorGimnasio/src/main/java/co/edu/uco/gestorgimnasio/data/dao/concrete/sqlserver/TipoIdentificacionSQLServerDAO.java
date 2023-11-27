package co.edu.uco.gestorgimnasio.data.dao.concrete.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.DataGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilTexto;
import co.edu.uco.gestorgimnasio.data.dao.TipoIdentificacionDAO;
import co.edu.uco.gestorgimnasio.data.dao.base.SQLDAO;
import co.edu.uco.gestorgimnasio.data.entity.TipoIdentificacionEntity;

public final class TipoIdentificacionSQLServerDAO extends SQLDAO implements TipoIdentificacionDAO {

	public TipoIdentificacionSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public final void crear(final TipoIdentificacionEntity entity) {
		final var sentencia = new StringBuilder();
		
		sentencia.append("insert into TipoIdentificacion (id,codigo,nombre,estado) ");
		sentencia.append("VALUES (?,?,?,?) ");
		
		try (final var sentenciaPreparada =getConexion().prepareStatement(sentencia.toString())){
			sentenciaPreparada.setObject(1,entity.getId());
			sentenciaPreparada.setString(2,entity.getCodigo());
			sentenciaPreparada.setString(3,entity.getNombre());
			sentenciaPreparada.setBoolean(4,entity.isEstado());
			
			sentenciaPreparada.executeUpdate();
			
		} catch (final SQLException excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de registrar la informacion del nuevo tipo de identificacion...";
			var mensajeTecnico ="Se ha presentado un problema de tipo SQLException en el metodo crear de la clase TipoIdentificacionSQLServer tratando de llevar a cabo el registro del nuevo tipo de identificacion. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}catch (final Exception excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de registrar la informacion del nuevo tipo de identificacion...";
			var mensajeTecnico ="Se ha presentado un problema inesperado de tipo Exception en el metodo crear de la clase TipoIdentificacionSQLServer tratando de llevar a cabo el registro del nuevo tipo de identificacion. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
	}

	@Override
	public final void modificar(final TipoIdentificacionEntity entity) {
	    final var sentencia = new StringBuilder();
	    sentencia.append("UPDATE TipoIdentificacion SET codigo = ?, nombre = ?, estado = ? WHERE id = ? ");
	    
	    try (final var sentenciaPreparada = getConexion().prepareStatement(sentencia.toString())) {
	    	sentenciaPreparada.setObject(4,entity.getId());
			sentenciaPreparada.setString(1,entity.getCodigo());
			sentenciaPreparada.setString(2,entity.getNombre());
			sentenciaPreparada.setBoolean(3,entity.isEstado());
			
	        
	        sentenciaPreparada.executeUpdate();
	    } catch (final SQLException excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información del tipo de identificación...";
	        var mensajeTécnico = "Se ha presentado un problema de tipo SQLException en el método modificar de la clase TipoIdentificacionSQLServer tratando de llevar a cabo la modificación del tipo de identificación. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw DataGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTécnico);
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de modificar la información del tipo de identificación...";
	        var mensajeTécnico = "Se ha presentado un problema inesperado de tipo Exception en el método modificar de la clase TipoIdentificacionSQLServer tratando de llevar a cabo la modificación del tipo de identificación. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw DataGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTécnico);
	    }
	}

	@Override
	public final void eliminar(final TipoIdentificacionEntity entity) {
	    final var sentencia = new StringBuilder();
	    sentencia.append("DELETE FROM TipoIdentificacion WHERE id = ?");
	    
	    try (final var sentenciaPreparada = getConexion().prepareStatement(sentencia.toString())) {
	        sentenciaPreparada.setObject(1, entity.getId());
	        sentenciaPreparada.executeUpdate();
	    } catch (final SQLException excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información del tipo de identificación...";
	        var mensajeTécnico = "Se ha presentado un problema de tipo SQLException en el método eliminar de la clase TipoIdentificacionSQLServer tratando de eliminar el tipo de identificación. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw DataGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTécnico);
	    } catch (final Exception excepcion) {
	        var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la información del tipo de identificación...";
	        var mensajeTécnico = "Se ha presentado un problema inesperado de tipo Exception en el método eliminar de la clase TipoIdentificacionSQLServer tratando de eliminar el tipo de identificación. Por favor revise la traza completa del problema presentado para así poder identificar qué sucedió...";
	        throw DataGestorGimnasioException.crear(excepcion, mensajeUsuario, mensajeTécnico);
	    }
	}


	@Override
	public final Optional<TipoIdentificacionEntity> consultarPorId(final UUID id) {
		
		final var sentencia = new StringBuilder();
		sentencia.append("SELECT * ");
		sentencia.append("FROM TipoIdentificacion ");
		sentencia.append("WHERE id = ? ");
		
		Optional<TipoIdentificacionEntity> resultado = Optional.empty();
		
		try (final var sentenciaPreparada = getConexion().prepareStatement(sentencia.toString())){
			
			sentenciaPreparada.setObject(1, id);
			resultado = ejecutarConsultaPorId(sentenciaPreparada);		
		}catch (final DataGestorGimnasioException exception) {
			throw exception;
		}catch(final SQLException excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema de tipo SQLException en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de preparar la sentencia SQL de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}catch (final Exception excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema inesperado de tipo Exception en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de preparar la sentencia SQL de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
		return resultado;
	}


	
	private Optional<TipoIdentificacionEntity>  ejecutarConsultaPorId(final PreparedStatement sentenciaPreparada) {
		Optional<TipoIdentificacionEntity> resultado = Optional.empty();
		
		try (final var resultados = sentenciaPreparada.executeQuery()){
			if (resultados.next()) {
				var tipoIdentificacionEntity = TipoIdentificacionEntity.crear(UUID.fromString(resultados.getObject("id").toString()), resultados.getString("codigo"), resultados.getString("nombre"), resultados.getBoolean("estado"));
				resultado = Optional.of(tipoIdentificacionEntity);
			}
		}catch (final SQLException excepcion){
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema de tipo SQLException en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de recuperar los datos de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
		catch (final Exception excepcion){
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema inesperado de tipo Exception en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de recuperar los datos de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
		return resultado;
	} 
	
	@Override
	public final List<TipoIdentificacionEntity> consultar (final TipoIdentificacionEntity entity) {
		final var parametros = new ArrayList<Object>();
		
		final String sentencia = formarSentenciaConsulta(entity, parametros);
		
		try (final var sentenciaPreparada = getConexion().prepareStatement(sentencia)){
			colocarParametrosConsulta(sentenciaPreparada,parametros);
			return ejecutarConsulta(sentenciaPreparada);
			
		}catch (final DataGestorGimnasioException excepcion) {
			throw excepcion;
		} 
		catch (final SQLException excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de llevar a cabo la consulta de los tipos de identificacion...";
			var mensajeTecnico ="Se ha presentado un problema  en el metodo colocar parametros consulta en la clase TipoIdentificacionSQLServerDAO tratando de preparar la sentencia sql. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);	
		}catch (final Exception excepcion) {
			var mensajeUsuario ="Se ha presentado un problema tratando de llevar a cabo la consulta de los tipos de identificacion...";
			var mensajeTecnico ="Se ha presentado un problema inesperado de tipo exception tratando de preparar la sentencia sql. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);	
		}
	
	}
	private final String formarSentenciaConsulta(final TipoIdentificacionEntity entity, final List<Object> parametros) {
		
		final StringBuilder sentencia = new StringBuilder();
		String operadorCondicional = "WHERE";
		
		sentencia.append("SELECT * FROM TipoIdentificacion; ");
		
		if(!UtilObjeto.esNulo(entity)) {
			if(!UtilObjeto.esNulo(entity.getId())) {
				sentencia.append(operadorCondicional).append(" id = ? ");
				operadorCondicional = "AND";
				parametros.add(entity.getId());
			}	
			if(!UtilTexto.estaVacio(entity.getCodigo())) {
				sentencia.append(operadorCondicional).append(" codigo = ? ");
				operadorCondicional = "AND";
				parametros.add(entity.getCodigo());
			}
			if(!UtilTexto.estaVacio(entity.getNombre())) {
				sentencia.append(operadorCondicional).append(" nombre = ? ");
				operadorCondicional = "AND";
				parametros.add(entity.getNombre());
			}
			
			//validar  ¿que pasa aqui?
			if(!UtilObjeto.esNulo(entity.isEstado())) {
				sentencia.append(operadorCondicional).append(" estado = ? ");
				parametros.add(entity.isEstado());
			}
		}
		sentencia.append("ORDER BY codigo ASC ");
		return sentencia.toString();
	}
	
	
	
	private final void colocarParametrosConsulta(final PreparedStatement sentenciaPreparada, final List<Object> parametros) {
		try {
			for (int  indice = 0; indice < parametros.size(); indice++) {
				sentenciaPreparada.setObject(indice + 1, parametros.get(indice));
			}
			}catch (final SQLException excepcion) {
				var mensajeUsuario ="Se ha presentado un problema tratando de llevar a cabo la consulta de los tipos de identificacion...";
				var mensajeTecnico ="Se ha presentado un problema  en el metodo colocar parametros consulta en la clase TipoIdentificacionSQLServerDAO. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
				throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);	
			}catch (final Exception excepcion) {
				var mensajeUsuario ="Se ha presentado un problema tratando de llevar a cabo la consulta de los tipos de identificacion...";
				var mensajeTecnico ="Se ha presentado un inesperado  en el metodo colocar parametros consulta en la clase TipoIdentificacionSQLServerDAO. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
				throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);	
			}
		}
	
	private final List<TipoIdentificacionEntity>ejecutarConsulta(final PreparedStatement sentenciaPreparada){
		final var listaResultados = new ArrayList<TipoIdentificacionEntity>();
		
		try (final var resultados = sentenciaPreparada.executeQuery()){
			while (resultados.next()) {
				var tipoIdentificacionEntity = TipoIdentificacionEntity.crear(UUID.fromString(resultados.getObject("id").toString()), resultados.getString("codigo"), resultados.getString("nombre"), resultados.getBoolean("estado"));
				listaResultados.add(tipoIdentificacionEntity);
			}
		}catch (final SQLException excepcion){
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema de tipo SQLException en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de recuperar los datos de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
		catch (final Exception excepcion){
			var mensajeUsuario ="Se ha presentado un problema tratando de consultar la informacion del tipo de identificacion por el identificador deceado...";
			var mensajeTecnico ="Se ha presentado un problema inesperado de tipo Exception en el metodo consultarPorId de la clase TipoIdentificacionSQLServer tratando de recuperar los datos de la consulta del tipo identificacion deseado. por favor revise la trasa completa del problema presentado para asi poder identificar que sucedio...";
			throw DataGestorGimnasioException.crear(excepcion,mensajeUsuario,mensajeTecnico);
		}
		return listaResultados;
	}
	
}


