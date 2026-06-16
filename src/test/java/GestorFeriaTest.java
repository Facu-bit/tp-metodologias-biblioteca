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

/**
 * GestorFeriaTest - suite de tests automatizados (SEMANA 3).
 *
 * Usa JUnit 5 (para los tests) y Mockito (para los mocks).
 * En total son 8 tests: 4 unitarios, 2 con mocks y 2 de TDD.
 */
class GestorFeriaTest {

    private GestorFeria gestor;
    private ObservadorFeria mockObservador; // este es un MOCK (objeto falso)

    // @BeforeEach: se ejecuta ANTES de cada test, deja todo limpio.
    @BeforeEach
    void setUp() {
        gestor = new GestorFeria();
        // MOCKITO: creamos un observador falso para no depender de consola ni archivo.
        mockObservador = Mockito.mock(ObservadorFeria.class);
        gestor.agregarObservador(mockObservador);
    }

    // TEST UNITARIO 1: registrar un emprendedor con datos validos funciona.
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

    // TEST UNITARIO 2 (caso limite): un email sin @ NO debe registrar al emprendedor.
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

    // TEST UNITARIO 3: al vender, el stock del producto debe bajar.
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

    // TEST UNITARIO 4 (caso limite): si no hay stock suficiente, no se vende.
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

    // TEST CON MOCK 1: verificamos que al registrar, el observador recibe el aviso "REGISTRO".
    @Test
    @DisplayName("Mock: observador recibe notificacion al registrar emprendedor")
    void observadorNotificadoAlRegistrar() {
        gestor.registrarEmprendedorConProductos(
                "Ana", "E001", "3423456789", "ana@gmail.com", "comida",
                Arrays.asList("Empanadas"),
                Arrays.asList(500.0),
                Arrays.asList(50)
        );
        // Mockito.verify: comprueba que actualizar() se llamo 1 vez con el evento REGISTRO.
        Mockito.verify(mockObservador, Mockito.times(1))
                .actualizar(Mockito.eq("REGISTRO"), Mockito.anyString());
    }

    // TEST CON MOCK 2: verificamos que al vender, el observador recibe el aviso "VENTA".
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

    // TEST TDD 1: buscar un emprendedor que SI existe devuelve el correcto.
    // (Este test se escribio ANTES de implementar buscarEmprendedorPorId = paso ROJO)
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

    // TEST TDD 2: buscar un ID que NO existe devuelve null.
    @Test
    @DisplayName("TDD: buscar emprendedor por ID inexistente devuelve null")
    void buscarEmprendedorPorIdInexistente() {
        Emprendedor resultado = gestor.buscarEmprendedorPorId("NOEXISTE");
        assertNull(resultado);
    }
}
