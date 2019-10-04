package org.example.graph;

import java.util.ArrayList;

public class DijkstraResult {
    int start;
    int[] dist;
    int[] p;

    ArrayList<Integer> V_S;

    public DijkstraResult(int start, int len) {
        this.start = start;
        this.dist = new int[len];
        this.p = new int[len];

        this.V_S = new ArrayList<>();
        for (int i = 0; i < len ; i++) {
            if (i != this.start)
                this.V_S.add(i);
        }
    }

    public void printResult() {
        System.out.println("start: " + start);

        System.out.print(" dist: ");
        for (int i = 0; i < this.dist.length; i++) {
            System.out.print(dist[i] + " ");
        }
        System.out.println();

        System.out.print("    p: ");
        for (int i = 0; i < this.p.length; i++) {
            System.out.print(p[i] + " ");
        }
        System.out.println();
    }
}
