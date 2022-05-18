package hw4;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Junjie Chen 10476718
 **/
public class Maze implements GridColors {

    /**
     * Four possible directions
     */
    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * The maze
     */
    private final TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /**
     * Check whether given position is valid
     *
     * @param x    The x-coordinate of current point
     * @param y    The y-coordinate of current point
     * @param rows Number of rows
     * @param cols Number of cols
     * @return If the given position is out of bound, true;
     * otherwise, false
     */
    private static boolean isInvalidPosition(int x, int y, int rows, int cols) {
        return x < 0 || x >= rows || y < 0 || y >= cols;
    }

    /**
     * Wrapper method.
     */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     *
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     * otherwise, false
     * @pre Possible path cells are in BACKGROUND color;
     * barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     * PATH color; all cells that were visited but are
     * not on the path are in the TEMPORARY color.
     */
    public boolean findMazePath(int x, int y) {
        // COMPLETE HERE FOR PROBLEM 1
        int rows = maze.getNRows();
        int cols = maze.getNCols();
        if (isInvalidPosition(x, y, rows, cols) || maze.getColor(x, y) != NON_BACKGROUND) {
            return false;
        }
        if (x == rows - 1 && y == cols - 1) {
            maze.recolor(x, y, PATH);
            return true;
        }
        maze.recolor(x, y, TEMPORARY);
        for (int[] d : DIRECTIONS) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (findMazePath(nx, ny)) {
                maze.recolor(x, y, PATH);
                return true;
            }
        }
        return false;
    }

    // ADD METHOD FOR PROBLEM 2 HERE

    /**
     * Attempts to find all possible path through point (x,y)
     *
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return A list of all the solutions to the maze;
     * each solution may be represented as a list of coordinates;
     * if such path doesn't exist, an empty list will be returned
     */
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathStackBased(x, y, result, trace);
        return result;
    }

    /**
     * Helper function of finding all possible path
     *
     * @param x      The x-coordinate of current point
     * @param y      The y-coordinate of current point
     * @param result the list of successful paths recorded up to now
     * @param trace  the trace of the current path being explored
     */
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
        int rows = maze.getNRows();
        int cols = maze.getNCols();
        if (isInvalidPosition(x, y, rows, cols) || maze.getColor(x, y) != NON_BACKGROUND) {
            return;
        }
        if (x == rows - 1 && y == cols - 1) {
            trace.push(new PairInt(x, y));
            result.add(new ArrayList<>(trace));
            trace.pop();
            return;
        }
        maze.recolor(x, y, PATH);
        trace.push(new PairInt(x, y));
        for (int[] d : DIRECTIONS) {
            int nx = x + d[0];
            int ny = y + d[1];
            findMazePathStackBased(nx, ny, result, trace);
        }
        trace.pop();
        maze.recolor(x, y, NON_BACKGROUND);
    }

    // ADD METHOD FOR PROBLEM 3 HERE

    /**
     * Attempts to find the shortest path through point (x,y)
     *
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return a list of coordinates of the shortest path;
     * if such path doesn't exist, an empty list will be returned
     */
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        ArrayList<PairInt> shortest = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathMinHelper(x, y, shortest, trace);
        return shortest;
    }

    /**
     * Helper function of finding the shortest path
     *
     * @param x        The x-coordinate of current point
     * @param y        The y-coordinate of current point
     * @param shortest the shortest path
     * @param trace    the trace of the current path being explored
     */
    public void findMazePathMinHelper(int x, int y, ArrayList<PairInt> shortest, Stack<PairInt> trace) {
        int rows = maze.getNRows();
        int cols = maze.getNCols();
        if (isInvalidPosition(x, y, rows, cols) || maze.getColor(x, y) != NON_BACKGROUND) {
            return;
        }
        if (x == rows - 1 && y == cols - 1) {
            trace.push(new PairInt(x, y));
            if (shortest.isEmpty() || shortest.size() > trace.size()) {
                shortest.clear();
                shortest.addAll(trace);
            }
            trace.pop();
            return;
        }
        maze.recolor(x, y, PATH);
        trace.push(new PairInt(x, y));
        for (int[] d : DIRECTIONS) {
            int nx = x + d[0];
            int ny = y + d[1];
            findMazePathMinHelper(nx, ny, shortest, trace);
        }
        trace.pop();
        maze.recolor(x, y, NON_BACKGROUND);
    }


    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
