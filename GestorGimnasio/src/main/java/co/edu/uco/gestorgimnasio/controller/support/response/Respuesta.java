package co.edu.uco.gestorgimnasio.controller.support.response;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.gestorgimnasio.crosscutting.util.UtilObjeto;

public final class Respuesta<T> {
    private List<T> datos;
    private List<String> mensajes;

    public Respuesta() {
        super();
        setDatos(new ArrayList<>());
        setMensajes(new ArrayList<>()); // Cambié "setMensaje" a "setMensajes"
    }

    public Respuesta(final List<T> dato, final List<String> mensaje) {
        super();
        setDatos(dato);
        setMensajes(mensaje); // Cambié "setMensaje" a "setMensajes"
    }

    public final List<T> getDatos() {
        return datos;
    }

    public final void setDatos(List<T> datos) {
        this.datos = UtilObjeto.obtenerValorDefecto(datos, new ArrayList<>());
    }

    public final List<String> getMensajes() {
        return mensajes;
    }

    public final void setMensajes(List<String> mensajes) {
        this.mensajes = UtilObjeto.obtenerValorDefecto(mensajes, new ArrayList<>());
    }
}

