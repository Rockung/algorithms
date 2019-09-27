package org.example.recursion;

public class Queen8 {
    private final static int MAX_QUEEN = 8;

    private static int count = 0;
    private static int judgeCount = 0;

    // one solution: the array index by 0, the chessboard index by 1
    // so, the ith item+1 is the queen column position of the i+1 row
    private int[] solution = new int[MAX_QUEEN];

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.println("      solutions: " + count);
        System.out.println("conflict counts: " + judgeCount);
    }

    /**
     * place the n'th queen on the chessboard
     *
     * @param n
     */
    private void check(int n) {
        if (n == MAX_QUEEN) { // the last queen position has found
            print();
            return;
        }

        for (int i = 0; i < MAX_QUEEN; i++) {
            // place n'th queen(row) on i'th column
            solution[n] = i;

            // check whether it's conflict
            if (judge(n)) {
                // no conflict, place the next queen
                check(n+1);
            }

            // there is a conflict, go to next loop. in the next loop, the queen
            // will be placed the next column
        }

    }

    /**
     * check if it's conflict with the queens before, after you place n'th
     * queen
     *
     * @param n
     * @return
     */
    private boolean judge(int n) {
        judgeCount++;

        for (int i = 0; i < n; i++) {
            // solution[i] == solution[n]: placed at the same column
            // Math.abs(n-i) == Math.abs(solution[n] - solution[i]):  placed at the same diagonal
            if(solution[i] == solution[n]
                    || Math.abs(n-i) == Math.abs(solution[n] - solution[i]) ) {
                return false;
            }
        }

        return true;
    }

    private void print() {
        count++;
        for (int i = 0; i < solution.length; i++) {
            System.out.print((solution[i]+1) + " ");
        }
        System.out.println();
    }

}
