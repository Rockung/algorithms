package org.example.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Use adjacent matrix to represent a graph
 *
 */
public class AdjacentMatrixGraph {
    private String[] vertexes;
    private int[][] edgeMatrix;

    public static void main(String[] args) {
        // test01();
        test02();
    }

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
     * Get the degree of a vertex
     *
     * @param v
     * @return if the vertex does not exist, return -1
     */
    public int degreeOf(String v) {
        // get the index of v
        int index = indexOf(v);
        if (index == -1) return -1;

        // get the degree of v
        int degree = 0;
        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[index][i] == 1) degree++;
        }

        return degree;
    }

    /**
     * Get the depth-first path
     *
     * @param v the start vertex
     * @return the list of path
     */
    public List<Integer> depthFirstSearch(String v) {
        int index = indexOf(v);
        if (index == -1) return new ArrayList<>();

        // prepare for recursive call
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[vertexes.length];

        // do depth-first searching
        doDepthFirstSearch(index, visited, path);

        return path;
    }

    /**
     * Add **start** to the path, mark it as visited and find the next one recursively
     *
     * @param start
     * @param visited
     * @param path
     */
    private void doDepthFirstSearch(int start, boolean[] visited, ArrayList<Integer> path) {
        visited[start] = true;
        path.add(start);

        for (int i = 0; i < edgeMatrix[0].length; i++) {
            if (edgeMatrix[start][i] == 1 && !visited[i]) {
                doDepthFirstSearch(i, visited, path);
                break;
            }
        }
    }

    public void breathFirstSearch() {

    }

    public void printGraphMatrix() {
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

    private static void test01() {
        AdjacentMatrixGraph graph = createGraph01();
        graph.printGraphMatrix();
        System.out.println("a's degree: " + graph.degreeOf("a"));
        System.out.println("b's degree: " + graph.degreeOf("b"));
        System.out.println("c's degree: " + graph.degreeOf("c"));
        System.out.println("d's degree: " + graph.degreeOf("d"));
        System.out.println("e's degree: " + graph.degreeOf("e"));
        System.out.println("f's degree: " + graph.degreeOf("f"));
    }

    private static void test02() {
        AdjacentMatrixGraph graph = createGraph01();
        List<Integer> path = graph.depthFirstSearch("a");

        for (int i: path) {
            System.out.print(graph.indexOf(i) + "->");
        }
        System.out.println();

    }
}
