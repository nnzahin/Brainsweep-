package somethingrandom.app;

import okhttp3.OkHttpClient;
import somethingrandom.dataaccess.google.GoogleDataAccessObject;
import somethingrandom.dataaccess.google.auth.LoginFlow;
import somethingrandom.dataaccess.google.auth.S256CodeVerifier;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.interfaceadapters.delete.DeleteItemController;
import somethingrandom.interfaceadapters.delete.DeleteItemPresenter;
import somethingrandom.interfaceadapters.details.ItemDetailsController;
import somethingrandom.interfaceadapters.details.ItemDetailsPresenter;
import somethingrandom.interfaceadapters.details.ItemDetailsViewModel;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.delete.DeleteItemInputBoundary;
import somethingrandom.usecase.delete.DeleteItemInteractor;
import somethingrandom.usecase.delete.DeleteItemOutputBoundary;
import somethingrandom.usecase.details.ItemDetailsInputBoundary;
import somethingrandom.usecase.details.ItemDetailsInteractor;
import somethingrandom.usecase.details.ItemDetailsOutputBoundary;
import somethingrandom.view.AddItemView;
import somethingrandom.view.ItemDetailsView;
import somethingrandom.view.SearchView;
import somethingrandom.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.util.Locale;

public class Brainsweep {
    public static void main(String[] args) {

        /*
        Constructing the main program view
         */
        JFrame brainSweep = new JFrame("Brainsweep");
        brainSweep.setSize(800, 600);
        brainSweep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.LINE_AXIS));
        brainSweep.add(contents);

        OkHttpClient client = new OkHttpClient();
        LoginFlow loginFlow = new LoginFlow(client, ("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"), new S256CodeVerifier(new SecureRandom()), GoogleDataAccessObject.getScopes());
        GoogleDataAccessObject dataAccess;
        try {
            dataAccess = new GoogleDataAccessObject(client, loginFlow.execute(), "Brainsweep");

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        /*
        Search
         */

        ItemDetailsViewModel detailsViewModel = new ItemDetailsViewModel();
        ItemDetailsOutputBoundary detailsPresenter = new ItemDetailsPresenter(detailsViewModel, Locale.getDefault(), ZoneId.systemDefault());
        ItemDetailsInputBoundary detailsUseCase = new ItemDetailsInteractor(dataAccess, detailsPresenter);
        ItemDetailsController detailsController = new ItemDetailsController(detailsUseCase);

        DeleteItemOutputBoundary deleteItemPresenter = new DeleteItemPresenter(detailsViewModel);
        DeleteItemInputBoundary deleteItemUseCase = new DeleteItemInteractor(dataAccess, deleteItemPresenter);
        DeleteItemController deleteItemController = new DeleteItemController(deleteItemUseCase);

        SearchViewModel searchViewModel = new SearchViewModel();
        SearchView searchView = SearchUseCaseFactory.create(searchViewModel, dataAccess, detailsController);
        contents.add(searchView);

        // Add and Details
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BorderLayout());
        JButton addButton = new JButton("Add Item...");
        addButton.setEnabled(false);
        rightPane.add(addButton, BorderLayout.NORTH);
        rightPane.add(new ItemDetailsView(detailsController, detailsViewModel, deleteItemController), BorderLayout.CENTER);
        contents.add(rightPane);

        brainSweep.pack();
        brainSweep.setVisible(true);

        CardLayout cardLayout = new CardLayout();
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


        }

        /*
        Details would add something like brainSweep.add(detailsView) or whatever your detail implementation is
         */

    }

