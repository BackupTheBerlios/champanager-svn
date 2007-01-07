/*
 * Copyright 2005-2006 Championship Manager development team.
 *
 * This file is part of Championship Manager.
 *
 * Championship Manager is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package org.championship.manager.splashscreen;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO: document me!!!
 * <p/>
 * <code>FadingSplashScreen</code>.
 * <p/>
 * User: raedler
 * Date: 30.10.2006
 * Time: 18:16:42
 *
 * @author Roman R&auml;dle
 * @version $Id$
 * @since Championship Manager 0.1
 */
public abstract class FadingSplashScreen extends JWindow {
    InputStream imageInputStream;

    int numberOfSecondsToShow;

    BufferedImage splashScreenImage;

    BufferedImage fadedSplashScreenImage;

    BufferedImage screenShot;

    ExecutorService fadingExecutor;

    Runnable fadingCommand;

    Runnable finishedCallback;

    int numberOfFadingSteps;

    /**
     * @param imageInputStream
     * @param numberOfFadingSteps
     * @param numberOfSecondsToShow
     */
    public FadingSplashScreen(InputStream imageInputStream, int numberOfFadingSteps, int numberOfSecondsToShow) {
        super();
        this.numberOfFadingSteps = numberOfFadingSteps;
        this.imageInputStream = imageInputStream;
        this.numberOfSecondsToShow = numberOfSecondsToShow;
        setAlwaysOnTop(true);
        init();
    }

    public abstract void doBefore();

    public void setFinishedCallback(Runnable finishedCallback) {
        this.finishedCallback = finishedCallback;
    }

    public void startFading() {
        fadedSplashScreenImage = splashScreenImage;
        repaint();

        doBefore();

        fadingExecutor.execute(fadingCommand);
    }

    public void start() {
        fadedSplashScreenImage = splashScreenImage;
        repaint();

        doBefore();
        setVisible(false);
        finishedCallback.run();

        setVisible(false);
        dispose();
    }

    private void init() {
        try {
            this.splashScreenImage = loadSplashScreenImage();
            this.screenShot = makeScreenShot(
                    splashScreenImage.getWidth(),
                    splashScreenImage.getHeight());
            setSize(splashScreenImage.getWidth(), splashScreenImage.getHeight());
            setLocationRelativeTo(null);
            this.fadingCommand = new Runnable() {
                public void run() {

                    fadedSplashScreenImage = splashScreenImage;
                    repaint();

                    /*
                    try {
                        TimeUnit.SECONDS.sleep(numberOfSecondsToShow);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    */

                    float alpha = 1.0F;
                    float alphaFactor = 1.0F / numberOfFadingSteps;
                    for (int i = 0; i < numberOfFadingSteps; i++) {
                        float currentAlpha = alpha - i * alphaFactor;
                        fadedSplashScreenImage = fadeSplashImage(AlphaComposite
                                .getInstance(
                                AlphaComposite.SRC_OVER,
                                currentAlpha));
                        //System.out.println(currentAlpha);
                        repaint();
                        try {
                            TimeUnit.MILLISECONDS.sleep(5);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    setVisible(false);
                    dispose();
                    finishedCallback.run();
                }
            };
            this.fadingExecutor = Executors.newSingleThreadExecutor();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage fadeSplashImage(AlphaComposite composite) {
        BufferedImage bufferedImage = new BufferedImage(
                splashScreenImage.getWidth(),
                splashScreenImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.drawImage(screenShot, 0, 0, null);
        g.setComposite(composite);
        g.drawImage(splashScreenImage, 0, 0, null);

        return bufferedImage;
    }

    private BufferedImage makeScreenShot(int width, int height)
            throws AWTException {
        Robot robot = new Robot();
        DisplayMode displayMode = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDisplayMode();

        return robot.createScreenCapture(new Rectangle(
                displayMode.getWidth() / 2 - width / 2,
                displayMode.getHeight() / 2 - height / 2,
                width,
                height));
    }

    private BufferedImage loadSplashScreenImage() throws IOException {
        return ImageIO.read(imageInputStream);
    }

    public void paint(Graphics g) {
        g.drawImage(fadedSplashScreenImage, 0, 0, null);
    }
}
