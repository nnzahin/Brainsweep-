package somethingrandom.usecase.add;

public interface AddItemOutputBoundary {
    void prepareSuccessView(String success);

    void prepareFailView();
}
