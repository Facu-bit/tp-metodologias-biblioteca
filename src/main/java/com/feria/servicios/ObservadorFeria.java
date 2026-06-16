package com.feria.servicios;

/**
 * Interfaz ObservadorFeria - es el CONTRATO del patron Observer (SEMANA 2).
 *
 * Cualquier clase que quiera "escuchar" los eventos de la feria debe implementar
 * esta interfaz y definir el metodo actualizar(). Asi GestorFeria puede avisarle
 * sin saber que tipo de observador es concretamente.
 */
public interface ObservadorFeria {
    // Este metodo se llama cada vez que ocurre un evento en la feria.
    // evento = tipo (REGISTRO, VENTA, COBRO, ERROR) / detalle = descripcion.
    void actualizar(String evento, String detalle);
}
