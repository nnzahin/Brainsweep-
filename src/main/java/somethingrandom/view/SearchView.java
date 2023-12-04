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

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final JTextField searchBar = new JTextField(15);
    private final JButton searchButton;
    private final JLabel noItemError = new JLabel();
    private final SearchController searchController;
    private final JPanel leftPanel = new JPanel();

    public SearchView(SearchController searchController, SearchViewModel searchViewModel) {
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        searchController.execute("");


        // task display list
        leftPanel.setLayout(new GridLayout());

        SearchState searchState = searchViewModel.getState();
        ArrayList<String> defaultTasks = searchState.getResultNames();


        DefaultListModel<String> preDisplay = new DefaultListModel<>();
        if (defaultTasks != null) {
            for (String item : defaultTasks)
                preDisplay.addElement(item);
            JList<String> defaultTaskList = new JList<>(preDisplay);

            leftPanel.add(defaultTaskList);
        }
        else{
            preDisplay.addElement("Add an item!");
            JList<String> emptyTaskList = new JList<String>(preDisplay);
            leftPanel.add(emptyTaskList);

        }



        // puts together search button and bar
        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        searchButton.addActionListener(this);
//            new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    if (evt.getSource().equals(searchButton)) {
//                        SearchState currentState = searchViewModel.getState();
//                        searchController.execute(currentState.getSearchQuery());
//                    }
//                }
//            }
//        );

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
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(searchPanel);
        this.add(leftPanel);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchState currentState = searchViewModel.getState();
        searchController.execute(currentState.getSearchQuery());

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getSource() == searchViewModel){
//            SearchState searchState = searchViewModel.getState();
//            searchState.getResultNames();
//        }

    }
}
