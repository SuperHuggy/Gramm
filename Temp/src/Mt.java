import Jama.Matrix;

import java.util.Arrays;
import java.util.Scanner;

public class Mt
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите номер задания");
        switch (in.nextInt())
        {
            case 2:
            {
                int l = 2;
                delegatable[] arr1 = new delegatable[]{x -> x};
                System.out.println("Введите количество точек");
                int h = in.nextInt();
                double[] y = new double[h];
                System.out.println("Введите значения функции в точках");
                for (int i = 0; i < h; i++)
                    y[i] = in.nextDouble();
                double[][] x = new double[l][h];
                for (int i = 0; i < h; i++)
                    x[0][i] = 1;
                for (int j = 0; j < h; j++)
                    x[1][j] = j;
                double[] res = Test(Prepearing(x, arr1), y);
                System.out.println(Arrays.toString(res));
                System.out.println("Погрешность = " + Error(Prepearing(x, arr1), y, res));
                break;
            }
            case 1:
            {
                int l = 4;
                delegatable[] arr1 = new delegatable[]{
                        x -> Math.log(x * x),
                        Math::log,
                        Math::sin};
                System.out.println("Введите количество точек");
                int h = in.nextInt();
                double[] y = new double[h];
                System.out.println("Введите значения функции в точках");
                for (int i = 0; i < h; i++)
                    y[i] = in.nextDouble();
                double[][] x = new double[l][h];
                for (int i = 0; i < h; i++)
                    x[0][i] = 1;
                for (int i = 1; i < l; i++)
                {

                    System.out.println("Введите " + i + " вектор аргументов");
                    for (int j = 0; j < h; j++)
                        x[i][j] = in.nextDouble();
                }
                double[] res = Test(Prepearing(x, arr1), y);
                System.out.println(Arrays.toString(res));
                System.out.println("Погрешность = " + Error(Prepearing(x, arr1), y, res));
                break;
            }
        }
    }

    public static double[][] Prepearing(double[][] x, delegatable[] arr1)
    {
        double[][] nx = new double[x.length + 1][x[0].length];
        for (int i = 0; i < x[0].length; i++)
            nx[0][i] = 1;
        for (int i = 1; i <= arr1.length; i++)
        {
            for (int j = 0; j < x[i].length; j++)
                nx[i][j] = arr1[i - 1].fun(x[i][j]);
        }
        return nx;
    }

    interface delegatable
    {
        double fun(double x);
    }

    public static double[] Test(double[][] xx, double[] yy)
    {
        Matrix X = new Matrix(xx);
        Matrix Y = new Matrix(yy, yy.length);
        return X.times(X.transpose()).inverse().times(X).times(Y).transpose().getArray()[0];
    }

    public static double Error(double[][] xx, double[] yy, double[] zz)
    {
        double Sum = 0, buf;
        for (int i = 0; i < xx[0].length; i++)
        {
            buf = 0;
            for (int j = 0; j < xx.length; j++)
                buf += xx[j][i] * zz[j];
            Sum += (buf - yy[i]) * (buf - yy[i]);
        }
        return Sum;
    }

}
