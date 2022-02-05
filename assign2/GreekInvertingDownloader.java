import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class GreekInvertingDownloader {
  public static void main (String[] args) {
    if (args.length == 0) { System.out.println("Usage:  java GreekInvertingDownloader (URL)"); }
    if (args.length > 0) {
      try {
        URL url = new URL(args[0]);

        System.out.println("Protocol: " + url.getProtocol());
        System.out.println("User info: " + url.getUserInfo());
        System.out.println("Host: " + url.getHost());
        System.out.println("Port: " + url.getPort());
        System.out.println("Path: " + url.getPath());
        System.out.println("Query: " + url.getQuery());

        DownloadWrite(url);
      }
      catch (MalformedURLException e) { System.err.println(args[0] + " not parseable"); }
    }
  }

  public static void DownloadWrite (URL url) {
    InputStream in = null;
    try {
      in = url.openStream();
      in = new BufferedInputStream(in);
      Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
      int c;

      File file = new File("out.html");
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

      char single;
      char convert;
      while ((c = r.read()) != -1) {
        single = (char)c;
        if ( single >= '\u0391' && single <= '\u03C9' ) {
          convert = invertGreek(single);
          output.write(convert);
        }
        else { output.write(single); }
      }
    }

    catch (IOException e) { System.err.println(e); }
    finally {
      try { in.close(); }
      catch (IOException e) {}
    }
  }

  public static char invertGreek (char c) {
    if (c >= '\u0391' && c <= '\u03A9') return Character.toLowerCase(c);
    return Character.toUpperCase(c);
  }
}
