import java.util.Vector;

public class Stack<E>
{
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
}