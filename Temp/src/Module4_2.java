import java.util.Vector;

public class Module4_2
{
    public static void main(String[] args)
    {
        System.out.println(Rabin_Karp("asdmfasdghjzxcvasd", "asd", 2047).toString());
    }

    public static Vector<Integer> Rabin_Karp(String h, String n, long q)
    {
        Vector<Integer> Rabin_Karp = new Vector<>();
        if (n.length() > h.length())
            throw new IllegalArgumentException();
        long x = (long) (Math.random() * (q - 1)), X = 1, nHash = 0, hPartHash = 0;
        for (int i = n.length() - 1; i > 0; i--)
        {
            nHash += (n.charAt(i) * X) % q;
            hPartHash += (h.charAt(i) * X) % q;
            X *= x;
            X %= q;
        }
        hPartHash += (h.charAt(0) * X) % q;
        hPartHash %= q;
        nHash += (n.charAt(0) * X) % q;
        nHash %= q;
        X %= q;
        int end = h.length() - n.length();
        if (hPartHash == nHash)
            Rabin_Karp.add(0);
        for (int i = 0; i < end; i++)
        {
            hPartHash = ((((hPartHash - (X * h.charAt(i)) % q + q) % q) * x) % q + h.charAt(i + n.length())) % q;
            if (hPartHash == nHash)
                Rabin_Karp.add(i + 1);
        }
        return Rabin_Karp;
    }
}
