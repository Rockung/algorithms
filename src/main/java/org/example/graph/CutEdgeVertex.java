package org.example.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * cut-edge and cut-vertex for graph
 */
public class CutEdgeVertex {
    List<Edge> cutEdges;       // list of cut-edges
    List<Integer> cutVertexes; // list of cut-vertexes

    public CutEdgeVertex() {
        this.cutEdges = new ArrayList<>();
        this.cutVertexes = new ArrayList<>();
    }
}
