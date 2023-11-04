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
    private UUID id;
    /**
     * The Creation date.
     */
    private Instant creationDate;
    /**
     * Returns name.
     *
     * @return the name
     */
    public String getName(){
        return this.name;
    }
    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Instant getCreationDate(){
        return this.creationDate;
    }
    /**
     * Returns ID.
     *
     * @return the ID
     */
    public UUID getID(){
        return this.id;
    }
    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public abstract ItemKind getItemKind();
}
