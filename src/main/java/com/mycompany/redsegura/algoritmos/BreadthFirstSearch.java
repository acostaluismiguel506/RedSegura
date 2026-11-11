/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.algoritmos;

/**
 *
 * @author acost
 */


import com.mycompany.redsegura.grafo.MixedGraph;
import com.mycompany.redsegura.modelo.Vertex;
import com.mycompany.redsegura.modelo.Edge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch {

    private final MixedGraph graph;

    public BreadthFirstSearch(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    // Recorrido BFS desde un vertice inicial
    public List<Vertex> search(String startId) {

        Vertex start = graph.getVertex(startId);

        if (start == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice: " + startId);
        }

        List<Vertex> result = new ArrayList<>();

        // Cola para administrar los vertices pendientes
        Queue<Vertex> queue = new ArrayDeque<>();

        // Evita visitar un vertice varias veces
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start.getId());

        while (!queue.isEmpty()) {

            Vertex current = queue.poll();

            result.add(current);

            // Revisar las conexiones disponibles
            for (Edge edge :
                    graph.getOutgoingEdges(current.getId())) {

                Vertex neighbor;

                if (edge.getOrigin().getId()
                        .equals(current.getId())) {

                    neighbor = edge.getDestination();

                } else {

                    neighbor = edge.getOrigin();
                }

                if (!visited.contains(neighbor.getId())) {

                    visited.add(neighbor.getId());

                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    // Mostrar recorrido BFS
    public void display(String startId) {

        List<Vertex> result = search(startId);

        System.out.println("\n===== RECORRIDO BFS =====");

        for (Vertex vertex : result) {
            System.out.println(
                    vertex.getId() + " - " + vertex.getLabel());
        }

        System.out.println("Total visitados: " + result.size());
    }
}
