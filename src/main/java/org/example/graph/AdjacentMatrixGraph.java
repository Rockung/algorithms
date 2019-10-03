package org.example.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use adjacent matrix to represent a graph
 *   1. degree of a vertex
 *   2. depth-first traversal and breadth-first traversal
 *   3. cut-vertex/cut-edge: Tarjan algorithm
 */
public class AdjacentMatrixGraph {
    private String[] vertexes;  // vertex array
    private int[][] edgeMatrix; // edge matrix

    /**
     * Construct with an array of vertex names
     *
     * @param vertexes the array of vertex names
     */
    public AdjacentMatrixGraph(String[] vertexes) {
        int len = vertexes.length;
        this.vertexes = Arrays.copyOf(vertexes, len);
        this.edgeMatrix = new int[len][len];
    }

    /**
     * Add an edge to the graph
     *
     * @param v1
     * @param v2
     * @throws IllegalArgumentException if the vertex's names are same or not exist
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

        if (index1 != -1 && index2 != -1) {
            // fill the cell with 1 systematically
            this.edgeMatrix[index1][index2] = 1;
            this.edgeMatrix[index2][index1] = 1;
        } else {
            throw new IllegalArgumentException("Vertexes are not exist.");
        }
    }

    /**
     * Get the degree of a vertex by name
     *
     * @param v
     * @return if the vertex does not exist, return -1
     */
    public int degreeOf(String v) {
        // get the index of v
        int index = indexOf(v);
        if (index == -1) return -1;

        return degreeOf(index);
    }

