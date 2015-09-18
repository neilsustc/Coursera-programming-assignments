import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();

        double[] thresholds = new double[T];
        for (int i = 0; i < T; i++)
        {
            Percolation perc = new Percolation(N);
            double opened = 0;
            while (!perc.percolates())
            {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);
                if (!perc.isOpen(x, y))
                {
                    perc.open(x, y);
                    opened++;
                }
            }
            thresholds[i] = opened / (N * N);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(T);
    }

    public double mean() // sample mean of percolation threshold
    {
        return mean;
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return stddev;
    }

    public double confidenceLo() // low endpoint of 95% confidence interval
    {
        return confidenceLo;
    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        return confidenceHi;
    }

    public static void main(String[] args)
    {
        PercolationStats percStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = "
                + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}
