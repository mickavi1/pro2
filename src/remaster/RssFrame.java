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
        });
        controlPanel.add(editButton, BorderLayout.WEST);

        add(controlPanel, BorderLayout.NORTH);

        content = new JPanel(new WrapLayout());

        test(); //fixme - bude notno po pridavani cardview volat refresh

        add(new JScrollPane(content), BorderLayout.CENTER);

        setVisible(true);

    }

    private void test(){

        try{
            /*
            URLConnection connection = new URL("src\\data\\download.xml").openConnection();
            connection.connect();
            connection.getInputStream();
            */

            InputStream is = new FileInputStream(new File("src\\data\\download.xml"));

            List<RSSitem> items = new RSSParser(is).parseItems();

            for (RSSitem item: items){
                content.add(new CardView(item));
            }
            content.doLayout();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
