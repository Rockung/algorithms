package org.example.graph;

public class TopologyResult {
    int p;       // a pointer for next index
    int[] order; // indexes for the result

    /**
     *
     * @param len # of the vertexes
     */
    public TopologyResult(int len) {
        this.p = 0;
        this.order = new int[len];
    }

    /**
     * add an index into the result
     * @param i the index of vertex
     */
    public void add(int i) {
        if (p >= this.order.length)
            throw new IndexOutOfBoundsException("Out of bounds when adding");

        this.order[p++] = i;
    }

    /**
     *
     * @return whether getting the right result
     */
    public boolean isValid() {
        return this.p == this.order.length;
    }

    /**
     * print the graph
     * @param g the graph the result is for
     */
    public void printResult(WeightedListGraph g) {
        if (this.p != this.order.length) {
            System.out.println("The graph has a circle.");
            return;
        }

        System.out.println("Topology sorting: ");
        for (int i = 0; i < this.order.length; i++) {
            System.out.printf("%s ", g.indexOf(this.order[i]));
        }
        System.out.println();
    }
}
