import java.util.ArrayList;

/**
 * TESTING SUMMARY DOC.
 */
public class MeetingList {
    private ArrayList<Meeting> meetingList = new ArrayList<>();

    public void add(Meeting meeting) {
        meetingList.add(meeting);
    }

    public void delete() {

    }

    public ArrayList<Meeting> getMeetingList() {
        return this.meetingList;
    }

    public int getMeetingListSize() {
        return meetingList.size();
    }

    public Boolean isDone() {
        return true;
    }
}
