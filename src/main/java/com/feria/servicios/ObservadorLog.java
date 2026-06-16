package com.feria.servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ObservadorLog - otra implementacion concreta del Observer (SEMANA 2).
 *
 * Cuando GestorFeria notifica un evento, este observador lo GUARDA en un archivo de texto.
 * Demuestra la ventaja del patron: agregamos una nueva forma de notificacion
 * SIN tocar GestorFeria (cumple OCP).
 */
public class ObservadorLog implements ObservadorFeria {

    private String archivoLog; // nombre del archivo donde se guardan los eventos

    public ObservadorLog(String archivoLog) {
        this.archivoLog = archivoLog;
    }

    @Override
    public void actualizar(String evento, String detalle) {
        try {
            // true = modo "append": agrega al final sin borrar lo anterior
            FileWriter fw = new FileWriter(archivoLog, true);
            // escribe la fecha/hora + el evento + el detalle
            fw.write(LocalDateTime.now() + " [" + evento + "] " + detalle + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir el log: " + e.getMessage());
        }
    }
}
