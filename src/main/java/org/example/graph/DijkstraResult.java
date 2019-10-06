package org.example.graph;

/**
 * The result of Dijkstra algorithm
 *
 * Dijkstra Algorithm
 *   1. S set and V-S set
 *   1.1 S set:   vertexes which shortest path is derived
 *   1.2 V-S set: vertexes which shortest path isn't derived yet
 *   2. dist[]    shortest length of path of each vertex
 *   3. p[]       precursor of each vertex
 */
public class DijkstraResult {
    int start;  // the index of start vertex
    int[] dist; // shortest length of path of each vertex
    int[] p;    // the indexes of precursor for each vertex

    /**
     *
     * @param start the index of start vertex
     * @param len # of vertexes
     */
    public DijkstraResult(int start, int len) {
        this.start = start;
        this.dist = new int[len];
        this.p = new int[len];

        for (int i = 0; i < len; i++) {
            this.dist[i] = Integer.MAX_VALUE;
            this.p[i] = -1;
        }
        this.dist[start] = 0;
    }

    /**
     * Print the result
     *
     * @param g the graph of the result
     */
    public void printResult(WeightedMatrixGraph g) {
        System.out.println("start: " + g.indexOf(this.start));
        System.out.println("shortest paths:");
        for (int i = 0; i < this.dist.length; i++) {
            if (i != this.start) {
                System.out.printf("\t%s(%d)\n", buildPath(i, g), dist[i]);
            }
        }
    }

    /**
     * build the path of start->i
     *
     * @param i ith vertex
     * @param g the graph
     * @return the path string
     */
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
