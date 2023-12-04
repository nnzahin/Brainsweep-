package somethingrandom.usecase.edititem;

import somethingrandom.entity.Item;

import java.time.Instant;
import java.time.Duration;
import java.util.UUID;
import java.util.Date;


public class EditItemInputData {
    private Item toEdit;
    private String name;

    private Duration neededTime;
    private Instant remindDate;

    private String description;

    public EditItemInputData(Item item, String name, String description){
        this.toEdit = item;
        this.name = name;
        this.description = description;
    }

    /**
     * Edit Item constructor for editing both the name and the description
     * @param item the item to edit.
     * @param name the name of the item.
     */
    public EditItemInputData(Item item, String name, Instant remindDate)
    {this.name = name;
    this.toEdit = item;
    this.remindDate = remindDate;
   }

   public EditItemInputData(Item item, String name, Duration neededTime){
        this.toEdit = item;
        this.name = name;
        this.neededTime = neededTime;
   }
   public String getName(){return name;}
    public void setNewName(String name) {this.name = name;}
    public Instant getRemindDate(){return remindDate;}
    public void setRemindDate(Instant remindDate) {this.remindDate = remindDate;}

    public String getDescription(){return description;}
    public void setDescription(String description) {this.description = description;}

    public Duration getNeededTime(){return neededTime;}
    public void setNeededTime(Duration neededTime){this.neededTime = neededTime;}

    public UUID getId() {return toEdit.getID();}

    public Instant getCreationDate(){return toEdit.getCreationDate();}

}
