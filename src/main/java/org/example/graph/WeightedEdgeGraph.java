package org.example.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Use a list to save edges for graph
 *
 * Kruskal Algorithm
 *   a solution to minimum spanning tree for graph
 *   sparse graph
 */
public class WeightedEdgeGraph {
    private String[] vertexes;
    private List<Edge> edges;

    public WeightedEdgeGraph(String[] vertexes) {
        this.vertexes = Arrays.copyOf(vertexes, vertexes.length);
        this.edges = new ArrayList<>();
    }

    /**
     * Add an edge to the graph
     *
     * @param from     start vertex of the edge
     * @param to       end vertex of the edge
     * @param weight   weight of the edge, cannot be negative
     */
    public void addEdge(String from, String to, int weight) {
        if (weight < 0)
            throw new IllegalArgumentException(" Weight cannot be negative.");

        if (from.equals(to))
            throw new IllegalArgumentException("Vertexes cannot be the same.");

        // get the indexes of from and to
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < vertexes.length; i++) {
            if (from.equals(vertexes[i])) index1 = i;
            if (to.equals(vertexes[i])) index2 = i;
        }

        if (index1 == -1 || index2 == -1)
            throw new IllegalArgumentException("Vertexes are not exist.");

        this.edges.add(new Edge(index1, index2, weight));
    }

    /**
     * Use Kruskal algorithm to make a MST
     *
     * @return the MST
     */
    public List<Edge> buildMinimumSpanningTree() {
        // init the numbers for sets
        int[] nodesets = new int[this.vertexes.length];
        for (int i = 0; i < nodesets.length ; i++) {
            nodesets[i] = i;
        }

        // sort the edge set
        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                if (e1.weight < e2.weight)
                    return -1;
                else if (e1.weight > e2.weight)
                    return 1;
                else
                    return 0;
            }
        });

        // Kruskal
        int n = nodesets.length;
        List<Edge> tree = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i); // edge with a small weight
            if (merge(nodesets, e.v1, e.v2)) {
                tree.add(e);  // if merged, add it to the tree
                n--;
                if (n == 1)
                    break;
            }
        }

        return tree;
    }

    /**
     * Merge an edge into a tree
     *
     * @param nodesets
     * @param a an index of the edge
     * @param b an index of the edge
     * @return if true, merged
     */
    private boolean merge(int[] nodesets, int a, int b) {
        int p = nodesets[a];
        int q = nodesets[b];
        if (p == q) return false;  // To avoid a circle, do not merge

        for (int i = 0; i < nodesets.length; i++) {
            if (nodesets[i] == q)  // unify the numbers of set
                nodesets[i] = p;
        }

        return true;
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
     * @param i index of the vertex
     * @return the vertex name
     */
    public String indexOf(int i) {
        return this.vertexes[i];
    }

    public void printGraph() {
        System.out.println("Edges: ");
        for (Edge e: edges) {
            System.out.printf("%s-%s(%d) ", indexOf(e.v1), indexOf(e.v2), e.weight);
        }
        System.out.println();
    }

    private static WeightedEdgeGraph createGraph01() {
        WeightedEdgeGraph g = new WeightedEdgeGraph(new String[] {
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
        WeightedEdgeGraph g = createGraph01();
        g.printGraph();
    }

    private static void test02() {
        WeightedEdgeGraph g = createGraph01();

        List<Edge> tree = g.buildMinimumSpanningTree();
        System.out.println("Minimum Spanning Tree: ");
        int cost = 0;
        for (Edge e: tree) {
            System.out.printf("\t%s-%s(%d) ", g.indexOf(e.v1), g.indexOf(e.v2), e.weight);
            cost += e.weight;
        }
        System.out.println();
        System.out.println("Minimum cost: " + cost);
    }

    public static void main(String[] args) {
        // test01();
        test02();
    }
}
