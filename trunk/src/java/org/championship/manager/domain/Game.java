package org.championship.manager.domain;

import org.championship.manager.util.GamePosition;

import java.util.Date;

// TODO: document me!!!

/**
 * Game.
 * <p/>
 * User: rro
 * Date: 31.12.2005
 * Time: 15:08:41
 *
 * @author Roman R&auml;dle
 * @version $Id: Game.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Game implements GamePosition {

    private Long id;
    private Integer gamePosition;
    private Date time;
    private String groupName;
    private String gameVsState;
    private String type;
    private Team hometeam;
    private Team awayteam;
    private String result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGamePosition() {
        return gamePosition;
    }

    public void setGamePosition(Integer gamePosition) {
        this.gamePosition = gamePosition;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGameVsState() {
        return gameVsState;
    }

    public void setGameVsState(String gameVsState) {
        this.gameVsState = gameVsState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Team getHometeam() {
        return hometeam;
    }

    public void setHometeam(Team hometeam) {
        this.hometeam = hometeam;
    }

    public Team getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(Team awayteam) {
        this.awayteam = awayteam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Game{" +
               "id=" + id +
               ", gamePosition=" + gamePosition +
               ", time=" + time +
               ", groupName='" + groupName + "'" +
               ", gameVsState='" + gameVsState + "'" +
               ", hometeam=" + hometeam +
               ", awayteam=" + awayteam +
               ", result='" + result + "'" +
               "}";
    }
}
