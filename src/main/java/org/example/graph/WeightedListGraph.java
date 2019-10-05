package org.example.graph;

import org.example.list.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use linked list to represent weighted graph
 *
 * Prim Algorithm
 *   a solution to minimum spanning tree for graph
 *   dense graph
 */
public class WeightedListGraph {
    private String[] vertexes;
    private LinkedList<Vertex>[] heads;

    public WeightedListGraph(String[] vertexes) {
        this.vertexes = Arrays.copyOf(vertexes, vertexes.length);
        this.heads = new LinkedList[vertexes.length];

        // init the heads to save code: if(heads != null)
        for (int i = 0; i < heads.length; i++) {
            this.heads[i] = new LinkedList<>();
        }
    }

    /**
     * Add an edge to the graph
     *
     * @param v1 the name of one edge end
     * @param v2 the name of one edge end
     * @param weight
     */
    public void addEdge(String v1, String v2, int weight) {
        if (v1.equals(v2))
            throw new IllegalArgumentException("Vertexes are same");

        // get the indexes of v1 and v2
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (v1.equals(vertexes[i])) index1 = i;
            if (v2.equals(vertexes[i])) index2 = i;
        }

        if (index1 == -1 || index2 == -1)
            throw new IllegalArgumentException("Vertexes are not exist.");

        // add to the linked lists
        heads[index1].add(new Vertex(index2, weight));
        heads[index2].add(new Vertex(index1, weight));
    }

    /**
     * Use Prim algorithm to make a MST
     *
     * @param v the start vertex name
     * @return PrimResult
     */
    public PrimResult buildMinimumSpanningTree(String v) {
        int current = indexOf(v);
        if (current == -1) return null;

        // init U, V-U
        // all vertexes are in the V, so at the beginning, U is empty
        boolean[] U = new boolean[this.vertexes.length];

        // init lowcost[] and closest[]
        // here we don't need to init lowcost[] because of default
        // initialization in PrimResult constructor
        PrimResult result = new PrimResult(current, this.vertexes.length);

        while (current != -1) {
            // add to U set, remove from V-U set
            U[current] = true;

            // update lowcost[] and closest[] in V-U
            // find the index of vertex with least weight
            int leastIndex = -1; // the index of vertex with least weight
            int leastWeight = Integer.MAX_VALUE;
            LinkedList.Node<Vertex> node = this.heads[current].head();
            while(node != null) { // for each adjacent edge
                Vertex vt = node.get();

                if (!U[vt.index]) { // the vertex is in V-U
                    // update lowcost[] and closest[]
                    if (vt.weight < result.lowcost[vt.index]) {
                        result.lowcost[vt.index] = vt.weight;
                        result.closest[vt.index] = current;
                    }

                    // find the smallest vertex
                    if (result.lowcost[vt.index] < leastWeight) {
                        leastWeight = result.lowcost[vt.index];
                        leastIndex = vt.index;
                    }
                }

                node = node.next();
            } // for each adjacent edge

            // for each step, select the edge with least weight
            current = leastIndex;
        }

        return result;
    }

    /**
     * Get the index of vertex v
     *
     * @param v the vertex name
     * @return if the vertex does not exist, return -1
     */
    public int indexOf(String v) {
        int index = -1;
        for (int i = 0; i < this.vertexes.length; i++) {
            if (v.equals(this.vertexes[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Get the vertex name of i
     *
     * @param i the index
     * @return the name of the vertex
     */
    public String indexOf(int i) {
        return this.vertexes[i];
    }

    /**
     * Print the graph
     */
    public void printGraph() {
        for (int i = 0; i < this.heads.length; i++) {
            System.out.print(vertexes[i] + "->");

            LinkedList.Node<Vertex> node = this.heads[i].head();
            while(node != null) {
                Vertex v = node.get();
                System.out.printf("%s(%d) ", indexOf(v.index) , v.weight);
                node = node.next();
            }

            System.out.println();
        }
    }

    private static class Vertex {
        int index;
        int weight;

        public Vertex(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    private static WeightedListGraph createGraph01() {
        WeightedListGraph g = new WeightedListGraph(new String[] {
                "1", "2", "3", "4", "5", "6", "7"
        });

        g.addEdge("1", "2", 23);
        g.addEdge("2", "3", 20);
        g.addEdge("3", "4", 15);
        g.addEdge("4", "5", 3);
        g.addEdge("5", "6", 17);
        g.addEdge("6", "1", 28);

        g.addEdge("7", "1", 36);
        g.addEdge("7", "2", 1);
        g.addEdge("7", "3", 4);
        g.addEdge("7", "4", 9);
        g.addEdge("7", "5", 16);
        g.addEdge("7", "6", 25);

        return g;
    }

    private static void test01() {
        WeightedListGraph g = createGraph01();
        g.printGraph();
    }

    private static void test02() {
        WeightedListGraph g = createGraph01();
        PrimResult r = g.buildMinimumSpanningTree("1");
        r.printResult(g);
    }

    public static void main(String[] args) {
        // test01();
        test02();
    }
}
