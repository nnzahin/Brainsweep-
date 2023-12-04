package somethingrandom.app;

import okhttp3.OkHttpClient;
import somethingrandom.dataaccess.google.GoogleDataAccessObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.dataaccess.google.auth.LoginFlow;
import somethingrandom.dataaccess.google.auth.S256CodeVerifier;
import somethingrandom.entity.Item;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.search.SearchItemsDataAccessInterface;
import somethingrandom.view.AddItemView;
import somethingrandom.view.SearchView;
import somethingrandom.view.ViewManager;
import somethingrandom.app.SearchUseCaseFactory;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

public class Brainsweep {
    public static void main(String[] args) {

        /*
        Constructing the main program view
         */
        JFrame brainSweep = new JFrame("Brainsweep");
        GridLayout grid = new GridLayout();
        brainSweep.setLayout(grid);
        brainSweep.setSize(800, 600);
        brainSweep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*
        Left panel task display
         */

        JPanel left = new JPanel(grid);
        brainSweep.add(left);


        CardLayout cardLayout = new CardLayout();




        OkHttpClient client = new OkHttpClient();
        LoginFlow loginFlow = new LoginFlow(client, ("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"), new S256CodeVerifier(new SecureRandom()), GoogleDataAccessObject.getScopes());
        GoogleDataAccessObject dataAccess;
        try {
            dataAccess = new GoogleDataAccessObject(client, loginFlow.execute(), "Brainsweep");

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }


        /*
        Add
         */
        JPanel views = new JPanel(cardLayout);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(cardLayout, views, viewManagerModel);

        JFrame application = new JFrame("Add To-Do List Item");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(views);

        AddItemViewModel addItemViewModel = new AddItemViewModel();
        AddItemView addItemView = AddItemUseCaseFactory.create(viewManagerModel, addItemViewModel, dataAccess);
        views.add(addItemView, addItemView.viewName);

        viewManagerModel.setActiveView(addItemView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);

        /*
        Search
         */

        SearchViewModel searchViewModel = new SearchViewModel();
        SearchView searchView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, dataAccess);

        JLabel emptyMessage = new JLabel("Add an item.");
        left.add(emptyMessage);
        emptyMessage.setVisible(false);
        // There's probably a more efficient way to do this
        // It's for when there are no items if I did SearchView correctly

        if (searchView == null){
            emptyMessage.setVisible(true);
        }
        // This should make The JList appear on the left panel if they have tasks
        else{
            left.add(searchView, searchView.viewName);

            left.setVisible(true);

            }
        }
    }

