package com.shooter.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.shooter.entities.Entity;
import com.shooter.entities.Projectile;
import com.shooter.utils.Constants;

/**
 * Basic weapon implementation.
 * Fires single projectiles at a fixed rate.
 */
public class BasicWeapon implements Weapon {
    // Weapon properties
    private float cooldown;
    private float cooldownTimer;
    private int damage;
    private boolean playerOwned;
    
    // Projectile properties
    private TextureRegion projectileTexture;
    private float projectileWidth;
    private float projectileHeight;
    
    // Active projectiles
    private Array<Projectile> projectiles;
    
    /**
     * Constructor for the basic weapon.
     * @param cooldown The cooldown time in seconds
     * @param damage The damage each projectile deals
     * @param playerOwned Whether the weapon is owned by the player
     * @param projectileTexture The texture for projectiles
     * @param projectileWidth The width of projectiles
     * @param projectileHeight The height of projectiles
     */
    public BasicWeapon(float cooldown, int damage, boolean playerOwned, 
                       TextureRegion projectileTexture, float projectileWidth, float projectileHeight) {
        this.cooldown = cooldown;
        this.cooldownTimer = 0;
        this.damage = damage;
        this.playerOwned = playerOwned;
        this.projectileTexture = projectileTexture;
        this.projectileWidth = projectileWidth;
        this.projectileHeight = projectileHeight;
        this.projectiles = new Array<Projectile>();
    }
    
    /**
     * Fires the weapon from the specified position.
     * @param x The x position to fire from
     * @param y The y position to fire from
     * @return True if the weapon fired, false if it's on cooldown
     */
    @Override
    public boolean fire(float x, float y) {
        if (cooldownTimer <= 0) {
            // Create a new projectile
            Projectile projectile = new Projectile(
                x, y, 
                projectileWidth, projectileHeight, 
                damage, playerOwned
            );
            
            // Set the projectile texture
            projectile.setTextureRegion(projectileTexture);
            
            // Add the projectile to the active projectiles
            projectiles.add(projectile);
            
            // Reset the cooldown timer
            cooldownTimer = cooldown;
            
            return true;
        }
        return false;
    }
    
    /**
     * Updates all active projectiles from this weapon.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        // Update cooldown timer
        if (cooldownTimer > 0) {
            cooldownTimer -= delta;
        }
        
        // Update projectiles
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            
            // Remove inactive projectiles
            if (!projectile.isActive()) {
                projectiles.removeIndex(i);
                i--;
            }
        }
    }
    
    /**
     * Renders all active projectiles from this weapon.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }
    }
    
    /**
     * Checks if any projectiles from this weapon collide with the specified entity.
     * @param entity The entity to check collision with
     * @return True if a collision occurred, false otherwise
     */
    @Override
    public boolean checkCollision(Entity entity) {
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.isActive() && projectile.collidesWith(entity)) {
                if (projectile.handleCollision(entity)) {
                    projectile.setActive(false);
                    projectiles.removeIndex(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Gets the cooldown time of the weapon.
     * @return The cooldown time in seconds
     */
    @Override
    public float getCooldown() {
        return cooldown;
    }
    
    /**
     * Sets the cooldown time of the weapon.
     * @param cooldown The cooldown time in seconds
     */
    @Override
    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * Gets the number of active projectiles.
     * @return The number of active projectiles
     */
    public int getActiveProjectileCount() {
        return projectiles.size;
    }
}