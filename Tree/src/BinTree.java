public class BinTree<T>
{
    TreeElement root;
    private int maxlen = 0;
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

    public BinTree(double key)
    {
        root = new TreeElement(key);
    }

    public BinTree()
    {
        root = null;
    }

    public boolean Add(double key)
    {
        if (Search(key) != null)
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
                Balancing(a);
            } else
            {
                a.ch1 = nw;
                nw.p = a;
                RecDeUpd(a);
            }
        } else if (nw.key > a.key)
        {
            if (a.ch2 != null)
            {
                RecAdd(nw, a.ch2);
                Balancing(a);
            } else
            {
                a.ch2 = nw;
                nw.p = a;
                RecDeUpd(a);
            }
        }
    }

    public TreeElement Search(double key)
    {
        if (root != null)
            return RecSearch(key, root);
        else
            return null;
    }

    private TreeElement RecSearch(double key, TreeElement a)
    {
        if (a.key == key)
            return a;
        else if (key < a.key && a.ch1 != null)
            return RecSearch(key, a.ch1);
        else if (key > a.key && a.ch2 != null)
            return RecSearch(key, a.ch2);
        else
            return null;
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
        RecDeUpd(a);
        return buf;
    }

    private TreeElement BTurnL(TreeElement a)
    {
        TreeElement b = a.ch2, c = b.ch1;
        a.ch2 = c.ch1;
        if (c.ch1 != null)
            a.ch2.p = a;
        b.ch1 = c.ch2;
        if (c.ch2 != null)
            b.ch1.p = b;
        c.p = a.p;
        if (a.p == null)
            root = c;
        else if (a.key < a.p.key)
            a.p.ch1 = c;
        else
            a.p.ch2 = c;
        c.ch1 = a;
        a.p = c;
        c.ch2 = b;
        b.p = c;
        RecDeUpd(a);
        RecDeUpd(b);
        return c;
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
        return buf;
    }

    private TreeElement BTurnR(TreeElement a)
    {
        TreeElement b = a.ch1, c = b.ch2;
        a.ch1 = c.ch2;
        if (c.ch2 != null)
            a.ch1.p = a;
        b.ch2 = c.ch1;
        if (c.ch1 != null)
            b.ch2.p = b;
        c.p = a.p;
        if (a.p == null)
            root = c;
        else if (a.key < a.p.key)
            a.p.ch1 = c;
        else
            a.p.ch2 = c;
        c.ch1 = b;
        b.p = c;
        c.ch2 = a;
        a.p = c;
        RecDeUpd(a);
        RecDeUpd(b);
        return c;
    }

    private void RecDeUpd(TreeElement a)
    {
        if (a == null)
            return;
        if (a.ch1 == null && a.ch2 == null && a.deepness != 0)
        {
            a.deepness = 0;
            RecDeUpd(a.p);
        } else if (a.ch1 != null && a.ch1.deepness != a.deepness - 1 && (a.ch2 == null || a.ch2.deepness <= a.ch1.deepness))
        {
            a.deepness = a.ch1.deepness + 1;
            RecDeUpd(a.p);
        } else if (a.ch2 != null && a.ch2.deepness != a.deepness - 1 && (a.ch1 == null || a.ch1.deepness <= a.ch2.deepness))
        {
            a.deepness = a.ch2.deepness + 1;
            RecDeUpd(a.p);
        }
    }

    private void Balancing(TreeElement a)
    {
        if (a.ch1 == null && a.ch2 == null || a.ch1 == null && a.ch2.deepness == 0 || a.ch2 == null && a.ch1.deepness == 0)
            return;
        if (a.ch2 != null && (a.ch1 == null || a.ch2.deepness - a.ch1.deepness == 2))
        {
            if (a.ch2.ch1 != null && (a.ch2.ch2 == null || a.ch2.ch1.deepness > a.ch2.ch2.deepness))
                a = BTurnL(a);
            else
                a = TurnL(a);
        } else if (a.ch2 == null || a.ch1.deepness - a.ch2.deepness == 2)
        {
            if (a.ch1.ch2 != null && (a.ch1.ch1 == null || a.ch1.ch2.deepness > a.ch1.ch1.deepness))
                a = BTurnR(a);
            else
                a = TurnR(a);
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
        TreeElement buf = Search(key);
        if (buf != null)
            RecRemove(buf);
        else
            return false;
        return true;
    }

    /*void AddBalancedTree(TreeElement x,TreeElement a)
    {
        if(x.deepness>a.deepness)
            throw new IllegalArgumentException();
        if(x.deepness==0)
            RecAdd(x,a);
        else if(x.key<a.key)
        {
            if(a.ch1==null)
            {
                a.ch1=x;
                x.p=a;
                RecDeUpd(x);
                Balancing(a.p);
            }
            else if(x.deepness-a.ch1.deepness==1)
            {
                TreeElement buf=a.ch1;
                a.ch1=x;
                x.p=a;
                AddBalancedTree(buf,a.ch1);
            }
            else if(x.deepness<=a.ch1.deepness)
            {
                AddBalancedTree(x,a.ch1);
            }
            else if(a.deepness==x.deepness)
            {
                TreeElement buf=a.ch1;
                a.ch1=x;
                x.p=a;
                a.deepness++;
                if(a.p!=null)
                    Balancing(a.p);
                AddBalancedTree(buf,a.ch1);
            }
        }
        else if(x.key>a.key)
        {
            if(a.ch2==null)
            {
                a.ch2=x;
                x.p=a;
                RecDeUpd(x);
                Balancing(a.p);
            }
            else if(x.deepness-a.ch2.deepness==1)
            {
                TreeElement buf=a.ch2;
                a.ch2=x;
                x.p=a;
                AddBalancedTree(buf,a.ch2);
            }
            else if(a.ch2.deepness>=x.deepness)
            {
                AddBalancedTree(x,a.ch1);
            }
            else if(a.deepness==x.deepness)
            {
                TreeElement buf=a.ch2;
                a.ch2=x;
                x.p=a;
                a.deepness++;
                if(a.p!=null)
                    Balancing(a.p);
                AddBalancedTree(buf,a.ch2);
            }
        }
    }*/

    private void RecRemove(TreeElement a)
    {
        if (a.ch1 == null && a.ch2 == null)
        {
            if (a.key > a.p.key)
                    a.p.ch2 = null;
            else
                a.p.ch1 = null;
            RecBalancing(a.p);
            } else if (a.ch1 == null || a.ch2 != null && a.ch2.deepness >= a.ch1.deepness)
        {
            TreeElement buf = FindTheClosest(a.key, a.ch2);
            Swap(a, buf);
            RecRemove(buf);
        } else
        {
            TreeElement buf = FindTheClosest(a.key, a.ch1);
            Swap(a, buf);
            RecRemove(buf);
        }
    }

    private void RecBalancing(TreeElement a)
    {
        if (a != null)
        {
            Balancing(a);
            RecDeUpd(a);
            RecBalancing(a.p);
        }
    }

    private TreeElement FindTheClosest(double key, TreeElement a)
    {
        if (key < a.key && a.ch1 != null)
            return FindTheClosest(key, a.ch1);
        else if (key > a.key && a.ch2 != null)
            return FindTheClosest(key, a.ch2);
        else
            return a;
    }

    private void Swap(TreeElement a, TreeElement b)
    {
        T buf = a.value;
        a.value = b.value;
        b.value = buf;
        double buff = a.key;
        a.key = b.key;
        b.key = buff;
    }
}
