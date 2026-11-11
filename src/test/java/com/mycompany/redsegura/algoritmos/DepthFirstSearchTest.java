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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepthFirstSearchTest {

    private Vertex v(String id) {
        return new Vertex(id, id, "T");
    }

    // ---------- Componentes debiles ----------

    @Test
    public void testGrafoOriginalUnaComponente() {
        MixedGraph g = RedSeguraFactory.createGraph();
        List<List<Vertex>> comps = new DepthFirstSearch(g).weakComponents();
        assertEquals(1, comps.size());
        assertEquals(12, comps.get(0).size());
    }

    @Test
    public void testDosIslasDosComponentes() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B"), c = v("C"), d = v("D");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addEdge(new Edge("E1", "ab", a, b, 1, false));
        g.addEdge(new Edge("E2", "cd", c, d, 1, false));
        assertEquals(2, new DepthFirstSearch(g).weakComponents().size());
    }

    @Test
    public void testComponenteDebilIgnoraOrientacion() {
        // A -> B dirigida: B no llega a A, pero son debilmente conexos
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B");
        g.addVertex(a);
        g.addVertex(b);
        g.addEdge(new Edge("E1", "ab", a, b, 1, true));
        assertEquals(1, new DepthFirstSearch(g).weakComponents().size());
    }

    @Test
    public void testVerticeAisladoEsUnaComponente() {
        MixedGraph g = new MixedGraph();
        g.addVertex(v("A"));
        g.addVertex(v("B"));
        assertEquals(2, new DepthFirstSearch(g).weakComponents().size());
    }

    // ---------- Ciclos ----------

    @Test
    public void testGrafoOriginalTieneCiclo() {
        MixedGraph g = RedSeguraFactory.createGraph();
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        assertTrue(dfs.hasCycle());
        List<Vertex> cycle = dfs.findCycle();
        // El ciclo empieza y termina en el mismo vertice
        assertEquals(cycle.get(0).getId(), cycle.get(cycle.size() - 1).getId());
    }

    @Test
    public void testConexionNoDirigidaSolaNoEsCiclo() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B");
        g.addVertex(a);
        g.addVertex(b);
        g.addEdge(new Edge("E1", "ab", a, b, 1, false));
        assertFalse(new DepthFirstSearch(g).hasCycle());
    }

    @Test
    public void testArbolNoDirigidoSinCiclos() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B"), c = v("C");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addEdge(new Edge("E1", "ab", a, b, 1, false));
        g.addEdge(new Edge("E2", "bc", b, c, 1, false));
        assertFalse(new DepthFirstSearch(g).hasCycle());
    }

    @Test
    public void testCadenaDirigidaSinCiclos() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B"), c = v("C");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addEdge(new Edge("E1", "ab", a, b, 1, true));
        g.addEdge(new Edge("E2", "bc", b, c, 1, true));
        assertFalse(new DepthFirstSearch(g).hasCycle());
    }

    @Test
    public void testCicloDirigido() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B"), c = v("C");
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addEdge(new Edge("E1", "ab", a, b, 1, true));
        g.addEdge(new Edge("E2", "bc", b, c, 1, true));
        g.addEdge(new Edge("E3", "ca", c, a, 1, true));
        assertTrue(new DepthFirstSearch(g).hasCycle());
    }

    @Test
    public void testDosConexionesDistintasEntreLosMismosVertices() {
        MixedGraph g = new MixedGraph();
        Vertex a = v("A"), b = v("B");
        g.addVertex(a);
        g.addVertex(b);
        g.addEdge(new Edge("E1", "uno", a, b, 1, false));
        g.addEdge(new Edge("E2", "dos", a, b, 2, false));
        assertTrue(new DepthFirstSearch(g).hasCycle());
    }

    @Test
    public void testSinConexionesNoHayCiclo() {
        MixedGraph g = new MixedGraph();
        g.addVertex(v("A"));
        assertFalse(new DepthFirstSearch(g).hasCycle());
    }
}