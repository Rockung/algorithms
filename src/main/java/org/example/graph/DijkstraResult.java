package org.example.graph;

public class DijkstraResult {
    int[] dist;
    int[] p;

    public DijkstraResult(int len) {
        this.dist = new int[len];
        this.p = new int[len];
    }
}
