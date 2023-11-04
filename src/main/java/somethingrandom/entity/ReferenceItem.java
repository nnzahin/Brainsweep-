package somethingrandom.entity;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Reference item.
 */
public class ReferenceItem extends Item {
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
    /**
     * The Description.
     */
    private String description;

    /**
     * Instantiates a new Reference item.
     *
     * @param name         the name
     * @param description  the description
     * @param creationDate the creation date
     */
    public ReferenceItem(String name, Instant creationDate, String description){
        this.name = name;
        this.id = UUID.randomUUID();
        this.description = description;
        this.creationDate = creationDate;
    }
    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public ItemKind getItemKind(){
        return ItemKind.REFERENCE;
    }
    /**
     * Get the item's description string.
     *
     * @return the description
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * Sets description.
     *
     * @param description the description date
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
