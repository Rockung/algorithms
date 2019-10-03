package org.example.graph;

import java.util.Arrays;

/**
 * Use matrix to save a weighted graph
 *  Dijkstra algorithm
 */
public class WeightedMatrixGraph {
    private String[] vertexes;  // vertex array
    private int[][] edgeMatrix; // edge matrix

    /**
     * Construct a graph with a vertex array
     *
     * @param vertexes the vertex array
     */
    public WeightedMatrixGraph(String[] vertexes) {
        int len = vertexes.length;

        this.vertexes = Arrays.copyOf(vertexes, len);
        this.edgeMatrix = new int[len][len];
    }

    /**
     * Add an edge to the graph
     *
     * @param from     start point of the edge
     * @param to       end point of the edge
     * @param weight   weight of the edge, cannot be negative
     */
    public void addEdge(String from, String to, int weight) {
        if (weight < 0)
                throw new IllegalArgumentException(" Weight cannot be negative.");

        if (from.equals(to))
                throw new IllegalArgumentException("Vertexes cannot be the same.");

        // get the indexes of v1 and v2
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (from.equals(vertexes[i])) index1 = i;
            if (to.equals(vertexes[i])) index2 = i;
        }

        if (index1 == -1 || index2 == -1)
                throw new IllegalArgumentException("Vertexes are not exist.");

        this.edgeMatrix[index1][index2] = weight;
    }

    /**
     * Build the shortest paths starting from vertex v
     *
     * @param v the starting point
     * @return
     */
    public DijkstraResult buildShortestPath(String v) {
        int start = indexOf(v);
        if (start == -1) return null;

        DijkstraResult result = new DijkstraResult(this.vertexes.length);

        return result;
    }

    /**
     * Print the graph matrix
     */
    public void printGraph() {
        System.out.print("  ");
        for (int i = 0; i < vertexes.length; i++) {
            System.out.print(vertexes[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < this.edgeMatrix.length; i++) {
            System.out.print(vertexes[i] + " ");
            for (int j = 0; j < this.edgeMatrix[0].length; j++) {
                System.out.print(this.edgeMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Get the index of vertex v
     *
     * @param v the vertex name
     * @return if the vertex does not exist, return -1
     */
    public int indexOf(String v) {
        int index = -1;
        for (int i = 0; i < this.vertexes.length; i++) {
            if (v.equals(this.vertexes[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Get the vertex name of i
     *
     * @param i
     * @return
     */
    public String indexOf(int i) {
        return this.vertexes[i];
    }

    private static WeightedMatrixGraph createGraph01() {
        WeightedMatrixGraph graph = new WeightedMatrixGraph(new String[] {
           "a", "b", "c", "d", "e", "f", "g", "h"
        });

        graph.addEdge("a", "b", 2);
        graph.addEdge("a", "d", 6);
        graph.addEdge("a", "f", 9);
        graph.addEdge("b", "c", 30);
        graph.addEdge("b", "d", 1);
        graph.addEdge("c", "h", 5);
        graph.addEdge("d", "e", 2);
        graph.addEdge("e", "c", 8);
        graph.addEdge("e", "g", 7);
        graph.addEdge("f", "e", 3);
        graph.addEdge("f", "g", 24);
        graph.addEdge("g", "h", 21);

        return graph;
    }

    private static void test01() {
        WeightedMatrixGraph graph = createGraph01();
        graph.printGraph();
    }

    public static void main(String[] args) {
        test01();
    }
}
