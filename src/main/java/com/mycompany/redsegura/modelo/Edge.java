/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.modelo;

/**
 *
 * @author acost
 */
/**
 * Conexion ponderada entre dos vertices. Puede ser dirigida (arco, solo se
 * recorre de origen a destino) o no dirigida (arista, se recorre en ambos
 * sentidos). El peso representa minutos estimados de desplazamiento.
 *
 * <p>Es inmutable: conserva ID, etiqueta, extremos, peso y orientacion.</p>
 */
public class Edge {

    private final String id;
    private final String label;
    private final Vertex origin;
    private final Vertex destination;
    private final double weight;
    private final boolean directed;

    /**
     * Crea una conexion validando sus datos.
     *
     * @param id          identificador unico, por ejemplo "R01"
     * @param label       descripcion de la conexion
     * @param origin      vertice de origen
     * @param destination vertice de destino
     * @param weight      minutos de desplazamiento; debe ser positivo y finito
     * @param directed    true si es un arco dirigido, false si es una arista
     * @throws IllegalArgumentException si el ID o la etiqueta estan vacios,
     *         si algun vertice es nulo, o si el peso es cero, negativo,
     *         NaN o infinito
     */
    public Edge(String id, String label, Vertex origin,
                Vertex destination, double weight, boolean directed) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la conexión no puede estar vacío.");
        }

        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("La etiqueta no puede estar vacía.");
        }

        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Los vértices no pueden ser nulos.");
        }

        if (weight <= 0 || Double.isNaN(weight)
                || Double.isInfinite(weight)) {
            throw new IllegalArgumentException("El peso debe ser positivo y válido.");
        }

        this.id = id;
        this.label = label;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.directed = directed;
    }

    /** @return identificador unico de la conexion */
    public String getId() {
        return id;
    }

    /** @return descripcion de la conexion */
    public String getLabel() {
        return label;
    }

    /** @return vertice de origen */
    public Vertex getOrigin() {
        return origin;
    }

    /** @return vertice de destino */
    public Vertex getDestination() {
        return destination;
    }

    /** @return peso en minutos */
    public double getWeight() {
        return weight;
    }

    /** @return true si la conexion es dirigida */
    public boolean isDirected() {
        return directed;
    }

    /** @return descripcion legible de la conexion */
    @Override
    public String toString() {
        String direction = directed ? "Dirigida" : "No dirigida";

        return "ID: " + id
                + " | " + origin.getId()
                + " -> " + destination.getId()
                + " | Peso: " + weight + " minutos"
                + " | Tipo: " + direction
                + " | Etiqueta: " + label;
    }
}