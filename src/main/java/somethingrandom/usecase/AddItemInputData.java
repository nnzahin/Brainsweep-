package somethingrandom.usecase;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class AddItemInputData {
    private String name;
    private String item;
    private UUID id;
    private Instant creationDate;
    private Duration neededTime;
    private Instant remindDate;

    private String description;

    public AddItemInputData(String item, String description) {
        this.item = item;
        this.description = description;
    }
    /**
     * AddActionable Item Constructor 1
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     */

    public AddItemInputData(String name, UUID id, Instant creationDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = null;
    }
    /**
     * AddActionable Item Constructor 2
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     * @param neededTime    The needed time to complete the item.
     */
    public AddItemInputData(String name, UUID id, Instant creationDate, Duration neededTime) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.neededTime = neededTime;
    }
    /**
     * Add Delayed Item Constructor
     * Create a new item with the following details.
     *
     * @param name         The name of the item.
     * @param id           The id
     * @param creationDate The creation date of the item.
     * @param remindDate   The reminder date of the item.
     */
    public AddItemInputData(String name, UUID id, Instant creationDate, Instant remindDate) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.remindDate = remindDate;
    }
    /**
     * AddReferenceItem Constructor
     * Create a new item with the following details.
     *
     * @param name          The name of the item.
     * @param id            The id of the item
     * @param creationDate  The creation date of the item.
     * @param description   The description of the item.
     */
    public AddItemInputData(String name, UUID id, Instant creationDate, String description) {
        this.name = name;
        this.id = id;
        this.creationDate = creationDate;
        this.description = description;
    }


    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getItem() {return item;}
    public void setItem(String item) {
        this.item = item;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Instant getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Instant creationDate){
        this.creationDate = creationDate;
    }

    public Duration getNeededTime() {
        return neededTime;
    }
    public void setNeededTime(Duration neededTime){
        this.neededTime = neededTime;
    }

    public Instant getRemindDate() {
        return remindDate;
    }
    public void setRemindDate(Instant remindDate){
        this.remindDate = remindDate;
    }

    public String getDescription() {return description;}
    public void setDescription(String description){
        this.description = description;
    }
}
