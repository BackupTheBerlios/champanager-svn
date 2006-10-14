package championship.manager.domain;

import championship.manager.util.TableEntryComparator;

import java.util.*;

// TODO: document me!!!

/**
 * Table.
 * <p/>
 * User: rro
 * Date: 02.01.2006
 * Time: 01:27:32
 *
 * @author Roman R&auml;dle
 * @version $Id: Table.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Table {

    private Long id;
    private Group group;
    private List<TableEntry> entries;

    public Table() {
        entries = new LinkedList<TableEntry>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<TableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TableEntry> entries) {
        this.entries = entries;
    }

    public Team getTeamAtPlacing(Integer placing) {

        int i = 1;
        for (TableEntry entry : entries) {

            if (i == placing) {
                return entry.getTeam();
            }

            i++;
        }
        
        return null;
    }

    public TableEntry getEntry(Team team) {
        for (TableEntry entry : entries) {
            if (entry.getTeam().equals(team)) {
                return entry;
            }
        }

        TableEntry entry = new TableEntry();
        entry.setTable(this);
        entry.setInitial(true);
        entry.setTeam(team);

        entries.add(entry);

        return entry;
    }

    public void addResult(Team team, String correctResult, String result, boolean hometeam) {

        TableEntry entry = getEntry(team);
        entry.addResult(correctResult, result, hometeam);

        Collections.<TableEntry>sort(entries, new TableEntryComparator());
    }


    public String toString() {
        return "Table{" +
               "id=" + id +
               ", entries=" + entries +
               "}";
    }
}
