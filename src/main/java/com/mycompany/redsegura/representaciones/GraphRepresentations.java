/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.representaciones;

/**
 *
 * @author acost
 */


import com.mycompany.redsegura.grafo.MixedGraph;
import com.mycompany.redsegura.modelo.Vertex;
import com.mycompany.redsegura.modelo.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphRepresentations {

    private final MixedGraph graph;

    // Constructor
    public GraphRepresentations(MixedGraph graph) {

        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo.");
        }

        this.graph = graph;
    }

    // Obtener los vertices en el mismo orden del grafo
    private List<Vertex> getOrderedVertices() {
        return new ArrayList<>(graph.getVertices());
    }

    // Obtener las posiciones de cada vertice
    private Map<String, Integer> getVertexIndexes(
            List<Vertex> vertices) {

        Map<String, Integer> indexes = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            indexes.put(vertices.get(i).getId(), i);
        }

        return indexes;
    }

    // =========================================
    // 1. LISTA DE ADYACENCIA
    // =========================================

    public void printAdjacencyList() {

        System.out.println("\n================================");
        System.out.println("       LISTA DE ADYACENCIA");
        System.out.println("================================");

        for (Vertex vertex : graph.getVertices()) {

            System.out.print(vertex.getId() + " -> ");

            List<Edge> connections =
                    graph.getOutgoingEdges(vertex.getId());

            if (connections.isEmpty()) {
                System.out.println("Sin conexiones");
                continue;
            }

            for (Edge edge : connections) {

                Vertex neighbor;

                if (edge.getOrigin().getId()
                        .equals(vertex.getId())) {

                    neighbor = edge.getDestination();

                } else {

                    neighbor = edge.getOrigin();
                }

                System.out.print(
                        neighbor.getId()
                        + " (" + edge.getWeight() + " min"
                        + ", " + edge.getId()
                        + ", "
                        + (edge.isDirected()
                            ? "Dirigida" : "No dirigida")
                        + ")  ");
            }

            System.out.println();
        }
    }

    // =========================================
    // 2. MATRIZ DE ADYACENCIA PONDERADA
    // =========================================

    public void printAdjacencyMatrix() {

        List<Vertex> vertices = getOrderedVertices();

        Map<String, Integer> indexes =
                getVertexIndexes(vertices);

        int n = vertices.size();

        double[][] matrix = new double[n][n];

        // Inicializar la matriz en cero
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }

        // Registrar las conexiones
        for (Edge edge : graph.getEdges()) {

            int origin = indexes.get(
                    edge.getOrigin().getId());

            int destination = indexes.get(
                    edge.getDestination().getId());

            matrix[origin][destination] = edge.getWeight();

            // Si no es dirigida, registrar ambos sentidos
            if (!edge.isDirected()) {
                matrix[destination][origin] =
                        edge.getWeight();
            }
        }

        System.out.println("\n================================");
        System.out.println(" MATRIZ DE ADYACENCIA PONDERADA");
        System.out.println("================================");

        System.out.printf("%8s", "");

        for (Vertex vertex : vertices) {
            System.out.printf("%8s", vertex.getId());
        }

        System.out.println();

        for (int i = 0; i < n; i++) {

            System.out.printf("%8s", vertices.get(i).getId());

            for (int j = 0; j < n; j++) {
                System.out.printf("%8s",
                        matrix[i][j] == 0
                        ? "-" : formatWeight(matrix[i][j]));
            }

            System.out.println();
        }

        System.out.println("\n0 o - representa ausencia de conexion.");
    }

    // =========================================
    // 3. MATRIZ DE INCIDENCIA
    // =========================================

    public void printIncidenceMatrix() {

        List<Vertex> vertices = getOrderedVertices();

        List<Edge> edges = graph.getEdges();

        Map<String, Integer> indexes =
                getVertexIndexes(vertices);

        int rows = vertices.size();
        int columns = edges.size();

        int[][] matrix = new int[rows][columns];

        // Recorrer todas las conexiones
        for (int j = 0; j < columns; j++) {

            Edge edge = edges.get(j);

            int origin = indexes.get(
                    edge.getOrigin().getId());

            int destination = indexes.get(
                    edge.getDestination().getId());

            if (edge.isDirected()) {

                // Arco dirigido:
                // -1 salida y +1 llegada
                matrix[origin][j] = -1;
                matrix[destination][j] = 1;

            } else {

                // Arista no dirigida:
                // 1 en ambos vertices
                matrix[origin][j] = 1;
                matrix[destination][j] = 1;
            }
        }

        System.out.println("\n================================");
        System.out.println("       MATRIZ DE INCIDENCIA");
        System.out.println("================================");

        System.out.printf("%8s", "");

        for (Edge edge : edges) {
            System.out.printf("%5s", edge.getId());
        }

        System.out.println();

        for (int i = 0; i < rows; i++) {

            System.out.printf("%8s", vertices.get(i).getId());

            for (int j = 0; j < columns; j++) {

                System.out.printf("%5d", matrix[i][j]);
            }

            System.out.println();
        }

        System.out.println("\nLeyenda:");
        System.out.println("-1 = origen de una conexion dirigida");
        System.out.println(" 1 = destino dirigido o extremo no dirigido");
        System.out.println(" 0 = sin relacion con esa conexion");
    }

    // =========================================
    // MOSTRAR LAS TRES REPRESENTACIONES
    // =========================================

    public void displayAll() {

        printAdjacencyList();

        printAdjacencyMatrix();

        printIncidenceMatrix();
    }

    // Formatear los pesos sin decimales innecesarios
    private String formatWeight(double weight) {

        if (weight == Math.floor(weight)) {
            return String.valueOf((int) weight);
        }

        return String.valueOf(weight);
    }
}
