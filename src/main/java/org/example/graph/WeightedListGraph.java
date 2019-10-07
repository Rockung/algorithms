package org.example.graph;

import org.example.list.LinkedList;
import org.example.list.Stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use linked list to represent weighted graph
 *   support undirected and directed graph
 *   support weighted graph
 *
 * Prim Algorithm
 *   a solution to minimum spanning tree for graph
 *   dense graph
 *
 * Topology Sorting
 *
 */
public class WeightedListGraph {
    private boolean directed = false;   // undirected or directed graph
    private String[] vertexes;          // vertexes of graph
    private int[] inDegrees;            // degrees of coming in
    private int[] outDegrees;           // degrees of going out
    private LinkedList<Vertex>[] heads; // going-out edges of each vertex

    public WeightedListGraph(String[] vertexes) {
        this(vertexes, false);
    }

    public WeightedListGraph(String[] vertexes, boolean directed) {
        int len = vertexes.length;
        this.vertexes = Arrays.copyOf(vertexes, len);
        this.inDegrees = new int[len];
        this.outDegrees = new int[len];
        this.heads = new LinkedList[len];
        this.directed = directed;

        // init the heads to save code: if(heads != null)
        for (int i = 0; i < heads.length; i++) {
            this.heads[i] = new LinkedList<>();
        }
    }

    /**
     * Add an edge to the graph in a normal order
     *
     * @param from   the name of from end
     * @param to     the name of to end
     * @param weight the weight of the edge
     */
    public void addEdge(String from, String to, int weight) {
        addEdge(from, to, weight, false);
    }

    /**
     * Add an edge to the graph
     *
     * @param from   the name of from end
     * @param to     the name of to end
     * @param weight the weight of the edge
     * @param inverse insert into the list inversely
     */
    public void addEdge(String from, String to, int weight, boolean inverse) {
        if (from.equals(to))
            throw new IllegalArgumentException("Vertexes are same");

        // get the indexes of from and to ends
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (from.equals(vertexes[i])) index1 = i;
            if (to.equals(vertexes[i])) index2 = i;
        }

        if (index1 == -1 || index2 == -1)
            throw new IllegalArgumentException("Vertexes are not exist.");

        // add to the linked lists and update in/out degrees
        if (inverse)
            heads[index1].addBefore(new Vertex(index2, weight));
        else
            heads[index1].add(new Vertex(index2, weight));
        outDegrees[index1]++;
        inDegrees[index2]++;

        if (!directed) {
            if (inverse)
                heads[index2].addBefore(new Vertex(index1, weight));
            else
                heads[index2].add(new Vertex(index1, weight));
            outDegrees[index2]++;
            inDegrees[index1]++;
        }
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
        // initialization in the constructor
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
     * Build a topology sorting
     *   the graph should be built inversely
     *   check in-degrees repeatedly
     */
    public TopologyResult buildTopologySorting() {
        int[] ins = Arrays.copyOf(this.inDegrees, this.inDegrees.length);
        TopologyResult result = new TopologyResult(this.vertexes.length);
        Stack<Integer> stack = new Stack<>();

        // start from the vertexes which in-degree is zero
        for (int i = 0; i < ins.length; i++) {
            if (ins[i] == 0) stack.push(i);
        }

        // loop until catching an exception because popping stack
        while(true) {
            try {
                int i = stack.pop();
                result.add(i);

                LinkedList.Node<Vertex> node = this.heads[i].head();
                while(node != null) {
                    int j = node.get().index;

                    // decrease, if zero, push onto stack
                    ins[j]--;
                    if (ins[j] == 0) stack.push(j);

                    node = node.next();
                }

            } catch (IllegalStateException e) {
                break;
            }
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

    /**
     * The other end of one vertex in the list
     */
    private static class Vertex {
        int index;  // the index in the vertex array
        int weight; // the weight of the edge

        public Vertex(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    // for Prim MST
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

    // To build topology sorting, the graph should be built inversely
    public static WeightedListGraph createGraph02() {
        WeightedListGraph g = new WeightedListGraph(new String[] {
                "C0", "C1", "C2", "C3", "C4", "C5"
        }, true);

        g.addEdge("C0", "C1", 1, true);
        g.addEdge("C0", "C2", 1, true);
        g.addEdge("C0", "C3", 1, true);
        g.addEdge("C2", "C1", 1, true);
        g.addEdge("C2", "C4", 1, true);
        g.addEdge("C3", "C4", 1, true);
        g.addEdge("C5", "C3", 1, true);
        g.addEdge("C5", "C4", 1, true);

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

    private static void test03() {
        WeightedListGraph g = createGraph02();
        TopologyResult r = g.buildTopologySorting();
        r.printResult(g);
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        test03(); // 502134
    }
}
