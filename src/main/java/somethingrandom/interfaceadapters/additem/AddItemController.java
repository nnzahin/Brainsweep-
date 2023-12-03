package somethingrandom.interfaceadapters.additem;

import somethingrandom.interfaceadapters.ItemDialogController;
import somethingrandom.usecase.AddItemInputBoundary;
import somethingrandom.usecase.AddItemInputData;

import java.time.Duration;
import java.time.Instant;

public class AddItemController implements ItemDialogController {
    private final AddItemInputBoundary addItemUseCaseInteractor;

    public AddItemController(AddItemInputBoundary addItemUseCaseInteractor) {
        this.addItemUseCaseInteractor = addItemUseCaseInteractor;
    }

    @Override
    public void finished(String title, String plannedKind, String description, Instant remindAt, Duration duration) {
        if (title.isEmpty()) {
            return;
        }

        AddItemInputData addItemInputData;
        if (plannedKind.equals("REFERENCE")) {
            addItemInputData = new AddItemInputData(title, description);
        } else if (plannedKind.equals("DELAYED")) {
            addItemInputData = new AddItemInputData(title, remindAt);
        } else if (plannedKind.equals("ACTIONABLE")) {
            addItemInputData = new AddItemInputData(title, duration);
        } else {
            return; // TODO indicate this somehow
        }

        addItemUseCaseInteractor.execute(addItemInputData);
    }
}
