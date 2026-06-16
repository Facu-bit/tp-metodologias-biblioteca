package com.feria.servicios;

/**
 * ObservadorConsola - implementacion concreta del Observer (SEMANA 2).
 *
 * Cuando GestorFeria notifica un evento, este observador lo IMPRIME en la consola.
 */
public class ObservadorConsola implements ObservadorFeria {

    @Override
    public void actualizar(String evento, String detalle) {
        // muestra el evento en pantalla con formato [TIPO] detalle
        System.out.println("[" + evento + "] " + detalle);
    }
}
