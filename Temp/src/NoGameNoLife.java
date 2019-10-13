import java.util.Iterator;

public class NoGameNoLife implements Iterable
{
    private boolean[][] field;
    private boolean isLive;
    private byte[] compact;

    NoGameNoLife(boolean[][] start)
    {
        field = start;
        isLive = isLive();
        compact = toByteArray();
    }

    private boolean isLive()
    {
        for (boolean[] buffer : field)
            for (boolean buf : buffer)
                if (buf)
                    return true;
        return false;
    }

    public byte[] toByteArray()
    {
        byte[] array = new byte[field.length * field[0].length / 8 + 1];
        for (int l = 0; l < field.length; l++)
            for (int h = 0; h < field[0].length; h++)
            {
                array[(l * field[0].length + h) / 8] <<= 1;
                if (field[l][h])
                    array[(l * field[0].length + h) / 8] += 1;
            }
        return array;
    }

    public boolean equals(NoGameNoLife game)
    {
        if (compact.length != game.compact.length)
            return false;
        for (int i = 0; i < compact.length; i++)
            if (compact[i] != game.compact[i])
                return false;
        return true;
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (int l = 0; l < field.length; l++)
        {
            s.append("|");
            for (int h = 0; h < field[0].length; h++)
                if (field[l][h])
                    s.append(1).append("|");
                else
                    s.append(0).append("|");
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public Iterator iterator()
    {
        NoGameNoLife parent = this;
        return new Iterator<>()
        {
            @Override
            public boolean hasNext()
            {
                return parent.isLive;
            }

            @Override
            public NoGameNoLife next()
            {
                boolean[][] newField = new boolean[field.length][field[0].length];
                for (int l = 0; l < field.length; l++)
                    for (int h = 0; h < field[0].length; h++)
                        newField[l][h] = isWillLive(l, h);
                field = newField;
                parent.isLive = parent.isLive();
                return parent;
            }

            private boolean isWillLive(int l, int h)
            {
                int counter = isLive(l - 1, h - 1) + isLive(l, h - 1) + isLive(l + 1, h - 1) + isLive(l + 1, h) + isLive(l + 1, h + 1) + isLive(l, h + 1) + isLive(l - 1, h + 1) + isLive(l - 1, h);
                return counter == 3 || counter == 2 && field[l][h];
            }

            private int isLive(int l, int h)
            {
                if (l == -1)
                    l += field.length;
                if (h == -1)
                    h += field[0].length;
                if (l == field.length)
                    l = 0;
                if (h == field[0].length)
                    h = 0;
                return field[l][h] ? 1 : 0;
            }
        };
    }
}
