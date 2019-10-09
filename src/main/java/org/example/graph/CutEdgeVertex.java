package org.example.graph;

import org.example.list.List;
import org.example.list.LinkedList;

/**
 * cut-edge and cut-vertex for graph
 */
public class CutEdgeVertex {
    List<Edge> cutEdges;       // list of cut-edges
    List<Integer> cutVertexes; // list of cut-vertexes

    public CutEdgeVertex() {
        this.cutEdges = new LinkedList<>();
        this.cutVertexes = new LinkedList<>();
    }
}
