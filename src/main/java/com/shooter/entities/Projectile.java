package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.utils.Constants;

/**
 * Projectile entity class.
 * Represents projectiles fired by weapons.
 */
public class Projectile extends Entity {
    // Projectile properties
    private int damage;
    private boolean playerOwned;
    
    /**
     * Constructor for the projectile.
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param damage The damage the projectile deals
     * @param playerOwned Whether the projectile is owned by the player
     */
    public Projectile(float x, float y, float width, float height, int damage, boolean playerOwned) {
        super(x, y, width, height);
        this.damage = damage;
        this.playerOwned = playerOwned;
        
        // Set default velocity based on ownership
        if (playerOwned) {
            velocity.x = Constants.PROJECTILE_SPEED;
        } else {
            velocity.x = -Constants.PROJECTILE_SPEED;
        }
    }
    
    /**
     * Updates the projectile state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        
        // Deactivate if out of bounds
        if (position.x < -width || position.x > Constants.DEFAULT_WIDTH ||
            position.y < -height || position.y > Constants.DEFAULT_HEIGHT) {
            active = false;
        }
    }
    
    /**
     * Renders the projectile.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
    /**
     * Handles collision with another entity.
     * @param other The other entity
     * @return True if the projectile should be deactivated, false otherwise
     */
    public boolean handleCollision(Entity other) {
        if (other instanceof Player) {
            // Only enemy projectiles can damage the player
            return !playerOwned;
        } else if (other instanceof Enemy) {
            // Only player projectiles can damage enemies
            return playerOwned;
        }
        return false;
    }
    
    // Getters and setters
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public boolean isPlayerOwned() {
        return playerOwned;
    }
    
    public void setPlayerOwned(boolean playerOwned) {
        this.playerOwned = playerOwned;
    }
}