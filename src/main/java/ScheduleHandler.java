import static java.lang.System.out;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TESTING SUMMARY DOC.
 */

public class ScheduleHandler {
    private final Boolean myScheduleBlocked = true;
    private final Boolean myScheduleFree = false;
    private Boolean[][] masterSchedule = new Boolean[7][48];
    // ArrayList of free slots in Integer type {startDay, startBlock, endDay, endBlock}
    private ArrayList<ArrayList<Integer>> freeBlocks = new ArrayList<ArrayList<Integer>>();

    public ScheduleHandler(ArrayList<TeamMember> teamMemberList) {
        for (int i = 0; i < 7; i++) {
            Arrays.fill(masterSchedule[i], myScheduleFree); // fill every 48 index of the 7 days with 0 initially
        }
        for (TeamMember t : teamMemberList) {
            Boolean[][] memberSchedule = t.getSchedule();
            fillMasterSchedule(memberSchedule);
        }
        updateFreeBlocks();
    }

    public Boolean[][] getMasterSchedule() {
        return masterSchedule;
    }

    public void fillMasterSchedule(Boolean[][] s) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (s[i][j]) {
                    masterSchedule[i][j] = myScheduleBlocked;
                }
            }
        }
    }


    public void updateFreeBlocks() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (masterSchedule[i][j] == myScheduleFree) {
                    boolean change = false;
                    boolean end = false;
                    while (masterSchedule[i][j] == myScheduleFree) {
                        if (change) {
                            change = false;
                        }
                        if (i == 6 && j == 47) {
                            end = true;
                            break;
                        }
                        if (j == 47) {
                            change = true;
                            j = 0;
                            i++;
                        }
                        j++;
                    }
                    int startDay = i;
                    int startBlock = j;
                    int endDay = i;
                    int endBlock = j - 1;
                    if (change) {
                        endBlock = 47;
                    }
                    if (end) {
                        endBlock = 47;
                        endDay = 0;
                    }
                    ArrayList<Integer> freeSlot = makeSlot(startDay, startBlock, endDay, endBlock);
                    this.freeBlocks.add(freeSlot);
                }
            }
        }
        correctFirstAndLastSlotContinuation();
    }

    private ArrayList<Integer> makeSlot(int startDay, int startBlock, int endDay, int endBlock) {
        ArrayList<Integer> freeSlot = new ArrayList<Integer>();
        freeSlot.add(startDay);
        freeSlot.add(startBlock);
        freeSlot.add(endDay);
        freeSlot.add(endBlock);
        return freeSlot;
    }

    private void correctFirstAndLastSlotContinuation() {
        int size = this.freeBlocks.size();
        if (size > 1) {
            Boolean isSunday = this.freeBlocks.get(0).get(0).equals(this.freeBlocks.get(size - 1).get(2));
            Boolean isMidnight = this.freeBlocks.get(0).get(1).equals(0)
                && this.freeBlocks.get(size - 1).get(3).equals(47);
            Boolean isSundayMidnightOverlap = isSunday && isMidnight;
            if (isSundayMidnightOverlap) {
                Integer newStartDay = this.freeBlocks.get(size - 1).get(0);
                Integer newStartBlock = this.freeBlocks.get(size - 1).get(1);
                Integer newEndDay = this.freeBlocks.get(0).get(2);
                Integer newEndBlock = this.freeBlocks.get(0).get(3);
                ArrayList<Integer> newFreeSlot = makeSlot(newStartDay, newStartBlock, newEndDay, newEndBlock);
                this.freeBlocks.set(0, newFreeSlot);
                this.freeBlocks.remove(size - 1);
            }
        }
    }


    public void printFreeTimings() {
        for (ArrayList<Integer> freeBlock : this.freeBlocks) {
            String startDay = getDayFromNumber(freeBlock.get(0));
            LocalTime startTime = getTimeFromBlock(freeBlock.get(1).intValue(), "START");
            String endDay = getDayFromNumber(freeBlock.get(2));
            LocalTime endTime = getTimeFromBlock(freeBlock.get(3), "END");
            out.println(startDay + " " + startTime + " to " + endDay + " " + endTime);
        }
    }

    public String getDayFromNumber(int dayNum) {
        String day;
        switch (dayNum) {
        case 0:
            day = "Sunday";
            break;
        case 1:
            day = "Monday";
            break;
        case 2:
            day = "Tuesday";
            break;
        case 3:
            day = "Wednesday";
            break;
        case 4:
            day = "Thursday";
            break;
        case 5:
            day = "Friday";
            break;
        case 6:
            day = "Saturday";
            break;
        default:
            day = "";
        }
        return day;
    }

    public LocalTime getTimeFromBlock(int blockNum, String startOrEnd) {
        int minuteTime = 0;
        int hourTime = 0;

        switch (startOrEnd) {
        case "START":
            hourTime = blockNum / 2;
            if (blockNum % 2 == 1) {
                minuteTime = 30;
            }
            break;
        case "END":
            if (blockNum == 47) {
                hourTime = 0;
            } else {
                hourTime = (blockNum + 1) / 2;
            }

            if (blockNum % 2 == 0) {
                minuteTime = 30;
            }
            break;
        default:
        }

        LocalTime myTime = LocalTime.of(hourTime, minuteTime);
        return myTime;
    }

    public ArrayList<ArrayList<Integer>> getFreeBlocks() {
        return this.freeBlocks;
    }

}