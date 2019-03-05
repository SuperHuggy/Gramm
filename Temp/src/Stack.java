import java.util.Vector;

public class Stack<E>
{
    public static void main(String[] args)
    {
        Stack<Double> a = new Stack<>();
        a.add(1d);
        a.add(2d);
        a.add(3d);
        a.add(4d);
        a.add(5d);
        a.add(6d);
        a.add(7d);
        a.add(8d);
        a.add(9d);
        System.out.println(a);
        System.out.println(a.search(6d));
        a.remove();
        a.remove();
        System.out.println(a);
    }
    private Vector<E> data;

    Stack()
    {
        data = new Vector<>();
    }

    public void add(E e)
    {
        data.add(e);
    }

    public E remove()
    {
        return data.remove(data.size() - 1);
    }

    public E firstElement()
    {
        return data.lastElement();
    }

    public boolean isEmpty()
    {
        return data.isEmpty();
    }

    public E search(E e)
    {
        Stack<E> buf = this.clone();
        E Buf;
        while (!buf.isEmpty())
        {
            Buf = buf.remove();
            if (Buf.equals(e))
                return Buf;
        }
        return null;
    }

    public Stack<E> clone()
    {
        Stack<E> buf = new Stack<>();
        buf.data = (Vector<E>) this.data.clone();
        return buf;
    }

    public String toString()
    {
        return data.toString();
    }
}