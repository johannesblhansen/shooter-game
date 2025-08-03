package com.shooter.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shooter.entities.Entity;

/**
 * Interface for all weapons in the game.
 */
public interface Weapon {
    /**
     * Fires the weapon from the specified position.
     * @param x The x position to fire from
     * @param y The y position to fire from
     * @return True if the weapon fired, false if it's on cooldown
     */
    boolean fire(float x, float y);
    
    /**
     * Updates all active projectiles from this weapon.
     * @param delta The time in seconds since the last update
     */
    void update(float delta);
    
    /**
     * Renders all active projectiles from this weapon.
     * @param batch The sprite batch to render with
     */
    void render(SpriteBatch batch);
    
    /**
     * Checks if any projectiles from this weapon collide with the specified entity.
     * @param entity The entity to check collision with
     * @return True if a collision occurred, false otherwise
     */
    boolean checkCollision(Entity entity);
    
    /**
     * Gets the cooldown time of the weapon.
     * @return The cooldown time in seconds
     */
    float getCooldown();
    
    /**
     * Sets the cooldown time of the weapon.
     * @param cooldown The cooldown time in seconds
     */
    void setCooldown(float cooldown);
}