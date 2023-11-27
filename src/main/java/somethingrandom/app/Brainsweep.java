package somethingrandom.app;

import somethingrandom.dataaccess.google.tasks.GoogleTasksDataAccessObject;
import somethingrandom.entity.ItemFactory;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.usecase.AddItemDataAccessInterface;
import somethingrandom.view.AddItemView;
import somethingrandom.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Brainsweep {
    public static void main(String[] args) {

        JFrame application = new JFrame("Add To-Do List Item");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(cardLayout,views, viewManagerModel);

        AddItemDataAccessInterface addItemDataAccessObject;
        try {
            addItemDataAccessObject = new GoogleTasksDataAccessObject();
        } catch (IOException e){
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
