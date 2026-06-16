package com.feria.utils;

import com.feria.modelos.Emprendedor;

/**
 * Clase Validadores - metodos utilitarios para validar datos.
 *
 * SEMANA 1: esta clase usaba los campos publicos viejos (e.m, e.t, e.n, e.cat).
 * Al corregir el SMELL de campos publicos, se actualizo para usar los getters.
 */
public class Validadores {

    // Valida que el email no sea nulo, tenga @ y un largo minimo.
    public static boolean emailValido(String email) {
        if (email == null) return false;
        if (!email.contains("@")) return false;
        if (email.length() < 5) return false;
        return true;
    }

    // Valida que el telefono no sea nulo y tenga al menos 8 caracteres.
    public static boolean telefonoValido(String telefono) {
        if (telefono == null) return false;
        if (telefono.length() < 8) return false;
        return true;
    }

    // Valida que el precio sea positivo y el stock no sea negativo.
    public static boolean validarPrecioStock(double precio, int stock) {
        if (precio <= 0) return false;
        if (stock < 0) return false;
        return true;
    }

    // CORREGIDO (SEMANA 1): antes accedia a los campos publicos (e.m, e.t...).
    // Ahora usa los GETTERS del emprendedor, respetando el encapsulamiento.
    public static boolean validarEmprendedorCompleto(Emprendedor e) {
        if (e == null) return false;
        if (!emailValido(e.getEmail())) return false;
        if (!telefonoValido(e.getTelefono())) return false;
        if (e.getNombre() == null || e.getNombre().length() < 2) return false;
        if (e.getCategoria() == null) return false;
        return true;
    }

    // Verifica que la categoria sea una de las permitidas.
    public static boolean categoriaPermitida(String categoria) {
        String[] permitidas = {"comida", "artesania", "tecnologia", "ropa"};
        for (String c : permitidas) {
            if (c.equals(categoria)) return true;
        }
        return false;
    }
}
