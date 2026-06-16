package com.feria.modelos;

/**
 * Clase Producto - representa un producto que vende un emprendedor.
 *
 * SEMANA 1: aca habia dos code smells importantes:
 * - Campos publicos (SMELL 3)
 * - Dos metodos identicos hayStockBajo() e isStockBajo() (SMELL 4 - Codigo duplicado)
 */
public class Producto {

    // SMELL 3 CORREGIDO (Campos publicos): antes todos estos atributos eran "public",
    // o sea cualquier clase los podia modificar sin control. Ahora son PRIVADOS.
    private String nombre;
    private double precio;
    private int stock;
    private String categoriaProducto;
    private String emprendedorId;

    public Producto(String nombre, double precio, int stock, String categoriaProd, String empId) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoriaProducto = categoriaProd;
        this.emprendedorId = empId;
    }

    // Calcula el valor total de este producto (precio por cantidad en stock).
    public double valorTotal() {
        return precio * stock;
    }

    // Devuelve un texto con los datos del producto.
    public String mostrar() {
        return nombre + " - $" + precio + " (stock: " + stock + ")";
    }

    // SMELL 4 CORREGIDO (Codigo duplicado): antes existian DOS metodos que hacian
    // exactamente lo mismo: hayStockBajo() e isStockBajo(). Se elimino el duplicado
    // y quedo solo este, que devuelve true si el stock es menor a 5.
    public boolean isStockBajo() {
        return stock < 5;
    }

    // Resta una cantidad al stock (se usa cuando se concreta una venta).
    public void reducirStock(int cantidad) {
        this.stock -= cantidad;
    }

    // GETTERS y SETTER: acceso controlado a los atributos privados.
    public String getNombre()          { return nombre; }
    public double getPrecio()          { return precio; }
    public int getStock()              { return stock; }
    public String getCategoriaProducto() { return categoriaProducto; }
    public String getEmprendedorId()   { return emprendedorId; }
    public void setStock(int stock)    { this.stock = stock; }
}
