package somethingrandom.view;

import somethingrandom.interfaceadapters.additem.AddItemState;
import somethingrandom.interfaceadapters.searchitems.SearchState;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final JTextField searchBar = new JTextField(15);

    private final JLabel noItemError = new JLabel();
    private final SearchController searchController;

    private final JPanel result = new JPanel();

    private final JLabel itemResultName = new JLabel();



    public SearchView(SearchController searchController, SearchViewModel searchViewModel, ListItemsViewModel listItemsViewModel){
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;

        searchViewModel.addPropertyChangeListener(this);


        LabelTextPanel searchInfo = new LabelTextPanel(
            new JLabel(SearchViewModel.SEARCH_LABEL), searchBar);



        searchBar.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(searchBar)) {
                        SearchState currentState = searchViewModel.getState();
                        searchController.execute(
                            currentState.getSearchQuery()
                        );
                    }
                }
            }
        );

        this.setLayout(new CardLayout());




    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == searchViewModel) {
            SearchState state = (SearchState) evt.getNewValue();
            if (state.getSearchError() != null) {
                JOptionPane.showMessageDialog(this, state.getSearchError());
            }
            else {
                setFields(state);
            }
        }

    }
    private void setFields(SearchState state) {
        itemResultName.setText(state.getSearchQuery());
        // may need to change if we allow search to be more flexible;
        // allowing search by description
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not sure what to do here

    }
}
