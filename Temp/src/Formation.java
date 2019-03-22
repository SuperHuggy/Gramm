import java.util.Scanner;

class Node
{
    int key;
    int deepness;
    Node left;
    Node right;
    Node parent;
}

public class Formation
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int dataArray[] = {20, 18, 19, 15, 17, 16, 3, 25, 22, 27, 26, 28, 21, 23};
        Node root = null;
        try
        {
            System.out.println(root.key);
        } catch (NullPointerException e1)
        {
            System.out.println("NullPointerException");
        }
        try
        {
            int a = 0, b = 1 / a;
        } catch (ArithmeticException ex2)
        {
            System.out.println("ArithmeticException");
        }
        try
        {
            System.out.println("Введите целое число");
            int c = Integer.parseInt(in.next());
        } catch (NumberFormatException ex4)
        {
            System.out.println("NumberFormatException");
        }

        try
        {
            Node z = new Node();
            z.key = dataArray[0];
            z.parent = null;
            root = insert(z, root);
            try
            {
                for (int i = 1; i <= dataArray.length; i++)
                {
                    z = new Node();
                    z.key = dataArray[i];
                    insert(z, root);
                }
            } catch (IndexOutOfBoundsException ex3)
            {
                System.out.println("IndexOutOfBoundsException");
            }
            try
            {
                z = new Node();
                z.key = 19;
                insert(z, root);
            } catch (IllegalArgumentException ex4)
            {
                System.out.println("IllegalArgumentException");
            }
        } catch (InvalidBallanceValue ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static Node insert(Node z, Node x) throws InvalidBallanceValue
    {
        if (x == null)
        {
            x = z;
        } else if (x.key == z.key)
            throw new IllegalArgumentException();
        else if (z.key < x.key)
        {
            x.left = insert(z, x.left);
            x.left.parent = x;
        } else
        {
            x.right = insert(z, x.right);
            x.right.parent = x;
        }
        if (x.left == null)
            x.deepness = 0;
        else if (x.deepness <= x.left.deepness)
            x.deepness = x.left.deepness + 1;
        if (x.right != null && x.deepness <= x.right.deepness)
            x.deepness = x.right.deepness + 1;
        if (x.right != null && x.left != null && Math.abs(x.left.deepness - x.right.deepness) > 1)
            throw new InvalidBallanceValue();
        else if (x.right != null && x.right.deepness > 1)
            throw new InvalidBallanceValue();
        else if (x.left != null && x.left.deepness > 1)
            throw new InvalidBallanceValue();
        return x;
    }

    public static Node search(Node x, int k)
    {
        if ((x == null) || (k == x.key))
            return x;
        if (k < x.key)
            return search(x.left, k);
        else
            return search(x.right, k);
    }


    public static void deepnessCheck(Node a) throws InvalidBallanceValue
    {
        if (a.right != null && a.left != null)
        {
            if (Math.abs(a.left.deepness - a.right.deepness) > 1)
                throw new InvalidBallanceValue();
            deepnessCheck(a.right);
            deepnessCheck(a.left);
        } else if (a.right != null)
        {
            if (a.right.deepness > 1)
                throw new InvalidBallanceValue();
            deepnessCheck(a.right);
        } else if (a.left != null)
        {
            if (a.left.deepness > 1)
                throw new InvalidBallanceValue();
            deepnessCheck(a.left);
        }
    }
}

class InvalidBallanceValue extends Exception
{
    @Override
    public String getMessage()
    {
        return "InvalidBallanceValue";
    }
}