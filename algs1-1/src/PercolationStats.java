import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by goncharov on 20/05/2017.
 */

public class PercolationStats {
  private double[] result;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.result = new double[trials];

    for (int t = 0; t < trials; t++) {
      Percolation p = new Percolation(n);
      int openedCount = 0;
      while (!p.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        if (!p.isOpen(row, col)) {
          p.open(row, col);
          openedCount += 1;
        }
      }
      result[t] = ((double) openedCount) / (n * n);
    }
  }

  public double mean() {
    return StdStats.mean(result);
  }

  public double stddev() {
    return StdStats.stddev(result);
  }

  public double confidenceLo() {
    return mean()-(1.96 * stddev() / Math.sqrt(result.length));
  }

  public double confidenceHi() {
    return mean()+(1.96 * stddev() / Math.sqrt(result.length));
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats s = new PercolationStats(n, trials);

    StdOut.println(String.format("mean = %s", s.mean()));
    StdOut.println(String.format("standard deviation = %s", s.stddev()));
    StdOut.println(String.format("95%% confidence interval = [%s, %s]", s.confidenceLo(), s.confidenceHi()));
  }
}
