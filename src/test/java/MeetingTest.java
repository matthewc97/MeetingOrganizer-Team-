import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MeetingTest {

    @Test
    public void testStringConversion() throws MoException {
        Meeting m = new Meeting("CS2113T Meeting", 1, LocalTime.parse("14:00"), 1, LocalTime.parse("15:00"));
        assertEquals("CS2113T Meeting, 1, 14:00, 1, 15:00", m.toString());
    }

}
