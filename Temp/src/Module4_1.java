import java.io.*;

public class Module4_1
{
    public static void main(String[] args)
    {
        try (BufferedReader in = new BufferedReader(new FileReader("module4.csv")))
        {
            in.readLine();
            String line;
            while ((line = in.readLine()) != null)
                System.out.println(line.split(",")[4]);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
