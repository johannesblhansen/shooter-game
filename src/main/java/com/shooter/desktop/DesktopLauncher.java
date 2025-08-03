package com.shooter.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.shooter.ShooterGame;
import com.shooter.utils.Constants;

/**
 * Desktop launcher for the 2D Sidescrolling Shooter Game.
 * This class configures and launches the game on desktop platforms.
 */
public class DesktopLauncher {
    /**
     * Main method that serves as the entry point for the desktop application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Configure the application
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle(Constants.GAME_TITLE);
        config.setWindowedMode(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        config.setForegroundFPS(60);
        config.setResizable(true);
        
        // Create and start the application
        new Lwjgl3Application(new ShooterGame(), config);
    }
}