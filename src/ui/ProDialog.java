package ui;

import model.ToDoItem;
import javax.swing.*;
import java.awt.*;

public class ProDialog extends JDialog {

    private ToDoItem item;

    public ProDialog(){
        setModal(true);

        setLayout(new GridLayout(2,2));

        //pridat nejake komponenty
        JLabel jLabel = new JLabel("Obsah");
        add(jLabel);

        JTextArea jTextArea = new JTextArea();
        add(jTextArea);

        JButton btnOK = new JButton("OK");
        add(btnOK);

        btnOK.addActionListener(action ->{
            //plnit ToDoItem
            item = new ToDoItem(jTextArea.getText());
            setVisible(false);
        });

        pack();     //shluk dle vnitrnich prvku
        setLocationRelativeTo(null);       //center
    }

    public ToDoItem getItem(){
        setVisible(true);   //zmrazi puvodni vlakno
        return item;
    }
}
