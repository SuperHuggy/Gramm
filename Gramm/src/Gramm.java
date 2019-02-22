import java.util.Arrays;

public class Gramm
{
    public static void main(String[] args)
    {
        double[][] arg =new double[][]{{5,4,3},{3,3,2},{8,1,3}};
        del Pr = (x,z) ->
        {
            double buf=0;
            for(int i=0;i<x.length;i++)
                buf+=x[i]*z[i];
            return buf;
        };
        double[][] res = MtGramma(arg,Pr);
        for(int i=0;i<res.length;i++)
            System.out.println(Arrays.toString(res[i]));
        System.out.println("Determinant = "+Determinant(res));
    }

    public static double[][] MtGramma(double[][] a,del Pr)
    {
        double[][] Mt=new double[a.length][a.length];
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a.length;j++)
                Mt[i][j]=Pr.ScProd(a[i],a[j]);
        return Mt;
    }

    private static double Determinant(double[][] A)
    {
        int n=A.length;
        double[] buf;
        double Buf=0,det=1;
        int max=0;
        for(int i=0;i<n;i++)
        {
            max=i;
            for(int j=i;j<n;j++)
            {
                if(A[j][i]>A[max][i])
                    max=j;
            }
            if(A[max][i]==0)
                return 0;
            buf=A[max];
            A[max]=A[i];
            A[i]=buf;
            det*=A[i][i];
            for(int j=n-1;j>=i;j--)
                A[i][j]/=A[i][i];
            for(int j=i+1;j<n;j++)
                for (int k = n - 1; k >= i; k--)
                    A[j][k] -= A[i][k] * A[j][i];
        }
        return det;
    }

    interface del
    {
        double ScProd(double[] a,double[] b);
    }
}

