package somethingrandom.view;

import somethingrandom.interfaceadapters.additem.AddItemController;
import somethingrandom.interfaceadapters.additem.AddItemState;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddItemView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add item";
    private final AddItemViewModel addItemViewModel;
    private final JTextField itemTitleInput = new JTextField(100);
    private final JTextField itemDiscInput = new JTextField(10);
    private final AddItemController addItemController;
    private final JButton saveItem;

    //private static JPanel panel = new JPanel();
    //private static JFrame frame = new JFrame();
    //private static JLabel itemTitle = new JLabel();

    //private static JLabel itemDisc = new JLabel();

    public AddItemView(AddItemController itemController, AddItemViewModel addItemViewModel) {
        this.addItemController = itemController;
        this.addItemViewModel = addItemViewModel;
        addItemViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("ADD ITEM");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel itemTitleInfo = new LabelTextPanel(
            new JLabel(AddItemViewModel.TITLE_LABEL), itemTitleInput);
        LabelTextPanel itemDiscInfo = new LabelTextPanel(
            new JLabel(AddItemViewModel.DISC_LABEL), itemDiscInput);

        JPanel buttons = new JPanel();
        saveItem = new JButton(AddItemViewModel.SAVE_BUTTON_LABEL);
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
        itemTitleInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                AddItemState currentState = addItemViewModel.getState();
                currentState.setItemName(itemTitleInput.getText() + e.getKeyChar());
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
                    currentState.setDescription(itemDiscInput.getText() + e.getKeyChar());
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(itemTitleInfo);
        this.add(itemDiscInfo);
        this.add(buttons);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == addItemViewModel) {
            AddItemState state = (AddItemState) evt.getNewValue();
            setFields(state);
        }

    }
    private void setFields(AddItemState state) {
        itemTitleInput.setText(state.getItemName());
    }
}
