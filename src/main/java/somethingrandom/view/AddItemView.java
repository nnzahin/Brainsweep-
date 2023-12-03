package somethingrandom.view;

import somethingrandom.interfaceadapters.ItemDialogController;
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
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.SortedMap;
import java.util.TreeMap;

public class AddItemView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add item";
    private final AddItemViewModel addItemViewModel;
    private final JTextField itemTitleInput = new JTextField(100);
    private final JTextField itemDiscInput = new JTextField(10);
    private final ItemDialogController controller;
    private final JButton saveItem;

    private final SortedMap<String, Duration> presets = new TreeMap<>();

    public AddItemView(ItemDialogController itemController, AddItemViewModel addItemViewModel) {
        this.controller = itemController;
        this.addItemViewModel = addItemViewModel;
        addItemViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("ADD ITEM");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel itemTitleInfo = new LabelTextPanel(
            new JLabel(AddItemViewModel.TITLE_LABEL), itemTitleInput);

        JPanel additional = new JPanel();
        additional.setLayout(new GridLayout(0, 2, 16, 8));

        JRadioButton referenceRadio = new JRadioButton(AddItemViewModel.REFERENCE);
        additional.add(referenceRadio);
        referenceRadio.setActionCommand("REFERENCE");
        additional.add(itemDiscInput);

        JSpinner datePicker = new JSpinner();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        datePicker.setModel(dateModel);
        datePicker.setEditor(new JSpinner.DateEditor(datePicker));
        JRadioButton delayedRadio = new JRadioButton(AddItemViewModel.DELAYED);
        additional.add(delayedRadio);
        delayedRadio.setActionCommand("DELAYED");
        additional.add(datePicker);

        presets.put("5 minutes", Duration.of(5, ChronoUnit.MINUTES));
        presets.put("30 minutes", Duration.of(30, ChronoUnit.MINUTES));
        presets.put("1 hour", Duration.of(1, ChronoUnit.HOURS));
        presets.put("2 hours", Duration.of(2, ChronoUnit.HOURS));
        presets.put("4 hours", Duration.of(4, ChronoUnit.HOURS));
        presets.put("1 day", Duration.of(4, ChronoUnit.DAYS));

        JComboBox<String> durations = new JComboBox<>(presets.keySet().toArray(new String[0]));
        JRadioButton actionableRadio = new JRadioButton(AddItemViewModel.ACTIONABLE);
        additional.add(actionableRadio);
        actionableRadio.setActionCommand("ACTIONABLE");
        additional.add(durations);

        ButtonGroup group = new ButtonGroup();
        group.add(actionableRadio);
        group.add(delayedRadio);
        group.add(referenceRadio);

        JPanel buttons = new JPanel();
        saveItem = new JButton(AddItemViewModel.SAVE_BUTTON_LABEL);
        buttons.add(saveItem);

        saveItem.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(saveItem)){
                        AddItemState currentState = addItemViewModel.getState();

                        itemController.finished(
                            currentState.getItemName(),
                            group.getSelection().getActionCommand(),
                            currentState.getDescription(),
                            dateModel.getDate().toInstant(),
                            presets.get((String) durations.getSelectedItem())
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
        this.add(additional);
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
