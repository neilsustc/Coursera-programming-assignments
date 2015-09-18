import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private int N;
    private boolean[] sites;
    private WeightedQuickUnionUF wquf;
    private WeightedQuickUnionUF wqufAvoidBackwash;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0)
            throw new java.lang.IllegalArgumentException();

        this.N = N;
        sites = new boolean[N * N];
        wquf = new WeightedQuickUnionUF(N * N + 2);
        wqufAvoidBackwash = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 0; i < sites.length; i++)
        {
            sites[i] = false;
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j)
    {
        validate(i, j);
        if (isOpen(i, j))
        {
            return;
        }
        sites[ijTrans(i, j)] = true;
        tryUnion(ijTrans(i, j), i - 1, j);
        tryUnion(ijTrans(i, j), i + 1, j);
        tryUnion(ijTrans(i, j), i, j - 1);
        tryUnion(ijTrans(i, j), i, j + 1);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        validate(i, j);
        return sites[ijTrans(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        validate(i, j);
        return wqufAvoidBackwash.connected(ijTrans(i, j), N * N) && isOpen(i, j);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return wquf.connected(N * N, N * N + 1);
    }

    // ========================================================================
    private int ijTrans(int i, int j)
    {
        return N * (i - 1) + j - 1;
    }

    private void validate(int i, int j)
    {
        if (i < 1 || i > N || j < 1 || j > N)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    private void tryUnion(int self, int i, int j)
    {
        if (i > 0 && i < N + 1 && j > 0 && j < N + 1)
        {
            if (isOpen(i, j))
            {
                wquf.union(self, ijTrans(i, j));
                wqufAvoidBackwash.union(self, ijTrans(i, j));
            }
        }

        // virtual site connection
        if (i == 0)
        {
            wquf.union(N * N, self);
            wqufAvoidBackwash.union(N * N, self);
        }
        if (i == N + 1)
        {
            wquf.union(N * N + 1, self);
        }
    }
}
