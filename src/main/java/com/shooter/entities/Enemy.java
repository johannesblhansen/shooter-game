package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.utils.Constants;

/**
 * Enemy entity class.
 * Represents enemies in the game.
 */
public class Enemy extends Entity {
    // Enemy properties
    private int health;
    private int scoreValue;
    private float shootTimer;
    private float shootInterval;
    
    /**
     * Constructor for the enemy.
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param health The health of the enemy
     * @param scoreValue The score value of the enemy
     */
    public Enemy(float x, float y, float width, float height, int health, int scoreValue) {
        super(x, y, width, height);
        this.health = health;
        this.scoreValue = scoreValue;
        this.shootTimer = 0;
        this.shootInterval = 2.0f; // Default shoot interval
        
        // Set default velocity
        velocity.x = -Constants.BASIC_ENEMY_SPEED;
    }
    
    /**
     * Updates the enemy state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        
        // Update shoot timer
        if (shootTimer > 0) {
            shootTimer -= delta;
        }
        
        // Deactivate if out of bounds
        if (position.x < -width) {
            active = false;
        }
    }
    
    /**
     * Renders the enemy.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
    /**
     * Damages the enemy.
     * @param amount The amount of damage to deal
     * @return True if the enemy died, false otherwise
     */
    public boolean damage(int amount) {
        health -= amount;
        if (health <= 0) {
            active = false;
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the enemy can shoot.
     * @return True if the enemy can shoot, false otherwise
     */
    public boolean canShoot() {
        return shootTimer <= 0;
    }
    
    /**
     * Resets the shoot timer.
     */
    public void resetShootTimer() {
        shootTimer = shootInterval;
    }
    
    // Getters and setters
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
    
    public float getShootInterval() {
        return shootInterval;
    }
    
    public void setShootInterval(float shootInterval) {
        this.shootInterval = shootInterval;
    }
}