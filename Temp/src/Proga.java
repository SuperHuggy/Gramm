public class Proga
{
    public static void main(String[] args)
    {
        System.out.println(Proga2.fun(2));
    }
    static double fun(int a)
    {
        return a*a;
    }
}
class Proga2 implements pr
{
    static double fun(int a)
    {
        return a*a*a;
    }
}
interface pr
{
    static double fun(int a)
    {
        return a;
    }
}
