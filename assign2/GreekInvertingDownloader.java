import java.io.*;
import java.net.*;

public class GreekInvertingDownloader {
  public static void main (String[] args) {
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
      Reader r = new InputStreamReader(in);
      int c;

      File file = new File("out.html");
      FileOutputStream output = new FileOutputStream(file);
      file.createNewFile();

      while ((c = r.read()) != -1) {
        output.write((char)c);
      }
    }
    catch (IOException e) { System.err.println(e); }
    finally {
      try { in.close(); }
      catch (IOException e) {}
    }
  }
}
