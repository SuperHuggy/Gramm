import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.Math.*;

public class LabWork1
{
    public static void main(String[] args)
    {
        System.out.println("Задание 2");
        Delegatable[] test = new Delegatable[]{x -> 2 * sin(x) + 1, x -> (x / Math.PI - 1) * (x / Math.PI - 1), x -> -(x / Math.PI) * (x / Math.PI) - 2 * x + 5 * Math.PI, x -> Math.cos(x) * Math.cos(x) / 2 + 1};
        int sum = 0;
        for (int i = 0; i < 4; i++)
        {
            sum += numberOfnegative(tabulation(-2 * PI, 2 * PI, PI / 6, test[i]));
            System.out.println(numberOfnegative(tabulation(-2 * PI, 2 * PI, PI / 6, test[i])));
        }
        System.out.println("Сумма: " + sum);
        int n = 1000;
        double[] x = new double[n];
        double min = Double.MAX_VALUE, max = 0;
        for (int i = 0; i < n; i++)
        {
            x[i] = Math.random() * 10 - 10;
            min = Double.min(min, Double.min(test[0].f(x[i]), Double.min(test[1].f(x[i]), Double.min(test[2].f(x[i]), test[3].f(x[i])))));
            max = Double.max(max, Double.max(test[0].f(x[i]), Double.max(test[1].f(x[i]), Double.max(test[2].f(x[i]), test[3].f(x[i])))));
        }
        System.out.println("Min " + min);
        System.out.println("Max " + max);
        System.out.println("Задание 4");
        System.out.println(bisectionMetod(0, PI, 0.0001, a -> a * sin(a) - 0.5));
        System.out.println(bisectionMetod(0, 0.9, 0.0001, a -> log(a * a - 3 * a + 2)));
        System.out.println(bisectionMetod(2.1, 5, 0.0001, a -> log(a * a - 3 * a + 2)));
        System.out.println(bisectionMetod(PI, 2 * PI, 0.0001, a -> tan(2.0 / 3 * (a + PI / 4)) / 2 - 1));
        System.out.println("Задание 5");
        System.out.println(integral(-PI, PI, test[0], n));
        System.out.println(integral(-PI, PI, test[2], n));
        System.out.println(integral(-PI, PI, test[3], n));
        System.out.println("Задание 6");
        try (BufferedReader in = new BufferedReader(new FileReader("file1")))
        {
            ArrayList<String> s = new ArrayList<>();
            String buf;
            while ((buf = in.readLine()) != null)
                s.add(buf);
            System.out.println(numberOfEntrance(s, a -> a.length() == 5));
            System.out.println(numberOfEntrance(s, LabWork1::isPolindrom));
            ArrayList<String> res = allEntrance(s, a -> a.charAt(0) == 'W');
            System.out.println(res);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isPolindrom(String s)
    {
        s = s.toLowerCase();
        int l = s.length();
        for (int i = 0; i < l / 2; i++)
            if (s.charAt(i) != s.charAt(l - i - 1))
                return false;
        return true;
    }

    public static int numberOfEntrance(ArrayList<String> s, Predicate<String> F)
    {
        int res = 0;
        for (String buf1 : s)
            for (String buf2 : buf1.split("\\W+"))
                if (F.test(buf2))
                    res++;
        return res;
    }

    public static ArrayList<String> allEntrance(ArrayList<String> s, Predicate<String> F)
    {
        ArrayList<String> res = new ArrayList<>();
        for (String buf1 : s)
            for (String buf2 : buf1.split("\\W+"))
                if (F.test(buf2))
                    res.add(buf2);
        return res;
    }

    public static double[] tabulation(double start, double end, double dx, Delegatable F)
    {
        double[] res = new double[(int) ((end - start) / dx)];
        for (int i = 0; start < end; start += dx, i++)
            res[i] = F.f(start);
        return res;
    }

    public static double bisectionMetod(double start, double end, double e, Delegatable F)
    {
        if (Math.abs(F.f(start)) < e)
            return start;
        if (Math.abs(F.f(end)) < e)
            return end;
        return signum(F.f(end - (end - start) / 2)) != signum(F.f(start)) ? bisectionMetod(start, end - (end - start) / 2, e, F) : bisectionMetod(end - (end - start) / 2, end, e, F);
    }

    public static double integral(double start, double end, Delegatable F, double n)
    {
        double dx = (end - start) / n, s = 0;
        for (int i = 0; i < n; i++, start += dx)
            s += F.f((start + dx) / 2) * dx;
        return s;
    }

    public static int numberOfnegative(double[] a)
    {
        int k = 0;
        for (double buf : a)
            if (buf < 0)
                k++;
        return k;
    }

    interface Delegatable
    {
        double f(double arg);
    }
}
