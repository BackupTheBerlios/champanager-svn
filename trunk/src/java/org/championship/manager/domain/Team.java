package org.championship.manager.domain;

import org.championship.manager.util.Name;

// TODO: document me!!!

/**
 * Team.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:41:34
 *
 * @author Roman R&auml;dle
 * @version $Id: Team.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Team implements Name {

    private Long id;
    private Integer groupPosition;
    private String name;
    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(Integer groupPosition) {
        this.groupPosition = groupPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Team team = (Team) o;

        if (groupName != null ? !groupName.equals(team.groupName) : team.groupName != null) return false;
        if (!groupPosition.equals(team.groupPosition)) return false;
        if (id != null ? !id.equals(team.id) : team.id != null) return false;
        if (!name.equals(team.name)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 29 * result + groupPosition.hashCode();
        result = 29 * result + name.hashCode();
        result = 29 * result + (groupName != null ? groupName.hashCode() : 0);
        return result;
    }
}
