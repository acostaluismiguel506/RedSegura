/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.grafo;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.modelo.Edge;
import com.mycompany.redsegura.modelo.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Grafo mixto: admite conexiones dirigidas (arcos) y no dirigidas (aristas),
 * todas etiquetadas y ponderadas.
 *
 * <p>Los vertices se guardan en un mapa que conserva el orden de insercion
 * (por eso las matrices usan siempre el mismo orden) y las conexiones en una
 * lista. Los datos internos no se exponen: las colecciones devueltas son de
 * solo lectura.</p>
 */
public class MixedGraph {

    // Almacena los vertices del grafo
    private final Map<String, Vertex> vertices;

    // Almacena las conexiones del grafo
    private final List<Edge> edges;

    /** Crea un grafo vacio. */
    public MixedGraph() {
        vertices = new LinkedHashMap<>();
        edges = new ArrayList<>();
    }

    /**
     * Agrega un vertice.
     *
     * @param vertex vertice a agregar
     * @throws IllegalArgumentException si es nulo o ya existe uno con su ID
     */
    public void addVertex(Vertex vertex) {

        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser nulo.");
        }

        if (vertices.containsKey(vertex.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un vertice con ID: " + vertex.getId());
        }

        vertices.put(vertex.getId(), vertex);
    }

    /**
     * Busca un vertice por su ID.
     *
     * @param id identificador del vertice
     * @return el vertice, o null si no existe
     */
    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    /**
     * Indica si existe un vertice con ese ID.
     *
     * @param id identificador del vertice
     * @return true si existe
     */
    public boolean containsVertex(String id) {
        return vertices.containsKey(id);
    }

    /**
     * Agrega una conexion.
     *
     * @param edge conexion a agregar
     * @throws IllegalArgumentException si es nula, si su ID ya existe o si
     *         su origen o destino no estan en el grafo
     */
    public void addEdge(Edge edge) {

        if (edge == null) {
            throw new IllegalArgumentException(
                    "La conexion no puede ser nula.");
        }

        if (getEdge(edge.getId()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una conexion con ID: " + edge.getId());
        }

        String originId = edge.getOrigin().getId();
        String destinationId = edge.getDestination().getId();

        if (!vertices.containsKey(originId)) {
            throw new IllegalArgumentException(
                    "No existe el vertice origen: " + originId);
        }

        if (!vertices.containsKey(destinationId)) {
            throw new IllegalArgumentException(
                    "No existe el vertice destino: " + destinationId);
        }

        edges.add(edge);
    }

    /**
     * Busca una conexion por su ID.
     *
     * @param id identificador de la conexion
     * @return la conexion, o null si no existe
     */
    public Edge getEdge(String id) {

        for (Edge edge : edges) {
            if (edge.getId().equals(id)) {
                return edge;
            }
        }

        return null;
    }

    /**
     * Devuelve todos los vertices en orden de insercion.
     *
     * @return coleccion de solo lectura
     */
    public Collection<Vertex> getVertices() {
        return Collections.unmodifiableCollection(
                vertices.values());
    }

    /**
     * Devuelve todas las conexiones en orden de insercion.
     *
     * @return lista de solo lectura
     */
    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    /**
     * Devuelve las conexiones que se pueden recorrer saliendo de un vertice:
     * los arcos que parten de el y las aristas que lo tocan por cualquier
     * extremo.
     *
     * @param vertexId ID del vertice
     * @return lista de conexiones transitables desde ese vertice
     * @throws IllegalArgumentException si el vertice no existe
     */
    public List<Edge> getOutgoingEdges(String vertexId) {

        if (!containsVertex(vertexId)) {
            throw new IllegalArgumentException(
                    "Vertice inexistente: " + vertexId);
        }

        List<Edge> result = new ArrayList<>();

        for (Edge edge : edges) {

            // Conexion que sale del vertice
            if (edge.getOrigin().getId().equals(vertexId)) {
                result.add(edge);
            }

            // Conexion no dirigida que llega al vertice
            else if (!edge.isDirected()
                    && edge.getDestination().getId().equals(vertexId)) {
                result.add(edge);
            }
        }

        return result;
    }

    /**
     * Devuelve los vertices a los que se puede llegar directamente desde un
     * vertice, respetando la orientacion de los arcos.
     *
     * @param vertexId ID del vertice
     * @return lista de vecinos
     * @throws IllegalArgumentException si el vertice no existe
     */
    public List<Vertex> getNeighbors(String vertexId) {

        List<Vertex> neighbors = new ArrayList<>();

        for (Edge edge : getOutgoingEdges(vertexId)) {

            Vertex neighbor;

            if (edge.getOrigin().getId().equals(vertexId)) {
                neighbor = edge.getDestination();
            } else {
                neighbor = edge.getOrigin();
            }

            neighbors.add(neighbor);
        }

        return neighbors;
    }

    /**
     * Elimina una conexion por su ID (por ejemplo, para bloquear una via).
     *
     * @param edgeId ID de la conexion
     * @return true si se elimino, false si no existia
     */
    public boolean removeEdge(String edgeId) {

        return edges.removeIf(
                edge -> edge.getId().equals(edgeId));
    }

    /** @return cantidad de vertices */
    public int getVertexCount() {
        return vertices.size();
    }

    /** @return cantidad de conexiones */
    public int getEdgeCount() {
        return edges.size();
    }

    /** Imprime cada vertice con las conexiones que salen de el. */
    public void displayGraph() {

        System.out.println("\n===== GRAFO REDSEGURA =====");

        for (Vertex vertex : vertices.values()) {

            System.out.println("\nLugar: " + vertex);

            for (Edge edge : getOutgoingEdges(vertex.getId())) {
                System.out.println("   " + edge);
            }
        }

        System.out.println("\nTotal de vertices: "
                + getVertexCount());

        System.out.println("Total de conexiones: "
                + getEdgeCount());
    }
}