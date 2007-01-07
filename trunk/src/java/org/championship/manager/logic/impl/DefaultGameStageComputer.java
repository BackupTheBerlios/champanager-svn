package org.championship.manager.logic.impl;

import org.championship.manager.logic.IGameStageComputable;
import org.championship.manager.domain.*;

import java.util.Set;

/**
 * TODO: document me!!!
 * <p/>
 * <code>DefaultGameStageComputer</code>.
 * <p/>
 * User: raedler
 * Date: Jan 6, 2007
 * Time: 11:46:46 PM
 *
 * @author <a href="mailto:roman@raedle.info">Roman R&auml;dle</a>
 * @version $Id$
 */
public class DefaultGameStageComputer implements IGameStageComputable {

    public Championship computeIntermediateStages(Championship championship) {
        Set<Linking> linkings = championship.getLinkings("intermediate_stage");

        for (Linking linking : linkings) {

            String link1 = linking.getLink1();
            String link2 = linking.getLink2();

            String groupName1 = link1.substring(0, 1);
            String groupName2 = link2.substring(0, 1);

            String placing1 = link1.substring(1, 2);
            String placing2 = link2.substring(1, 2);

            Group group1 = championship.getGroup(groupName1);
            Group group2 = championship.getGroup(groupName2);

            group1.setGroupTable(null);

            Team tmpTeam1 = group1.getTeam(Integer.parseInt(placing1));
            Team tmpTeam2 = group2.getTeam(Integer.parseInt(placing2));

            String tmpTeamName1 = tmpTeam1.getName();
            String tmpTeamName2 = tmpTeam2.getName();

            String tmpGroupName1 = tmpTeamName1.substring(0, 1);
            String tmpGroupName2 = tmpTeamName2.substring(0, 1);

            String tmpPlacing1 = tmpTeamName1.substring(1, 2);
            String tmpPlacing2 = tmpTeamName2.substring(1, 2);

            Group tmpGroup1 = championship.getGroup(tmpGroupName1);
            Group tmpGroup2 = championship.getGroup(tmpGroupName2);

            Team team1 = tmpGroup1.getGroupTable().getTeamAtPlacing(Integer.parseInt(tmpPlacing1));
            Team team2 = tmpGroup2.getGroupTable().getTeamAtPlacing(Integer.parseInt(tmpPlacing2));

            Game game = new Game();
            game.setGamePosition(linking.getGamePosition());
            game.setGameVsState(link1 + " - " + link2);
            game.setHometeam(team1);
            game.setAwayteam(team2);
            game.setGroupName(groupName1);

            championship.addGameToGroup(game);
        }

        return championship;
    }
}
