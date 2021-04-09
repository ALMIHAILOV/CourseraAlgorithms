package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] state;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF full;
    private final int side;
    private final int vTop;
    private final int vBottom;
    private int openSites;

    public Percolation(int n) {
        int gridSquared;
        if (n <= 0) throw new IllegalArgumentException("N must be > 0");
        side = n;
        gridSquared = n * n;
        state = new boolean[side][side];
        grid = new WeightedQuickUnionUF(gridSquared + 2);
        full = new WeightedQuickUnionUF(gridSquared + 1);
        vBottom = gridSquared + 1;
        vTop = gridSquared;
        openSites = 0;
    }

    public void open(int row, int col) {
        isSiteNotValid(row, col);

        int shiftRow = row - 1;
        int shiftCol = col - 1;
        int index = iGrid(row, col) - 1;

        if (isOpen(row, col)) {
            return;
        }

        state[shiftRow][shiftCol] = true;
        openSites++;

        if (row == 1) {
            grid.union(vTop, index);
            full.union(vTop, index);
        }

        if (row == side) {
            grid.union(vBottom, index);
        }

        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            union(index, row, col -1);
        }

        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            union(index, row, col +1);
        }

        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            union(index, row -1, col);
        }

        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            union(index, row +1, col);
        }
    }

    private void union(int index, int iRow, int iCol) {
        grid.union(index, iGrid(iRow, iCol) - 1);
        full.union(index, iGrid(iRow, iCol) - 1);
    }

    public boolean isOpen(int row, int col) {
        isSiteNotValid(row, col);
        return state[row - 1][col - 1];

    }

    public boolean isFull(int row, int col) {
        isSiteNotValid(row, col);
        return full.find(vTop) == full.find(iGrid(row, col) - 1);
    }

    private int iGrid(int row, int col) {
        return side * (row - 1) + col;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
       return grid.find(vTop) == grid.find(vBottom);
    }

    private void isSiteNotValid(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException("Exception");
        }
    }

    private boolean isOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < side && shiftCol < side);
    }
}
