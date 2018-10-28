package ui;

import RSS.RSSParser;
import RSS.RSSitem;
import model.TableModel;
import model.ToDoItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProFrame extends JFrame {      //rozsireni o JFrame

    static int width = 800;
    static int height = 600;
    private TableModel model;

    public static void main(String... args) {
        ProFrame proFrame = new ProFrame();//vytvoreni instance (JFrame)
        proFrame.init(width, height);
    }

    private void init(int width, int height) {       //inicializace, vykresleni okna
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //automaticky ukončí proces
        setVisible(true);
        setSize(width, height);
        setTitle("Prográmko 2");

        setLayout(new BoxLayout(getContentPane(),1));

        JPanel toolbar = new JPanel();      //instance panelu
        add(toolbar, BorderLayout.NORTH);
        JPanel toolbar2 = new JPanel();
        add(toolbar2);

        JButton button = new JButton();     //tlacitko
        button.setText("Přidat poznámku");
        toolbar.add(button);

        JButton loadbutton = new JButton();
        loadbutton.setText("Načíst data");
        toolbar.add(loadbutton);

        JButton savebutton = new JButton();
        savebutton.setText("Uložit data");
        toolbar.add(savebutton);

        button.addActionListener(action -> {
            ToDoItem item = new ProDialog().getItem();     //nova instance prodialogu se zavolanim getItem
            //model.add(item);
        });

        loadbutton.addActionListener(action -> {
            loadItems();
        });

        savebutton.addActionListener(action -> {
            saveItems();
        });

        JTextField field = new JTextField("Vaše URL");
        JButton loadUrlBtn = new JButton("Načíst URL");
        toolbar2.add(field);
        toolbar2.add(loadUrlBtn);
        loadUrlBtn.addActionListener(action ->{
            addFeedCsv(field.getText());
            readFeedsCsv();
        });
        model = new TableModel();

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);     //zajisteni scrollingu u tabulky
        pack();

        setLocationRelativeTo(null);        //neni komponenta, ke ktere by se priblizil - otevreni na stredu obrazovky

        //parse();
        readFeeds();

    }

    private void parse(String url){
        try {
            /*
            RSSParser parser = new RSSParser(new FileInputStream(new File("download.xml")));
            */

            //String url = "http://www.eurofotbal.cz/feed/rss/premier-league/";

            URLConnection connection = new URL(url).openConnection();
            connection.connect();       //otevreni definovaneho spojeni
            InputStream stream = connection.getInputStream();
            RSSParser parser = new RSSParser(stream);

            List<RSSitem> rssItems = parser.parseItems();
            for (RSSitem rssItem: rssItems){
                System.out.println(rssItem.toString());
            }
            stream.close();
        } catch (Exception e){

        }
    }

    private void saveItems() {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File("our.db")));
            stream.writeObject(model.getItems());
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItems()
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File("our.db")));
            List<ToDoItem> items = (List<ToDoItem>) stream.readObject();
            stream.close();
            model.setItems(items);
            model.fireTableDataChanged();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addFeed(String url){
        try{
            File file = new File("feed.txt");
            if (!file.exists()) {
                file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(url);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(url);
        writer.newLine();
        writer.flush();

        }
        catch (Exception e){

        }
    }

    private void readFeeds(){   //brzy bude vracet List<String>
        try{
            List<String> urls = new ArrayList<>();
            File file = new File("feed.txt");

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null){
                urls.add(line);
            }
            for (String url: urls){
                parse(url);
            }


        } catch (Exception e){

        }
    }

    private void addFeedCsv(String url){
        try {
            File file = new File("feed.csv");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(url);
            writer.write(";");
            writer.flush();
            }

        catch(Exception e){

        }
    }

    private void readFeedsCsv(){
        try{
            List<String> urls = new ArrayList<>();
            File file = new File("feed.csv");

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null){
                urls = Arrays.asList(line.split(";"));
            }
            for (String url: urls){
                System.out.println(url);
                parse(url);
            }


        } catch (Exception e){

        }

    }
}
