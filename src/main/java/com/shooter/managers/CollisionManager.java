package com.shooter.managers;

import com.badlogic.gdx.utils.Array;
import com.shooter.entities.Enemy;
import com.shooter.entities.Player;
import com.shooter.entities.Projectile;
import com.shooter.weapons.Weapon;

/**
 * Collision manager class.
 * Handles collision detection and response between game entities.
 */
public class CollisionManager {
    // Entities
    private Player player;
    private Array<Enemy> enemies;
    private Weapon playerWeapon;
    
    /**
     * Constructor for the collision manager.
     * @param player The player
     * @param enemies The enemies
     * @param playerWeapon The player's weapon
     */
    public CollisionManager(Player player, Array<Enemy> enemies, Weapon playerWeapon) {
        this.player = player;
        this.enemies = enemies;
        this.playerWeapon = playerWeapon;
    }
    
    /**
     * Checks for collisions between entities.
     * @return True if the player died, false otherwise
     */
    public boolean checkCollisions() {
        boolean playerDied = false;
        
        // Check for collisions between player and enemies
        if (player.isActive() && !player.isInvulnerable()) {
            for (Enemy enemy : enemies) {
                if (enemy.isActive() && player.collidesWith(enemy)) {
                    // Player collided with enemy
                    playerDied = player.damage();
                    enemy.damage(1);
                    
                    // Create visual feedback for collision
                    createCollisionEffect(player.getPosition().x, player.getPosition().y);
                }
            }
        }
        
        // Check for collisions between player projectiles and enemies
        for (Enemy enemy : enemies) {
            if (enemy.isActive() && playerWeapon.checkCollision(enemy)) {
                // Enemy hit by player projectile
                if (enemy.damage(1)) {
                    // Enemy destroyed
                    player.addScore(enemy.getScoreValue());
                    
                    // Create visual feedback for enemy destruction
                    createExplosionEffect(enemy.getPosition().x, enemy.getPosition().y);
                } else {
                    // Enemy damaged but not destroyed
                    createHitEffect(enemy.getPosition().x, enemy.getPosition().y);
                }
            }
        }
        
        // Check for collisions between enemy projectiles and player
        // This will be implemented when enemy weapons are added
        
        return playerDied;
    }
    
    /**
     * Creates a collision effect at the specified position.
     * This is a placeholder for visual feedback.
     * @param x The x position
     * @param y The y position
     */
    private void createCollisionEffect(float x, float y) {
        // This would create a visual effect for collisions
        // For now, it's just a placeholder
    }
    
    /**
     * Creates a hit effect at the specified position.
     * This is a placeholder for visual feedback.
     * @param x The x position
     * @param y The y position
     */
    private void createHitEffect(float x, float y) {
        // This would create a visual effect for hits
        // For now, it's just a placeholder
    }
    
    /**
     * Creates an explosion effect at the specified position.
     * This is a placeholder for visual feedback.
     * @param x The x position
     * @param y The y position
     */
    private void createExplosionEffect(float x, float y) {
        // This would create a visual effect for explosions
        // For now, it's just a placeholder
    }
}