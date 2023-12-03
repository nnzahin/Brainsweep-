package somethingrandom.view;

import javax.swing.*;
import java.awt.*;

class ItemDetailRow extends JPanel {
    public ItemDetailRow(String name, String value) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("sans-serif", Font.BOLD, 12));
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(nameLabel);

        JTextArea valueLabel = new JTextArea(value);
        valueLabel.setEditable(false);
        valueLabel.setOpaque(false);
        valueLabel.setLineWrap(true);
        valueLabel.setWrapStyleWord(true);
        valueLabel.setAlignmentX(LEFT_ALIGNMENT);
        valueLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));
        add(valueLabel);
    }
}
