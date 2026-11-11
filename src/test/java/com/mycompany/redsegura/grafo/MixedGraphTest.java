/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.grafo;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.aplicacion.RedSeguraFactory;
import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.modelo.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MixedGraphTest {

    private MixedGraph graph;

    @BeforeEach
    public void setUp() {
        graph = RedSeguraFactory.createGraph();
    }

    @Test
    public void testDatosOriginales() {
        assertEquals(12, graph.getVertexCount());
        assertEquals(18, graph.getEdgeCount());
    }

    @Test
    public void testVerticeDuplicado() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.addVertex(new Vertex("S01", "Otro", "Prueba")));
    }

    @Test
    public void testConexionDuplicada() {
        Edge e = new Edge("R01", "Repetida",
                graph.getVertex("S01"), graph.getVertex("S02"), 3, true);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(e));
    }

    @Test
    public void testConexionConVerticeInexistente() {
        Vertex fantasma = new Vertex("S99", "Fantasma", "Prueba");
        Edge e = new Edge("R99", "Mala",
                graph.getVertex("S01"), fantasma, 3, true);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(e));
    }

    @Test
    public void testPesoInvalido() {
        Vertex a = graph.getVertex("S01");
        Vertex b = graph.getVertex("S02");
        assertThrows(IllegalArgumentException.class,
                () -> new Edge("R98", "Cero", a, b, 0, true));
        assertThrows(IllegalArgumentException.class,
                () -> new Edge("R98", "Negativo", a, b, -5, true));
    }

    @Test
    public void testVerticeVacioInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vertex("", "Sin id", "Prueba"));
    }

    @Test
    public void testVerticeInexistente() {
        assertNull(graph.getVertex("S99"));
        assertFalse(graph.containsVertex("S99"));
    }

    @Test
    public void testOrientacionDeVecinos() {
        // R16 es dirigida S07 -> S08: S08 no puede volver a S07
        assertTrue(graph.getNeighbors("S07").contains(graph.getVertex("S08")));
        assertFalse(graph.getNeighbors("S08").contains(graph.getVertex("S07")));
        // R15 es no dirigida S07 <-> S09
        assertTrue(graph.getNeighbors("S09").contains(graph.getVertex("S07")));
    }

    @Test
    public void testEliminarConexion() {
        assertTrue(graph.removeEdge("R16"));
        assertNull(graph.getEdge("R16"));
        assertFalse(graph.removeEdge("R16"));
    }
}