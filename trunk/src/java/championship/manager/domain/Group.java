package championship.manager.domain;

import championship.manager.util.GamePositionComparator;
import championship.manager.util.NameComparator;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedHashSet;

// TODO: document me!!!

/**
 * Group.
 * <p/>
 * User: rro
 * Date: 31.12.2005
 * Time: 00:12:48
 *
 * @author Roman R&auml;dle
 * @version $Id: Group.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Group {

    private Long id;
    private String name;
    private Table groupTable;
    private Set<Team> teams;
    private Set<Game> games;

    public Group() {
        teams = new TreeSet<Team>(new NameComparator());
        games = new TreeSet<Game>(new GamePositionComparator());
    }

    public Group(String name) {
        this.name = name;
        teams = new TreeSet<Team>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getGroupTable() {
        return groupTable;
    }

    public void setGroupTable(Table groupTable) {
        this.groupTable = groupTable;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        Team tmpTeam = getTeam(team.getGroupPosition());

        if (tmpTeam == null) {
            team.setGroupName(getName());
            teams.add(team);
            return;
        }

        tmpTeam.setName(team.getName());
    }

    public Team getTeam(Integer groupPosition) {
        for (Team team : teams) {
            if (team.getGroupPosition().equals(groupPosition)) return team;
        }

        return null;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        Game tmpGame = getGame(game.getGamePosition());
        if (tmpGame != null) {
            games.remove(tmpGame);
        }

        games.add(game);
    }

    public Game getGame(Integer gamePosition) {
        for (Game game : games) {

            if (game.getGamePosition().equals(gamePosition)) {
                return game;
            }
        }
        return null;
    }

    public Game getGame(Team team1, Team team2) {

        for (Game game : games) {
            if ((game.getHometeam().getId().equals(team1.getId()) &&
                    game.getAwayteam().getId().equals(team2.getId())) ||
                    game.getHometeam().getId().equals(team2.getId()) &&
                    game.getAwayteam().getId().equals(team1.getId())) {

                return game;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Group{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", groupTable=" + groupTable +
               ", teams=" + teams +
               ", games=" + games +
               "}";
    }
}
