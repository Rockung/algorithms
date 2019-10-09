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

        // init to MAX_VALUE
        // should check overflow when building shortest paths
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i != j)
                    this.edgeMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Add an edge to the graph
     *
     * @param from     start vertex of the edge
     * @param to       end vertex of the edge
     * @param weight   weight of the edge, cannot be negative
     */
    public void addEdge(String from, String to, int weight) {
        if (weight < 0)
                throw new IllegalArgumentException(" Weight cannot be negative.");

        if (from.equals(to))
                throw new IllegalArgumentException("Vertexes cannot be the same.");

        // get the indexes of from and to
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
     *   1. S set and V-S set
     *   1.1 S set:   vertexes which shortest path is derived
     *   1.2 V-S set: vertexes which shortest path isn't derived yet
     *   2. dist[]    shortest length of path of each vertex
     *   3. p[]       precursor of each vertex
     *
     * @param v the starting point
     * @return
     */
    public DijkstraResult buildShortestPath(String v) {
        int current = indexOf(v);
        if (current == -1) return null;

        // init S, V-S
        // all vertexes are in the V, so at the beginning, S is empty
        boolean[] S = new boolean[this.vertexes.length];

        // init dist[] and p[]
        // here we don't need to init dist[] and p[] because of the default
        // initialization in the constructor
        DijkstraResult result = new DijkstraResult(current, this.vertexes.length);

        // loop until no index is found
        while(current != -1) {
            // remove from V-S, add to S set
            S[current] = true;

            // 1. update dist[] and p[] using current adjacent edges
            // 2. find the index of least dist
            int index = -1;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < S.length; i++ ) {
                // exclude S set
                if (S[i]) continue;

                // 1. update dist[] and p[] once a time
                if (this.edgeMatrix[current][i] < Integer.MAX_VALUE)  { // avoid overflow
                    if (result.dist[current] < Integer.MAX_VALUE) {     // avoid overflow
                        int dist = result.dist[current] + this.edgeMatrix[current][i];
                        // if shorter, update
                        if ( dist < result.dist[i]) {
                            result.dist[i] = dist;
                            result.p[i] = current;
                        }
                    }
                }

                // 2. find the index of least dist
                if (result.dist[i] <= min) {
                    min = result.dist[i];
                    index = i;
                }
            }

            // go to next vertex
            current = index;
        }

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
        WeightedMatrixGraph g = new WeightedMatrixGraph(new String[] {
                "1", "2", "3", "4", "5"
        });

        g.addEdge("1", "5", 4);
        g.addEdge("1", "4", 2);
        g.addEdge("2", "1", 6);
        g.addEdge("2", "4", 2);
        g.addEdge("3", "2", 2);
        g.addEdge("3", "4", 5);
        g.addEdge("4", "1", 7);
        g.addEdge("4", "5", 1);

        return g;
    }

    private static WeightedMatrixGraph createGraph02() {
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
        DijkstraResult result = graph.buildShortestPath("3");
        result.printResult(graph);
    }

    private static void test02() {
        WeightedMatrixGraph graph = createGraph02();
        DijkstraResult result = graph.buildShortestPath("a");
        result.printResult(graph);
    }

    public static void main(String[] args) {
        test01();
        // test02();
    }
}
