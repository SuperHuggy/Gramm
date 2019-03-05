import java.util.Arrays;

public class BinTree<T>
{
    TreeElement root;
    int maxlen = 0;
    private StringBuilder buf, space;


    class TreeElement
    {
        TreeElement p = null, ch1 = null, ch2 = null;
        double key;
        T value;
        int deepness = 0, len;

        TreeElement(double key)
        {
            this.key = key;
            this.value = null;
            len = Double.toString(key).length();
            if (len > maxlen)
                maxlen = len;
        }

        TreeElement(double key, T value)
        {
            this.key = key;
            this.value = value;
            len = Double.toString(key).length();
            if (len > maxlen)
                maxlen = len;
        }
    }

    public static void main(String[] args)
    {
        BinTree dub = new BinTree(13);
        double[] arr = new double[]{7, 4, 6, 14, 1, 10, 3, 8};
        StringBuilder[] buf;
        System.out.println(dub.root.deepness);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
        dub.Remove(13);
        buf = dub.Print();
        for (int j = 0; j < buf.length; j++)
            System.out.println(buf[j].toString());
    }

    BinTree(double key)
    {
        root = new TreeElement(key);
    }

    BinTree()
    {
        root = null;
    }

    public boolean Add(double key)
    {
        if (Search(key))
            return false;
        RecAdd(new TreeElement(key), root);
        return true;
    }

    private void RecAdd(TreeElement nw, TreeElement a)
    {
        int buf = 0;
        if (nw.key < a.key)
        {
            if (a.ch1 != null)
            {
                RecAdd(nw, a.ch1);
                if (a.ch2 == null || a.ch1.deepness > a.ch2.deepness)
                    a.deepness = a.ch1.deepness + 1;
                Balancing(a);
            } else
            {
                a.ch1 = nw;
                nw.p = a;
                if (a.ch2 == null)
                    a.deepness += nw.deepness + 1;
            }
        } else if (nw.key > a.key)
        {
            if (a.ch2 != null)
            {
                RecAdd(nw, a.ch2);
                if (a.ch1 == null || a.ch2.deepness > a.ch1.deepness)
                    a.deepness = a.ch2.deepness + 1;
                Balancing(a);
            } else
            {
                a.ch2 = nw;
                nw.p = a;
                if (a.ch1 == null)
                    a.deepness += nw.deepness + 1;
            }
        }
    }

    public boolean Search(double key)
    {
        if (root != null)
            return RecSearch(key, root);
        else
            return false;
    }

    private boolean RecSearch(double key, TreeElement a)
    {
        if (a.key == key)
            return true;
        else if (key < a.key && a.ch1 != null)
            return RecSearch(key, a.ch1);
        else if (key > a.key && a.ch2 != null)
            return RecSearch(key, a.ch2);
        else
            return false;
    }

    public T ValueOf(double key) throws Exception
    {
        if (root != null)
            return RecValueOf(key, root);
        else
            throw new NullPointerException();
    }

    private T RecValueOf(double key, TreeElement a) throws Exception
    {
        if (a.key == key)
            return a.value;
        else if (key < a.key && a.ch1 != null)
            return RecValueOf(key, a.ch1);
        else if (key > a.key && a.ch2 != null)
            return RecValueOf(key, a.ch2);
        else
            throw new NullPointerException();
    }

    private TreeElement TurnL(TreeElement a)
    {
        TreeElement buf = a.ch2;
        a.ch2 = buf.ch1;
        if (buf.ch1 != null)
            a.ch2.p = a;
        buf.ch1 = a;
        buf.p = a.p;
        if (a.p == null)
            root = buf;
        else if (a.key < a.p.key)
            a.p.ch1 = buf;
        else
            a.p.ch2 = buf;
        a.p = buf;
        if (a.ch2 != null && (a.ch1 == null || a.ch2.deepness > a.ch1.deepness))
            a.deepness = a.ch2.deepness + 1;
        if (buf.ch2 == null || buf.ch1.deepness > buf.ch2.deepness)
            buf.deepness = buf.ch1.deepness + 1;
        return buf;
    }

    private TreeElement TurnR(TreeElement a)
    {
        TreeElement buf = a.ch1;
        a.ch1 = buf.ch2;
        if (buf.ch2 != null)
            a.ch1.p = a;
        buf.ch2 = a;
        buf.p = a.p;
        if (a.p == null)
            root = buf;
        else if (a.key < a.p.key)
            a.p.ch1 = buf;
        else
            a.p.ch2 = buf;
        a.p = buf;
        RecDeUpd(a);
        RecDeUpd(buf);
        return buf;
    }

