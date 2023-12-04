package somethingrandom.view;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
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
    private final AddItemViewModel addItemViewModel;
    private final JTextField searchBar = new JTextField(15);
    private final JButton searchButton;
    private final JButton addButton;
    private final JLabel noItemError = new JLabel();
    private final SearchController searchController;
    private final JPanel leftPanel = new JPanel();



    public SearchView(SearchController searchController, SearchViewModel searchViewModel){
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        this.addItemViewModel = new AddItemViewModel();
        addItemViewModel.addPropertyChangeListener(this);

        SearchState searchState = searchViewModel.getState();
        ArrayList<String> defaultTasks = searchState.getResultNames();

        DefaultListModel<String> preDisplay = new DefaultListModel<>();
        for(String item: defaultTasks)
            preDisplay.addElement(item);

        JList<String> defaultTaskList = new JList<>(preDisplay);

        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        addButton = new JButton(AddItemViewModel.ADD_BUTTON_LABEL);

        JSplitPane topBar = new JSplitPane();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setLeftComponent(searchPanel);
        topBar.setRightComponent(addButton);


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
        if (e.getSource().equals(searchButton)){
            SearchState currentState = searchViewModel.getState();

            searchController.execute(currentState.getSearchQuery());
        }

    }



}
