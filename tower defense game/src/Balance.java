import javax.swing.*;
import java.awt.*;

public class Balance extends JPanel {
    private JTextField text1;
    private JTextField text2;
    private JTextField text3;

    public Balance(JTextField text1, JTextField text2){
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = new JTextField("");

        setLayout(new GridLayout(1, 3));
        add(this.text1);
        add(this.text2);
        add(text3);
    }

    public void setText1(String text) {
        text1.setText(text);
    }

    public void setText2(String text) {
        text2.setText(text);
    }

    public void setText3(String text) {
        text3.setText(text);
    }
}
