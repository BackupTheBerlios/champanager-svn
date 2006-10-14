package championship.manager.domain;

import java.util.StringTokenizer;

// TODO: document me!!!

/**
 * TableEntry.
 * <p/>
 * User: rro
 * Date: 02.01.2006
 * Time: 17:25:37
 *
 * @author Roman R&auml;dle
 * @version $Id: TableEntry.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class TableEntry {

    private Long id;
    private Table table;
    private Team team;
    private boolean initial;
    private Integer placing;
    private Integer gameCount = 0;
    private Integer goals = 0;
    private Integer goalsAgainst = 0;
    private Integer points = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public Integer getPlacing() {
        return placing;
    }

    public void setPlacing(Integer placing) {
        this.placing = placing;
    }

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void addResult(String correctResult, String result, boolean hometeam) {

        if (correctResult == null) {
            gameCount++;
        }

        if (correctResult != null && !initial) {
            StringTokenizer tokenizer = new StringTokenizer(correctResult, ":");
            int homeCorrect = Integer.parseInt(tokenizer.nextToken());
            int awayCorrect = Integer.parseInt(tokenizer.nextToken());

            if (hometeam) {
                goals -= homeCorrect;
                goalsAgainst -= awayCorrect;

                if (points > 0) {
                    if (homeCorrect > awayCorrect) {
                        points -= 3;
                    }
                    else if (homeCorrect == awayCorrect) {
                        points -= 1;
                    }
                }
            }
            else {
                goals -= awayCorrect;
                goalsAgainst -= homeCorrect;

                if (points > 0) {
                    if (awayCorrect > homeCorrect) {
                        points -= 3;
                    }
                    else if (awayCorrect == homeCorrect) {
                        points -= 1;
                    }
                }
            }
        }

        StringTokenizer tokenizer = new StringTokenizer(result, ":");
        int home = Integer.parseInt(tokenizer.nextToken());
        int away = Integer.parseInt(tokenizer.nextToken());

        if (hometeam) {
            goals += home;
            goalsAgainst += away;

            if (home > away) {
                points += 3;
            }
            else if (home == away) {
                points += 1;
            }
        }
        else {
            goals += away;
            goalsAgainst += home;

            if (away > home) {
                points += 3;
            }
            else if (away == home) {
                points += 1;
            }
        }

        initial = false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TableEntry that = (TableEntry) o;

        return !(team != null ? !team.equals(that.team) : that.team != null);
    }

    public int hashCode() {
        return (team != null ? team.hashCode() : 0);
    }

    public String toString() {
        return "TableEntry{" +
               "id=" + id +
               ", team=" + team +
               ", initial=" + initial +
               ", placing=" + placing +
               ", goals=" + goals +
               ", goalsAgainst=" + goalsAgainst +
               ", points=" + points +
               "}";
    }
}
