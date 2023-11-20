package somethingrandom.interfaceadapters.additem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddItemView extends JPanel implements ActionListener, PropertyChangeListener {

    private static JPanel panel = new JPanel();
    private static JFrame frame = new JFrame();
    private static JLabel itemTitle = new JLabel();
    private static JTextField itemtitleInput = new JTextField(100);
    private static JLabel itemDisc = new JLabel();
    private static JTextField itemDiscInput = new JTextField(10);
    private static JButton saveItem;
    private final AddItemController itemController;
    private AddItemViewModel addItemViewModel;


    public AddItemView(AddItemController itemController, AddItemViewModel addItemViewModel) {
        this.itemController = itemController;
        this.addItemViewModel = addItemViewModel;

        JLabel title = new JLabel("ADD ITEM");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        saveItem = new JButton();
        buttons.add(saveItem);

        saveItem.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(saveItem)){
                        AddItemState currentState = addItemViewModel.getState();

                        itemController.execute(
                            currentState.getItemName(),
                            currentState.getDescription()
                        );
                    }
                }
            }
        );
        itemtitleInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                AddItemState currentState = addItemViewModel.getState();
                currentState.setItemName(itemtitleInput.getText() + e.getKeyChar());
                addItemViewModel.setState(currentState);

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        itemDiscInput.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    AddItemState currentState = addItemViewModel.getState();
                    currentState.setItemName(itemDiscInput.getText() + e.getKeyChar());
                    addItemViewModel.setState(currentState);

                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            }
        );

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
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AddItemState state = (AddItemState) evt.getNewValue();
        setFields(state);

    }

    private void setFields(AddItemState state) {
        itemtitleInput.setText(state.getItemName());
    }
}
