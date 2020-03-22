import java.util.ArrayList;

/**
 * Stores team members in a team member list.
 * Contains an ArrayList object as the team member list.
 * Has constructor and getter methods for the team member list.
 *
 * @see TeamMember
 */

public class TeamMemberList {

    private static ArrayList<TeamMember> teamMemberList;


    public TeamMemberList(ArrayList<TeamMember> tl) {
        this.teamMemberList = tl;
    }

    public void add(TeamMember t) {
        this.teamMemberList.add(t);
    }


    public ArrayList<TeamMember> getTeamMemberList() {

        return this.teamMemberList;
    }
}