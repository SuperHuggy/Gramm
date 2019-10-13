import java.util.*;

public class BrokenLine implements Iterable<Point2>
{
    private ArrayList<Point2> points;

    BrokenLine(ArrayList<Point2> p)
    {
        points = (ArrayList<Point2>) p.clone();
    }

    public void addPoint(Point2 p)
    {
        points.add(p);
    }

    public int GetN()
    {
        return points.size();
    }

    public ArrayList<Point2> GetPoints()
    {
        return points;
    }

    public double length()
    {
        double s = 0;
        for (int i = 0; i < points.size() - 1; i++)
            s += Point.Sub(points.get(i), points.get(i + 1)).Abs();
        return s;
    }

    public String toString()
    {
        StringBuilder buf = new StringBuilder("{" + points.get(0));
        for (int i = 1; i < points.size(); i++)
            buf.append(",").append(points.get(i));
        return buf.append("}").toString();
    }

    @Override
    public Iterator<Point2> iterator()
    {
        return points.iterator();
    }
}