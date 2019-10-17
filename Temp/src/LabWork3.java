import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LabWork3
{
    public static void main(String[] args)
    {

        System.out.println("Задание 1");
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse("file3.1.xml");
            Node root = document.getDocumentElement();
            NodeList authors = root.getChildNodes();
            System.out.println("-------------------");
            for (int i = 0; i < authors.getLength(); i++)
                if (authors.item(i).getNodeType() != 3)
                {
                    System.out.println("Автор - " + authors.item(i).getAttributes().getNamedItem("FIO").getTextContent());
                    System.out.println("Произведения:");
                    NodeList books = authors.item(i).getChildNodes();
                    for (int j = 0; j < books.getLength(); j++)
                        if (books.item(j).getNodeType() != 3)
                            System.out.println(books.item(j).getAttributes().getNamedItem("name").getTextContent() + " Количество страниц - " + books.item(j).getAttributes().getNamedItem("numberOfPages").getTextContent());
                    System.out.println("-------------------");
                }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        ArrayList<Sportshuman> sh = new ArrayList<>();
        try
        {
            System.out.println("Задание 2");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse("file3.2.xml");
            Node root = document.getDocumentElement();
            NodeList sportspeople = root.getChildNodes();
            for (int i = 0; i < sportspeople.getLength(); i++)
                if (sportspeople.item(i).getNodeType() != 3)
                    sh.add(new Sportshuman(sportspeople.item(i)));
            System.out.println("а)");
            for (Sportshuman buf : sh)
                if (buf.sex.compareTo("м") == 0)
                    System.out.println("Имя - " + buf.name + " Дата рождения: " + buf.birthday);
            System.out.println("б)");
            for (Sportshuman buf : sh)
                if (buf.birthday.isAfter(LocalDate.of(1985, 12, 31)) && buf.sex.compareTo("ж") == 0)
                    System.out.println("Имя - " + buf.name + " Дата рождения: " + buf.birthday + " Количество медалей - " + buf.events.size());
            System.out.println("в)");
            for (Sportshuman buf : sh)
                for (Event event : buf.events)
                    if (event.year == 2002 && event.place.compareTo("москва") == 0)
                        System.out.println("Имя - " + buf.name + " Получена " + event.award + " медаль");
            System.out.println("Задание 3");
            Scanner in = new Scanner(System.in);
            Sportshuman newSH = new Sportshuman();
            System.out.println("Введите имя спортсмена");
            newSH.name = in.next();
            System.out.println("Ввведите пол спортсмена(м/ж)");
            newSH.sex = in.next();
            System.out.println("Введите год, месяц и день через пробелы");
            newSH.birthday = LocalDate.of(in.nextInt(), in.nextInt(), in.nextInt());
            System.out.println("Введите количество соревнований в которых спортсмен принял участие");
            int n = in.nextInt();
            newSH.events = new ArrayList<>();
            for (int i = 0; i < n; i++)
            {
                Event eve = new Event();
                System.out.println("Введите место проведения");
                eve.place = in.next();
                System.out.println("Введите год проведения");
                eve.year = in.nextShort();
                System.out.println("Введите количество очков");
                eve.result = in.nextShort();
                System.out.println("Введите медаль");
                eve.award = in.next();
                newSH.events.add(eve);
            }
            sh.add(newSH);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Задание 4");
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            document.appendChild(document.createElement("team"));
            Node root = document.getDocumentElement();
            for (Sportshuman buf : sh)
            {
                Element el = document.createElement("sportsman");
                el.setAttribute("name", buf.name);
                el.setAttribute("numberOfEvents", Integer.toString(buf.events.size()));
                int points = 0;
                for (Event ev : buf.events)
                    points += ev.result;
                el.setAttribute("points", Integer.toString(points));
                root.appendChild(document.createTextNode("\n"));
                root.appendChild(el);
            }
            writeDocument(document, "ishod3.xml");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void writeDocument(Document document, String name) throws TransformerFactoryConfigurationError
    {
        try
        {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(new FileOutputStream(name)));
        } catch (TransformerException | IOException e)
        {
            e.printStackTrace(System.out);
        }
    }

    static class Sportshuman
    {
        String name;
        ArrayList<Event> events;
        LocalDate birthday;
        String sex;

        Sportshuman(Node s)
        {
            name = s.getAttributes().getNamedItem("name").getTextContent();
            events = new ArrayList<>();
            for (int i = 0; i < s.getChildNodes().getLength(); i++)
                if (s.getChildNodes().item(i).getNodeType() != 3)
                    events.add(new Event(s.getChildNodes().item(i)));
            String[] date = s.getAttributes().getNamedItem("birthday").getTextContent().split("-");
            birthday = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            sex = s.getAttributes().getNamedItem("s").getTextContent();
        }

        Sportshuman()
        {
        }

    }

    static private class Event
    {
        String place;
        short year;
        short result;
        String award;

        Event(Node e)
        {
            place = e.getAttributes().getNamedItem("place").getTextContent();
            year = Short.parseShort(e.getAttributes().getNamedItem("year").getTextContent());
            result = Short.parseShort(e.getChildNodes().item(1).getTextContent());
            award = e.getChildNodes().item(3).getTextContent();
        }

        Event()
        {
        }

    }
}
