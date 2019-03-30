import java.util.Vector;

public class Module4_2
{
    public static void main(String[] args)
    {
        System.out.println(Rabin_Karp("masdfasdghjzxcv", "asd", 2047).toString());
    }

    public static Vector<Integer> Rabin_Karp(String h, String n, long q)
    {
        Vector<Integer> Rabin_Karp = new Vector<>();
        if (n.length() > h.length())
            throw new IllegalArgumentException();
        long x = (long) (Math.random() * (q - 1)), X = 1, nHash = 0, hPartHash = 0;
        for (int i = n.length() - 1; i > 0; i--)
        {
            nHash += n.charAt(i) * X;
            hPartHash += h.charAt(i) * X;
            X *= x;
        }
        hPartHash += h.charAt(0) * X;
        nHash += n.charAt(0) * X;
        int end = h.length() - n.length(), i = 0;
        if (hPartHash == nHash)
            Rabin_Karp.add(0);
        for (; i < end; i++)
        {
            hPartHash = ((hPartHash - X * h.charAt(i)) * x + h.charAt(i + n.length()));
            if (hPartHash == nHash)
                Rabin_Karp.add(i + 1);
        }
        return Rabin_Karp;
    }
}
