package co.edu.uco.gestorgimnasio.data.dao.base;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uco.gestorgimnasio.crosscutting.exception.concrete.DataGestorGimnasioException;
import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;

public class SQLDAO {

    private Connection conexion;

    protected SQLDAO(final Connection conexion) {
        setConexion(conexion);
    }

    private final void setConexion(final Connection conexion) {
        this.conexion = conexion;
    }

    protected final Connection getConexion() {
        return conexion;
    }

    protected void cerrarConexion() {
        if (!UtilObjeto.esNulo(conexion)     		) {
            try {
                conexion.close();
            } catch (SQLException e) {
               throw DataGestorGimnasioException.crear("Ocurrio un problema al cerrar la conexion");
            }
        }
    }
}
