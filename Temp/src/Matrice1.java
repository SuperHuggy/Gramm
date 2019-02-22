import Jama.Matrix;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;


public class Matrice1
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        //В скобках после b указываеся номер коэффицента
        //Номер вектора X учавствующего в выражении умножаемом на b указывать не нужно, он соответствует номеру b
        System.out.println("Введите правую часть уравнения(Пример: b(0) + b(1)*X + b(2)*X + b(3)*X + b(4)*X)");
        String[] s = in.nextLine().replaceAll(" ", "").split("\\+b");
        int l = s.length;
        Argument[] ar = new Argument[l];
        for (int i = 0; i < l; i++)
            ar[i] = new Argument(s[i]);
        System.out.println("Введите количество известных точек");
        int h = in.nextInt();
        double[] y = new double[h];
        System.out.println("Введите значения функции в точках");
        for (int i = 0; i < h; i++)
            y[i] = in.nextDouble();
        double[][] x = new double[l][h];
        for (int i = 0; i < l; i++)
        {
            if (ar[i].s.equals(""))
            {
                for (int j = 0; j < h; j++)
                    x[i][j] = 1;
                continue;
            }
            System.out.println("Введите " + i + " вектор аргументов");
            for (int j = 0; j < h; j++)
                x[i][j] = Parsing(ar[i].s, in.nextDouble());
        }
        double[] res = FastTest(x, y);
        System.out.print("Y = " + res[0] + ar[0].s);
        for (int i = 1; i < l; i++)
            System.out.print(" + " + new DecimalFormat("#0.00").format(res[i]) + "*" + ar[i].s + "{X" + ar[i].number + "}");
        System.out.println();
        System.out.println("Более точное представление коэффицентов");
        System.out.println(Arrays.toString(FastTest(x, y)));
        System.out.println("Погрешность = " + Error(x, y, FastTest(x, y)));
    }

    private static class Argument
    {
        int number;
        String s;

        Argument(String s)
        {
            number = Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf(')')));
            this.s = s.substring(s.indexOf(')') + 1);
            this.s = this.s.replace("*", "");
        }
    }

    //Возвращает коэффиценты линейной функции
    //Название сохранено для поддержания приемственности
    public static double[] Test(double[][] xx, double[] yy)
    {
        Matrix X = new Matrix(xx);
        Matrix Y = new Matrix(yy, yy.length);
        return X.times(X.transpose()).inverse().times(X).times(Y).transpose().getArray()[0];
    }

    public static double[] FastTest(double[][] xx, double[] yy)
    {
        Matrix X = new Matrix(xx).transpose();
        Matrix Y = new Matrix(yy, yy.length);
        return MetodGausa(X.transpose().times(X).getArray(), X.transpose().times(Y).transpose().getArray()[0]);
    }

    //При недостаточной универсатьности можно добавить функции
    private static double Parsing(String s, double x)
    {
        if (s.contains("sin"))
        {
            int n = s.indexOf("sin"), end = EndIndex(s, n + 3);
            return Parsing(s.substring(0, n) + Math.sin(Parsing(s.substring(n + 4, end), x)) + s.substring(end + 1), x);
        }
        if (s.contains("cos"))
        {
            int n = s.indexOf("cos"), end = EndIndex(s, n + 3);
            return Parsing(s.substring(0, n) + Math.log(Parsing(s.substring(n + 4, end), x)) + s.substring(end + 1), x);
        }
        if (s.contains("ln"))
        {
            int n = s.indexOf("ln"), end = EndIndex(s, n + 2);
            return Parsing(s.substring(0, n) + Math.log(Parsing(s.substring(n + 3, end), x)) + s.substring(end + 1), x);
        }
        if (s.contains("("))
            return Parsing(s.substring(0, s.indexOf("(")) + Parsing(s.substring(s.indexOf("(") + 1, EndIndex(s, s.indexOf("("))), x) + s.substring(EndIndex(s, s.indexOf("(")) + 1), x);
        if (s.contains("+"))
            return Parsing(s.substring(0, s.indexOf("+")), x) + Parsing(s.substring(s.indexOf("+") + 1), x);
        if (s.contains("-"))
            return Parsing(s.substring(0, s.indexOf("-")), x) - Parsing(s.substring(s.indexOf("-") + 1), x);
        if (s.contains("*"))
            return Parsing(s.substring(0, s.indexOf("*")), x) * Parsing(s.substring(s.indexOf("*") + 1), x);
        if (s.contains("/"))
            return Parsing(s.substring(0, s.indexOf("/")), x) / Parsing(s.substring(s.indexOf("/") + 1), x);
        if (s.contains("^"))
            return Math.pow(Parsing(s.substring(0, s.indexOf("^")), x), Parsing(s.substring(s.indexOf("^") + 1), x));
        if (s.contains("X"))
            return x;
        return Double.parseDouble(s);
    }

    private static int EndIndex(String s, int StInd)
    {
        int k = 1;
        for (int i = StInd + 1; i < s.length(); i++)
        {
            if (s.charAt(i) == '(')
                k++;
            else if (s.charAt(i) == ')')
                k--;
            if (k == 0)
                return i;
        }
        return s.length();
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

    private static double[] MetodGausa(double[][] A, double[] B)
    {
        int n = B.length;
        double[] buf, X = new double[n];
        double Buf;
        int max;
        for (int i = 0; i < n; i++)
        {
            max = i;
            for (int j = i; j < n; j++)
            {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i]))
                    max = j;
            }
            buf = A[max];
            A[max] = A[i];
            A[i] = buf;
            Buf = B[max];
            B[max] = B[i];
            B[i] = Buf;
            B[i] /= A[i][i];
            for (int j = n - 1; j >= i; j--)
                A[i][j] /= A[i][i];
            for (int j = i + 1; j < n; j++)
            {
                B[j] -= B[i] * A[j][i];
                for (int k = n - 1; k >= i; k--)
                    A[j][k] -= A[i][k] * A[j][i];
            }
        }
        for (int i = n - 1; i >= 0; i--)
        {
            Buf = B[i];
            for (int j = 0; j < n; j++)
                Buf -= X[j] * A[i][j];
            X[i] = Buf / A[i][i];
        }
        return X;
    }

}