
public class Main
{
    public static void main(String[] args)
    {
        StringBuilder[] buf;
        BinTree dub = new BinTree(13);
        double[] arr = new double[]{1, 7, 4, 6, 14, 2, 5, 9, 10, 11, 12, 15, 16, 8};
        for (double a : arr)
            dub.Add(a);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
        System.out.println();
        System.out.println();
        for (double a : new double[]{14, 2, 5, 9, 10})
            dub.Remove(a);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
    }
}
