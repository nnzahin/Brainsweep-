package somethingrandom.app;

import somethingrandom.entity.ItemFactory;
import somethingrandom.interfaceadapters.ViewManagerModel;
import somethingrandom.interfaceadapters.additem.AddItemViewModel;
import somethingrandom.view.AddItemView;
import somethingrandom.view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Brainsweep {
    public static void main(String[] args) {

        JFrame application = new JFrame("Add To-Do List Item");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(cardLayout,views, viewManagerModel);

        AddItemViewModel addItemViewModel = new AddItemViewModel();
        AddItemView addItemView = AddItemUseCaseFactory.create(viewManagerModel, addItemViewModel);
        views.add(addItemView, addItemView.viewName);

        viewManagerModel.setActiveView(addItemView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);





    }
}
