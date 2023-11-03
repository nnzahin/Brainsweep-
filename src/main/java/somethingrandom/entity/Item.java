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
    public String name;
    /**
     * The ID.
     */
    public UUID id;
    /**
     * The Creation date.
     */
    public Instant creationDate;
    /**
     * The Item kind.
     */
    public ItemKind itemKind;

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public abstract Instant getCreationDate();

    /**
     * Returns name.
     *
     * @return the name
     */
    public abstract String getName();
    /**
     * Returns ID.
     *
     * @return the ID
     */
    public abstract UUID getID();
    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public abstract String setName(String name);

    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public abstract ItemKind getItemKind();
}
