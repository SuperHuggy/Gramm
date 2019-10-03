import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;

public class LabWork2
{
    public static void main(String[] args)
    {
        System.out.println("Задание 1");
        {
            Stack<Byte> stack = new Stack<>();
            ArrayDeque<Byte> deque = new ArrayDeque<>();
            int number = 12345, x = number, razr = 1;
            while (x != 0)
            {
                stack.add((byte) (x % 10));
                deque.add((byte) (x % 10));
                razr *= 10;
                x /= 10;
            }
            razr /= 10;
            while (!stack.isEmpty())
                System.out.print(stack.remove() + " ");
            System.out.println();
            while (!deque.isEmpty())
                System.out.print(deque.remove() + " ");
            System.out.println();
            stack = new Stack<>();
            deque = new ArrayDeque<>();
            x = number;
            while (razr != 0)
            {
                stack.add((byte) (x / razr));
                deque.add((byte) (x / razr));
                x = x % razr;
                razr /= 10;
            }
            while (!stack.isEmpty())
                System.out.print(stack.remove() + " ");
            System.out.println();
            while (!deque.isEmpty())
                System.out.print(deque.remove() + " ");
        }
        System.out.println("Задание 2");
        try (BufferedReader in = new BufferedReader(new FileReader("file2.1")))
        {
            Stack<Person> stack = new Stack<>();
            ArrayDeque<Person> deque = new ArrayDeque<>();
            String buf;
            while ((buf = in.readLine()) != null)
            {
                stack.add(new Person(buf));
                deque.add(new Person(buf));
            }
            System.out.println("Люди младше 40");
            LocalDate date = LocalDate.now().minusYears(40);
            while (!stack.isEmpty())
            {
                Person buff = stack.remove();
                if (buff.date.isAfter(date))
                    System.out.println(buff);
            }

            System.out.println("Остальные");
            while (!deque.isEmpty())
            {
                Person buff = deque.remove();
                if (buff.date.isBefore(date))
                    System.out.println(buff);
            }
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Задание 3");
        System.out.println(balance("M(m(3,5),M(1,2))"));
        System.out.println(balance("M{m[3,5),M(1,2)}"));
        System.out.println("Задание 4");
        System.out.println(parse("M(m(3,5),M(1,2))", 1));
        System.out.println("Задание 5");
        try (BufferedReader in = new BufferedReader(new FileReader("file2.2")))
        {
            ArrayList<String> s = new ArrayList<>();
            String buf;
            while ((buf = in.readLine()) != null)
                s.add(buf);
            s.sort(Comparator.comparingInt(String::length)); //a)
            s.sort(String::compareTo); //b)
            s.sort(Comparator.comparingInt(a ->
            {
                int res = 0;
                for (int i = 0; i < a.length(); i++)
                    if (a.charAt(i) >= 'A' && a.charAt(i) <= 'Z')
                        res++;
                return -res;
            })); //c)
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Задание 6");
        ArrayList<Point2> points = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            points.add(new Point2(i, 9 - i));
        BrokenLine brokenLine = new BrokenLine(points);
        System.out.println(brokenLine);
    }

    static boolean balance(String s)
    {
        Stack<Character> stack = new Stack<>();
        char buf;
        for (int i = 0; i < s.length(); i++)
        {
            buf = s.charAt(i);
            if (buf == '{' || buf == '(' || buf == '[')
                stack.add(buf);
            else if (buf == '}' && stack.firstElement() == '{' || buf == ')' && stack.firstElement() == '(' || buf == ']' && stack.firstElement() == '(')
                stack.remove();
            else if (buf == '}' || buf == ')' || buf == ']')
                return false;
        }
        return true;
    }

    private static double parse(String s, double x)
    {
        if (s.contains("M"))
        {
            int st = s.indexOf("M") + 2, end = st, comma = 0, brackets = 1;
            for (; brackets != 0; end++)
            {
                if (s.charAt(end) == '(')
                    brackets++;
                else if (s.charAt(end) == ')')
                    brackets--;
                else if (brackets == 1 && s.charAt(end) == ',')
                    comma = end;
            }
            end--;
            return parse(s.substring(0, st - 2) + Double.max(parse(s.substring(st, comma), x), parse(s.substring(comma + 1, end), x)) + s.substring(end + 1), x);
        }
        if (s.contains("m"))
        {
            int st = s.indexOf("m") + 2, end = st, comma = 0, brackets = 1;
            for (; brackets != 0; end++)
            {
                if (s.charAt(end) == '(')
                    brackets++;
                else if (s.charAt(end) == ')')
                    brackets--;
                else if (brackets == 1 && s.charAt(end) == ',')
                    comma = end;
            }
            end--;
            return parse(s.substring(0, st - 2) + Double.min(parse(s.substring(st, comma), x), parse(s.substring(comma + 1, end), x)) + s.substring(end + 1), x);
        }
        if (s.contains("("))
        {
            int st = s.indexOf("("), end = st, brackets = 1;
            for (; brackets != 0; end++)
            {
                if (s.charAt(end) == '(')
                    brackets++;
                else if (s.charAt(end) == ')')
                    brackets--;
            }
            end--;
            return parse(s.substring(0, st) + parse(s.substring(st + 1, end), x) + s.substring(end + 1), x);
        }
        if (s.contains("+"))
            return parse(s.substring(0, s.indexOf("+")), x) + parse(s.substring(s.indexOf("+") + 1), x);
        if (s.contains("-"))
            return parse(s.substring(0, s.indexOf("-")), x) - parse(s.substring(s.indexOf("-") + 1), x);
        if (s.contains("*"))
            return parse(s.substring(0, s.indexOf("*")), x) * parse(s.substring(s.indexOf("*") + 1), x);
        if (s.contains("/"))
            return parse(s.substring(0, s.indexOf("/")), x) / parse(s.substring(s.indexOf("/") + 1), x);
        if (s.contains("x"))
            return x;
        return Double.parseDouble(s);
    }
}

class Person
{
    String firstName, secondName, sex;
    public LocalDate date;

    Person(String data)
    {
        String[] s = data.split("(\\s)|(\\.)");
        firstName = s[0];
        secondName = s[1];
        date = LocalDate.of(Integer.parseInt(s[4]), Integer.parseInt(s[3]), Integer.parseInt(s[2]));
        sex = s[5];
    }

    public String toString()
    {
        return "Имя - " + firstName + " Фамилия - " + secondName + " Дата рождения - " + String.format("%d%s%d", date.getDayOfMonth(), date.getMonth(), date.getYear()) + "Пол - " + sex;
    }
}
