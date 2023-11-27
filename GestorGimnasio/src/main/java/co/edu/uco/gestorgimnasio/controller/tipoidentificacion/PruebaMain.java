package co.edu.uco.gestorgimnasio.controller.tipoidentificacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;




public class PruebaMain {

	public static void main(String[] args) {
		
		try {
            // Cargar el controlador de la base de datos
			Class.forName("org.postgresql.Driver");
            final String url = "jdbc:postgresql://localhost:5432/gestor";
    		final String usuario = "postgres";
    		final String clave = "";
            // Establecer la conexión
         Connection conexion = DriverManager.getConnection(url, usuario, clave);

            // Consulta SQL
            String sql = "SELECT * FROM tipoidentificacion";
            PreparedStatement statement = conexion.prepareStatement(sql);

            // Ejecutar la consulta
            ResultSet resultado = statement.executeQuery();
System.out.println("Hola gente");
            // Procesar los resultados
            while (resultado.next()) {
            	System.out.println(resultado);
                UUID id = UUID.fromString(resultado.getString("id")) ;
                String codigo = resultado.getString("codigo");
                String nombre = resultado.getString("nombre");
                boolean estado = resultado.getBoolean("estado");
             // Puedes imprimir o procesar los resultados como desees
                System.out.println("ID: " +id + ", Código: " + codigo + ", Nombre: " + nombre + ", Estado: " + estado);
            }

            // Cerrar recursos
            resultado.close();
            statement.close();
            conexion.close();

        } catch ( SQLException e) {
            ((Throwable) e).printStackTrace();
        }catch(ClassNotFoundException e) {
        	
        }
		//DAOFactory factory = DAOFactory.obtenerDAOFactory(TipoDAOFactory.SQLSERVER);
		//ConsultarTipoIdentificacionUseCase use = new ConsultarTipoIdentificacionUseCase(factory);
		//TipoIdentificacionDomain dimain = new TipoIdentificacionDomain(null, null, null, false);
	//	use.execute(dimain);
//System.out.println(use);
	
	}
}
