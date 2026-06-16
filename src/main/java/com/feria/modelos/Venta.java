package com.feria.modelos;

/**
 * Clase Venta - representa una venta realizada en la feria.
 *
 * SEMANA 1: aca los atributos tambien eran publicos (SMELL 3) y se pasaron a privados.
 */
public class Venta {

    // SMELL 3 CORREGIDO (Campos publicos): atributos ahora privados con getters/setters.
    private String idVenta;
    private String emprendedorId;
    private String productoNombre;
    private int cantidad;
    private double precioUnitario;
    private String fecha;
    private boolean pagoRealizado;

    public Venta(String idVenta, String empId, String prodNombre, int cant, double precioUnit, String fecha) {
        this.idVenta = idVenta;
        this.emprendedorId = empId;
        this.productoNombre = prodNombre;
        this.cantidad = cant;
        this.precioUnitario = precioUnit;
        this.fecha = fecha;
        this.pagoRealizado = false; // al crearse, la venta arranca como NO pagada
    }

    // Calcula el total a cobrar aplicando descuentos:
    // - 10% si se compran mas de 10 unidades
    // - 5% adicional si el total supera $5000
    public double calcularTotalConDescuento() {
        double total = cantidad * precioUnitario;
        if (cantidad > 10) total = total * 0.9;
        if (total > 5000) total = total * 0.95;
        return total;
    }

    // Arma un texto tipo recibo con todos los datos de la venta.
    public String generarRecibo() {
        String recibo = "=== RECIBO DE VENTA ===\n";
        recibo += "Venta ID: " + idVenta + "\n";
        recibo += "Fecha: " + fecha + "\n";
        recibo += "Producto: " + productoNombre + "\n";
        recibo += "Cantidad: " + cantidad + "\n";
        recibo += "Precio unitario: $" + precioUnitario + "\n";
        recibo += "Total con descuentos: $" + calcularTotalConDescuento() + "\n";
        recibo += "Pago: " + (pagoRealizado ? "Realizado" : "Pendiente") + "\n";
        return recibo;
    }

    // GETTERS y SETTERS: acceso controlado a los datos.
    public String getIdVenta()        { return idVenta; }
    public String getEmprendedorId()  { return emprendedorId; }
    public String getProductoNombre() { return productoNombre; }
    public int getCantidad()          { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public String getFecha()          { return fecha; }
    public boolean isPagoRealizado()  { return pagoRealizado; }
    public void setPagoRealizado(boolean pago) { this.pagoRealizado = pago; }
}
