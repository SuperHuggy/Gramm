import java.util.Scanner;

class Point
{
    private double[] x;
    private int n;

    Point(int n)
    {
        this.n = n;
        x = new double[n];
        Read();
    }

    Point(int n, double[] x)
    {
        this.n = n;
        this.x = x;
    }

    public void SetC(double[] x)
    {
        this.x = x;
    }

    public void SetCAt(double x, int i)
    {
        this.x[i] = x;
    }

    public int GetDim()
    {
        return n;
    }

    public double[] GetC()
    {
        return x;
    }

    public double GetCAt(int i)
    {
        return x[i];
    }

    public double Abs()
    {
        double s = 0;
        for (double buf : x) s += buf * buf;
        return Math.sqrt(s);
    }

    public static Point Add(Point a, Point b)
    {
        double[] X = new double[Math.max(a.n, b.n)];
        for (int i = 0; i < a.n; i++) X[i] = a.GetC()[i] + b.GetC()[i];
        a.SetC(X);
        return a;
    }

    public void Add(Point a)
    {
        for (int i = 0; i < a.n; i++) x[i] += a.GetC()[i];
    }

    public static Point Sub(Point a, Point b)
    {
        double[] X = new double[Math.max(a.n, b.n)];
        for (int i = 0; i < a.n; i++) X[i] = a.GetC()[i] - b.GetC()[i];
        return new Point2(X);
    }

    public void Sub(Point a)
    {
        for (int i = 0; i < a.n; i++) x[i] -= a.GetC()[i];
    }

    public static Point mult(Point a, double b)
    {
        double[] X = new double[a.n];
        for (int i = 0; i < a.n; i++) X[i] = a.GetC()[i] * b;
        a.SetC(X);
        return a;
    }

    public void mult(double a)
    {
        for (int i = 0; i < this.n; i++) x[i] *= a;
    }

    public static double mult(Point a, Point b)
    {
        double s = 0;

        for (int i = 0; i < a.n; i++) s += a.GetC()[i] * b.GetC()[i];
        return s;
    }

    public void mult(Point a)
    {
        for (int i = 0; i < a.n; i++) x[i] *= a.GetC()[i];
    }

    public String toString()
    {
        String s = "(" + x[0];
        for (int i = 1; i < n; i++) s += ";" + x[i];
        return s + ")";
    }

    public void SymAxis(int N)
    {
        for (int i = 0; i < n; i++) x[i] *= -1;
        x[N] *= -1;
    }

    public void Read()
    {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++) x[i] = in.nextDouble();
    }
}

public class Point2 extends Point
{
    Point2(double x, double y)
    {
        super(2, new double[]{x, y});
    }

    Point2(double[] x)
    {
        super(2, x);
    }

    Point2()
    {
        super(2);
    }

    public void Turn(double alpha, Point2 b)
    {
        Point2 a = new Point2(0, 0);
        a.SetCAt(Sub(this, b).GetCAt(0) * Math.cos(alpha) - Sub(this, b).GetCAt(1) * Math.sin(alpha), 0);
        a.SetCAt(Sub(this, b).GetCAt(0) * Math.sin(alpha) + Sub(this, b).GetCAt(1) * Math.cos(alpha), 1);
        Add(a, b);
    }
}