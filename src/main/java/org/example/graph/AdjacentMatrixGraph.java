package org.example.graph;

import java.util.Arrays;

/**
 * Use adjacent matrix to represent a graph
 *
 */
public class AdjacentMatrixGraph {
    private String[] vertexes;
    private int[][] edgeMatrix;

    public static void main(String[] args) {
        test01();
    }

    /**
     * Construct with an array of vertex names
     *
     * @param vertexes the array of vertex names
     */
    public AdjacentMatrixGraph(String[] vertexes) {
        int len = vertexes.length;
        this.vertexes = Arrays.copyOf(vertexes, len);
        this.edgeMatrix = new int[len][len];
    }

    /**
     * Add an edge to the graph
     *
     * @param v1
     * @param v2
     * @throws IllegalArgumentException if the vertex's names are same or not exist
     */
    public void addEdge(String v1, String v2) throws IllegalArgumentException {
        if (v1.equals(v2))
            throw new IllegalArgumentException("Vertexes are same");

        // get the indexes of v1 and v2
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (v1.equals(vertexes[i])) index1 = i;
            if (v2.equals(vertexes[i])) index2 = i;
        }

        if (index1 != -1 && index2 != -1) {
            // fill the cell with 1 systematically
            this.edgeMatrix[index1][index2] = 1;
            this.edgeMatrix[index2][index1] = 1;
        } else {
            throw new IllegalArgumentException("Vertexes are not exist.");
        }
    }

    /**
     * Get the degree of a vertex
     *
     * @param v
     * @return if the vertex does not exist, return -1
     */
    public int degreeOf(String v) {
        // get the index of v
        int index = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (v.equals(vertexes[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) return -1;

        // get the degree of v
        int degree = 0;
        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[index][i] == 1) degree++;
        }

        return degree;
    }

    public void depthFirstSearch() {


    }

    public void breathFirstSearch() {

    }

    public void printGraphMatrix() {
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
     * Create a graph for test
     *
     * @return
     */
    private static AdjacentMatrixGraph createGraph01() {
        AdjacentMatrixGraph graph = new AdjacentMatrixGraph(new String[] {
                "a", "b", "c", "d", "e"
        });

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "c");
        graph.addEdge("e", "c");
        graph.addEdge("e", "d");

        return graph;
    }

    private static void test01() {
        AdjacentMatrixGraph graph = createGraph01();
        graph.printGraphMatrix();
        System.out.println("a's degree: " + graph.degreeOf("a"));
        System.out.println("b's degree: " + graph.degreeOf("b"));
        System.out.println("c's degree: " + graph.degreeOf("c"));
        System.out.println("d's degree: " + graph.degreeOf("d"));
        System.out.println("e's degree: " + graph.degreeOf("e"));
        System.out.println("f's degree: " + graph.degreeOf("f"));
    }
}
