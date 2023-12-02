package somethingrandom.usecase.edititem;

public interface EditItemOutputBoundary {
    void prepareSuccessView();

    void prepareFailView(String success);

}
