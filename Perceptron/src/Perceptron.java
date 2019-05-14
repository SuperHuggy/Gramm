import java.io.*;
import java.util.Arrays;

public class Perceptron
{

    public static void main(String[] args)
    {
        Perceptron Perc = new Perceptron(3, new int[]{3, 3, 3});
        try (BufferedReader in = new BufferedReader(new FileReader("v9.csv"));
             FileWriter out = new FileWriter("out.txt"))
        {
            double[][] x = new double[178][];
            double[] t = new double[178];
            String line;
            int i = 0, k = 0;
            while ((line = in.readLine()) != null)
            {
                String[] buf = line.split(",");
                t[i] = Double.parseDouble(buf[0]) - 1;
                x[i++] = new double[]{Double.parseDouble(buf[1]) > 300 ? 1 : Double.parseDouble(buf[1]) / 150 - 1, Double.parseDouble(buf[2]) > 300 ? 1 : Double.parseDouble(buf[2]) / 150 - 1, Double.parseDouble(buf[2]) > 300 ? 1 : Double.parseDouble(buf[2]) / 150 - 1};
            }
            Perc.read();
            Perc.teach(x, t, 0.1);
            double s = 0;
            for (i = 0; i < x.length; i++)
            {
                double v = Perc.value(x[i]);
                v = (int) (v + 0.5);
                out.write(t[i] + "   " + v + "\n");
                s += Math.abs(v - t[i]);
            }
            System.out.println(s / 178);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static double a = 1, z = 0.01;

    private int min = 100;

    private Neuron[][] N;

    protected double sigm(double s)
    {
        return 1 / (1 + Math.exp(-a * s));
    }

    class Neuron
    {
        double del, val;
        double[] wt;

        Neuron(int n)
        {
            wt = new double[n];
            for (int i = 0; i < wt.length; i++)
                wt[i] = Math.random();
        }

        double value(double[] x)
        {
            double s = 0;
            for (int i = 0; i < x.length; i++)
                s += x[i] * wt[i];
            s = sigm(s);
            val = s;
            del = 0;
            return s;
        }
    }

    Perceptron(int in, int[] hidden)
    {
        N = new Neuron[hidden.length + 2][];
        //Формирование входного слоя
        N[0] = new Neuron[in];
        for (int i = 0; i < in; i++)
        {
            N[0][i] = new Neuron(1);
            N[0][i].wt[0] = 1;
        }
        //Формирование скрытых слоёв
        for (int i = 1; i <= hidden.length; i++)
        {
            N[i] = new Neuron[hidden[i - 1]];
            for (int j = 0; j < hidden[i - 1]; j++)
                N[i][j] = new Neuron(N[i - 1].length);
        }
        //Формирование выходного нейрона
        N[N.length - 1] = new Neuron[1];
        N[N.length - 1][0] = new Neuron(N[N.length - 2].length);
    }

    public double value(double[] x)
    {
        double[] a = new double[x.length], b;
        for (int i = 0; i < a.length; i++)
            a[i] = N[0][i].value(new double[]{x[i]});
        for (int i = 1; i < N.length - 1; i++)
        {
            b = new double[N[i].length];
            for (int j = 0; j < b.length; j++)
                b[j] = N[i][j].value(a);
            a = b;
        }
        return N[N.length - 1][0].value(a);
    }

    private void learn(double[] x, double t)
    {
        value(x);
        int n = N.length - 1;
        N[n][0].del = a * N[n][0].val * (1 - N[n][0].val) * (t - N[n][0].val);
        for (int i = 0; i < N[n - 1].length; i++)
            N[n - 1][i].del += N[n][0].wt[i] * N[n][0].del;
        for (int i = n - 1; i > 0; i--)
        {
            for (int j = 0; j < N[i].length; j++)
            {
                N[i][j].del *= a * N[i][j].val * (1 - N[i][j].val);
                for (int k = 0; k < N[i - 1].length; k++)
                    N[i - 1][k].del += N[i][j].wt[k] * N[i][j].del;
            }
        }
        for (int i = 1; i < N.length; i++)
            for (int j = 0; j < N[i].length; j++)
                for (int k = 0; k < N[i - 1].length; k++)
                    N[i][j].wt[k] += z * (N.length - i) * N[i][j].del * N[i - 1][k].val;
    }

    public void teach(double[][] x, double[] t, double err)
    {
        double error, error1 = 1, s = x.length;
        while (s / x.length > err)
        {
            error = 0;
            s = 0;
            for (int j = 0; j < x.length; j++)
            {
                learn(x[j], t[j]);
                double buf = Math.abs(value(x[j]) - t[j]);
                s += buf;
                if (buf > error)
                    error = buf;
            }
            System.out.print("\r \b");
            System.out.print(error + "  " + (error - error1) + "     " + s / x.length);
            if (min > (int) (s / x.length * 100 + 0.5))
            {
                min = (int) (s / x.length * 100 + 0.5);
                write();
            }
            error1 = error;
        }
    }

    public void write()
    {
        try (FileWriter out = new FileWriter("status.csv"))
        {
            out.write(min + "\n");
            for (int i = 0; i < N.length; i++)
            {
                for (int j = 0; j < N[i].length; j++)
                {
                    for (double buf : N[i][j].wt)
                        out.write(buf + ",");
                    out.write(";");
                }
                out.write("\n");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void read()
    {
        try (BufferedReader in = new BufferedReader(new FileReader("status.csv")))
        {
            min = Integer.parseInt(in.readLine());
            for (int i = 0; i < N.length; i++)
            {
                String[] buf = in.readLine().split(";");
                for (int j = 0; j < N[i].length; j++)
                {
                    String[] buf1 = buf[j].split(",");
                    for (int k = 0; k < N[i][j].wt.length; k++)
                        N[i][j].wt[k] = Double.parseDouble(buf1[k]);
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    interface deleg
    {
        double sigm(double s);
    }
}