import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class NewsAggregator {
  public static void main(String[] args) {
    ArrayList<String> links = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    links.add("https://news.google.com/rss/search?q=%22Independent%20School%20Entrance%20Examination%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=allintitle%3Assat%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22shsat%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22Specialized%20High%20Schools%20Admissions%20Test%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=college%20school%20education%20students%20%22sat%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=college%20school%20education%20students%20%22tutoring%22%20%22act%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22gmat%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=education%20test%20%22gre%22%20when%3A7d%20-stock&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22standardized%20test%22%20when%3A7d%20-bloomberg&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22testing%22%20admission%20school%20standardized%20when%3A7d%20-covid%20-sexually&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22mock%20exam%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22computerized%20testing%22%20education%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22online%20exams%22%20education%20test%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/search?q=allintitle%3Acollegeboard%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22Educational%20Records%20Bureau%22%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=%22lsat%22%20law%20when%3A7d%20-sports&hl=en-US&gl=US&ceid=US%3Aen");
    links.add("https://news.google.com/rss/search?q=math%20when%3A7d&hl=en-US&gl=US&ceid=US%3Aen");
    ArrayList<String> temp = readRssGoogle(links);
    int i;
    for (i = 0; i < temp.size() - 1; i += 2) {
      if (!list.contains(temp.get(i))) {
        list.add(temp.get(i));
        list.add(temp.get(i + 1));
      } 
    } 
    String[] l1 = new String[list.size() / 2];
    int count = 0;
    int j;
    for (j = 0; j < list.size() - 1; j += 2) {
      l1[count] = "<html><a href=\"" + (String)list.get(j + 1) + "\">" + (String)list.get(j) + "</a><br/><br/></html>";
      count++;
    } 
    final JList<String> textLabel = new JList<>(l1);
    textLabel.setLayout(new BorderLayout());
    textLabel.setFont(new Font("Verdana", 0, 14));
    textLabel.setBorder(BorderFactory.createEmptyBorder(6, 8, -5, 6));
    textLabel.setCursor(Cursor.getPredefinedCursor(12));
    textLabel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting())
              return; 
            String link = "" + textLabel.getSelectedValue();
            link = link.substring(link.indexOf("href=\"") + 6, link.indexOf("\">"));
            try {
              Desktop.getDesktop().browse(new URI(link));
            } catch (URISyntaxException|IOException ex) {
              ex.printStackTrace();
            } 
          }
        });
    JScrollPane scroll = new JScrollPane(textLabel, 22, 32);
    scroll.getVerticalScrollBar().setUnitIncrement(25);
    scroll.getHorizontalScrollBar().setUnitIncrement(25);
    scroll.setEnabled(true);
    scroll.setVisible(true);
    JFrame frame = new JFrame("News Aggregator");
    frame.setDefaultCloseOperation(3);
    frame.setSize(800, 500);
    frame.setLayout(new BorderLayout());
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.getContentPane().add(scroll);
  }
  
  public static ArrayList<String> readRssGoogle(ArrayList<String> links) {
    ArrayList<String> sourceCode = new ArrayList<>();
    for (String urlAddress : links) {
      try {
        URL rssURL = new URL(urlAddress);
        BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));
        String line = in.readLine();
        String lowerCaseTitle = "";
        int count = 0;
        while (line.contains("</title>")) {
          if (line.contains("<title>")) {
            int firstPos = line.indexOf("<title>");
            String temp = line.substring(firstPos);
            temp = temp.replace("<title>", "");
            int lastPos = temp.indexOf("</title>");
            temp = temp.substring(0, lastPos);
            if (count != 0)
              sourceCode.add(temp); 
            line = line.substring(line.indexOf(temp) + temp.length());
            firstPos = line.indexOf("<link>");
            temp = line.substring(firstPos);
            temp = temp.replace("<link>", "");
            lastPos = temp.indexOf("</link>");
            temp = temp.substring(0, lastPos);
            if (count == 0) {
              count++;
            } else {
              sourceCode.add(temp);
            } 
            line = line.substring(line.indexOf(temp) + temp.length());
          } 
        } 
        in.close();
      } catch (MalformedURLException ue) {
        System.out.println("Malformed URL");
        return null;
      } catch (IOException e) {
        System.out.println("Something went while reading the content");
        return null;
      } 
    } 
    return sourceCode;
  }
}