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
    private final SearchController searchController;
    private final DefaultListModel<String> taskModel = new DefaultListModel<>();

    public SearchView(SearchController searchController, SearchViewModel searchViewModel) {
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        searchViewModel.addPropertyChangeListener(this);

        searchController.execute("");
        System.out.println(searchViewModel.getState().getResultNames());


        SearchState searchState = searchViewModel.getState();
        ArrayList<String> defaultTasks = searchState.getResultNames();

        // puts together search button and bar
        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.NORTH);

        JList<String> taskList = new JList<>(taskModel);

        add(taskList, BorderLayout.CENTER);

        searchButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(searchButton)) {
                        SearchState currentState = searchViewModel.getState();
                        searchController.execute(currentState.getSearchQuery());
                    }
                }
            }
        );

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
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        System.out.println("Click " + e.getActionCommand());

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == searchViewModel) {
            SearchState state = (SearchState) evt.getNewValue();
            if (state.getSearchError() != null) {
                JOptionPane.showMessageDialog(this, state.getSearchError());
                return;
            }

            taskModel.removeAllElements();
            for (String item : state.getResultNames())
                taskModel.addElement(item);
        }

    }
}
