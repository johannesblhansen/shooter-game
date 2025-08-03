package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Enemy definition class.
 * Defines the properties of different enemy types.
 */
public class EnemyDefinition {
    // Enemy type
    private final EnemyType type;
    
    // Enemy properties
    private final int health;
    private final int scoreValue;
    private final float speed;
    private final float shootInterval;
    private final TextureRegion textureRegion;
    private final float width;
    private final float height;
    
    /**
     * Constructor for the enemy definition.
     * @param type The enemy type
     * @param health The health of the enemy
     * @param scoreValue The score value of the enemy
     * @param speed The speed of the enemy
     * @param shootInterval The shoot interval of the enemy
     * @param textureRegion The texture region of the enemy
     * @param width The width of the enemy
     * @param height The height of the enemy
     */
    public EnemyDefinition(EnemyType type, int health, int scoreValue, float speed, 
                          float shootInterval, TextureRegion textureRegion, 
                          float width, float height) {
        this.type = type;
        this.health = health;
        this.scoreValue = scoreValue;
        this.speed = speed;
        this.shootInterval = shootInterval;
        this.textureRegion = textureRegion;
        this.width = width;
        this.height = height;
    }
    
    // Getters
    
    public EnemyType getType() {
        return type;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public float getShootInterval() {
        return shootInterval;
    }
    
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
}