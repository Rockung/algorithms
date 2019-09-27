package org.example.recursion;

public class Maze {
    private final static int MAP_STATE_WALL = 1;    // the wall
    private final static int MAP_STATE_NO_RUN = 0;  // haven't run yet
    private final static int MAP_STATE_PASS = 2;    // you can pass
    private final static int MAP_STATE_NO_WAY = 3;  // you can't pass

    public static void main(String[] args) {
        // create and init an 2-D array, simulate a maze
        int[][] map = createMap(8, 7);

        // print the map
        printMap(map);

        boolean found = findWay(map, 1, 1);
        System.out.println("found the way: " + found);
        printMap(map);
    }

    private static boolean findWay(int[][] map, int i, int j) {
        if (map[map.length-2][map[0].length-2] == MAP_STATE_PASS)
            return true;

        if (map[i][j] == MAP_STATE_NO_RUN) {
            // first, mark this step can pass through
            map[i][j] = MAP_STATE_PASS;

            // second, check the direction that can go
            // different strategies have different routes
            if (findWay(map, i+1, j)) {             // down
                return true;
            } else if (findWay(map, i, j+1)) {      // right
                return true;
            } else if (findWay(map, i-1, j)) {      // up
                return true;
            } else if (findWay(map, i, j- 1)) {     // left
                return true;
            }

            // no way to go
            map[i][j] = MAP_STATE_NO_WAY;
            return false;
        }

        return false;
    }

    private static void printMap(int[][] map) {
        System.out.println("Map info: ");
        for (int i = 0; i < map.length ; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[][] createMap(int row, int col) {
        int[][] map = new int[row][col];

        // horizontal walls
        for (int i = 0; i < col; i++) {
            map[0][i] = MAP_STATE_WALL;
            map[row-1][i] = MAP_STATE_WALL;
        }

        // vertical walls
        for (int i = 0; i < row; i++) {
            map[i][0] = MAP_STATE_WALL;
            map[i][col-1] = MAP_STATE_WALL;
        }

        // barrier
        map[3][1] = MAP_STATE_WALL;
        map[3][2] = MAP_STATE_WALL;

        return map;
    }
}
