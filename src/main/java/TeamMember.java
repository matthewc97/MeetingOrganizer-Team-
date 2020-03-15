import static common.Messages.MESSAGE_STARTENDDAY_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDTIME_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDTIME_WRONG_FORMAT;
import static java.lang.System.out;


import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * TESTING SUMMARY DOC.
 */

public class TeamMember {
    private final Boolean myScheduleBlocked = true;
    private final Boolean myScheduleFree = false;
    private String memberName;
    private Boolean[][] mySchedule; //String[7][48]; 7 days, separated into 30mins within 24 hours period.
    private String[][] myScheduleName;

    public TeamMember(String name) {
        this.memberName = name;
        this.mySchedule = new Boolean[7][48];
        this.myScheduleName = new String[7][48];
        for (int i = 0; i < 7; i++) {
            Arrays.fill(mySchedule[i], myScheduleFree); // fill every 48 index of the 7 days with 0 initially
            Arrays.fill(myScheduleName[i], null);

        }
    }

    public String addBusyBlocks(String meetingName, Integer startDay, String startTime, Integer endDay, String endTime) {
        LocalTime localTimeStart;
        LocalTime localTimeEnd;
        try {
            localTimeStart = LocalTime.parse(startTime);
            localTimeEnd = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            out.println(MESSAGE_STARTENDTIME_OUT_OF_RANGE);
            return MESSAGE_STARTENDTIME_OUT_OF_RANGE;
        }
        Integer startBlock = 0;
        Integer endBlock = 0;
        try {
            startBlock = getBlocksFromTime(localTimeStart);
            endBlock = getBlocksFromTime(localTimeEnd);
        } catch (MoException e) {
            out.println(e.getMessage());
            return e.getMessage();
        }

        if (!checkLegitDay(startDay) || !checkLegitDay(endDay)) {
            return MESSAGE_STARTENDDAY_OUT_OF_RANGE;
        }

        if (!startDay.equals(endDay)) {
            int startDayCopy = startDay; // prevent modifying param aruguments
            for (int i = startBlock; i < 48; i++) {
                mySchedule[startDayCopy][i] = myScheduleBlocked;
                myScheduleName[startDayCopy][i] = meetingName;
            }
            startDayCopy++;
            while (startDayCopy != endDay) {
                for (int i = 0; i < 48; i++) {
                    mySchedule[startDayCopy][i] = myScheduleBlocked;
                    myScheduleName[startDayCopy][i] = meetingName;
                }
                startDayCopy++;
            }
            for (int i = 0; i < endBlock; i++) {
                mySchedule[startDayCopy][i] = myScheduleBlocked;
                myScheduleName[startDayCopy][i] = meetingName;
            }
        } else {
            for (int i = startBlock; i < endBlock; i++) {
                mySchedule[startDay][i] = myScheduleBlocked;
                myScheduleName[startDay][i] = meetingName;
            }
        }
        return "Success";
    }

    boolean checkLegitDay(Integer day) {
        return day >= 0 && day <= 6;
    }


    public void deleteBusyBlocks(String meetingName) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (myScheduleName[i][j].equals(meetingName)) {
                    mySchedule[i][j] = myScheduleFree;
                    myScheduleName[i][j] = null;

                }
            }
        }
    }

    public Integer getBlocksFromTime(LocalTime myTime) throws MoException {
        int minuteBlocks = -1;
        int hourBlocks = -1;
        switch (myTime.getMinute()) {
        case 0:
            minuteBlocks = 0;
            break;
        case 30:
            minuteBlocks = 1;
            break;
        default:
            throw new MoException(MESSAGE_STARTENDTIME_WRONG_FORMAT);

        }
        hourBlocks = myTime.getHour() * 2;
        return minuteBlocks + hourBlocks;
    }

    public String getName() {
        return this.memberName;
    }

    public Boolean[][] getSchedule() {
        return this.mySchedule;
    }


}
