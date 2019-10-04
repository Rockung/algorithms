package org.example.graph;

public class DijkstraResult {
    int start;
    int[] dist;
    int[] p;

    public DijkstraResult(int start, int len) {
        this.start = start;
        this.dist = new int[len];
        this.p = new int[len];
    }

    public void printResult(WeightedMatrixGraph g) {
        System.out.println("start: " + g.indexOf(this.start));
        System.out.println("shortest paths:");
        for (int i = 0; i < this.dist.length; i++) {
            if (i != this.start)
                System.out.printf("\t%s(%d)\n", buildPath(i, g), dist[i]);
        }
    }

    private String buildPath(int i, WeightedMatrixGraph g) {
        StringBuilder builder = new StringBuilder();
        builder.insert(0, "->"+ g.indexOf(i));

        int j = this.p[i];
        while( j != -1) {
            builder.insert(0, "->" + g.indexOf(j));
            j = this.p[j];
        }

        return builder.toString();
    }
}
