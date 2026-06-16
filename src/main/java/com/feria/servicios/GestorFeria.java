package com.feria.servicios;

import com.feria.modelos.Emprendedor;
import com.feria.modelos.Producto;
import com.feria.modelos.Venta;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestorFeria - es el CEREBRO del sistema. Maneja emprendedores, productos y ventas.
 *
 * SEMANA 2 (Patron Observer): esta clase es el "SUBJECT" (el observado).
 * Antes tenia System.out.println mezclado con la logica. Ahora solo avisa a sus
 * observadores con notificar() y no sabe quien escucha. Esto cumple OCP.
 */
public class GestorFeria {

    private List<Emprendedor> emprendedores;
    private List<Producto> productos;
    private List<Venta> ventas;

    // PATRON OBSERVER: lista de observadores que seran notificados cuando pase algo.
    private List<ObservadorFeria> observadores;

    public GestorFeria() {
        emprendedores = new ArrayList<>();
        productos = new ArrayList<>();
        ventas = new ArrayList<>();
        observadores = new ArrayList<>();
    }

    // OBSERVER: agrega un observador a la lista (ej: consola o log).
    public void agregarObservador(ObservadorFeria observador) {
        observadores.add(observador);
    }

    // OBSERVER: quita un observador de la lista.
    public void quitarObservador(ObservadorFeria observador) {
        observadores.remove(observador);
    }

    // OBSERVER: recorre TODOS los observadores y les avisa que paso algo.
    // GestorFeria no sabe si el observador imprime, guarda en archivo o manda email.
    private void notificar(String evento, String detalle) {
        for (ObservadorFeria obs : observadores) {
            obs.actualizar(evento, detalle);
        }
    }

    // Registra un emprendedor junto con sus productos.
    public void registrarEmprendedorConProductos(String nombre, String id, String telefono,
                                                 String email, String categoria,
                                                 List<String> nombresProductos,
                                                 List<Double> precios,
                                                 List<Integer> stocks) {
        // primero valida (si los datos son malos, no registra nada)
        if (!validarDatosEmprendedor(nombre, email)) return;

        Emprendedor e = new Emprendedor(nombre, id, telefono, email, categoria);

        // crea cada producto y lo agrega tanto al emprendedor como a la lista general
        for (int i = 0; i < nombresProductos.size(); i++) {
            Producto p = new Producto(nombresProductos.get(i), precios.get(i), stocks.get(i), categoria, id);
            e.agregarProducto(p);
            productos.add(p);
        }

        emprendedores.add(e);
        // OBSERVER: en lugar de System.out.println, avisamos a los observadores
        notificar("REGISTRO", "Emprendedor registrado: " + nombre + " con " + nombresProductos.size() + " productos");
    }

    // SEMANA 1 (SRP): metodo extraido para que la validacion sea una responsabilidad aparte.
    private boolean validarDatosEmprendedor(String nombre, String email) {
        if (nombre == null || nombre.length() < 2) {
            notificar("ERROR", "Nombre de emprendedor invalido");
            return false;
        }
        if (email == null || !email.contains("@")) {
            notificar("ERROR", "Email de emprendedor invalido");
            return false;
        }
        return true;
    }

    // Registra una venta: busca el producto, verifica stock, descuenta y notifica.
    public void registrarVenta(String idVenta, String empId, String prodNombre,
                               int cantidad, double precio, String fecha) {
        Producto productoEncontrado = buscarProducto(prodNombre, empId);

        if (productoEncontrado == null) {
            notificar("ERROR", "Producto no encontrado: " + prodNombre);
            return;
        }

        if (productoEncontrado.getStock() < cantidad) {
            notificar("ERROR", "Stock insuficiente para: " + prodNombre);
            return;
        }

        Venta v = new Venta(idVenta, empId, prodNombre, cantidad, precio, fecha);
        ventas.add(v);
        productoEncontrado.reducirStock(cantidad);
        notificar("VENTA", "Venta registrada: " + prodNombre + " - Nuevo stock: " + productoEncontrado.getStock());
    }

    // SEMANA 1 (SRP): metodo extraido. Su unica tarea es buscar un producto por nombre y emprendedor.
    private Producto buscarProducto(String nombre, String empId) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre) && p.getEmprendedorId().equals(empId)) {
                return p;
            }
        }
        return null;
    }

    // Cobra todas las ventas que estan pendientes de pago y notifica el total recaudado.
    public void procesarVentasPendientesYCobrar() {
        double totalRecaudado = 0;
        for (Venta v : ventas) {
            if (!v.isPagoRealizado()) {
                double monto = v.calcularTotalConDescuento();
                totalRecaudado += monto;
                v.setPagoRealizado(true);
                notificar("COBRO", "Cobrada venta " + v.getIdVenta() + " por $" + monto);
            }
        }
        notificar("COBRO", "Total recaudado: $" + totalRecaudado);
    }

    // Devuelve la lista de emprendedores que tienen al menos un producto con stock bajo.
    public List<Emprendedor> getEmprendedoresConStockBajo() {
        List<Emprendedor> resultado = new ArrayList<>();
        for (Emprendedor e : emprendedores) {
            for (Producto p : e.getProductos()) {
                if (p.isStockBajo()) {
                    resultado.add(e);
                    break;
                }
            }
        }
        return resultado;
    }

    // SEMANA 3 (TDD): este metodo se agrego con Test Driven Development.
    // Primero se escribio el test (fallaba = ROJO), despues se implemento esto (VERDE).
    // Busca un emprendedor por su ID, devuelve null si no existe.
    public Emprendedor buscarEmprendedorPorId(String id) {
        for (Emprendedor e : emprendedores) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    // GETTERS de las listas (privadas por encapsulamiento - SEMANA 1).
    public List<Emprendedor> getEmprendedores() { return emprendedores; }
    public List<Producto> getProductos()         { return productos; }
    public List<Venta> getVentas()               { return ventas; }
}
