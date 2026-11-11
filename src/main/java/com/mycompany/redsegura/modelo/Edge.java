/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.modelo;

/**
 *
 * @author acost
 */


public class Edge {

    private String id;
    private String label;
    private Vertex origin;
    private Vertex destination;
    private double weight;
    private boolean directed;

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

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public Vertex getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isDirected() {
        return directed;
    }

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

