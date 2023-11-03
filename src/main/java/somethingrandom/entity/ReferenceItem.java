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
     * The Creation date.
     */
    private final Instant creationDate;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Item kind.
     */
    private final ItemKind itemKind;

    /**
     * Instantiates a new Reference item.
     *
     * @param name         the name
     * @param description  the description
     * @param creationDate the creation date
     * @param itemKind     the item kind
     */
    public ReferenceItem(String name, Instant creationDate, String description, ItemKind itemKind){
        this.name = name;
        this.id = UUID.randomUUID();
        this.description = description;
        this.creationDate = creationDate;
        this.itemKind = itemKind;
    }
    public String getName(){
        return this.name;
    }
    public UUID getID(){
        return this.id;
    }
    public String setName(String name){
        this.name = name;
        return this.name;
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
    public void setRemindDate(String description) {
        this.description = description;
    }
    public Instant getCreationDate(){
        return this.creationDate;
    }
    public ItemKind getItemKind(){
        return this.itemKind;
    }
}