    /**
     * Get the degree of a vertex by index
     *
     * @param v
     * @return
     */
    public int degreeOf(int v) {
        // get the degree of v
        int degree = 0;
        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[v][i] == 1) degree++;
        }

        return degree;
    }

    /**
     * Get the depth-first traversal path
     *
     * @param v the start vertex
     * @return the list of path
     */
    public List<Edge> depthFirstTraverse(String v) {
        int start = indexOf(v);
        if (start == -1) return new ArrayList<>();

        // prepare for recursive call
        ArrayList<Edge> path = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        // do depth-first traversal
        doDepthFirstTraverse(start, visited, path);

        return path;
    }

    /**
     * Do depth-first traversal from **start**
     *   1. mark it as visited
     *   2. add the first edge which end is not yet visited to the path
     *   3. recursively forward
     *
     * @param start
     * @param visited
     * @param path
     */
    private void doDepthFirstTraverse(int start, boolean[] visited, ArrayList<Edge> path) {
        visited[start] = true;

        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[start][i] == 0) continue;

            if (!visited[i]) {
                path.add(new Edge(start, i));
                doDepthFirstTraverse(i, visited, path);
            }
        }
    }

    /**
     * Get the cut-edges and cut-vertexes of the graph
     *
     * @param v
     * @return
     */
    public CutEdgeVertex buildCutEdgeAndVertex(String v) {
        int start = indexOf(v);
        if (start == -1) return null;

        // prepare for recursive call
        TarjanContext context = new TarjanContext(this.vertexes.length);

        // parent: -1, no parent
        // root: true, the start is the root
        doTarjanAlgorithm(start, -1, true, context);

        return context.edgeVertex;
    }

    /**
     * Tarjan algorithm
     *
     * @param start
     * @param parent
     * @param context
     */
    private void doTarjanAlgorithm(int start, int parent, boolean root, TarjanContext context) {
        if (context.dfn[start] <=0) {  // not visited yet
            context.num++;
            context.dfn[start] = context.num;
            context.low[start] = context.num;
        }

        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[start][i] == 0) continue; // not an edge

            if (context.dfn[i] <=0) {  // not visited yet
                doTarjanAlgorithm(i, start, false, context);

                // when tracing back
                context.low[start] = Math.min(context.low[start], context.low[i]);

                // cut-vertex
                if (context.low[i] >= context.dfn[start]) {
                    /**
                     * 1. root with at least two sub-trees
                     * 2. internal vertex
                     */
                    if (!root || this.degreeOf(start) > 1)
                            context.edgeVertex.cutVertexes.add(start);

                    // cut-edge
                    if (context.low[i] > context.dfn[start]) {
                        context.edgeVertex.cutEdges.add(new Edge(start, i));
                    }
                }
            } else {
                if (parent != i && context.dfn[i] < context.dfn[start]) {
                    context.low[start] = Math.min(context.low[start], context.dfn[i]);
                }
            }
        }
    }

    /**
     * Context for runing Tarjan algorithm
     */
    private static class TarjanContext {
        int num;
        int[] dfn;
        int[] low;
        CutEdgeVertex edgeVertex;

        public TarjanContext(int len) {
            this.num = 0;
            this.dfn = new int[len];
            this.low = new int[len];
            this.edgeVertex = new CutEdgeVertex();
        }
    }

    /**
     * Breadth-first traserval
     *   use FIFO queue to visit vertexes
     *   mark the visited vertexes
     *
     * @param v the start vertex
     * @return
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

            for (int i = 0; i < edgeMatrix[0].length; i++) {
                if (edgeMatrix[start][i] == 0) continue; // not an edge

                if (!visited[i]) { // not visited yet
                    visited[i] = true;
                    path.add(new Edge(start, i));

                    queue.add(i); // add to the queue for the next loop
                }
            }
        }

        return path;
    }

    /**
     * Print the graph matrix
     */
    public void printGraph() {
        System.out.print("  ");
        for (int i = 0; i < vertexes.length; i++) {
            System.out.print(vertexes[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < this.edgeMatrix.length; i++) {
            System.out.print(vertexes[i] + " ");
            for (int j = 0; j < this.edgeMatrix[0].length; j++) {
                System.out.print(this.edgeMatrix[i][j] + " ");
            }
            System.out.println();
        }
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
     * Create a graph for test
     *
     * @return
     */
    private static AdjacentMatrixGraph createGraph01() {
        AdjacentMatrixGraph graph = new AdjacentMatrixGraph(new String[] {
                "a", "b", "c", "d", "e"
        });

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "c");
        graph.addEdge("e", "c");
        graph.addEdge("e", "d");

        return graph;
    }

    private static AdjacentMatrixGraph createGraph02() {
        AdjacentMatrixGraph graph = new AdjacentMatrixGraph(new String[] {
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

    private static AdjacentMatrixGraph createGraph03() {
        AdjacentMatrixGraph graph = new AdjacentMatrixGraph(new String[] {
                "1", "2", "3"
        });

        graph.addEdge("1", "2");
        graph.addEdge("2", "3");

        return graph;
    }

    private static AdjacentMatrixGraph createGraph04() {
        AdjacentMatrixGraph graph = new AdjacentMatrixGraph(new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
        });

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("F", "G");
        graph.addEdge("G", "H");
        graph.addEdge("H", "I");
        graph.addEdge("H", "J");
        graph.addEdge("H", "F");
        graph.addEdge("J", "B");
        graph.addEdge("E", "K");
        graph.addEdge("K", "L");
        graph.addEdge("I", "D");

        return graph;
    }

    private static void test01() {
        AdjacentMatrixGraph graph = createGraph01();
        graph.printGraph();
        System.out.println("a's degree: " + graph.degreeOf("a"));
        System.out.println("b's degree: " + graph.degreeOf("b"));
        System.out.println("c's degree: " + graph.degreeOf("c"));
        System.out.println("d's degree: " + graph.degreeOf("d"));
        System.out.println("e's degree: " + graph.degreeOf("e"));
        System.out.println("f's degree: " + graph.degreeOf("f"));
    }

    private static void test02() {
        AdjacentMatrixGraph graph = createGraph02();
        List<Edge> path = graph.depthFirstTraverse("1");

        for (Edge e: path) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();

    }

    private static void test03() {
        AdjacentMatrixGraph graph = createGraph02();
        List<Edge> path = graph.breadthFirstTraverse("1");

        for (Edge e: path) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();

    }

    private static void test04() {
        AdjacentMatrixGraph graph = createGraph04();
        CutEdgeVertex edgeVertex = graph.buildCutEdgeAndVertex("A");

        System.out.print("Cut-edges: ");
        for (Edge e: edgeVertex.cutEdges) {
            System.out.print(graph.indexOf(e.v1) + "-" + graph.indexOf(e.v2) + " ");
        }
        System.out.println();

        System.out.println("Cut-vertexes:");
        for (int v: edgeVertex.cutVertexes) {
            System.out.print(graph.indexOf(v)+ " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        // test03();
        test04();
    }

}
