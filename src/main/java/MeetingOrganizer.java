import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MeetingOrganizer {
    public MeetingOrganizer() {
        //declare objects here
        myMeetingList = new MeetingList();
    }

    private MeetingList myMeetingList;

    public void botResponse(String userInput) throws MoException {
        switch (userInput) {
            case "1":
                System.out.println("You have selected new scheduled meeting");

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
                System.out.println("Which meeting slot do you want to delete?");
                break;
            case "3":
                System.out.println("Which meeting slot do you want to edit?");
                break;
            case "4":
                System.out.println("Here are all your meeting slots.");
                break;
            default:
                throw new MoException("Unknown command, please try again.");
        }
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

