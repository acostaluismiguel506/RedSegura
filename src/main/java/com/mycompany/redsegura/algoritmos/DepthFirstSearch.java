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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepthFirstSearch {

    private final MixedGraph graph;

    public DepthFirstSearch(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    // Recorrido DFS desde un vertice inicial
    public List<Vertex> search(String startId) {

        Vertex start = graph.getVertex(startId);

        if (start == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice: " + startId);
        }

        List<Vertex> result = new ArrayList<>();

        Set<String> visited = new HashSet<>();

        dfsRecursive(start, visited, result);

        return result;
    }

    // Metodo recursivo DFS
    private void dfsRecursive(
            Vertex current,
            Set<String> visited,
            List<Vertex> result) {

        // Marcar como visitado
        visited.add(current.getId());

        // Guardar el vertice visitado
        result.add(current);

        // Explorar sus conexiones
        for (Edge edge :
                graph.getOutgoingEdges(current.getId())) {

            Vertex neighbor;

            if (edge.getOrigin().getId()
                    .equals(current.getId())) {

                neighbor = edge.getDestination();

            } else {

                neighbor = edge.getOrigin();
            }

            // Continuar si no ha sido visitado
            if (!visited.contains(neighbor.getId())) {

                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    // Mostrar recorrido DFS
    public void display(String startId) {

        List<Vertex> result = search(startId);

        System.out.println("\n===== RECORRIDO DFS =====");

        for (Vertex vertex : result) {

            System.out.println(
                    vertex.getId() + " - " + vertex.getLabel());
        }

        System.out.println("Total visitados: " + result.size());
    }
}
