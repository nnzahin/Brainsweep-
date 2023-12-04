package somethingrandom.view;
import somethingrandom.interfaceadapters.searchitems.SearchController;
import somethingrandom.interfaceadapters.searchitems.SearchState;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final JTextField searchBar = new JTextField(15);
    private final JLabel noItemError = new JLabel();
    private final SearchController searchController;
    private final JList defaultTaskList = new JList();




    public SearchView(SearchController searchController, SearchViewModel searchViewModel){
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        LabelTextPanel searchBarLabel = new LabelTextPanel(
            new JLabel(SearchViewModel.SEARCH_LABEL), searchBar);

        SearchState searchState = searchViewModel.getState();
        ArrayList<String> defaultTasks = searchState.getResultNames();

        // Can simplify this if I get time
        // this is so we can add strings to JList. I'm sure there's a cleaner way to do this

        DefaultListModel<String> preDisplay = new DefaultListModel();
        for(String item: defaultTasks)
            preDisplay.addElement(item);

        JList<String> displayList = new JList<>(preDisplay);




        searchBar.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    SearchState currentState = searchViewModel.getState();
                    currentState.setSearchQuery(searchBar.getText());
                    searchViewModel.setState(currentState);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            }
        );

        this.setLayout(new CardLayout());

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == searchViewModel) {
            SearchState state = (SearchState) evt.getNewValue();
            if (!state.getSearchError().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getSearchError());
            }
            else {
                setFields(state);
            }
        }
    }

    private void setFields(SearchState searchState){
        // not sure what to do here

    }
    // searchBar.setText(state.getSearchQuery());
    // I don't know if this is necessary since we have property change listeners
    // }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(searchBar)){
            SearchState currentState = searchViewModel.getState();

            searchController.execute(currentState.getSearchQuery());
        }

    }
}
