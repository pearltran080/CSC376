import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

class SolarSysDataAndRefs {
  public static void main(String[] args) {
    String link = "https://en.wikipedia.org/wiki/List_of_Solar_System_objects_by_size#References";
    try
    {
      Document doc = Jsoup.connect(link).get();
      Elements refs = doc.getElementsByClass("references");
      Elements elements = refs.getElementsByTag("ol");

      for (Element element : elements)
      {
        String linkHref = link.attr("href");
        String linkText = link.text();

        System.out.println(linkHref + " => " + linkText);
      }
    }
    catch (IOException e)
    {
      System.err.println("Caught: " + e);
    }
  }
}
