package remaster;

import model.FeedItem;
import model.FeedTableModel;
import model.TableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableDialog extends JDialog {

    FeedTableModel model;

    public TableDialog(List<FeedItem> feedItems){
        setModal(true);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        //TODO pridat tlacitka na pridavani
        //TODO TextField apod.
        //TODO po kluknuti na pridani vycistit text. pole

        JButton finishButton = new JButton("Dokončit");
        toolbar.add(finishButton, BorderLayout.EAST);

        JTextField urlField = new JTextField();
        toolbar.add(urlField, BorderLayout.CENTER);
        urlField.setPreferredSize(new Dimension(300,30));

        JPanel btnLayout = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Přidat");
        JButton removeButton = new JButton("Smazat");
        btnLayout.add(addButton);
        btnLayout.add(removeButton);
        toolbar.add(btnLayout, BorderLayout.WEST);
        addButton.addActionListener(action ->{
            model.add(new FeedItem(urlField.getText()));
            urlField.setText("");
        });

        add(toolbar, BorderLayout.NORTH);

        model = new FeedTableModel();
        model.setItems(feedItems);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        removeButton.addActionListener(a ->{
            model.remove(table.getSelectedRow());
        });

        finishButton.addActionListener(action ->{
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(null);

    }

    public void open(){
        //TODO navraceni do frame
        setVisible(true);
    }
}

