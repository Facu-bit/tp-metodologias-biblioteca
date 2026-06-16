package com.feria.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Emprendedor - representa a un emprendedor de la feria con sus productos.
 *
 * SEMANA 1 (Refactorizacion + SOLID):
 * - Esta clase tenia atributos con nombres cripticos (n, t, m, cat) = CODE SMELL "Cryptic Names".
 * - Tambien tenia un metodo mostrarInfoYValidar() que hacia DOS cosas = CODE SMELL "God Method".
 */
public class Emprendedor {

    // SMELL 1 CORREGIDO (Cryptic Names): antes se llamaban n, t, m, cat.
    // Ahora tienen nombres descriptivos. Ademas son PRIVADOS (antes eran publicos) -> encapsulamiento.
    private String nombre;
    private String id;
    private String telefono;
    private String email;
    private String categoria;

    private List<Producto> productos;

    // Constructor: recibe los datos del emprendedor e inicializa su lista de productos vacia.
    public Emprendedor(String nombre, String id, String telefono, String email, String categoria) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.productos = new ArrayList<>();
    }

    // SMELL 2 CORREGIDO (God Method + SRP): antes existia mostrarInfoYValidar() que mostraba
    // datos Y validaba al mismo tiempo. Se separo en dos metodos con una sola responsabilidad cada uno.
    // ESTE metodo SOLO arma el texto con la informacion del emprendedor.
    public String mostrarInfo() {
        String info = "Emprendedor: " + nombre + "\n";
        info += "ID: " + id + "\n";
        info += "Contacto: " + telefono + " | " + email + "\n";
        info += "Categoría: " + categoria + "\n";
        info += "Productos:\n";
        for (Producto p : productos) {
            info += "  - " + p.getNombre() + " ($" + p.getPrecio() + ")\n";
        }
        return info;
    }

    // SRP: este metodo SOLO valida que los datos del emprendedor sean correctos.
    public boolean validarCompleto() {
        if (nombre == null || nombre.length() < 2) return false;
        if (email == null || !email.contains("@")) return false;
        if (categoria == null || (!categoria.equals("comida") && !categoria.equals("artesania")
                && !categoria.equals("tecnologia") && !categoria.equals("ropa"))) return false;
        return true;
    }

    // Agrega un producto a la lista del emprendedor.
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    // Calcula el valor total del stock (precio * cantidad de cada producto).
    public int calcularValorTotalStock() {
        int total = 0;
        for (Producto p : productos) {
            total += p.getPrecio() * p.getStock();
        }
        return total;
    }

    // GETTERS: como los atributos son privados (SMELL 3 corregido), el acceso desde afuera
    // se hace a traves de estos metodos. Esto da control sobre como se accede a los datos.
    public String getNombre()    { return nombre; }
    public String getId()        { return id; }
    public String getTelefono()  { return telefono; }
    public String getEmail()     { return email; }
    public String getCategoria() { return categoria; }
    public List<Producto> getProductos() { return productos; }
}
