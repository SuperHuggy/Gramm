import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LabWork5
{
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {

        System.out.println("Задание 2");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        MyXmlHandler handler = new MyXmlHandler();
        parser.parse("file3.2.xml", handler);
        System.out.println("a)");
        for (Sportshuman buf : handler.sh)
            System.out.println(buf);
        try (FileWriter out = new FileWriter("file5.json"))
        {
            out.write("{\n\"Sportpeople\" :[\n");
            for (int i = 0; i < handler.sh.size() - 1; i++)
                out.write(handler.sh.get(i).toJson() + ",\n");
            out.write(handler.sh.get(handler.sh.size() - 1).toJson() + "\n");
            out.write("]\n}");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static class MyXmlHandler extends DefaultHandler
    {

        private String lastElementName;
        ArrayList<Sportshuman> sh = new ArrayList<>();


        public void startElement(String namespaceURI, String lname, String qname, Attributes attrs)
        {
            if (qname.equals("sportsman"))
                sh.add(new Sportshuman(attrs.getValue("name"), attrs.getValue("birthday"), attrs.getValue("s")));
            else if (qname.equals("event"))
                sh.get(sh.size() - 1).events.add(new Event(attrs.getValue("place"), Short.parseShort(attrs.getValue("year"))));
            lastElementName = qname;
        }

        public void characters(char[] ch, int start, int length)
        {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();
            if (!information.isEmpty())
            {
                if (lastElementName.equals("result"))
                    sh.get(sh.size() - 1).events.get(sh.get(sh.size() - 1).events.size() - 1).result = Short.parseShort(information);
                else if (lastElementName.equals("award"))
                    sh.get(sh.size() - 1).events.get(sh.get(sh.size() - 1).events.size() - 1).award = information;
            }
        }
    }

    static class Sportshuman
    {
        String name;
        ArrayList<Event> events;
        LocalDate birthday;
        String sex;

        Sportshuman(String name, String birthday, String sex)
        {
            this.name = name;
            events = new ArrayList<>();
            String[] date = birthday.split("-");
            this.birthday = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            this.sex = sex;
        }

        Sportshuman()
        {
        }

        public String toString()
        {
            StringBuilder s = new StringBuilder();
            s.append(name).append(" ").append(birthday).append(" ").append(sex);
            return s.toString();
        }

        public String toJson()
        {
            StringBuilder s = new StringBuilder();
            s.append("{\n");
            s.append("\"name\": \"").append(name).append("\",\n");
            s.append("\"birthday\": ").append("\"").append(birthday).append("\",\n");
            s.append("\"sex\": \"").append(sex).append("\",\n");
            s.append("\"events\": [\n");
            for (Event buf : events)
                s.append(buf.toJson()).append(",\n");
            s.deleteCharAt(s.length() - 2);
            s.append("]\n}");
            return s.toString();
        }

    }

    static private class Event
    {
        String place;
        short year;
        short result;
        String award;

        Event(String place, short year)
        {
            this.place = place;
            this.year = year;
        }

        Event()
        {
        }

        public String toJson()
        {
            StringBuilder s = new StringBuilder();
            s.append("{\n");
            s.append("\"place\": \"").append(place).append("\",\n");
            s.append("\"year\": ").append(year).append(",\n");
            s.append("\"result\": ").append(result).append(",\n");
            s.append("\"award\": \"").append(award).append("\"\n");
            s.append("}");
            return s.toString();
        }

    }

}