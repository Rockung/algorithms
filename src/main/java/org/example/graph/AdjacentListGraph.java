package org.example.graph;

import org.example.list.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use linked list to represent a graph
 *   1. degree of a vertex
 *   2. depth-first traversal and breadth-first traversal
 *   3. cut-vertex/cut-edge: Tarjan algorithm
 */
public class AdjacentListGraph {
    private String[] vertexes;            // vertexes of graph
    private LinkedList<Integer>[] heads;  // list of adjacent edges for each vertex

    /**
     * Construct from vertexes
     *
     * @param vertexes
     */
    public AdjacentListGraph(String[] vertexes) {
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
     */
    public void addEdge(String v1, String v2) {
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
        heads[index1].add(index2);
        heads[index2].add(index1);
    }

    /**
     * Depth-first traversal
     *
     * @param v the start vertex's name
     * @return the DFS tree, a list of edges
     */
    public List<Edge> depthFirstTraverse(String v) {
        int start = indexOf(v);
        if (start == -1) return new ArrayList<>();

        // prepare for recursive call
        ArrayList<Edge> path = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        // do depth-first searching
        doDepthFirstTraverse(start, visited, path);

        return path;
    }

    /**
     * Do depth-first search
     *   1. mark it as visited
     *   2. add the first edge which end is not yet visited to the path
     *   3. recursively forward
     *
     * @param start    the start index
     * @param visited  the array as visited marks
     * @param path     the visited path
     */
    private void doDepthFirstTraverse(int start, boolean[] visited, ArrayList<Edge> path) {
        visited[start] = true;

        LinkedList.Node<Integer> node = this.heads[start].head();
        while(node != null) {
            int i = node.get();
            if (!visited[i]) {
                path.add(new Edge(start, node.get()));
                doDepthFirstTraverse(i, visited, path);
            }

            node = node.next();
        }
    }

    /**
     * Breadth-first traversal
     *   use FIFO queue to visit vertexes
     *   mark the visited vertexes
     *   add the first visited edges to the tree
     *
     * @param v the start vertex's name
     * @return the BFS tree, a list of edges
     */
    public List<Edge> breadthFirstTraverse(String v) {
        int start = indexOf(v);
        if (start == -1) return new ArrayList<>();

        ArrayList<Edge> path = new ArrayList<>();
        ArrayList<Integer> queue = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        queue.add(start);
        while(!queue.isEmpty()) {
            start = queue.remove(0);
            visited[start] = true;

            LinkedList.Node<Integer> node = this.heads[start].head();
            while (node != null) {
                int i = node.get();

                if (!visited[i]) { // not visited yet
                    visited[i] = true;
                    path.add(new Edge(start, i));

                    queue.add(i); // add to the queue for the next loop
                }

                node = node.next();
            }
        }

        return path;
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

            LinkedList.Node<Integer> node = this.heads[i].head();
            while(node != null) {
                System.out.print(vertexes[node.get()] + " ");
                node = node.next();
            }

            System.out.println();
        }
    }

    private static AdjacentListGraph createGraph() {
        AdjacentListGraph graph = new AdjacentListGraph(new String[] {
                "1", "2", "3", "4", "5", "6", "7", "8"
        });

        graph.addEdge("1", "2");
        graph.addEdge("1", "3");

        graph.addEdge("2", "4");
        graph.addEdge("2", "5");
        graph.addEdge("2", "6");

        graph.addEdge("3", "7");
        graph.addEdge("3", "8");

        graph.addEdge("4", "5");
        graph.addEdge("7", "8");

        return graph;
    }

    private static void test01() {
        AdjacentListGraph graph = createGraph();
        graph.printGraph();
    }

    private static void test02() {
        AdjacentListGraph graph = createGraph();
        List<Edge> path = graph.depthFirstTraverse("1");

        for (Edge e: path) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();
    }

    private static void test03() {
        AdjacentListGraph graph = createGraph();
        List<Edge> path = graph.breadthFirstTraverse("1");

        for (Edge e: path) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        test03();
    }
}
