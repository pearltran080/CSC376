import java.net.MalformedURLException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;

class SolarSysDataAndRefs {
  public static void main (String[]args) {

   String urlText = "https://en.wikipedia.org/wiki/List_of_Solar_System_objects_by_size#References";
   Document doc;
   HashMap<String, String> idToText = new HashMap<String, String>();

   try {
     doc = Jsoup.connect(urlText).get();

     // id-to-text map
     Element references = doc.select("ol").get(2);
     Elements lines = references.getElementsByTag("li");
     String id, text;

     for (Element l : lines) {
       id = l.attr("id");
       text = l.text();
       idToText.put(id, text);
     }

     // table elements
     Elements tables = doc.select("table");
     Elements stuff, a;
     Element box;
     String href;

     for (Element table : tables) {
       stuff = table.select("th");
       a = stuff.select("a");
       for ( int i = 22; i < a.size(); i++ ){
         box = a.get(i);
         href = (a.get(i).attr("href")).substring(1);
         if (idToText.get(href) == null) System.out.println(stuff.get(i).text());
         else System.out.println(stuff.get(i).text() + " Ref" + idToText.get(href));
       }
     }

     for (Element table : tables) {
       stuff = table.select("td");
       a = stuff.select("a");
       for ( int i = 0; i < a.size(); i++ ){
         box = a.get(i);
         href = (a.get(i).attr("href")).substring(1);
         System.out.println(stuff.get(i).text() + " Ref" + idToText.get(href));
       }
     }

   }
   catch (IOException error) {
     System.err.println("Caught " + error);
   }

 }
}
