package org.championship.manager.domain;

import org.championship.manager.util.GamePosition;

// TODO: document me!!!

/**
 * Linking.
 * <p/>
 * User: rro
 * Date: 02.01.2006
 * Time: 00:39:43
 *
 * @author Roman R&auml;dle
 * @version $Id: Linking.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class Linking implements GamePosition {

    private Long id;
    private Integer gamePosition;
    private String gameType;
    private String link1;
    private String link2;

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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }
}
