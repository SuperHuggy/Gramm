
public class Main
{
    public static void main(String[] args)
    {
        BinTree dub = new BinTree(13);
        double[] arr = new double[]{7, 4, 6, 14, 1, 10, 3, 8};
        for (double a : arr)
            dub.Add(a);
        StringBuilder[] buf;
        System.out.println(dub.root.deepness);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
        System.out.println();
        System.out.println();
        dub.Remove(13);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
    }
}
