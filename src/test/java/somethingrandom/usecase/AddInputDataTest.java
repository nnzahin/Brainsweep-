package somethingrandom.usecase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.*;
public class AddInputDataTest{

    Duration duration = Duration.ofHours(5).plusMinutes(33);
    LocalDateTime localDateTime = LocalDateTime.of(2024, Month.JANUARY, 23, 0, 0, 0);
    Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    AddItemInputData referenceInput = new AddItemInputData("Reference", "Favorite books");
    AddItemInputData actionableInput = new AddItemInputData("Actionable", duration);
    AddItemInputData delayedInput = new AddItemInputData("Delayed", instant);

    @Test
    public void getName(){

        Assertions.assertEquals(referenceInput.getName(), "Reference");
        Assertions.assertEquals(delayedInput.getName(), "Delayed");
        Assertions.assertEquals(actionableInput.getName(), "Actionable");
    }
    @Test
    public void getDescription(){
        Assertions.assertEquals(referenceInput.getDescription(), "Favorite books");
        Assertions.assertNull(delayedInput.getDescription());
        Assertions.assertNull(actionableInput.getDescription());
    }
    @Test
    public void getNeededTime() {

        Assertions.assertEquals(actionableInput.getNeededTime(), duration);
        Assertions.assertNull(delayedInput.getNeededTime());
        Assertions.assertNull(referenceInput.getNeededTime());
    }
    @Test
    public void getRemindDate(){
        Assertions.assertEquals(delayedInput.getRemindDate(), instant);
        Assertions.assertNull(referenceInput.getRemindDate());
        Assertions.assertNull(actionableInput.getRemindDate());
    }

}
