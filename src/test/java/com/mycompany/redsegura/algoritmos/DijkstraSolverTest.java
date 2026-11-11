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
import com.mycompany.redsegura.modelo.Vertex;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraSolverTest {

    private MixedGraph graph;
    private DijkstraSolver dijkstra;

    @BeforeEach
    public void setUp() {

        graph = RedSeguraFactory.createGraph();

        dijkstra = new DijkstraSolver(graph);
    }

    // PRUEBA 1: BFS debe recorrer los 12 vertices
    @Test
    public void testBFS() {

        BreadthFirstSearch bfs =
                new BreadthFirstSearch(graph);

        List<Vertex> result = bfs.search("S01");

        assertEquals(12, result.size());
    }

    // PRUEBA 2: DFS debe recorrer los 12 vertices
    @Test
    public void testDFS() {

        DepthFirstSearch dfs =
                new DepthFirstSearch(graph);

        List<Vertex> result = dfs.search("S01");

        assertEquals(12, result.size());
    }

    // PRUEBA 3: Dijkstra debe encontrar 16 minutos
    @Test
    public void testShortestPath() {

        List<Vertex> path =
                dijkstra.findShortestPath("S01", "S08");

        double cost = dijkstra.getPathCost(path);

        assertEquals(16.0, cost, 0.001);
    }

    // PRUEBA 4: Origen y destino iguales
    @Test
    public void testSameOriginAndDestination() {

        List<Vertex> path =
                dijkstra.findShortestPath("S01", "S01");

        assertEquals(1, path.size());

        assertEquals(
                0.0,
                dijkstra.getPathCost(path),
                0.001);
    }

    // PRUEBA 5: Vertice inexistente
    @Test
    public void testInvalidVertex() {

        assertThrows(
                IllegalArgumentException.class,
                () -> dijkstra.findShortestPath("S99", "S08")
        );
    }
}