    private void RecDeUpd(TreeElement a)
    {
        if (a.ch1 == null && a.ch2 == null && a.deepness != 1)
            a.deepness = 1;
        else if (a.ch1 != null && a.ch1.deepness != a.deepness - 1 && (a.ch2 == null || a.ch2.deepness < a.ch1.deepness))
        {
            a.deepness = a.ch1.deepness + 1;
            RecDeUpd(a.p);
        } else if (a.ch2 != null && a.ch2.deepness != a.deepness - 1 && (a.ch1 == null || a.ch1.deepness < a.ch2.deepness))
        {
            a.deepness = a.ch2.deepness + 1;
            RecDeUpd(a.p);
        }
    }

    private void Balancing(TreeElement a)
    {
        if (a.ch1 == null && a.ch2 == null || a.ch1 == null && a.ch2.deepness == 1 || a.ch2 == null && a.ch1.deepness == 1)
            return;
        if (a.ch1 == null || a.ch2 != null && a.ch2.deepness - a.ch1.deepness > 1)
        {
            do
            {
                a = TurnL(a);
            } while (a.ch2.deepness - a.ch1.deepness > 1);
            return;
        }
        if (a.ch2 == null || a.ch1.deepness - a.ch2.deepness > 1)
        {
            do
            {
                a = TurnR(a);
            } while (a.ch1.deepness - a.ch2.deepness > 1);
        }
    }

    public StringBuilder[] Print()
    {
        buf = new StringBuilder(maxlen);
        space = new StringBuilder(maxlen);
        for (int i = 0; i < maxlen; i++)
        {
            buf.append(0);
            space.append(" ");
        }
        StringBuilder[] a = new StringBuilder[root.deepness + 1];
        for (int i = 0; i < a.length; i++)
            a[i] = new StringBuilder(maxlen * (int) Math.pow(2, root.deepness));
        RecPrint(a, 0, root);
        return a;
    }

    private void RecPrint(StringBuilder[] a, int l, TreeElement b)
    {
        if (b.ch1 != null)
            RecPrint(a, l + 1, b.ch1);
        else
            RecSpace(a, l + 1);
        for (int i = 0; i < l; i++)
            a[i].append(space);
        for (int i = l + 1; i <= root.deepness; i++)
            a[i].append(space);
        a[l].append(b.key);
        a[l].append(buf.substring(0, maxlen - b.len));
        if (b.ch2 != null)
            RecPrint(a, l + 1, b.ch2);
        else
            RecSpace(a, l + 1);
    }

    private void RecSpace(StringBuilder[] a, int l)
    {
        if (l > root.deepness)
            return;
        RecSpace(a, l + 1);
        for (int i = 0; i <= root.deepness; i++)
            a[i].append(space);
        RecSpace(a, l + 1);
    }

    public boolean Remove(double key)
    {
        if (Search(key))
            RecRemove(key, root);
        return Search(key);
    }

    private void RecRemove(double key, TreeElement a)
    {
        if (a.key == key)
        {
            if (a.p == null)
            {
                if (a.ch2 != null)
                {
                    root = a.ch2;
                    a.ch2.p = null;
                    if (a.ch1 != null)
                    {
                        a.ch1.p = null;
                        RecAdd(a.ch1, root);
                    }
                } else if (a.ch1 != null)
                {
                    root = a.ch1;
                    a.ch1.p = null;
                } else
                    root = null;
            } else if (a.p.ch1 == a)
            {
                if (a.ch1 != null)
                {
                    a.p.ch1 = a.ch1;
                    a.ch1.p = a.p;
                } else
                {
                    a.p.ch1 = null;
                }
                if (a.ch2 != null)
                {
                    a.ch2.p = null;
                    RecAdd(a.ch2, root);
                }
            } else if (a.p.ch2 == a)
            {
                if (a.ch2 != null)
                {
                    a.p.ch2 = a.ch2;
                    a.ch2.p = a.p;
                } else
                {
                    a.p.ch2 = null;
                }
                if (a.ch1 != null)
                {
                    a.ch1.p = null;
                    RecAdd(a.ch1, root);
                }
            }
        } else if (key < a.key && a.ch1 != null)
        {
            RecRemove(key, a.ch1);
        } else if (key > a.key && a.ch2 != null)
        {
            RecRemove(key, a.ch2);
        }
    }
}
