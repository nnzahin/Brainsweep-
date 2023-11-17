package somethingrandom.interfaceadapters.additem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddItemView {
    public class ViewTest extends JPanel implements ActionListener, PropertyChangeListener {
        private static JPanel panel = new JPanel();
        private static JFrame frame = new JFrame();
        private static JLabel itemTitle = new JLabel();
        private static JTextField itemtitleInput = new JTextField(100);
        private static JLabel itemDisc = new JLabel();
        private static JTextField itemDiscInput = new JTextField(10);
        private static JButton saveItem;

        private final AddItemController itemController;
        private final AddItemViewModel addItemViewModel;

        public AddItemView(AddItemController itemController, AddItemViewModel ItemViewModel ) {
            this.itemController = itemController;
            this.addItemViewModel = additemViewModel;

            JLabel title = new JLabel("ADD ITEM");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);


        }

        public static void main(String[] args) {

            frame.setTitle("Add Item");
            frame.setSize(600,400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);

            panel.setLayout(null);
            itemTitle= new JLabel("Item:");
            itemTitle.setBounds(50, 40, 80, 25);
            panel.add(itemTitle);

            itemtitleInput = new JTextField(100);
            itemtitleInput.setBounds(100, 40, 365, 25);
            panel.add(itemtitleInput);

            itemDisc = new JLabel("Description:");
            itemDisc.setBounds(10, 80, 80, 25);
            panel.add(itemDisc);

            itemDiscInput = new JTextField(100);
            itemDiscInput.setBounds(100, 80, 365,50);
            panel.add(itemDiscInput);

            saveItem = new JButton("Save");
            saveItem.setBounds(250,150,80,25);
            panel.add(saveItem);

            frame.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String itemTitle = itemtitleInput.getText();
            String itemDescription = itemDiscInput.getText();


        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

        }
    }
}
