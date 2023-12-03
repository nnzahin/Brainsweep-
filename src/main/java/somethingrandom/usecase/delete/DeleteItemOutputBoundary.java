package somethingrandom.usecase.delete;

public interface DeleteItemOutputBoundary {
    /**
     * Called when the item is deleted, or attempted to be deleted.
     */
    void presentSuccessView(String message);

    void presentFailureView(String message);

}
