package testing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AllAvailableFontsComboBox extends JPanel {

    JComboBox fonts;

    public AllAvailableFontsComboBox() {
        add(new JLabel("Fonts"));

        GraphicsEnvironment gEnv = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        Vector vector = new Vector();
        for (int i = 1; i < envfonts.length; i++) {
            vector.addElement(envfonts[i]);
        }
        fonts = new JComboBox(vector);
        add(fonts);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("FontSelection");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        AllAvailableFontsComboBox fontSelection = new AllAvailableFontsComboBox();
        f.getContentPane().add(fontSelection, BorderLayout.CENTER);
        f.setSize(new Dimension(350, 250));
        f.setVisible(true);
    }
}
