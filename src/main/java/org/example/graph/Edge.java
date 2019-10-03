package org.example.graph;

/**
 * edge for graph
 */
public class Edge {
    int v1; // index in the vertex array of graph
    int v2; // index in the vertex array of graph

    /**
     * construct an edge from two vertex indexes
     *
     * @param v1
     * @param v2
     */
    public Edge(int v1, int v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}
