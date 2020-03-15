import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TESTING SUMMARY DOC.
 */
public class MeetingOrganizer {


    public MeetingOrganizer() {
        //declare objects here
        myMeetingList = new MeetingList();
    }

    private MeetingList myMeetingList;

    void botResponse(String userInput) throws MoException {

        TeamMember member1 = new TeamMember("john");
        TeamMember member2 = new TeamMember("mary");
        switch (userInput) {
            case "1":
                out.println("You have selected new scheduled meeting");
                // Test printTimetable
                boolean[][] mySchedule = new boolean[7][48];
                TextUI.printTimetable(mySchedule);

                member1.addBusyBlocks("test1", 0, "10:00", 2, "12:00");
                printSchedule("member 1 schedule", member1.getSchedule()); //check

                member2.addBusyBlocks("test2", 2, "14:00", 2, "18:00");
                printSchedule("member 2 schedule", member2.getSchedule()); //check

                ArrayList<TeamMember> myScheduleList = new ArrayList<>();
                myScheduleList.add(member1);
                myScheduleList.add(member2);
                ScheduleHandler myScheduleHandler = new ScheduleHandler(myScheduleList);
                Boolean[][] myMasterSchedule = myScheduleHandler.getMasterSchedule();
                printSchedule("master schedule", myMasterSchedule); //check
                myScheduleHandler.printFreeTimings();

                // Test add meeting
                Scanner in = new Scanner(System.in);
                System.out.println("What do you want to name your meeting?");
                String meetingName = in.nextLine(); // eg. CS2113 Meeting
                System.out.println("Enter meeting details: <Start Day> <Start Time> <End Day> <End Time>");
                String input = in.nextLine(); // eg. Monday 19:00 Tuesday 12:30
                String[] meetingDetails = input.split(" ", 4);

                Integer startDay = Integer.parseInt(meetingDetails[0]);
                LocalTime startTime = LocalTime.parse(meetingDetails[1]);
                Integer endDay = Integer.parseInt(meetingDetails[2]);
                LocalTime endTime = LocalTime.parse(meetingDetails[3]);

                myMeetingList.add(new Meeting(meetingName, startDay, startTime, endDay, endTime));

                System.out.println("You now have " + myMeetingList.getMeetingListSize() + " meetings in the list.");
                //

                break;
            case "2":
                out.println("Which meeting slot do you want to delete?");
                member1.deleteBusyBlocks("TESTMEETING");
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 48; j++) {
                        out.print(member1.getSchedule()[i][j]);
                    }
                    out.println();
                }
                break;
            case "3":
                out.println("Which meeting slot do you want to edit?");
                break;
            case "4":
                out.println("Here are all your meeting slots.");
                break;
            default:
                throw new MoException("Unknown command, please try again.");
        }
    }

    private void printSchedule(String scheduleName, Boolean[][] s) {
        out.println(scheduleName);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 48; j++) {
                if (s[i][j]) {
                    out.print("1");
                } else {
                    out.print("0");
                }
            }
            out.println();
        }
        out.println();
    }

    /**
     * Main entry-point for the application.
     */
    public void run() {
        TextUI.introMsg();
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        while (!userInput.equals("5")) {
            try {
                botResponse(userInput);
            } catch (MoException e) {
                TextUI.errorMsg(e);
            } finally {
                userInput = in.nextLine();
            }
        }
        TextUI.exitMsg();
    }

    public static void main(String[] args) {
        new MeetingOrganizer().run();
    }

}

