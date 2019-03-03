import java.util.Vector;

public class Heap<E>
{
    private Vector<E> data;
    private del<E> s;

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
        return HeapForm(arg, 0, arg.size());
    }

    private Vector<E> HeapForm(Vector<E> arg, int i, int end)
    {
        Vector<E> arr = (Vector<E>) arg.clone();
        E buf;
        if (i * 2 + 2 < end)
        {

            if (s.fun(arr.get(i * 2 + 1), arr.get(i * 2 + 2)) == 1 && s.fun(arr.get(i * 2 + 1), arr.get(i)) == 1)
            {
                buf = arr.get(i);
                arr.set(i, arr.get(i * 2 + 1));
                arr.set(i * 2 + 1, buf);
                arr = HeapForm(arr, i * 2 + 1, end);
            } else if (s.fun(arr.get(i * 2 + 2), arr.get(i)) == 1)
            {
                buf = arr.get(i);
                arr.set(i, arr.get(i * 2 + 2));
                arr.set(i * 2 + 2, buf);
                arr = HeapForm(arr, i * 2 + 2, end);
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
        if (s.fun(e, data.get(i)) == 1)
            return null;
        if (s.fun(data.get(i), e) == 0)
            return data.get(i);
        E a = this.search(e, i * 2 + 1), b = this.search(e, i * 2 + 1);
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
        if (s.fun(e, data.get(i)) == 1)
            return -1;
        if (s.fun(data.get(i), e) == 0)
            return i;
        int a = this.indexOf(e, i * 2 + 1), b = this.indexOf(e, i * 2 + 1);
        if (a != -1)
            return a;
        else
            return b;
    }
}
