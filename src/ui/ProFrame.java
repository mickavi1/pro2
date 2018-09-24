package ui;

import javax.swing.*;
import java.awt.*;

public class ProFrame extends JFrame {      //rozsireni o JFrame

    static int width = 800;
    static int height = 600;

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

        JTable table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);     //zajisteni scrollingu u tabulky
        pack();
    }
}
