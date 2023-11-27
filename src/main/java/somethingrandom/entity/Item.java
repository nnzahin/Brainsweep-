package somethingrandom.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * An abstract Item.
 */
public abstract class Item {

    /**
     * The Name.
     */
    private String name;

    /**
     * The ID.
     */
    private final UUID id;

    /**
     * The Creation date.
     */
    private final Instant creationDate;

    public Item(String name, UUID id, Instant creationDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
    }

    /**
     * Returns name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Instant getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns ID.
     *
     * @return the ID
     */
    public UUID getID() {
        return this.id;
    }

    /**
     * Sets name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public abstract String getItemKind();

    public String toString(){
        return "Item: " + this.name + "\n Item kind: " + this.getItemKind() + "\n Creation Date: " + this.creationDate;
    }
}
