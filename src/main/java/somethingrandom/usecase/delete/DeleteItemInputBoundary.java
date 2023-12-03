package somethingrandom.usecase.delete;

import java.util.UUID;

public interface DeleteItemInputBoundary {

    /**
     * Delete an item from the database.
     *
     * @param id The ID of the item to delete.
     */
    void execute(UUID id);

    /**
     * Delete an item from the database. If there are multiple items
     * with the same name, delete the first occurrence in the database.
     *
     * @param name The name of the item to delete.
     */
    void execute(String name);
}
