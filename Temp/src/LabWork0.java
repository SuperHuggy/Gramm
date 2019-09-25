import java.io.*;
import java.text.DecimalFormat;

public class LabWork0
{
    public static void main(String[] args)
    {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" №  | слово                                                   |           |               Количество информации                    ");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("    |                                                         |           |  кол-во   | байт, размер |     бит,     |     бит,     ");
        System.out.println("    |                                                         | палиндром | символов  | в программе  |   по Хартли  |  по Шеннону  ");
        try (BufferedReader in = new BufferedReader(new FileReader("file0")))
        {
            String s;
            int i = 0, S = 0;
            double H = 0, Sh = 0;
            while ((s = in.readLine()) != null)
            {
                System.out.printf("%-4s", (i++));
                S += s.length();
                H += hartli(s);
                Sh += henon(s);
                System.out.print(formatedInformation(s));
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            }
            System.out.printf("%-4s%-58s%-12s%-12s%1s%14s%1s%14.2f%1s%14.9f%n", "", "|Итого", "|", "|" + S, "|", S * 2, "|", H, "|", Sh);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static String formatedInformation(String s)
    {
        int l = s.length();
        return String.format("%-58s%-12s%-12s%1s%14s%1s%14.2f%1s%14.9f%n", "|" + s, "|" + isPolindrom(s), "|" + l, "|", l * 2, "|", hartli(s), "|", henon(s));
    }

    private static double hartli(String s)
    {
        int alphPower = 0;
        short[] alph = new short[58];
        for (int i = 0; i < s.length(); i++)
        {
            char buf = s.charAt(i);
            alph[buf - (buf < 'а' ? ('a' - 32) : 'а')] = 1;
        }
        for (int i = 0; i < 58; i++)
            alphPower += alph[i];
        return s.length() * Math.log(alphPower) / Math.log(2);
    }

    private static double henon(String s)
    {
        int l = s.length();
        double res = 0;
        short[] alph = new short[58];
        for (int i = 0; i < s.length(); i++)
        {
            char buf = s.charAt(i);
            alph[buf - (buf < 'а' ? ('a' - 32) : 'а')]++;
        }
        for (int i = 0; i < 58; i++)
            res -= alph[i] == 0 ? 0 : (double) alph[i] / l * Math.log((double) alph[i] / l) / Math.log(2.0);
        return res;
    }

    private static char isPolindrom(String s)
    {
        int l = s.length();
        for (int i = 0; i < l / 2; i++)
            if (s.charAt(i) != s.charAt(l - i - 1))
                return '-';
        return '+';
    }

}
