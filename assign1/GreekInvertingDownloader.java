import java.io.*;
import java.net.*;

public class GreekInvertingDownloader {
  public static void main (String[] args) {
    if (args.length > 0) {

      try {
        URL url = new URL(args[0]);

        // protocol
        System.out.println("Protocol: " + url.getProtocol());
        // userinfo
        System.out.println("User info: " + url.getUserInfo());
        // host
        System.out.println("Host: " + url.getHost());
        // port
        System.out.println("Port: " + url.getPort());
        // path
        System.out.println("Path: " + url.getPath());
        // query
        System.out.println("Query: " + url.getQuery());

        DownloadWrite(url);
      }
      catch (MalformedURLException e) { System.err.println(args[0] + " not parseable"); }
    }
  }

  public void DownloadWrite (URL url) {
    InputStream in = null;
    System.out.println("HELLO?1?!?!");
    try {
      in = url.openStream();
      in = new BufferedInputStream(in);
      Reader r = new InputStreamReader(in);
      int c;

      File file = new File("out.html");
      FileOutputStream output = new FileOutputStream(file);
      file.createNewFile();

      while (c = r.read() != -1) {
        output.write(c.getBytes());
      }
    }
    catch (IOException e) { System.err.println(e); }
    finally {
      try { in.close(); }
      catch (IOException e) {}
    }
  }
}
