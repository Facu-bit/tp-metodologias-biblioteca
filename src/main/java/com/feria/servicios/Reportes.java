package com.feria.servicios;

import com.feria.modelos.Emprendedor;
import com.feria.modelos.Producto;
import com.feria.modelos.Venta;

/**
 * Clase Reportes - genera informes sobre la feria.
 *
 * SEMANA 1: aca habia code smells de codigo duplicado (SMELL 4/5) y un metodo
 * que hacia demasiadas cosas (SMELL 2 - God Method).
 */
public class Reportes {

    // SMELL CORREGIDO (Codigo duplicado): antes habia DOS metodos casi iguales:
    // generarReportePorCategoria() y generarReportePorCategoriaAlternativo().
    // Se elimino el duplicado y quedo solo este.
    public String generarReportePorCategoria(GestorFeria gestor, String categoria) {
        String reporte = "=== REPORTE DE EMPRENDEDORES - CATEGORÍA: " + categoria + " ===\n";
        for (Emprendedor e : gestor.getEmprendedores()) {
            if (e.getCategoria().equals(categoria)) {
                reporte += e.mostrarInfo();
                reporte += "---\n";
            }
        }
        return reporte;
    }

    // Suma el total facturado de todas las ventas (con descuentos aplicados).
    public double calcularVentasTotales(GestorFeria gestor) {
        double total = 0;
        for (Venta v : gestor.getVentas()) {
            total += v.calcularTotalConDescuento();
        }
        return total;
    }

    // Imprime un resumen general de la feria.
    public void imprimirResumenEjecutivo(GestorFeria gestor) {
        System.out.println("========== RESUMEN EJECUTIVO ==========");
        System.out.println("Total emprendedores: " + gestor.getEmprendedores().size());
        System.out.println("Total productos: " + gestor.getProductos().size());
        System.out.println("Total ventas: " + gestor.getVentas().size());
        System.out.println("Total facturado: $" + calcularVentasTotales(gestor));
        System.out.println("Emprendedores con stock bajo: " + contarEmprendedoresConStockBajo(gestor));
        System.out.println("=======================================");
    }

    // SMELL 2 CORREGIDO (SRP): antes esta logica de contar estaba metida DENTRO de
    // imprimirResumenEjecutivo(). Se extrajo a su propio metodo con una sola tarea.
    private int contarEmprendedoresConStockBajo(GestorFeria gestor) {
        int contador = 0;
        for (Emprendedor e : gestor.getEmprendedores()) {
            for (Producto p : e.getProductos()) {
                if (p.isStockBajo()) {
                    contador++;
                    break;
                }
            }
        }
        return contador;
    }
}
