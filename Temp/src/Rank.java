public class Rank
{
    public static void main(String[] args)
    {
        System.out.println(MinorsRank(new double[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 0}}));
        //System.out.println(MinorsRank(new double[][]{{24,19,36,72,-38},{49,40,73,147,-80},{73,59,98,219,-118},{47,36,71,141,-72}}));
    }

    public static int MinorsRank(double[][] A)
    {
        int n = A.length <= A[0].length ? A.length : A[0].length, i = 0;
        mark:
        while (i < n)
        {
            for (int j = i; j < A[0].length; j++)
            {
                for (int k = i; k < A.length; k++)
                {
                    if (Math.abs(det(A, i + 1)) >= 0.0000000001)
                    {
                        i++;
                        continue mark;
                    }
                    swap(A[i], A[k]);
                }
                swap(A, i, j);
            }
            return i;
        }
        return i;
    }

    private static void swap(double[] a, double[] b)
    {
        double[] buf = a;
        a = b;
        b = buf;
    }

    private static void swap(double[][] A, int a, int b)
    {
        double buf;
        for (int i = 0; i < A.length; i++)
        {
            buf = A[i][a];
            A[i][a] = A[i][b];
            A[i][b] = buf;
        }
    }

    private static double det(double[][] a, int n)
    {
        double[][] A = clone(a, n);
        double[] buf;
        double det = 1;
        int max;
        for (int i = 0; i < n; i++)
        {
            max = i;
            for (int j = i; j < n; j++)
            {
                if (A[j][i] > A[max][i])
                    max = j;
            }
            buf = A[max];
            A[max] = A[i];
            A[i] = buf;
            det *= A[i][i] * (i == max ? 1 : -1);
            for (int j = n - 1; j >= i; j--)
                A[i][j] /= A[i][i];
            for (int j = i + 1; j < n; j++)
            {
                for (int k = n - 1; k >= i; k--)
                    A[j][k] -= A[i][k] * A[j][i];
            }
        }
        return det;
    }

    private static double[][] clone(double[][] a, int n)
    {
        if (a.length < n)
            return null;
        double[][] A = new double[n][n];
        try
        {
            for (int i = 0; i < n; i++)
                System.arraycopy(a[i], 0, A[i], 0, n);
        } catch (Exception e)
        {
            return null;
        }
        return A;
    }
}
