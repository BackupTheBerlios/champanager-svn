package org.championship.manager.domain;

import org.championship.manager.util.NameComparator;
import org.championship.manager.util.GamePositionComparator;
import org.championship.manager.NotInitializedException;

import java.util.*;

// TODO: document me!!!

/**
 * Championship.
 * <p/>
 * User: rro
 * Date: 30.12.2005
 * Time: 22:59:37
 *
 * @author Roman R&auml;dle
 * @version $Id: Championship.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Championship {

    private Long id;
    private String name;
    private Integer groupCount = 0;
    private Integer teamsPerGroup = 0;
    private Boolean intermediateStage = false;
    private Boolean quarterFinal = false;
    private Boolean thirdPlaceGame = false;
    private Integer qualifyingTeams = 0;
    private Integer intermediateStageGroupCount = 0;
    private Map<String, Group> groups;
    private Set<Linking> linkings;
    private Set<Game> finalGames;

    public Championship() {
        groups = new HashMap<String, Group>();
        linkings = new TreeSet<Linking>(new GamePositionComparator());
        finalGames = new TreeSet<Game>(new GamePositionComparator());
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

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getTeamsPerGroup() {
        return teamsPerGroup;
    }

    public void setTeamsPerGroup(Integer teamsPerGroup) {
        this.teamsPerGroup = teamsPerGroup;
    }

    public Boolean getIntermediateStage() {
        return intermediateStage;
    }

    public void setIntermediateStage(Boolean mediRound) {
        this.intermediateStage = mediRound;
    }

    public Boolean getQuarterFinal() {
        return quarterFinal;
    }

    public void setQuarterFinal(Boolean quarterRound) {
        this.quarterFinal = quarterRound;
    }

    public Boolean getThirdPlaceGame() {
        return thirdPlaceGame;
    }

    public void setThirdPlaceGame(Boolean thirdPlaceGame) {
        this.thirdPlaceGame = thirdPlaceGame;
    }

    public Integer getQualifyingTeams() {
        return qualifyingTeams;
    }

    public void setQualifyingTeams(Integer qualifyingTeams) {
        this.qualifyingTeams = qualifyingTeams;
    }

    public Integer getIntermediateStageGroupCount() {
        return intermediateStageGroupCount;
    }

    public void setIntermediateStageGroupCount(Integer mediRoundGroupCount) {
        this.intermediateStageGroupCount = mediRoundGroupCount;
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    public void addGroup(String groupName, Group group) {
        groups.put(groupName, group);
    }

    public Group getGroup(String groupName) {
        return groups.get(groupName);
    }

    public Set<Linking> getLinkings() {
        return linkings;
    }

    public void setLinkings(Set<Linking> linkings) {
        this.linkings = linkings;
    }

    public void addLinking(Linking linking) {
        Linking tmpLinking = getLinking(linking.getGamePosition());

        if (tmpLinking != null) {
            linking.setId(tmpLinking.getId());
            linkings.remove(tmpLinking);
        }

        linkings.add(linking);
    }

    public void addLinkings(Set<Linking> linkings) {
        for (Linking linking : linkings) {
            addLinking(linking);
        }
    }

    public Set<Linking> getLinkings(String gameType) {

        Set<Linking> result = new LinkedHashSet<Linking>();

        for (Linking linking : linkings) {

            if (linking.getGameType().equals(gameType)) {
                result.add(linking);
            }
        }

        return result;
    }

    public Linking getLinking(Integer gamePosition) {
        for (Linking linking : linkings) {

            if (linking.getGamePosition().equals(gamePosition)) {
                return linking;
            }
        }
        return null;
    }

    public Set<String> getAllGroupNames() {
        return groups.keySet();
    }

    public Set<Team> getAllTeams() {

        Set<Team> allTeams = new TreeSet<Team>(new NameComparator());

        Set<String> preliminaryGroupDefinitions = getPreliminaryRoundGroupDefinitions();

        for (String groupName : groups.keySet()) {
            Group group = groups.get(groupName);

            if (preliminaryGroupDefinitions.contains(groupName)) {
                allTeams.addAll(group.getTeams());
            }
        }

        return allTeams;
    }

    public Set<String> getPreliminaryRoundGroupDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        for (int i = 0; i < getGroupCount(); i++) {
            result.add(String.valueOf((char) (65 + i)));
        }

        return result;
    }

    public Set<String> getIntermediateStageGroupDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        int shift = getGroupCount();
        for (int i = 0; i < getIntermediateStageGroupCount(); i++) {
            result.add(String.valueOf((char) (65 + shift + i)));
        }

        return result;
    }

    public Set<String> getIntermediateStageTeamDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        int shift = getGroupCount();
        for (int i = 0; i < getIntermediateStageGroupCount(); i++) {
            for (int j = 1; j <= getQualifyingTeams(); j++) {
                result.add(String.valueOf((char) (65 + shift + i)) + j);
            }
        }

        return result;
    }

    public Set<String> getQuarterFinalTeamDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        int shift = getGroupCount();
        for (int i = 0; i < getIntermediateStageGroupCount(); i++) {
            result.add(String.valueOf((char) (65 + shift + i)) + 1);
            result.add(String.valueOf((char) (65 + shift + i)) + 2);
        }

        return result;
    }

    public Set<String> getSemiFinalTeamDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        for (int i = 1; i <= 4; i++) {
            result.add("V" + i);
        }

        return result;
    }

    public Set<String> getThirdPlaceGameTeamDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        for (int i = 1; i <= 2; i++) {
            result.add("H" + i);
        }

        return result;
    }

    public Set<String> getFinalTeamDefinitions() {

        Set<String> result = new LinkedHashSet<String>();

        for (int i = 1; i <= 2; i++) {
            result.add("H" + i);
        }

        return result;
    }

    public Set<Game> getFinalGames() {
        return finalGames;
    }

    public void setFinalGames(Set<Game> finalGames) {
        this.finalGames = finalGames;
    }

    public Set<Game> getFinalGames(String gameType) {

        Set<Game> result = new TreeSet<Game>(new GamePositionComparator());

        for (Game game : getFinalGames()) {

            if (game.getType().equals(gameType)) {
                result.add(game);
            }
        }

        return result;
    }

    public int getAllGamesCount() {
        return getGames().size() + getFinalGames().size();
    }

    public Set<Game> getGames() {

        Set<Game> result = new TreeSet<Game>(new GamePositionComparator());

        for (String groupName : groups.keySet()) {
            Group group = groups.get(groupName);

            result.addAll(group.getGames());
        }

        return result;
    }

    public Set<Game> getGames(Set<String> groupNames) throws NotInitializedException {

        Set<Game> result = new TreeSet<Game>(new GamePositionComparator());

        for (String groupName : groupNames) {
            Group group = groups.get(groupName);

            if (group == null) {
                throw new NotInitializedException();
            }

            result.addAll(group.getGames());
        }

        return result;
    }

    public Set<Game> getPreliminaryRoundGames() {
        return getGames(getPreliminaryRoundGroupDefinitions());
    }

    public Set<Game> getIntermediateStageGames() {
        return getGames(getIntermediateStageGroupDefinitions());
    }

    public Game getGame(String uniqueGroup) {
         for (Game game : getFinalGames()) {
            if (game.getGroupName().equals(uniqueGroup)) {
                return game;
            }
        }

        return null;
    }

    public Game getGame(Integer gamePosition) {
        for (Game game : getGames()) {
            if (game.getGamePosition().equals(gamePosition)) {
                return game;
            }
        }

        for (Game game : getFinalGames()) {
            if (game.getGamePosition().equals(gamePosition)) {
                return game;
            }
        }

        return null;
    }

    public void addGameToGroup(Game game) {

        if (game.getGroupName() != null) {
            Group group = groups.get(game.getGroupName());
            group.addGame(game);
        }
    }

    public void addFinalGame(Game game) {
        Game tmpGame = getFinalGame(game.getGamePosition());
        if (tmpGame != null) {
            finalGames.remove(tmpGame);
        }

        finalGames.add(game);
    }

    public Game getFinalGame(Integer gamePosition) {
        for (Game game : getFinalGames()) {
            if (game.getGamePosition().equals(gamePosition)) {
                return game;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
