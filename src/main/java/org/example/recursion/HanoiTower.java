package org.example.recursion;

/**
 * HanoiTower
 *
 * There are three towers: A, B, C and **N** plates with different size are
 * placed on A from bigger to smaller.
 *
 * Write a algorithm to move all plates to C through B, in each step,
 * make sure that the bigger plate is under the smaller one.
 *
 * Divide and Conquer:
 *    1. divide a big problem into small but similar problems
 *    2. resolve the small problems
 *    3. merge the results of the small problems
 *
 * Recursion:
 *    1. recursively call itself with different parameters
 *    2. make sure there is an exit that the calls converge the exit
 */
public class HanoiTower {
    private static int steps = 0;

    public static void main(String[] args) {
        movePlates(5, 'A', 'B', 'C');
    }

    /**
     * move n plates from a to c through b
     *
     * @param n number of plates
     * @param a A tower
     * @param b B tower
     * @param c C tower
     */
    public static void movePlates(int n, char a, char b, char c) {
        if (n == 1) {
            System.out.printf("%2d: move No.%d from %c->%c\n", ++steps, n, a, c);
        } else {
            movePlates(n-1, a, c, b);
            System.out.printf("%2d: move No.%d from %c->%c\n", ++steps, n, a, b);
            movePlates(n-1, b, a, c);
        }
    }
}
