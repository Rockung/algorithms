package org.example.recursion;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class HorseChessboard {

    private static int DEFAULT_ROWS = 8; // default chessboard rows
    private static int DEFAULT_COLS = 8; // default chessboard columns

    // chessboard size
    private int boardRows;
    private int boardCols;

    private int[][] chessboard;     // the chessboard
    private boolean[][] visited;    // whether the place is visited
    private boolean finished;       // whether the game is finished
    private int failedTries;        // number of failed tries
    private int emptyCount;         // number of empty tries

    public static void main(String[] args) {
        System.out.println("The horse is running, come on ...");

        // the start position from user's point of view
        int row = 2;     // start from 1
        int column = 2;  // start from 1

        HorseChessboard chessboard = new HorseChessboard();

        // measure the time used
        long start = System.currentTimeMillis();
        for (int i = 0; i < DEFAULT_ROWS; i++) {
            for (int j = 0; j < DEFAULT_ROWS; j++) {
                chessboard.traverseChessboard(i, j);
            }
        }
        // chessboard.traverseChessboard(row - 1, column - 1);
        long end = System.currentTimeMillis();
        System.out.println("time used:  " + (end - start) + " miliseconds");

        chessboard.printChessboard();
    }

    public HorseChessboard() {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }

    public HorseChessboard(int rows, int cols) {
        this.boardRows = rows;
        this.boardCols = cols;
        this.chessboard = new int[rows][cols];
        this.visited = new boolean[rows][cols];
    }

    /**
     * Traverse the chessboard
     *
     * @param row the row the horse starts, from 0
     * @param col the col the horse starts, from 0
     */
    public void traverseChessboard(int row, int col) {
        this.clearChessboard();
        this.doTraverseChessboard(row, col, 1);
    }

    /**
     * Traverse chessboard recursively
     *
     * @param row  the row the horse starts, from 0
     * @param col  the col the horse starts, from 0
     * @param step the step the horse runs
     */
    private void doTraverseChessboard(int row, int col, int step) {
        this.chessboard[row][col] = step;
        this.visited[row][col] = true;

        // if the step reaches to the chessboard size, the horse stops
        // set **finished** to true, rewind the recursive calls
        if (step >= boardRows * boardCols) {
            this.finished = true;
            return;
        }

        // get the places the horse can go in the next step
        ArrayList<Point> ps = next(new Point(col, row));
        if (!ps.isEmpty()) {
            // optimization: try the places first with less possible, so sort the
            // places by non-decreasing way
            sort(ps);

            // try all possibilities
            while (!ps.isEmpty()) {
                Point p = ps.remove(0);    // get the first place
                if (!this.visited[p.y][p.x]) {    // not visited, recursively traverse
                    this.doTraverseChessboard(p.y, p.x, step + 1);
                }
            }
        } else {
            this.emptyCount++;
        }

        // if it's finished and steps are less than the chessboard size, it a failed path.
        // so, clear the states to let other recursive call running normally
        if (step < boardRows * boardCols && !this.finished) {
            this.chessboard[row][col] = 0;
            this.visited[row][col] = false;
            this.failedTries++;
        }

        if (step == 1 && finished) {
            System.out.printf("start at (%d, %d) with %d failed, %d empty\n", row + 1, col + 1, this.failedTries, emptyCount);
        }
    }

    public int getFailedTries() {
        return failedTries;
    }

    /**
     * Print the chessboard
     */
    public void printChessboard() {
        for (int[] rows : this.chessboard) {
            for (int step : rows) {
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Compute the places the horse can go in the next step
     *
     * @param curPoint
     * @return
     */
    private ArrayList<Point> next(Point curPoint) {
        ArrayList<Point> ps = new ArrayList<Point>();
        Point p = new Point();

        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y - 1) >= 0) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y - 2) >= 0) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x + 1) < this.boardCols && (p.y = curPoint.y - 2) >= 0) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x + 2) < this.boardCols && (p.y = curPoint.y - 1) >= 0) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x + 2) < this.boardCols && (p.y = curPoint.y + 1) < this.boardRows) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x + 1) < this.boardCols && (p.y = curPoint.y + 2) < this.boardRows) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y + 2) < this.boardRows) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }
        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y + 1) < this.boardRows) {
            if (!this.visited[p.y][p.x]) ps.add(new Point(p));
        }

        return ps;
    }

    private void clearChessboard() {
        this.finished = false;
        this.failedTries = 0;
        this.emptyCount = 0;

        for (int i = 0; i < this.boardRows; i++) {
            for (int j = 0; j < this.boardCols; j++) {
                chessboard[i][j] = 0;
                visited[i][j] = false;
            }
        }
    }

    // non-decreasing sorting
    private void sort(ArrayList<Point> ps) {
        ps.sort(new Comparator<Point>() {

            @Override
            public int compare(Point o1, Point o2) {
                int count1 = next(o1).size();
                int count2 = next(o2).size();

                if (count1 < count2) {
                    return -1;
                } else if (count1 == count2) {
                    return 0;
                } else {
                    return 1;
                }
            }

        });
    }
}
