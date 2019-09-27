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
    private boolean finished;      // whether the game is finished

    public static void main(String[] args) {
        System.out.println("The horse is running, come on ...");

        // the start position from user's point of view
        int row = 2;     // start from 1
        int column = 1;  // start from 1

        HorseChessboard chessboard = new HorseChessboard();

        // measure the time used
        long start = System.currentTimeMillis();
        chessboard.traverseChessboard(row - 1, column - 1);
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
        this.traverseChessboard(row, col, 1);
    }

    /**
     * Traverse chessboard recursively
     *
     * @param row  the row the horse starts, from 0
     * @param col  the col the horse starts, from 0
     * @param step the step the horse runs
     */
    private void traverseChessboard(int row, int col, int step) {
        this.chessboard[row][col] = step;
        this.visited[row][col] = true;

        // get the places the horse can go in the next step
        ArrayList<Point> ps = next(new Point(col, row));
        // optimization: try the places first with less possible, so sort the
        // places by non-decreasing way
        sort(ps);

        // try all possibilities
        while (!ps.isEmpty()) {
            Point p = ps.remove(0); // get the first place
            if (!this.visited[p.y][p.x]) {      // not visited, recursively traverse
                this.traverseChessboard(p.y, p.x, step + 1);
            }
        }

        // if the step reaches to the chessboard size, the horse stops
        // set **finished** to true, end the recursive calls
        if (step < DEFAULT_ROWS * DEFAULT_COLS && !this.finished) {
            //  clear the states to let other recursive call running normally
            this.chessboard[row][col] = 0;
            this.visited[row][col] = false;
        } else {
            if (!this.finished) {
                this.finished = true;
                System.out.println("finished at step " + step);
            }
        }
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
        Point p1 = new Point();

        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1) < this.boardCols && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2) < this.boardCols && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 2) < this.boardCols && (p1.y = curPoint.y + 1) < this.boardRows) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x + 1) < this.boardCols && (p1.y = curPoint.y + 2) < this.boardRows) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < this.boardRows) {
            ps.add(new Point(p1));
        }
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < this.boardRows) {
            ps.add(new Point(p1));
        }

        return ps;
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
