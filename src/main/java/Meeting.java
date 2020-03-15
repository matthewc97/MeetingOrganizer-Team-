import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TESTING SUMMARY DOC.
 */
public class Meeting {
    private String meetingName;
    private Integer startDay;
    private LocalTime startTime;
    private Integer endDay;
    private LocalTime endTime;

    public Meeting(String meetingName, Integer startDay, LocalTime startTime, Integer endDay, LocalTime endTime) throws MoException {

        if (startDay < 0 || startDay > 6 || endDay < 0 || endDay > 6) {
            throw new MoException("Start and/or End Day out of range.");
        }

        this.meetingName = meetingName;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void editTime() {

    }

    public String toString() {
        return meetingName + ", " + startDay + ", " + startTime + ", " + endDay + ", " + endTime;
    }
}
