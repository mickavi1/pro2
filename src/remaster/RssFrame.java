package remaster;

import RSS.RSSParser;
import RSS.RSSitem;
import model.FeedItem;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;

public class RssFrame extends JFrame {

    private JPanel content;

    public static void main(String[] args) {
        RssFrame frame = new RssFrame();
        frame.init(800, 600);
    }

    public void init (int width, int height){
        setSize(width, height);
        setTitle("RSS čtečka");
        setLocationRelativeTo(null);    //center
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel(new BorderLayout());
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(action ->{
            List<FeedItem> items = Utils.getAllFeeds();     //nacteni feed items
            new TableDialog(items).open();
            Utils.saveAllFeeds(items);

            loadCards();
        });
        controlPanel.add(editButton, BorderLayout.WEST);

        add(controlPanel, BorderLayout.NORTH);

        content = new JPanel(new WrapLayout());

        loadCards();

        add(new JScrollPane(content), BorderLayout.CENTER);

        setVisible(true);

    }
    //todo - async
    private void loadCards(){
        content.removeAll();
        List<RSSitem> list = loadItems();
        for (RSSitem rssItem : list){
            content.add(new CardView(rssItem));
        }
        content.updateUI();
    }

    private List<RSSitem> loadItems(){
        List<RSSitem> list = new ArrayList<>();

        List<FeedItem> allFeeds = Utils.getAllFeeds();
        for (FeedItem feed: allFeeds){
            if (feed.isShouldShow()){
                loadFromFeedItem(list, feed);
            }
        }


        Collections.sort(list, (o1, o2) -> {
            long millis1 = Utils.getMillisFromDateString(o1.getPubDate());
            long millis2 = Utils.getMillisFromDateString(o2.getPubDate());
            return Long.compare(millis2, millis1);
        });
        //todo - sorting - comparator
        //todo - filtry - nastavitelne

        return list;
    }

    private void loadFromFeedItem(List<RSSitem> items, FeedItem item) {
        //TODO validace URL
        //pokud obsahuje http, nebude se pokracovat a pokracuje se dale v programu
        if (!item.getUrl().contains("http")) {
            return;
        }

        try {
            URLConnection conn = new URL(item.getUrl()).openConnection();
            items.addAll(new RSSParser(conn.getInputStream()).parseItems());
            conn.getInputStream().close();  //uzavreni spojeni
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Deprecated
        private void test() {
            try {
            /*
            URLConnection connection = new URL("src\\data\\download.xml").openConnection();
            connection.connect();
            connection.getInputStream();
            */

            InputStream is = new FileInputStream(new File("src\\data\\download.xml"));

            List<RSSitem> items = new RSSParser(is).parseItems();

            for (RSSitem item : items) {
                content.add(new CardView(item));
            }
        } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

