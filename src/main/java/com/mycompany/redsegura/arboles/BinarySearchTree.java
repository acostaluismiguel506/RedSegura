/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.arboles;

/**
 *
 * @author acost
 */

import com.mycompany.redsegura.modelo.Vertex;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    // Nodo interno del arbol
    private class Node {

        Vertex data;
        Node left;
        Node right;

        Node(Vertex data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Guarda los pasos de la ultima busqueda
    private int lastSearchSteps;

    // Constructor
    public BinarySearchTree() {
        root = null;
        lastSearchSteps = 0;
    }

    // =====================================
    // 1. INSERTAR
    // =====================================

    public void insert(Vertex vertex) {

        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser nulo.");
        }

        root = insertRecursive(root, vertex);
    }

    private Node insertRecursive(Node current, Vertex vertex) {

        if (current == null) {
            return new Node(vertex);
        }

        int comparison = vertex.getLabel().compareToIgnoreCase(
                current.data.getLabel());

        if (comparison < 0) {

            current.left = insertRecursive(current.left, vertex);

        } else if (comparison > 0) {

            current.right = insertRecursive(current.right, vertex);

        } else {

            throw new IllegalArgumentException(
                    "Ya existe un lugar con ese nombre: "
                    + vertex.getLabel());
        }

        return current;
    }

    // =====================================
    // 2. BUSCAR
    // =====================================

    public Vertex search(String label) {

        lastSearchSteps = 0;

        if (label == null || label.trim().isEmpty()) {
            return null;
        }

        Node current = root;

        while (current != null) {

            lastSearchSteps++;

            int comparison = label.compareToIgnoreCase(
                    current.data.getLabel());

            if (comparison == 0) {
                return current.data;
            }

            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // Consultar cantidad de pasos de la ultima busqueda
    public int getLastSearchSteps() {
        return lastSearchSteps;
    }

    // =====================================
    // 3. ELIMINAR
    // =====================================

    public boolean delete(String label) {

        if (label == null || label.trim().isEmpty()) {
            return false;
        }

        if (search(label) == null) {
            return false;
        }

        root = deleteRecursive(root, label);

        return true;
    }

    private Node deleteRecursive(Node current, String label) {

        if (current == null) {
            return null;
        }

        int comparison = label.compareToIgnoreCase(
                current.data.getLabel());

        if (comparison < 0) {

            current.left = deleteRecursive(current.left, label);

        } else if (comparison > 0) {

            current.right = deleteRecursive(current.right, label);

        } else {

            // Caso 1: sin hijos
            if (current.left == null && current.right == null) {
                return null;
            }

            // Caso 2: solamente hijo derecho
            if (current.left == null) {
                return current.right;
            }

            // Caso 2: solamente hijo izquierdo
            if (current.right == null) {
                return current.left;
            }

            // Caso 3: tiene dos hijos
            Node successor = findMinimum(current.right);

            current.data = successor.data;

            current.right = deleteRecursive(
                    current.right,
                    successor.data.getLabel());
        }

        return current;
    }

    // Encontrar el menor elemento
    private Node findMinimum(Node current) {

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    // =====================================
    // 4. RECORRIDO INORDEN
    // =====================================

    public List<Vertex> inOrder() {

        List<Vertex> result = new ArrayList<>();

        inOrderRecursive(root, result);

        return result;
    }

    private void inOrderRecursive(
            Node current, List<Vertex> result) {

        if (current != null) {

            inOrderRecursive(current.left, result);

            result.add(current.data);

            inOrderRecursive(current.right, result);
        }
    }

    // =====================================
    // 5. RECORRIDO PREORDEN
    // =====================================

    public List<Vertex> preOrder() {

        List<Vertex> result = new ArrayList<>();

        preOrderRecursive(root, result);

        return result;
    }

    private void preOrderRecursive(
            Node current, List<Vertex> result) {

        if (current != null) {

            result.add(current.data);

            preOrderRecursive(current.left, result);

            preOrderRecursive(current.right, result);
        }
    }

    // =====================================
    // 6. RECORRIDO POSTORDEN
    // =====================================

    public List<Vertex> postOrder() {

        List<Vertex> result = new ArrayList<>();

        postOrderRecursive(root, result);

        return result;
    }

    private void postOrderRecursive(
            Node current, List<Vertex> result) {

        if (current != null) {

            postOrderRecursive(current.left, result);

            postOrderRecursive(current.right, result);

            result.add(current.data);
        }
    }

    // =====================================
    // 7. ALTURA DEL ARBOL
    // =====================================

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node current) {

        if (current == null) {
            return 0;
        }

        int leftHeight = heightRecursive(current.left);

        int rightHeight = heightRecursive(current.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    // =====================================
    // 8. CANTIDAD DE NODOS
    // =====================================

    public int size() {
        return sizeRecursive(root);
    }

    private int sizeRecursive(Node current) {

        if (current == null) {
            return 0;
        }

        return 1
                + sizeRecursive(current.left)
                + sizeRecursive(current.right);
    }

    // Verificar si esta vacio
    public boolean isEmpty() {
        return root == null;
    }
}
