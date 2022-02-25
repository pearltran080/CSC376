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
     Elements stuff, a;
     String href;

       stuff = doc.select("th");
       for (Element s : stuff) {
         a = s.select("a");
         if (a.size() == 0) System.out.println(s.text());
         else {
           for (Element aa : a) {
             href = aa.attr("href").substring(1);
             if (idToText.get(href) != null) System.out.println(s.text() + " Ref[" + idToText.get(href) + "]");
             else System.out.println(s.text());
           }
         }
       }

       stuff = doc.select("td");
       for (Element s : stuff) {
         a = s.select("a");
         if (a.size() == 0) System.out.println(s.text());
         else {
           for (Element aa : a) {
             href = aa.attr("href");
             if (href.length() > 0 && href.charAt(0) == '#') {  // exists ref in map
               href = href.substring(1);
               System.out.println(s.text() + " Ref[" + idToText.get(href) + "]");
             }
             else System.out.println(s.text() + " Ref[" + href + "]");  // refs that links to other Wiki pages
           }
         }
       }

   }
   catch (IOException error) {
     System.err.println("Caught " + error);
   }
 }
}
