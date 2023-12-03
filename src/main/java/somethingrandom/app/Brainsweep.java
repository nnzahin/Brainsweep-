package somethingrandom.app;

import okhttp3.OkHttpClient;
import somethingrandom.dataaccess.google.GoogleDataAccessObject;
import somethingrandom.dataaccess.google.auth.AuthenticationException;
import somethingrandom.dataaccess.google.auth.LoginFlow;
import somethingrandom.dataaccess.google.auth.S256CodeVerifier;
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

public class Brainsweep {
    public static void main(String[] args) {
        /*
        Login Info
         */

        OkHttpClient client = new OkHttpClient();
        LoginFlow loginFlow = new LoginFlow(client, System.getenv("OAUTH_CLIENT_SECRET"), new S256CodeVerifier(new SecureRandom()), GoogleDataAccessObject.getScopes());

        /*
        Constructing the main program view
         */
        JFrame brainSweep = new JFrame("Brainsweep");
        GridLayout grid = new GridLayout();
        brainSweep.setLayout(grid);
        brainSweep.setSize(800, 600);
        brainSweep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel left = new JPanel(grid);
        JList<Object> taskList = new JList<>();



        JButton addButton = new JButton("Add+");
        JFrame application = new JFrame("Add To-Do List Item");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(cardLayout,views, viewManagerModel);


        AddItemDataAccessInterface addItemDataAccessObject;
        try {
            addItemDataAccessObject = new GoogleDataAccessObject(client, loginFlow.execute(), "Brainsweep");
        } catch (DataAccessException e){
            throw new RuntimeException(e);
        }

        AddItemViewModel addItemViewModel = new AddItemViewModel();
        AddItemView addItemView = AddItemUseCaseFactory.create(viewManagerModel, addItemViewModel,addItemDataAccessObject );
        views.add(addItemView, addItemView.viewName);



        viewManagerModel.setActiveView(addItemView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);



        /*
        Search
         */
        SearchItemsDataAccessInterface searchItemsDataAccessObject;
        try {
            searchItemsDataAccessObject = new GoogleDataAccessObject(client, loginFlow.execute(), "Brainsweep");
        } catch (DataAccessException e){
            throw new RuntimeException(e);
        }
        SearchViewModel searchViewModel = new SearchViewModel();
        SearchView searchView = SearchUseCaseFactory.create(viewManagerModel, searchViewModel, searchItemsDataAccessObject);
        left.add(searchView, searchView.viewName);

        left.setVisible(true);
        // need to set the default view of the left panel to the list of all tasks...
        // Not sure exactly where to put that as of now







    }
}
