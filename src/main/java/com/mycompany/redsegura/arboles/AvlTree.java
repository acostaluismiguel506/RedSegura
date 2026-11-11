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

public class AvlTree {

    // Nodo interno del arbol AVL
    private class Node {

        Vertex data;
        Node left;
        Node right;
        int height;

        Node(Vertex data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;
    private int lastSearchSteps;

    // Constructor
    public AvlTree() {
        root = null;
        lastSearchSteps = 0;
    }

    // =====================================
    // ALTURA Y FACTOR DE EQUILIBRIO
    // =====================================

    private int nodeHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {

        if (node == null) {
            return 0;
        }

        return nodeHeight(node.left) - nodeHeight(node.right);
    }

    private void updateHeight(Node node) {

        node.height = 1 + Math.max(
                nodeHeight(node.left),
                nodeHeight(node.right));
    }

    // =====================================
    // ROTACION SIMPLE A LA DERECHA (LL)
    // =====================================

    private Node rotateRight(Node y) {

        Node x = y.left;
        Node temporary = x.right;

        x.right = y;
        y.left = temporary;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // =====================================
    // ROTACION SIMPLE A LA IZQUIERDA (RR)
    // =====================================

    private Node rotateLeft(Node x) {

        Node y = x.right;
        Node temporary = y.left;

        y.left = x;
        x.right = temporary;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // =====================================
    // INSERTAR
    // =====================================

    public void insert(Vertex vertex) {

        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser nulo.");
        }

        root = insertRecursive(root, vertex);
    }

    private Node insertRecursive(Node node, Vertex vertex) {

        // Insertar como en un ABB
        if (node == null) {
            return new Node(vertex);
        }

        int comparison = vertex.getLabel().compareToIgnoreCase(
                node.data.getLabel());

        if (comparison < 0) {

            node.left = insertRecursive(node.left, vertex);

        } else if (comparison > 0) {

            node.right = insertRecursive(node.right, vertex);

        } else {

            throw new IllegalArgumentException(
                    "El lugar ya existe: " + vertex.getLabel());
        }

        // Actualizar altura
        updateHeight(node);

        // Calcular factor de equilibrio
        int balance = getBalance(node);

        // CASO LL
        if (balance > 1
                && vertex.getLabel().compareToIgnoreCase(
                        node.left.data.getLabel()) < 0) {

            return rotateRight(node);
        }

        // CASO RR
        if (balance < -1
                && vertex.getLabel().compareToIgnoreCase(
                        node.right.data.getLabel()) > 0) {

            return rotateLeft(node);
        }

        // CASO LR
        if (balance > 1
                && vertex.getLabel().compareToIgnoreCase(
                        node.left.data.getLabel()) > 0) {

            node.left = rotateLeft(node.left);

            return rotateRight(node);
        }

        // CASO RL
        if (balance < -1
                && vertex.getLabel().compareToIgnoreCase(
                        node.right.data.getLabel()) < 0) {

            node.right = rotateRight(node.right);

            return rotateLeft(node);
        }

        return node;
    }

    // =====================================
    // BUSCAR
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

    public int getLastSearchSteps() {
        return lastSearchSteps;
    }

    // =====================================
    // ELIMINAR
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

    private Node deleteRecursive(Node node, String label) {

        if (node == null) {
            return null;
        }

        int comparison = label.compareToIgnoreCase(
                node.data.getLabel());

        if (comparison < 0) {

            node.left = deleteRecursive(node.left, label);

        } else if (comparison > 0) {

            node.right = deleteRecursive(node.right, label);

        } else {

            // Nodo sin hijos
            if (node.left == null && node.right == null) {
                return null;
            }

            // Nodo con un solo hijo
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            // Nodo con dos hijos
            Node successor = findMinimum(node.right);

            node.data = successor.data;

            node.right = deleteRecursive(
                    node.right,
                    successor.data.getLabel());
        }

        // Actualizar altura
        updateHeight(node);

        // Revisar equilibrio
        int balance = getBalance(node);

        // CASO LL
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // CASO LR
        if (balance > 1 && getBalance(node.left) < 0) {

            node.left = rotateLeft(node.left);

            return rotateRight(node);
        }

        // CASO RR
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // CASO RL
        if (balance < -1 && getBalance(node.right) > 0) {

            node.right = rotateRight(node.right);

            return rotateLeft(node);
        }

        return node;
    }

    private Node findMinimum(Node node) {

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // =====================================
    // RECORRIDO INORDEN
    // =====================================

    public List<Vertex> inOrder() {

        List<Vertex> result = new ArrayList<>();

        inOrderRecursive(root, result);

        return result;
    }

    private void inOrderRecursive(
            Node node, List<Vertex> result) {

        if (node != null) {

            inOrderRecursive(node.left, result);

            result.add(node.data);

            inOrderRecursive(node.right, result);
        }
    }

    // =====================================
    // RECORRIDO PREORDEN
    // =====================================

    public List<Vertex> preOrder() {

        List<Vertex> result = new ArrayList<>();

        preOrderRecursive(root, result);

        return result;
    }

    private void preOrderRecursive(
            Node node, List<Vertex> result) {

        if (node != null) {

            result.add(node.data);

            preOrderRecursive(node.left, result);

            preOrderRecursive(node.right, result);
        }
    }

    // =====================================
    // RECORRIDO POSTORDEN
    // =====================================

    public List<Vertex> postOrder() {

        List<Vertex> result = new ArrayList<>();

        postOrderRecursive(root, result);

        return result;
    }

    private void postOrderRecursive(
            Node node, List<Vertex> result) {

        if (node != null) {

            postOrderRecursive(node.left, result);

            postOrderRecursive(node.right, result);

            result.add(node.data);
        }
    }

    // =====================================
    // ALTURA DEL ARBOL
    // =====================================

    public int height() {
        return nodeHeight(root);
    }

    // =====================================
    // CANTIDAD DE NODOS
    // =====================================

    public int size() {
        return sizeRecursive(root);
    }

    private int sizeRecursive(Node node) {

        if (node == null) {
            return 0;
        }

        return 1
                + sizeRecursive(node.left)
                + sizeRecursive(node.right);
    }

    // =====================================
    // FACTOR DE EQUILIBRIO DE LA RAIZ
    // =====================================

    public int getRootBalance() {
        return getBalance(root);
    }

    // =====================================
    // VERIFICAR SI ESTA VACIO
    // =====================================

    public boolean isEmpty() {
        return root == null;
    }
}
