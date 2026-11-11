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
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BreadthFirstSearchTest {

    private MixedGraph graph;
    private BreadthFirstSearch bfs;

    @BeforeEach
    public void setUp() {
        graph = RedSeguraFactory.createGraph();
        bfs = new BreadthFirstSearch(graph);
    }

    @Test
    public void testRaizTienePadreNuloYNivelCero() {
        Map<String, String> parents = bfs.getParents("S02");
        Map<String, Integer> levels = bfs.getLevels("S02");
        assertTrue(parents.containsKey("S02"));
        assertNull(parents.get("S02"));
        assertEquals(0, levels.get("S02").intValue());
    }

    @Test
    public void testNivelesDesdeS02() {
        Map<String, Integer> levels = bfs.getLevels("S02");
        assertEquals(1, levels.get("S01").intValue());
        assertEquals(1, levels.get("S06").intValue());
        assertEquals(2, levels.get("S07").intValue());
        assertEquals(5, levels.get("S11").intValue());
    }

    @Test
    public void testPadresDesdeS02() {
        Map<String, String> parents = bfs.getParents("S02");
        assertEquals("S02", parents.get("S01"));
        assertEquals("S01", parents.get("S07"));
        assertEquals("S07", parents.get("S08"));
    }

    @Test
    public void testNivelDelHijoEsNivelDelPadreMasUno() {
        Map<String, String> parents = bfs.getParents("S02");
        Map<String, Integer> levels = bfs.getLevels("S02");
        for (String id : parents.keySet()) {
            String p = parents.get(id);
            if (p != null) {
                assertEquals(levels.get(p) + 1, levels.get(id).intValue());
            }
        }
    }

    @Test
    public void testArbolTieneUnVerticePorCadaVisitado() {
        assertEquals(12, bfs.getParents("S02").size());
        assertEquals(bfs.search("S02").size(), bfs.getParents("S02").size());
    }

    @Test
    public void testArbolRespetaOrientacion() {
        // Desde S08 solo se llega a S10 por R10 (dirigida); S07 se alcanza por S09
        Map<String, String> parents = bfs.getParents("S08");
        assertEquals("S08", parents.get("S10"));
        assertEquals("S09", parents.get("S07"));
    }

    @Test
    public void testGrafoDesconectadoSoloAlcanzaSuComponente() {
        MixedGraph g = new MixedGraph();
        Vertex a = new Vertex("A", "A", "T");
        Vertex b = new Vertex("B", "B", "T");
        Vertex c = new Vertex("C", "C", "T");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addEdge(new Edge("E1", "ab", a, b, 1, false));
        Map<String, String> parents = new BreadthFirstSearch(g).getParents("A");
        assertEquals(2, parents.size());
        assertFalse(parents.containsKey("C"));
    }

    @Test
    public void testVerticeInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> bfs.getParents("S99"));
        assertThrows(IllegalArgumentException.class,
                () -> bfs.getLevels("S99"));
    }
}