package org.championship.manager.util;

import org.championship.manager.domain.TableEntry;
import org.championship.manager.domain.Game;

import java.util.Comparator;
import java.util.StringTokenizer;

// TODO: document me!!!

/**
 * TableEntryComparator.
 * <p/>
 * User: rro
 * Date: 02.01.2006
 * Time: 01:38:34
 *
 * @author Roman R&auml;dle
 * @version $Id: TableEntryComparator.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class TableEntryComparator implements Comparator {

    public int compare(Object o1, Object o2) {

        if (o1 instanceof TableEntry && o2 instanceof TableEntry) {

            TableEntry tableEntry1 = (TableEntry) o1;
            TableEntry tableEntry2 = (TableEntry) o2;

            int value = 0;

            if (tableEntry1.getPoints() != null && tableEntry2.getPoints() != null) {
                value = tableEntry2.getPoints().compareTo(tableEntry1.getPoints());
            }

            if (value == 0) {
                if (tableEntry1.getGoals() != null && tableEntry2.getGoals() != null &&
                    tableEntry1.getGoalsAgainst() != null && tableEntry2.getGoalsAgainst() != null) {

                    Integer difference1 = tableEntry1.getGoals() - tableEntry1.getGoalsAgainst();
                    Integer difference2 = tableEntry2.getGoals() - tableEntry2.getGoalsAgainst();

                    if (difference1 > difference2) {
                        value = -1;
                    }
                    else if (difference1 < difference2) {
                        value = 1;
                    }
                    else {

                        if (tableEntry1.getGoals() > tableEntry2.getGoals()) {
                            value = -1;
                        }
                        else if (tableEntry1.getGoals() < tableEntry2.getGoals()) {
                            value = 1;
                        }
                    }
                }
            }

            if (value == 0) {

                Game game = tableEntry1.getTable().getGroup().getGame(tableEntry1.getTeam(), tableEntry1.getTeam());

                if (game != null) {
                    String result = game.getResult();
                    StringTokenizer tokenizer = new StringTokenizer(result, ":");
                    int home = Integer.parseInt(tokenizer.nextToken());
                    int away = Integer.parseInt(tokenizer.nextToken());

                    if (home > away) {
                        return -1;
                    }
                    else if (away > home) {
                        return 1;
                    }
                }
            }

            return value;
        }

        return 0;
    }
}
