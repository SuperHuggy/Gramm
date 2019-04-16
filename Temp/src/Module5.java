import java.util.*;
import java.util.concurrent.*;

public class Module5
{

    public static void main(String[] args)
    {
        int[][] buf = KMP("asdfghjklzxcvbnm,qwertyuioasdfghjkzxcvbnasdfghqwertyuqwertyqwertyuasdfghzxcvbnasdfghqwertysdfgzxcvb", new String[]{"asd", "zxc", "qwe", "a", "dfg"});
        for (int[] ints : buf) System.out.println(Arrays.toString(ints));
    }

    public static int[][] KMP(String h, String[] n)
    {
        int[][] result = new int[n.length][];
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() > n.length ? n.length : Runtime.getRuntime().availableProcessors());
        Future<ArrayList<Integer>>[] buf = new Future[n.length];
        for (int i = 0; i < n.length; i++)
            buf[i] = exec.submit(new MultyKMP(h, n[i]));
        int i = 0;
        exec.shutdown();
        for (Future<ArrayList<Integer>> arr : buf)
        {
            try
            {
                ArrayList<Integer> temp = arr.get();
                int[] index = new int[temp.size()];
                int j = 0;
                for (Integer ints : temp)
                    index[j++] = ints;
                result[i++] = index;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

}

class MultyKMP implements Callable<ArrayList<Integer>>
{
    MultyKMP(String haystack, String needle)
    {
        h = haystack;
        n = needle;
    }

    private String h, n;

    @Override
    public ArrayList<Integer> call()
    {
        return KMP(h, n);
    }

    private static ArrayList<Integer> KMP(String h, String n)
    {
        ArrayList<Integer> index = new ArrayList<>();
        int[] prefs = pref(n);
        int j = 0;
        for (int i = 0; i < h.length(); i++)
        {
            if (h.charAt(i) == n.charAt(j))
            {
                j++;
                if (j == n.length())
                {
                    index.add(i - j + 1);
                    j = 0;
                }
            } else
            {
                i += j;
                j = prefs[j];
            }
        }
        return index;
    }

    private static int[] pref(String s)
    {
        int[] pref = new int[s.length()];
        int k = 0;
        for (int i = 1; i < s.length(); i++)
        {
            if (s.charAt(k) == s.charAt(i))
                pref[i] = ++k;
            else
            {
                k = 0;
                pref[i] = 0;
            }
        }
        return pref;
    }
}
