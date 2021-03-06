package teammember;

import exception.MoException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static common.Messages.MESSAGE_STARTENDTIME_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDDAY_OUT_OF_RANGE;
import static common.Messages.MESSAGE_STARTENDTIME_WRONG_FORMAT;

/**
 * TESTING SUMMARY DOC.
 */

public class TeamMember {
    private static final Boolean MYSCHEDULEBLOCKED = true;
    private static final Boolean MYSCHEDULEFREE = false;
    private String memberName;
    private Boolean[][] mySchedule; //String[7][48]; 7 days, separated into 30mins within 24 hours period.
    private String[][] myScheduleName;
    private boolean isMainUser = false;

    public TeamMember(String name) {
        if (name.contains("_main")) {
            isMainUser = true;
            name = name.replace("_main","");
        }
        this.memberName = name;
        this.mySchedule = new Boolean[7][48];
        this.myScheduleName = new String[7][48];
        for (int i = 0; i < 7; i++) {
            Arrays.fill(mySchedule[i], MYSCHEDULEFREE);
            Arrays.fill(myScheduleName[i], null);

        }
    }

    public void addBusyBlocks(String meetingName, Integer startDay, String startTime, Integer endDay, String endTime) {
        editBlocks(MYSCHEDULEBLOCKED, meetingName, startDay, startTime, endDay, endTime);
    }

    public void addFreeBlocks(String meetingName, Integer startDay, String startTime, Integer endDay, String endTime) {
        editBlocks(MYSCHEDULEFREE, meetingName, startDay, startTime, endDay, endTime);
    }

    public String editBlocks(Boolean blockedorfree, String meetingName, Integer startDay,
                             String startTime, Integer endDay, String endTime) {
        LocalTime localTimeStart;
        LocalTime localTimeEnd;
        try {
            localTimeStart = LocalTime.parse(startTime);
            localTimeEnd = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            System.out.println(MESSAGE_STARTENDTIME_OUT_OF_RANGE);
            return MESSAGE_STARTENDTIME_OUT_OF_RANGE;
        }
        Integer startBlock = 0;
        Integer endBlock = 0;
        try {
            startBlock = getBlocksFromTime(localTimeStart);
            endBlock = getBlocksFromTime(localTimeEnd);
        } catch (MoException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        if (!checkLegitDay(startDay) || !checkLegitDay(endDay)) {
            return MESSAGE_STARTENDDAY_OUT_OF_RANGE;
        }

        Boolean myScheduleStatus = blockedorfree;
        String myScheduleNameStatus = "null";
        if (blockedorfree == MYSCHEDULEBLOCKED) {
            myScheduleNameStatus = meetingName;
        }

        if (!startDay.equals(endDay)) {
            int startDayCopy = startDay; // prevent modifying param arguments
            for (int i = startBlock; i < 48; i++) {
                mySchedule[startDayCopy][i] = myScheduleStatus;
                myScheduleName[startDayCopy][i] = myScheduleNameStatus;
            }
            startDayCopy++;
            while (startDayCopy != endDay) {
                for (int i = 0; i < 48; i++) {
                    mySchedule[startDayCopy][i] = myScheduleStatus;
                    myScheduleName[startDayCopy][i] = myScheduleNameStatus;
                }
                startDayCopy++;
            }
            for (int i = 0; i < endBlock; i++) {
                mySchedule[startDayCopy][i] = myScheduleStatus;
                myScheduleName[startDayCopy][i] = myScheduleNameStatus;
            }
        } else {
            for (int i = startBlock; i < endBlock; i++) {
                mySchedule[startDay][i] = myScheduleStatus;
                myScheduleName[startDay][i] = myScheduleNameStatus;
            }
        }
        return "SUCCESS";
    }

    boolean checkLegitDay(Integer day) {
        return day >= 0 && day <= 6;
    }


    public void deleteBusyBlocks(String meetingName) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (myScheduleName[i][j] != null && myScheduleName[i][j].equals(meetingName)) {
                    mySchedule[i][j] = MYSCHEDULEFREE;
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

    public String[][] getMyScheduleName() {
        return this.myScheduleName;
    }

    public void setMyScheduleName(String[][] myScheduleName) {
        this.myScheduleName = myScheduleName;
    }

    public void setMyScheduleFromScheduleName() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (myScheduleName[i][j].equals("null")) {
                    mySchedule[i][j] = false;
                } else {
                    mySchedule[i][j] = true;
                }
            }
        }
    }

    public void setMainUser() {
        this.isMainUser = true;
    }

    public boolean isMainUser() {
        return isMainUser;
    }
}
