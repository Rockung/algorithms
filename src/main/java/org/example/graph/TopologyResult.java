package org.example.graph;

public class TopologyResult {
    int p;           // a pointer for next index
    int[] topology;  // indexes for the result

    /**
     *
     * @param len # of the vertexes
     */
    public TopologyResult(int len) {
        this.p = 0;
        this.topology = new int[len];
    }

    /**
     * add an index into the result
     * @param i
     */
    public void add(int i) {
        this.topology[p++] = i;
    }

    /**
     * print the graph
     * @param g the graph the result is for
     */
    public void printResult(WeightedListGraph g) {
        if (this.p != this.topology.length) {
            System.out.println("The graph has a circle.");
            return;
        }

        System.out.println("Topology sorting: ");
        for (int i = 0; i < this.topology.length; i++) {
            System.out.printf("%s ", g.indexOf(this.topology[i]));
        }
        System.out.println();
    }
}
