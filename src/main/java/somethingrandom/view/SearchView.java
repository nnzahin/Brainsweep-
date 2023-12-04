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

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final JTextField searchBar = new JTextField(15);
    private final JLabel noItemError = new JLabel();
    private final SearchController searchController;

    public SearchView(SearchController searchController, SearchViewModel searchViewModel){
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        LabelTextPanel searchBarLabel = new LabelTextPanel(
            new JLabel(SearchViewModel.SEARCH_LABEL), searchBar);

        SearchState searchState = searchViewModel.getState();
        ArrayList<String> defaultTasks = searchState.getResultNames();

        DefaultListModel<String> preDisplay = new DefaultListModel();
        for(String item: defaultTasks)
            preDisplay.addElement(item);

        JList<String> defaultTaskList = new JList<>(preDisplay);

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
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(searchBar)){
            SearchState currentState = searchViewModel.getState();

            searchController.execute(currentState.getSearchQuery());
        }

    }
}
