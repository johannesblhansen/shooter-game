package com.shooter.utils;

/**
 * Constants used throughout the game.
 * This class contains static final variables for game settings, entity properties, etc.
 */
public class Constants {
    // Game settings
    public static final String GAME_TITLE = "2D Sidescrolling Shooter";
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 480;
    public static final boolean FULLSCREEN = false;
    
    // Player settings
    public static final float PLAYER_SPEED = 200f; // pixels per second
    public static final int PLAYER_INITIAL_LIVES = 3;
    public static final float PLAYER_RESPAWN_TIME = 2f; // seconds
    public static final float PLAYER_INVULNERABILITY_TIME = 3f; // seconds after respawn
    
    // Weapon settings
    public static final float BASIC_WEAPON_COOLDOWN = 0.25f; // seconds
    public static final float PROJECTILE_SPEED = 400f; // pixels per second
    
    // Enemy settings
    public static final float BASIC_ENEMY_SPEED = 100f; // pixels per second
    public static final int BASIC_ENEMY_HEALTH = 1;
    public static final int BASIC_ENEMY_SCORE = 100;
    
    // Game world settings
    public static final float WORLD_SCROLL_SPEED = 60f; // pixels per second
    
    // Screen transition time
    public static final float SCREEN_TRANSITION_TIME = 0.5f; // seconds
    
    // Debug settings
    public static final boolean DEBUG_MODE = true;
    public static final boolean SHOW_FPS = true;
    
    // Asset paths
    public static final String SPRITES_PATH = "sprites/";
    public static final String AUDIO_PATH = "audio/";
    public static final String LEVELS_PATH = "levels/";
    
    private Constants() {
        // Private constructor to prevent instantiation
    }
}