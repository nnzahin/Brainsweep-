package view;
import interfaceadapters.listitems.ActionableItems.ListActionableItemsState;
import interfaceadapters.listitems.ListItemsState;
import interfaceadapters.listitems.ListItemsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ListItemsView extends JPanel implements ActionListener, PropertyChangeListener {

        public final String viewName = "logged in";
        private final ListItemsViewModel listItemsViewModel;

        JLabel ActionableItems;
        JLabel ReferenceItems;
        JLabel DelayedItems;

        final JButton referenceItems;

        final JButton actionableItems;
        final JButton delayedItems;
        final JButton Search;
        final JButton Add;

        final JLabel title;

        /**
         * A window with a title and a JButton.
         */
    public ListItemsView(ListItemsViewModel listItemsViewModel, JLabel title) {
            this.listItemsViewModel = listItemsViewModel;
            this.listItemsViewModel.addPropertyChangeListener(this);
            // I'm hoping this will allow us to set the title based on the item type but
        // idk if that's how it works
            this.title = title;

            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            ActionableItems = new JLabel();
            DelayedItems = new JLabel();
            ReferenceItems = new JLabel();


            JPanel buttons = new JPanel();

            referenceItems = new JButton(listItemsViewModel.REFERENCE_ITEMS_BUTTON_LABEL);
            actionableItems = new JButton(ListItemsViewModel.ACTIONABLE_ITEMS_BUTTON_LABEL);
            delayedItems = new JButton(listItemsViewModel.DELAYED_ITEMS_BUTTON_LABEL);
            Add = new JButton(ListItemsViewModel.ADD_BUTTON_LABEL);
            Search = new JButton(ListItemsViewModel.SEARCH_BUTTON_LABEL);

            buttons.add(referenceItems);
            buttons.add(actionableItems);
            buttons.add(delayedItems);
            buttons.add(Add);
            buttons.add(Search);

            referenceItems.addActionListener(this);
            actionableItems.addActionListener(this);
            delayedItems.addActionListener(this);
            Add.addActionListener(this);
            Search.addActionListener(this);

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



            this.add(title);
            this.add(ActionableItems);
            // don't forget to come back and figure out how to change the title based on which item we're viewing
            this.add(buttons);
        }

        /**
         * React to a button click that results in evt.
         */
        public void actionPerformed (ActionEvent evt){
            System.out.println("Click " + evt.getActionCommand());
        }

        @Override
        public void propertyChange (PropertyChangeEvent evt){
            ListItemsState state = (ListItemsState) evt.getNewValue();
            setFields(state);


            }

    private void setFields(ListItemsState state) {
        // TODO
    }
    }

