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
   Element content;
   Elements links;
   try {
     doc = Jsoup.connect(urlText).get();

     Elements tables = doc.select("table");
     Elements stuff;

     for (Element table : tables) {
       stuff = table.select("th");
       for ( int i = 22; i < stuff.size(); i++ ){
         Element box = stuff.get(i);
         System.out.println(box.text());
       }
     }

     for (Element table : tables) {
       stuff = table.select("td");
       for ( int i = 0; i < stuff.size(); i++ ){
         Element box = stuff.get(i);
         System.out.println(box.text());
       }
     }

////////////////////////////////////////////////////////////////////////
     // content = doc.getElementById("content");
     // links = (content == null)
     // ? doc.getElementsByTag("a")
     // : content.getElementsByTag("a");
     // for (Element link : links) {
     // String href = link.attr("href");
     // String text = link.text();
     // System.out.println(text + " => " + href);
     // }
   }
   catch (IOException error) {
     System.err.println("Caught " + error);
   }

 }
}
