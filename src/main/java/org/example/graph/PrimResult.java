package org.example.graph;

/**
 * The result for Prim Algorithm
 *
 * Prim Algorithm
 *   a solution to minimum spanning tree for graph
 */
public class PrimResult {
    int start;     // the index of start vertex
    int[] lowcost; // minimum costs of the selected vertexes
    int[] closest; // the indexes of the other end
}
