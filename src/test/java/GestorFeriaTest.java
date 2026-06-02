package com.feria;

import com.feria.modelos.Emprendedor;
import com.feria.servicios.GestorFeria;
import com.feria.servicios.ObservadorFeria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GestorFeriaTest {

    private GestorFeria gestor;
    private ObservadorFeria mockObservador;

    @BeforeEach
    void setUp() {
        gestor = new GestorFeria();
        mockObservador = Mockito.mock(ObservadorFeria.class);
        gestor.agregarObservador(mockObservador);
    }

    @Test
    @DisplayName("Registrar emprendedor con productos validos")
    void registrarEmprendedorCorrecto() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        assertEquals(1, gestor.getEmprendedores().size());
        assertEquals("Ana", gestor.getEmprendedores().get(0).getNombre());
    }

    @Test
    @DisplayName("No registrar emprendedor con email invalido")
    void registrarEmprendedorEmailInvalido() {
        gestor.registrarEmprendedorConProductos(
                "Carlos", "E002", "3423456789", "emailSinArroba", "comida",
                Arrays.asList("Tortas"),
                Arrays.asList(1000.0),
                Arrays.asList(10)
        );
        assertEquals(0, gestor.getEmprendedores().size());
    }

    @Test
    @DisplayName("Registrar venta reduce el stock correctamente")
    void registrarVentaReduceStock() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        gestor.registrarVenta("V001", "E001", "Empanadas", 10, 500.0, "2026-06-01");
        assertEquals(40, gestor.getProductos().get(0).getStock());
    }

    @Test
    @DisplayName("No registrar venta si stock insuficiente")
    void registrarVentaStockInsuficiente() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(5)
        );
        gestor.registrarVenta("V001", "E001", "Empanadas", 100, 500.0, "2026-06-01");
        assertEquals(5, gestor.getProductos().get(0).getStock());
    }

    @Test
    @DisplayName("Mock: observador recibe notificacion al registrar emprendedor")
    void observadorNotificadoAlRegistrar() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        Mockito.verify(mockObservador, Mockito.times(1))
                .actualizar(Mockito.eq("REGISTRO"), Mockito.anyString());
    }

    @Test
    @DisplayName("Mock: observador recibe notificacion al registrar venta")
    void observadorNotificadoAlVender() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        gestor.registrarVenta("V001", "E001", "Empanadas", 5, 500.0, "2026-06-01");
        Mockito.verify(mockObservador, Mockito.times(1))
                .actualizar(Mockito.eq("VENTA"), Mockito.anyString());
    }

    @Test
    @DisplayName("TDD: buscar emprendedor por ID existente")
    void buscarEmprendedorPorIdExistente() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        Emprendedor resultado = gestor.buscarEmprendedorPorId("E001");
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
    }

    @Test
    @DisplayName("TDD: buscar emprendedor por ID inexistente devuelve null")
    void buscarEmprendedorPorIdInexistente() {
        Emprendedor resultado = gestor.buscarEmprendedorPorId("NOEXISTE");
        assertNull(resultado);
    }
}