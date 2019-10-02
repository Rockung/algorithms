package org.example.graph;

import org.example.list.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use linked list to represent a graph
 *
 */
public class AdjacentListGraph {
    private String[] vertexes;   // vertexes of graph
    private LinkedList[] heads;  // list of adjacent edges for each vertex

    /**
     * Construct from vertexes
     *
     * @param vertexes
     */
    public AdjacentListGraph(String[] vertexes) {
        this.vertexes = Arrays.copyOf(vertexes, vertexes.length);
        this.heads = new LinkedList[vertexes.length];
    }

    /**
     * Add an edge to the graph
     *
     * @param v1
     * @param v2
     * @throws IllegalArgumentException
     */
    public void addEdge(String v1, String v2) throws IllegalArgumentException {
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

        LinkedList list = null;

        // v1
        list = heads[index1];
        if (list == null) {
            list = new LinkedList();
            heads[index1] = list;
        }
        list.add(index2);

        // v2
        list = heads[index2];
        if (list == null) {
            list = new LinkedList();
            heads[index2] = list;
        }
        list.add(index1);
    }

    /**
     * Depth-first search
     *
     * @param v
     * @return
     */
    public List<Edge> depthFirstSearch(String v) {
        int start = indexOf(v);
        if (start == -1) return new ArrayList<>();

        // prepare for recursive call
        ArrayList<Edge> path = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        // do depth-first searching
        doDepthFirstSearch(start, visited, path);

        return path;
    }

    /**
     * Do depth-first search
     *   1. mark it as visited
     *   2. add the first edge which end is not yet visited to the path
     *   3. recursively forward
     *
     * @param start
     * @param visisted
     * @param path
     */
    private void doDepthFirstSearch(int start, boolean[] visisted, ArrayList<Edge> path) {
        visisted[start] = true;

        LinkedList list = this.heads[start];
        if (list == null) return;

        LinkedList.Node node = list.head();
        while(node != null) {
            int i = node.get();
            if (!visisted[i]) {
                path.add(new Edge(start, node.get()));
                doDepthFirstSearch(i, visisted, path);
            }

            node = node.next();
        }
    }

    /**
     * Breadth-first search
     *   use FIFO queue to visit vertexes
     *   mark the visited vertexes
     *
     * @param v
     * @return
     */
    public List<Edge> breadthFirstSearch(String v) {
        int start = indexOf(v);
        if (start == -1) return new ArrayList<>();

        ArrayList<Edge> path = new ArrayList<>();
        ArrayList<Integer> queue = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        queue.add(start);
        while(!queue.isEmpty()) {
            start = queue.remove(0);
            visited[start] = true;

            LinkedList list = this.heads[start];
            if (list == null) continue;

            LinkedList.Node node = list.head();
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
     * @param i
     * @return
     */
    public String indexOf(int i) {
        return this.vertexes[i];
    }

    /**
     * Print the graph
     */
    public void printGraph() {
        for (int i = 0; i < heads.length; i++) {
            LinkedList list = heads[i];
            System.out.print(vertexes[i] + "->");
            if (list != null) {
                LinkedList.Node node = list.head();
                while(node != null) {
                    System.out.print(vertexes[node.get()] + " ");
                    node = node.next();
                }
            }
            System.out.println();
        }
    }

    public static class Edge {
        int v1;
        int v2;

        public Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
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
        List<Edge> path = graph.depthFirstSearch("1");

        for (Edge e: path) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();
    }

    private static void test03() {
        AdjacentListGraph graph = createGraph();
        List<Edge> path = graph.breadthFirstSearch("1");

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
