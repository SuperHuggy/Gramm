import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabWork4
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите строку");
        String input = in.nextLine();
        Pattern pattern;
        Matcher matcher;
        System.out.println("Задание 1а");
        System.out.println(input.matches("abcd(1{7})02019"));
        System.out.println("Задание 1б");
        {
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(input);
            ArrayList<Integer> arr = new ArrayList<>();
            while (matcher.find())
                try
                {
                    arr.add(Integer.parseInt(input.substring(matcher.start(), matcher.end())));
                } catch (NumberFormatException e)
                {
                    System.out.println(e.getMessage());
                }
            int s = 0, max = 0;
            for (int i = 0; i < arr.size(); i++)
            {
                s += arr.get(i);
                if (arr.get(i) > arr.get(max))
                    max = i;
            }
            if (!arr.isEmpty())
                System.out.println(s + "  " + max + "  " + arr.get(max));
        }
        System.out.println("Задание 1в");
        {
            ArrayList<Double> arr = new ArrayList<>();
            pattern = Pattern.compile("\\d+[.,]\\d+");
            matcher = pattern.matcher(input);
            while (matcher.find())
                try
                {
                    arr.add(Double.parseDouble(input.substring(matcher.start(), matcher.end()).replace(',', '.')));
                } catch (NumberFormatException e)
                {
                    System.out.println(e.getMessage());
                }
            System.out.println(arr);
        }
        System.out.println("Задание 1г");
        {
            pattern = Pattern.compile("[a-zA-Z]{10,}");
            matcher = pattern.matcher(input);
            StringBuilder buf = new StringBuilder();
            while (matcher.find())
                matcher.appendReplacement(buf, "*");
            System.out.println(buf);
            matcher.reset(input);
            buf = new StringBuilder();
            while (matcher.find())
                matcher.appendReplacement(buf, Character.toString(input.charAt(matcher.start())));
            System.out.println(buf);
            matcher.reset(input);
            buf = new StringBuilder();
            while (matcher.find())
                matcher.appendReplacement(buf, replacer(input.charAt(matcher.start()), matcher.end() - matcher.start()));
            System.out.println(buf);
            matcher.reset(input);
        }
        System.out.println("Задание 1д");
        System.out.println(input.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"));
        System.out.println("Задание 1е");
        System.out.println(input.matches("#[a-fA-F0-9]{6}"));
        System.out.println("Задание 1ж");
        System.out.println(Arrays.toString(input.split(":*\\\\+")));
        System.out.println("Задание 1з");
        System.out.println(input.replaceAll("ик", ""));
        /*System.out.println("Задание 1и");
        {
            System.out.println("Введите целевую валюту(USD RUR EU)");
            String tar=in.next();
            System.out.println("Введите обменный курс USD/RUR");
            double usd_rur=in.nextDouble();
            System.out.println("Введите обменный курс EU/RUR");
            double eu_rur=in.nextDouble();
            ArrayList<Double> usd=new ArrayList<>(), rur=new ArrayList<>(), eu=new ArrayList<>();
            pattern = Pattern.compile("\\d+(.\\d{1,2})?\\s?USD");
            matcher = pattern.matcher(input);
            while(matcher.find())
            {
                double buf=Double.parseDouble(input.substring(matcher.start(), matcher.end()).replace("USD", ""));
                usd.add(buf);
                if(tar.equals("RUR"))
                    input = matcher.replaceFirst(buf*usd_rur+"RUR");
                if(tar.equals("EU"))
                    input = matcher.replaceFirst(buf*eu_rur/usd_rur+"EU");
            }
            pattern=Pattern.compile("\\d+(.\\d{1,2})?\\s?RUR");
            matcher = pattern.matcher(input);
            while(matcher.find())
            {
                double buf=Double.parseDouble(input.substring(matcher.start(), matcher.end()).replace("RUR", ""));
                rur.add(buf);
                if(tar.equals("USD"))
                    input=matcher.replaceFirst(buf/usd_rur+"USD");
                if(tar.equals("EU"))
                    input=matcher.replaceFirst(buf/eu_rur+"EU");
            }
            pattern = Pattern.compile("\\d+(.\\d{1,2})?\\s?EU");
            matcher = pattern.matcher(input);
            while(matcher.find())
            {
                double buf=Double.parseDouble(input.substring(matcher.start(), matcher.end()).replace("EU", ""));
                eu.add(buf);
                if(tar.equals("RUR"))
                    input=matcher.replaceFirst(buf*eu_rur+"RUR");
                if(tar.equals("USD"))
                    input=matcher.replaceFirst(buf*usd_rur/eu_rur+"USD");
            }
        System.out.println(input)
        }*/
        System.out.println("Задание 3");
        System.out.println(input.matches("([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}"));
        System.out.println("Задание 4");
        System.out.println(input.replaceAll(" ", "").matches("(https?://)?(www\\.)?([A-Za-z]{2,63}\\.)*([A-Za-z]{2,63}\\.)(ru|su|com|net|org)(/.+)*((\\?[A-Za-z]+=\\w+)(&[A-Za-z]+=\\w+)*)?(#\\w+)?"));
        System.out.println("Задание 6");
        try
        {
            int buf = Integer.parseInt(input.split("/")[2]);
            System.out.println(input.matches("(((?=\\d*/(04|06|09|11)/)(30|([12]\\d)|(0[1-9])))" +
                    "|((?=\\d*/02/)((0[1-9])|(1\\d)|(2[0-" + ((buf % 400 == 0 || buf % 4 == 0 && buf % 100 != 0) ? 9 : 8) + "])))" +
                    "|((?=\\d*/(01|03|05|07|08|10|12)/)(3[01]|([12]\\d)|(0[1-9]))))/((1[0-2])|(0[1-9]))/((1[6-9]\\d\\d)|([2-9]\\d{3}))"));
        } catch (Exception e)
        {
        }
        /*
        System.out.println("Задание 7");
        System.out.println(input.matches("[-\\w.]+@[a-zA-Z\\d]{2,63}\\.[a-zA-Z]{2,63}"));
        System.out.println("Задание 8");
        System.out.println(input.matches("((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)"));
        System.out.println("Задание 9");
        System.out.println(input.matches("(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\w{8,}"));
        System.out.println("Задание 10");
        System.out.println(input.matches("[1-9]\\d{5}"));
        System.out.println("Задание 11");
        System.out.println(input.matches(".*\\d+([^+].*)?"));*/

    }

    static String replacer(char r, int n)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++)
            s.append(r);
        return s.toString();
    }
}
