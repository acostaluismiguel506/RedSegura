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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraSolver {

    private final MixedGraph graph;

    public DijkstraSolver(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    // Calcula la ruta de menor tiempo entre dos vertices
    public List<Vertex> findShortestPath(
            String startId, String destinationId) {

        Vertex start = graph.getVertex(startId);
        Vertex destination = graph.getVertex(destinationId);

        if (start == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice inicial: " + startId);
        }

        if (destination == null) {
            throw new IllegalArgumentException(
                    "No existe el vertice destino: " + destinationId);
        }

        // Almacenar las distancias minimas
        Map<String, Double> distances = new HashMap<>();

        // Almacenar el vertice anterior de cada nodo
        Map<String, Vertex> previous = new HashMap<>();

        // Controlar los vertices visitados
        Set<String> visited = new HashSet<>();

        // Cola de prioridad: menor distancia primero
        PriorityQueue<NodeDistance> queue =
                new PriorityQueue<>(
                        Comparator.comparingDouble(
                                node -> node.distance));

        // Inicializar las distancias
        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex.getId(), Double.POSITIVE_INFINITY);
        }

        distances.put(start.getId(), 0.0);

        queue.add(new NodeDistance(start, 0.0));

        while (!queue.isEmpty()) {

            NodeDistance currentNode = queue.poll();

            Vertex current = currentNode.vertex;

            // Ignorar vertices ya procesados
            if (visited.contains(current.getId())) {
                continue;
            }

            visited.add(current.getId());

            // Si llegamos al destino, terminamos
            if (current.getId().equals(destination.getId())) {
                break;
            }

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

                double weight = edge.getWeight();

                // Dijkstra requiere pesos no negativos
                if (weight < 0) {
                    throw new IllegalArgumentException(
                            "El grafo contiene pesos negativos.");
                }

                if (visited.contains(neighbor.getId())) {
                    continue;
                }

                double newDistance =
                        distances.get(current.getId()) + weight;

                // Actualizar si encontramos una ruta mejor
                if (newDistance <
                        distances.get(neighbor.getId())) {

                    distances.put(
                            neighbor.getId(), newDistance);

                    previous.put(
                            neighbor.getId(), current);

                    queue.add(
                            new NodeDistance(neighbor, newDistance));
                }
            }
        }

        // Reconstruir la ruta
        if (distances.get(destination.getId())
                == Double.POSITIVE_INFINITY) {

            return Collections.emptyList();
        }

        List<Vertex> path = new ArrayList<>();

        Vertex current = destination;

        while (current != null) {

            path.add(current);

            current = previous.get(current.getId());
        }

        Collections.reverse(path);

        return path;
    }

    // Calcular el tiempo total de una ruta
    public double getPathCost(List<Vertex> path) {

        if (path == null || path.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        }

        if (path.size() == 1) {
            return 0.0;
        }

        double total = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {

            Vertex origin = path.get(i);
            Vertex destination = path.get(i + 1);

            boolean found = false;

            for (Edge edge :
                    graph.getOutgoingEdges(origin.getId())) {

                boolean connects =
                        (edge.getOrigin().getId().equals(origin.getId())
                        && edge.getDestination().getId()
                                .equals(destination.getId()))
                        || (!edge.isDirected()
                        && edge.getOrigin().getId()
                                .equals(destination.getId())
                        && edge.getDestination().getId()
                                .equals(origin.getId()));

                if (connects) {
                    total += edge.getWeight();
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException(
                        "No existe una conexion valida entre "
                        + origin.getId() + " y "
                        + destination.getId());
            }
        }

        return total;
    }

    // Mostrar la ruta encontrada
    public void displayShortestPath(
            String startId, String destinationId) {

        List<Vertex> path =
                findShortestPath(startId, destinationId);

        System.out.println("\n===== RUTA MAS CORTA - DIJKSTRA =====");

        if (path.isEmpty()) {

            System.out.println(
                    "No existe una ruta entre "
                    + startId + " y " + destinationId);

            return;
        }

        System.out.println("Origen: " + startId);
        System.out.println("Destino: " + destinationId);

        System.out.println("\nRecorrido:");

        for (int i = 0; i < path.size(); i++) {

            Vertex vertex = path.get(i);

            System.out.print(vertex.getId());

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }

        System.out.println();

        System.out.println(
                "Total de lugares visitados en la ruta: "
                + path.size());

        System.out.println(
                "Tiempo minimo: "
                + getPathCost(path) + " minutos");
    }

    // Clase auxiliar para la cola de prioridad
    private static class NodeDistance {

        Vertex vertex;
        double distance;

        NodeDistance(Vertex vertex, double distance) {

            this.vertex = vertex;
            this.distance = distance;
        }
    }
}