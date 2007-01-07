package org.championship.manager.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

/**
 * @author Tom
 */
public class CountingWatchPanel extends JPanel {

    private final Log LOG = LogFactory.getLog(CountingWatchPanel.class);

    private final int segmentLen = 25;

    private final int segmentGap = 30;

    private final int clockStartX = 10;

    private final int clockStartY = 10;

    private long fixTime;
    private long time;
    private long decrement;

    private Date date = new Date(time);

    private Timer clock;

    /**
     * Unsere 7-Segmentanzeige:
     *        (6)
     *    ----------
     *    |        |
     * 0) |        |  (5)
     *    |   (4)  |
     *    ----------
     *    |        |
     * (1)|        | (3)
     *    |        |
     *    ----------
     *       (2)
     */

    /**
     * �ber den Index (i) im segments byte[] finden wir Daten f�r die Darstellung der Ziffer des Indexes (i) in unserer 7 Segmentanzeige.
     * Segmente:  6 5 4 3 2 1 0
     * segments[0] = 111 entspricht Darstellung Ziffer 0 -> 1 1 0 1 1 1 1
     * -> Flag f�r Segment 4 ist nicht gesetzt -> Segment wird nicht gezeichnet.
     * <p/>
     * (6)
     * ----------
     * |        |
     * 0) |        |  (5)
     * |        |
     * |        |
     * |        |
     * (1)|        | (3)
     * |        |
     * ----------
     * (2)
     */
    private byte[] segments = {(byte) 111, (byte) 40, (byte) 118, (byte) 124,
                       (byte) 57, (byte) 93, (byte) 95, (byte) 104, (byte) 127,
                       (byte) 125};

    private Stroke stroke = new BasicStroke(10.0F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    private Color color = Color.RED;

    private boolean currentlyRunning;

    public CountingWatchPanel(long time, long decrement) {
        this.fixTime = time;
        this.time = time;
        this.decrement = decrement;

        date.setTime(time);

        this.addMouseListener(new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                if (color.equals(Color.RED)) {
                    color = Color.BLACK;
                }
                else {
                    color = Color.RED;
                }

                repaint();
            }
        });
    }

    public void startWatch(boolean continueWatchTime) {

        if (!continueWatchTime) resetWatch();

        if (time > 0 && clock == null) {
            TimerTask task = new TimerTask() {
                public void run() {
                    time = time - decrement;

                    if (time <= 0) {
                        alert();
                        clock.cancel();
                    }

                    date.setTime(time);
                    repaint();
                }
            };

            clock = new Timer();

            clock.schedule(task, 1000L, decrement);
        }
    }

    public void stopWatch() {
        if (clock != null) clock.cancel();
        clock = null;
    }

    public void resetWatch() {
        time = fixTime;
        date.setTime(time);

        repaint();
    }

    public long getTime() {
        return fixTime;
    }

    public void setTime(long fixTime) {
        this.fixTime = fixTime;
        this.time = fixTime;
        this.date.setTime(time);
    }

    public long getDecrement() {
        return decrement;
    }

    public void setDecrement(long decrement) {
        this.decrement = decrement;
    }

    private void alert() {
        LOG.info("The counting time reached its end.");
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        g.setColor(color);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(stroke);
        drawTime(sdf.format(date), g);
    }

    public void drawTime(String string, Graphics2D g) {
        char[] c = string.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int idx = c[i] - '0';
            byte value = idx == 10 ? 10 : segments[idx];
            drawSegments(value, i, g);
        }
    }

    public void drawSegments(byte b, int i, Graphics2D g) {
        int xPos = clockStartX + i * (clockStartX + segmentGap);
        int yPos = clockStartY;

        if (b == 10) {
            xPos += segmentLen / 2;
            yPos += segmentLen / 2;
            g.fillOval(xPos, yPos, 3, 3);
            g.fillOval(xPos, yPos + segmentLen / 2, 3, 3);
            return;
        }

        if (isBitSet(0, b)) {
            g.drawLine(xPos, yPos, xPos, yPos + segmentLen);
        }

        if (isBitSet(1, b)) {
            g.drawLine(xPos, yPos + segmentLen, xPos, yPos + 2 * segmentLen);
        }

        if (isBitSet(2, b)) {
            g.drawLine(xPos, yPos + 2 * segmentLen, xPos + segmentLen, yPos + 2 * segmentLen);
        }

        if (isBitSet(3, b)) {
            g.drawLine(xPos + segmentLen, yPos + 2 * segmentLen, xPos + segmentLen, yPos + segmentLen);
        }

        if (isBitSet(4, b)) {
            g.drawLine(xPos + segmentLen, yPos + segmentLen, xPos, yPos + segmentLen);
        }

        if (isBitSet(5, b)) {
            g.drawLine(xPos + segmentLen, yPos + segmentLen, xPos + segmentLen, yPos);
        }

        if (isBitSet(6, b)) {
            g.drawLine(xPos + segmentLen, yPos, xPos, yPos);
        }
    }

    private boolean isBitSet(int i, byte b) {
        int pow = (byte) 1 << i;
        return (b & pow) == pow;
    }
}