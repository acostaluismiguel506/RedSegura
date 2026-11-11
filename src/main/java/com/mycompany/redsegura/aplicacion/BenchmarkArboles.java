/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsegura.aplicacion;

/**
 *
 * @author acost
 */
import com.mycompany.redsegura.arboles.AvlTree;
import com.mycompany.redsegura.arboles.BinarySearchTree;
import com.mycompany.redsegura.modelo.Vertex;

/**
 * Medicion breve y reproducible: ABB vs AVL con claves insertadas en orden
 * creciente (el peor caso del ABB). Ejecutar con: main de esta clase.
 */
public class BenchmarkArboles {

    public static void main(String[] args) {

        int[] tamanos = {250, 500, 1000, 2000};

        System.out.println("n     | ABB altura | ABB pasos | AVL altura | AVL pasos");
        System.out.println("------+------------+-----------+------------+----------");

        for (int n : tamanos) {
            BinarySearchTree abb = new BinarySearchTree();
            AvlTree avl = new AvlTree();

            // Claves con ceros a la izquierda para que el orden alfabetico
            // coincida con el numerico: 00000, 00001, 00002...
            for (int i = 0; i < n; i++) {
                String clave = String.format("%05d", i);
                Vertex v = new Vertex("V" + i, clave, "Prueba");
                abb.insert(v);
                avl.insert(v);
            }

            // Se busca la ultima clave insertada (peor caso del ABB)
            String buscada = String.format("%05d", n - 1);
            abb.search(buscada);
            avl.search(buscada);

            System.out.printf("%-5d | %-10d | %-9d | %-10d | %-9d%n",
                    n, abb.height(), abb.getLastSearchSteps(),
                    avl.height(), avl.getLastSearchSteps());
        }
    }
}
