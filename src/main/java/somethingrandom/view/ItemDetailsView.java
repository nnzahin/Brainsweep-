package somethingrandom.view;

import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.interfaceadapters.details.ItemDetailsController;
import somethingrandom.interfaceadapters.details.ItemDetailsPresenter;
import somethingrandom.interfaceadapters.details.ItemDetailsViewModel;
import somethingrandom.usecase.DataAccessException;
import somethingrandom.usecase.details.ItemDetailsDataAccessInterface;
import somethingrandom.usecase.details.ItemDetailsInputBoundary;
import somethingrandom.usecase.details.ItemDetailsInteractor;
import somethingrandom.usecase.details.ItemDetailsOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ItemDetailsView extends JPanel implements PropertyChangeListener {
    private final ItemDetailsController controller;
    private final ItemDetailsViewModel viewModel;

    private final JPanel itemContainer;
    private final JPanel detailRows;
    private final JLabel errorMessage;

    public ItemDetailsView(ItemDetailsController controller, ItemDetailsViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new OverlayLayout(this));

        JPanel detailRowsAnchor = new JPanel();
        detailRowsAnchor.setLayout(new BorderLayout());

        detailRows = new JPanel();
        detailRows.setLayout(new GridLayout(0, 1, 0, 12));
        detailRowsAnchor.add(detailRows, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(detailRowsAnchor);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.LEADING));

        JButton edit = new JButton(ItemDetailsViewModel.EDIT_LABEL);
        edit.addActionListener((event) -> openEditDialog());
        buttons.add(edit);

        JButton delete = new JButton(ItemDetailsViewModel.DELETE_LABEL);
        delete.setEnabled(false);
        buttons.add(delete);

        itemContainer = new JPanel();
        itemContainer.setLayout(new BorderLayout());
        itemContainer.setVisible(false);
        itemContainer.add(buttons, BorderLayout.NORTH);
        itemContainer.add(scrollPane, BorderLayout.CENTER);
        add(itemContainer);

        errorMessage = new JLabel();
        errorMessage.setVisible(true);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        errorMessage.setAlignmentY(CENTER_ALIGNMENT);
        errorMessage.setText(viewModel.getErrorMessage());
        add(errorMessage);

        viewModel.addPropertyChangeListener(this);
        showState(viewModel.getState());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("state")) {
            showState((Map<String, String>) propertyChangeEvent.getNewValue());
        }

        if (propertyChangeEvent.getPropertyName().equals("errorMessage")) {
            errorMessage.setText((String) propertyChangeEvent.getNewValue());
        }
    }

    private void showState(Map<String, String> newValue) {
        if (newValue == null) {
            itemContainer.setVisible(false);
            errorMessage.setVisible(true);
            return;
        }

        itemContainer.setVisible(true);
        errorMessage.setVisible(false);

        detailRows.removeAll();
        for (String key : newValue.keySet().stream().sorted().toList()) {
            detailRows.add(new ItemDetailRow(key, newValue.get(key)));
        }

        detailRows.repaint();
        detailRows.revalidate();
    }

    public void openEditDialog() {
        System.out.println("Edit!");
    }

    private static class DummyDAO implements ItemDetailsDataAccessInterface {
        @Override
        public Optional<Item> getItemById(UUID uuid) throws DataAccessException {
            return Optional.of(new ReferenceItem("Example Item", uuid, Instant.now(), "Description, which could totally be a little bit long."));
        }
    }

    public static void main(String[] args) {
        JFrame application = new JFrame("Details Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.setMinimumSize(new Dimension(200, 300));
        application.setSize(new Dimension(200, 300));

        ItemDetailsViewModel viewModel = new ItemDetailsViewModel();
        ItemDetailsOutputBoundary presenter = new ItemDetailsPresenter(viewModel, Locale.getDefault(), ZoneId.systemDefault());
        ItemDetailsInputBoundary usecase = new ItemDetailsInteractor(new DummyDAO(), presenter);
        ItemDetailsController controller = new ItemDetailsController(usecase);
        JPanel jp = new ItemDetailsView(controller, viewModel);
        application.add(jp);
        application.pack();
        application.setVisible(true);

        controller.requestDetails(UUID.randomUUID());
    }
}
