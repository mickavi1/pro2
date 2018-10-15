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

        JPanel toolbar = new JPanel();      //instance panelu
        add(toolbar, BorderLayout.NORTH);

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

        model = new TableModel();
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);     //zajisteni scrollingu u tabulky
        pack();

        setLocationRelativeTo(null);        //neni komponenta, ke ktere by se priblizil - otevreni na stredu obrazovky

        parse();

    }

    private void parse(){
        try {
            RSSParser parser = new RSSParser(new FileInputStream(new File("download.xml")));

            List<RSSitem> rssItems = parser.parseItems();
            for (RSSitem rssItem: rssItems){
                System.out.println(rssItem.toString());
            }

        } catch (FileNotFoundException e){

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
}
