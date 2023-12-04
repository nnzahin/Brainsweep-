package somethingrandom.usecase;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AddInputDataTest{

    Duration duration = Duration.ofHours(5).plusMinutes(33);
    LocalDateTime localDateTime = LocalDateTime.of(2024, Month.JANUARY, 23, 0, 0, 0);
    Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
    AddItemInputData referenceInput = new AddItemInputData("Reference", "Favorite books");
    AddItemInputData actionableInput = new AddItemInputData("Actionable", duration);
    AddItemInputData delayedInput = new AddItemInputData("Delayed", instant);

    @Test
    public void getName(){
        assertEquals(referenceInput.getName(), "Reference");
        assertEquals(delayedInput.getName(), "Delayed");
        assertEquals(actionableInput.getName(), "Actionable");
    }
    @Test
    public void getDescription(){
        assertEquals(referenceInput.getDescription(), "Favorite books");
        assertNull(delayedInput.getDescription());
        assertNull(actionableInput.getDescription());
    }
    @Test
    public void getNeededTime() {

        assertEquals(actionableInput.getNeededTime(), duration);
        assertNull(delayedInput.getNeededTime());
        assertNull(referenceInput.getNeededTime());
    }
    @Test
    public void getRemindDate(){
        assertEquals(delayedInput.getRemindDate(), instant);
        assertNull(referenceInput.getRemindDate());
        assertNull(actionableInput.getRemindDate());
    }

}
