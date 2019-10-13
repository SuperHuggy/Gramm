import java.util.ArrayList;
import java.util.Arrays;

public class LabWork2d2
{
    public static void main(String[] args)
    {
        int n = 7;
        boolean[][] field = new boolean[n][n];
        for (int l = 0; l < n; l++)
            for (int h = 0; h < n; h++)
                field[l][h] = Math.random() > 0.5;
        NoGameNoLife game = new NoGameNoLife(field);
        ArrayList<byte[]> bytes = new ArrayList<>();
        mark:
        while (game.iterator().hasNext())
        {
            byte[] byteArray = game.toByteArray();
            System.out.println(game);
            System.out.println("---------------------------------------------------------------------------------------------");
            for (byte[] buf : bytes)
                if (Arrays.equals(buf, byteArray))
                {
                    if (Arrays.equals(bytes.get(bytes.size() - 1), buf))
                        System.out.println("Состояние стабилизировалось");
                    else
                        System.out.println("Произошло зацикливание");
                    break mark;
                }
            bytes.add(byteArray);
            game.iterator().next();
        }
        byte[] buf = game.toByteArray();
        if (Arrays.equals(buf, new byte[buf.length]))
            System.out.println("Произошло вымирание");
    }
}
