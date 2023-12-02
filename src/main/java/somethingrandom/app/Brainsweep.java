package somethingrandom.app;

import okhttp3.OkHttpClient;
import somethingrandom.dataaccess.google.GoogleDataAccessObject;
import somethingrandom.dataaccess.google.auth.LoginFlow;
import somethingrandom.dataaccess.google.auth.S256CodeVerifier;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.view.AddItemView;
import somethingrandom.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;

public class Brainsweep {
    public static void main(String[] args) {

        /*
        Constructing the main program view
         */
        JFrame mainWindow = new JFrame("Brainsweep");
        GridLayout grid = new GridLayout();
        mainWindow.setLayout(grid);
        mainWindow.setSize(800, 600);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton addButton = new JButton("Add+");
        JFrame application = new JFrame("Add To-Do List Item");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(cardLayout,views, viewManagerModel);

        OkHttpClient client = new OkHttpClient();
        LoginFlow loginFlow = new LoginFlow(client, System.getenv("OAUTH_CLIENT_SECRET"), new S256CodeVerifier(new SecureRandom()), GoogleDataAccessObject.getScopes());
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





    }
}
