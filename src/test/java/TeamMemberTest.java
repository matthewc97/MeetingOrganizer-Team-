// CHECKSTYLE:OFF

import static common.Messages.MESSAGE_STARTENDDAY_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDTIME_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDTIME_WRONG_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamMemberTest {
    ArrayList<TeamMember> myMembers;

    @BeforeEach
    public void setUp() {
        myMembers = new ArrayList<>();
    }

    @Test
    public void addBusyBlocks_outOfRangeTime() {
        int validStartDay = 1;
        int validEndDay = 3;
        String validStartTime = "08:30";
        String invalidOutOfRangeEndTime = "24:00";
        String meetingName = "TEST_MEETING";

        myMembers.add(new TeamMember("TEST_MEMBER2"));
        String invalidOutOfRangeEndTime_Message = myMembers.get(1).addBusyBlocks(meetingName, validStartDay, validStartTime, validEndDay, invalidOutOfRangeEndTime);
        assertEquals(invalidOutOfRangeEndTime_Message, MESSAGE_STARTENDTIME_OUT_OF_RANGE);

        myMembers.clear();
    }

    @Test
    public void addBusyBlocks_outOfRangeDay() {
        int invalidOutOfRangeStartDay = -1;
        int validStartDay = 1;
        int invalidOutOfRangeEndDay = 9;
        int validEndDay = 3;
        String validStartTime = "08:30";
        String validEndTime = "12:00";
        String meetingName = "TEST_MEETING";

        myMembers.add(new TeamMember("TEST_MEMBER3"));
        String invalidOutOfRangeEndDay_Message = myMembers.get(2).addBusyBlocks(meetingName, validStartDay, validStartTime, invalidOutOfRangeEndDay, validEndTime);
        assertEquals(invalidOutOfRangeEndDay_Message, MESSAGE_STARTENDDAY_OUT_OF_RANGE);

        myMembers.add(new TeamMember("TEST_MEMBER4"));
        String invalidOutOfRangeStartDay_Message = myMembers.get(3).addBusyBlocks(meetingName, invalidOutOfRangeStartDay, validStartTime, validEndDay, validEndTime);
        assertEquals(invalidOutOfRangeStartDay_Message, MESSAGE_STARTENDDAY_OUT_OF_RANGE);

        myMembers.clear();
    }

    @Test
    public void addBusyBlocks_timeNotInBlocks() {
        String meetingName = "TEST_MEETING";
        int validStartDay = 1;
        int validEndDay = 3;
        String invalidFormatStartTime = "08:35";
        String validEndTime = "12:00";

        myMembers.add(new TeamMember("TEST_MEMBER1"));
        String invalidFormatStartTime_Message = myMembers.get(0).addBusyBlocks(meetingName, validStartDay, invalidFormatStartTime, validEndDay, validEndTime);
        assertEquals(invalidFormatStartTime_Message, MESSAGE_STARTENDTIME_WRONG_FORMAT);

        myMembers.clear();
    }
}
