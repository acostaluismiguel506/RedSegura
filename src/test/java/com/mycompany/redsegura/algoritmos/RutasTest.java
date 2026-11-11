/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.algoritmos;

/**
 *
 * @author acost
 */


import com.mycompany.redsegura.aplicacion.RedSeguraFactory;
import com.mycompany.redsegura.grafo.MixedGraph;
import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.modelo.Vertex;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RutasTest {

    private MixedGraph graph;

    @BeforeEach
    public void setUp() {
        graph = RedSeguraFactory.createGraph();
    }

    private String ids(List<Vertex> path) {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : path) {
            sb.append(v.getId()).append(" ");
        }
        return sb.toString().trim();
    }

    /** Grafo con dos islas: A-B y C-D (desconectado). */
    private MixedGraph grafoDesconectado() {
        MixedGraph g = new MixedGraph();
        Vertex a = new Vertex("A", "A", "T");
        Vertex b = new Vertex("B", "B", "T");
        Vertex c = new Vertex("C", "C", "T");
        Vertex d = new Vertex("D", "D", "T");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addEdge(new Edge("E1", "ab", a, b, 1, false));
        g.addEdge(new Edge("E2", "cd", c, d, 1, false));
        return g;
    }

    // ---------- Dijkstra ----------

    @Test
    public void testControlS02S08Da24() {
        DijkstraSolver d = new DijkstraSolver(graph);
        List<Vertex> path = d.findShortestPath("S02", "S08");
        assertEquals("S02 S01 S07 S08", ids(path));
        assertEquals(24.0, d.getPathCost(path), 0.001);
    }

    @Test
    public void testBloquearR16CambiaRutaPeroNoCosto() {
        graph.removeEdge("R16");
        DijkstraSolver d = new DijkstraSolver(graph);
        List<Vertex> path = d.findShortestPath("S02", "S08");
        assertEquals("S02 S01 S09 S10 S08", ids(path));
        assertEquals(24.0, d.getPathCost(path), 0.001);
    }

    @Test
    public void testDijkstraRespetaOrientacion() {
        // S08 -> S10 (9) -> S09 (3) -> S07 (3) = 15
        DijkstraSolver d = new DijkstraSolver(graph);
        List<Vertex> path = d.findShortestPath("S08", "S07");
        assertEquals(15.0, d.getPathCost(path), 0.001);
    }

    @Test
    public void testDijkstraGrafoDesconectado() {
        DijkstraSolver d = new DijkstraSolver(grafoDesconectado());
        assertTrue(d.findShortestPath("A", "D").isEmpty());
    }

    @Test
    public void testDijkstraVerticeInexistente() {
        DijkstraSolver d = new DijkstraSolver(graph);
        assertThrows(IllegalArgumentException.class,
                () -> d.findShortestPath("S99", "S08"));
        assertThrows(IllegalArgumentException.class,
                () -> d.findShortestPath("S02", "S99"));
    }

    // ---------- BFS ----------

    @Test
    public void testBfsDesdeS02() {
        List<Vertex> r = new BreadthFirstSearch(graph).search("S02");
        assertEquals(12, r.size());
        assertEquals("S02", r.get(0).getId());
        assertEquals("S01", r.get(1).getId());
        assertEquals("S06", r.get(2).getId());
    }

    @Test
    public void testBfsGrafoDesconectado() {
        List<Vertex> r = new BreadthFirstSearch(grafoDesconectado()).search("A");
        assertEquals(2, r.size());
    }

    @Test
    public void testBfsVerticeInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> new BreadthFirstSearch(graph).search("S99"));
    }

    // ---------- DFS ----------

    @Test
    public void testDfsDesdeS02() {
        List<Vertex> r = new DepthFirstSearch(graph).search("S02");
        assertEquals(12, r.size());
        assertEquals("S02", r.get(0).getId());
        assertEquals("S01", r.get(1).getId());
        assertEquals("S07", r.get(2).getId());
    }

    @Test
    public void testDfsGrafoDesconectado() {
        List<Vertex> r = new DepthFirstSearch(grafoDesconectado()).search("C");
        assertEquals(2, r.size());
    }

    @Test
    public void testBfsYDfsDifierenEnOrden() {
        List<Vertex> b = new BreadthFirstSearch(graph).search("S02");
        List<Vertex> d = new DepthFirstSearch(graph).search("S02");
        assertNotEquals(ids(b), ids(d));
    }
}
