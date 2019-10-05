package org.example.graph;

/**
 * edge for graph
 */
public class Edge {
    int v1;     // index in the vertex array of graph
    int v2;     // index in the vertex array of graph
    int weight; // weight for the edge

    /**
     * construct an edge from two vertex indexes
     *
     * @param v1 index for one end of edge
     * @param v2 index for one end of edge
     */
    public Edge(int v1, int v2) {
        this(v1, v2, 0);
    }

    /**
     * construct a weighted edge
     * @param v1 index for one end of edge
     * @param v2 index for one end of edge
     * @param weight edge's weight
     */
    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}
