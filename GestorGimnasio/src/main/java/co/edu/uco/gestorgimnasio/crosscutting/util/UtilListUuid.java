package co.edu.uco.gestorgimnasio.crosscutting.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UtilListUuid {
    private UtilListUuid() {
    }

    // Método para verificar si una lista de UUID está vacía
    public static boolean estaVacia(List<UUID> lista) {
        return lista == null || lista.isEmpty();
    }

    // Método para obtener el tamaño de una lista de UUID
    public static int tamano(List<UUID> lista) {
        return lista != null ? lista.size() : 0;
    }

    // Método para agregar un UUID al final de la lista
    public static void agregarAlFinal(List<UUID> lista, UUID uuid) {
        if (lista != null && uuid != null) {
            lista.add(uuid);
        }
    }

    // Método para eliminar un UUID de la lista
    public static void eliminarUUID(List<UUID> lista, UUID uuid) {
        if (lista != null && uuid != null) {
            lista.remove(uuid);
        }
    }

    // Método para verificar si un UUID está en la lista
    public static boolean contieneUUID(List<UUID> lista, UUID uuid) {
        return lista != null && uuid != null && lista.contains(uuid);
    }

    // Método para vaciar una lista de UUID
    public static void vaciarLista(List<UUID> lista) {
        if (lista != null) {
            lista.clear();
        }
    }

    public static List<UUID> crearPorDefecto(List<UUID> ejercicios) {
        List<UUID> listaPorDefecto = new ArrayList<>();
        ejercicios.forEach(ejercicio -> {
            listaPorDefecto.add(new UUID(0, 0));
        });
        return listaPorDefecto;
    }

}

