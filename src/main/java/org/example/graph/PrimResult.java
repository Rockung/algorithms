package org.example.graph;

/**
 * The result for Prim Algorithm
 *
 * Prim Algorithm
 *   a solution to minimum spanning tree for graph
 *   U set and V-U set
 *     U set: the selected vertexes
 *     V-U set: the vertexes which has not been selected yet
 *   lowcost[] and closest[]
 *     lowcost[]: the least weights for each step
 *     closest[]: the closest edges for each step
 */
public class PrimResult {
    int start;     // the index of start vertex
    int[] lowcost; // minimum costs of the selected edges
    int[] closest; // the indexes of the other ends

    public PrimResult(int start, int len) {
        this.start = start;
        this.lowcost = new int[len];
        this.closest = new int[len];

        for (int i = 0; i < this.closest.length ; i++) {
            this.closest[i] = -1;
            this.lowcost[i] = Integer.MAX_VALUE;
        }
        this.lowcost[start] = 0;
    }

    public void printResult(WeightedListGraph g) {
        int cost = 0;
        System.out.print("lowcost: ");
        for (int i = 0; i < this.lowcost.length; i++) {
            cost += this.lowcost[i];
            System.out.print(this.lowcost[i] + " ");
        }
        System.out.printf("(%d)\n", cost);

        System.out.print("closest: ");
        for (int i = 0; i < this.closest.length; i++) {
            if (this.closest[i] != -1)
                System.out.printf("%s-%s ", g.indexOf(i),  g.indexOf(this.closest[i]));
        }
        System.out.println();
    }
}
