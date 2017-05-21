import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by goncharov on 20/05/2017.
 */

public class Percolation {
  private int gridDimension;
  private int gridSize;
  private int gridVirtualTopIndex;
  private int gridVirtualBottomIndex;
  private boolean[] grid;
  private WeightedQuickUnionUF percolation;
  private WeightedQuickUnionUF fullPercolation;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    gridDimension = n;
    gridSize = gridDimension * gridDimension;

    gridVirtualTopIndex = gridSize;
    gridVirtualBottomIndex = gridVirtualTopIndex + 1;

    int arrSizeVirtualized = gridSize + 2;

    grid = new boolean[arrSizeVirtualized];
    percolation = new WeightedQuickUnionUF(arrSizeVirtualized);
    fullPercolation = new WeightedQuickUnionUF(arrSizeVirtualized);

    grid[gridVirtualTopIndex] = true;
    grid[gridVirtualBottomIndex] = true;

    for (int i = 0; i < gridDimension; i++) {
      percolation.union(gridVirtualTopIndex, index(1, i + 1));
      fullPercolation.union(gridVirtualTopIndex, index(1, i + 1));
      percolation.union(gridVirtualBottomIndex, index(n, i + 1));
    }
  }

  public void open(int row, int col) {
    int i = index(row, col);
    grid[i] = true;

    union(i, row-1, col);
    union(i, row+1, col);
    union(i, row, col-1);
    union(i, row, col+1);
  }

  public boolean isOpen(int row, int col) {
    int i = index(row, col);
    return grid[i];
  }

  public boolean isFull(int row, int col) {
    int i = index(row, col);
    return isOpen(row, col) && fullPercolation.connected(i, gridVirtualTopIndex);
  }

  public int numberOfOpenSites() {
    int openedSites = 0;
    for (int i = 0; i < gridSize; i++) {
      if (grid[i]) {
        openedSites += 1;
      }
    }
    return openedSites;
  }

  public boolean percolates() {
    if (gridDimension == 1) return isOpen(1, 1);
    return percolation.connected(gridVirtualTopIndex, gridVirtualBottomIndex);
  }

  public static void main(String[] args) {
  }

  private boolean isValid(int row, int col) {
    return row >= 1 && row <= gridDimension &&
            col >= 1 && col <= gridDimension;
  }

  private int index(int row, int col) {
    if (!isValid(row, col)) {
      throw new IndexOutOfBoundsException();
    }

    return (row-1) * gridDimension + (col-1);
  }

  private boolean union(int p, int qrow, int qcol) {
    if (isValid(qrow, qcol) && isOpen(qrow, qcol)) {
      percolation.union(p, index(qrow, qcol));
      fullPercolation.union(p, index(qrow, qcol));
      return true;
    }
    return false;
  }

}
