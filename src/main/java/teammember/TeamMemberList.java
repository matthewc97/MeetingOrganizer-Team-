package teammember;

import java.util.ArrayList;

/**
 * Stores team members in a team member list.
 * Contains an ArrayList object as the team member list.
 * Has constructor and getter methods for the team member list.
 *
 * @see TeamMember
 */

public class TeamMemberList {

    private ArrayList<TeamMember> teamMemberList;

    public TeamMemberList(ArrayList<TeamMember> tl) {
        this.teamMemberList = tl;
    }

    public void add(TeamMember t) {
        this.teamMemberList.add(t);
    }

    public void set(int index, TeamMember member) {
        this.teamMemberList.set(index, member);
    }

    public ArrayList<TeamMember> getTeamMemberList() throws IndexOutOfBoundsException {
        return this.teamMemberList;
    }

    public int getSize() {
        return this.getTeamMemberList().size();
    }
}