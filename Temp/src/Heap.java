import java.util.Vector;

public class Heap<E>
{
    private Vector<E> data;
    private del<E> s;

    public static void main(String[] args)
    {
        Vector<Double> a = new Vector<>();
        a.add(1d);
        a.add(2d);
        a.add(3d);
        a.add(4d);
        a.add(5d);
        a.add(6d);
        a.add(7d);
        a.add(8d);
        a.add(9d);
        Heap<Double> h= new Heap<>(a,(b,c)-> b > c ? 1 : b < c ? -1 : 0);
        System.out.println(h);
        System.out.println(h.search(5d));
        h.remove(6d);
        h.remove(5d);
        System.out.println(h);
    }

    Heap(del<E> sravn)
    {
        s = sravn;
        data = new Vector<>();
    }

    Heap(Vector<E> data, del<E> sravn)
    {
        s = sravn;
        this.data = HeapForm(data);
    }

    public Vector<E> HeapForm(Vector<E> arg)
    {
        Vector<E> arr = (Vector<E>) arg.clone();
        for(int i=arg.size()-1;i>=0;i--)
            ReHeap(arr,i,arr.size());
        return arr;
    }

    private Vector<E> ReHeap(Vector<E> arr, int i, int end)
    {
        E buf;
        if (i * 2 + 2 < end)
        {

            if (s.fun(arr.get(i * 2 + 1), arr.get(i * 2 + 2)) == 1 && s.fun(arr.get(i * 2 + 1), arr.get(i)) == 1)
            {
                buf = arr.get(i);
                arr.set(i, arr.get(i * 2 + 1));
                arr.set(i * 2 + 1, buf);
                arr = ReHeap(arr, i * 2 + 1, end);
            } else if (s.fun(arr.get(i * 2 + 2), arr.get(i)) == 1)
            {
                buf = arr.get(i);
                arr.set(i, arr.get(i * 2 + 2));
                arr.set(i * 2 + 2, buf);
                arr = ReHeap(arr, i * 2 + 2, end);
            }
        } else if (i * 2 + 1 < end && s.fun(arr.get(i * 2 + 1), arr.get(i)) == 1)
        {
            buf = arr.get(i);
            arr.set(i, arr.get(i * 2 + 1));
            arr.set(i * 2 + 1, buf);
        }
        return arr;
    }

    //Функция возвращает 1, если a>b, -1,если a<b и 0 если a=b
    interface del<E>
    {
        int fun(E a, E b);
    }

    public boolean remove(E e)
    {
        int buf = indexOf(e);
        if (buf != -1)
        {
            data.remove(buf);
            data = HeapForm(data);
            return true;
        }
        return false;
    }

    public E search(E e)
    {
        return search(e, 0);
    }

    private E search(E e, int i)
    {
        if(i>=data.size())
            return null;
        if (s.fun(e, data.get(i)) == 1)
            return null;
        if (s.fun(data.get(i), e) == 0)
            return data.get(i);
        E a = this.search(e, i * 2 + 1), b = this.search(e, i * 2 + 2);
        if (a != null)
            return a;
        else
            return b;
    }

    public int indexOf(E e)
    {
        return indexOf(e, 0);
    }

    private int indexOf(E e, int i)
    {
        if(i>=data.size())
            return -1;
        if (s.fun(e, data.get(i)) == 1)
            return -1;
        if (s.fun(data.get(i), e) == 0)
            return i;
        int a = this.indexOf(e, i * 2 + 1), b = this.indexOf(e, i * 2 + 2);
        if (a != -1)
            return a;
        else
            return b;
    }

    public String toString()
    {
        return data.toString();
    }
}
