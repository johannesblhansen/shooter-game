package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.shooter.utils.Constants;

/**
 * Projectile entity class.
 * Represents projectiles fired by weapons.
 */
public class Projectile extends Entity {
    // Projectile properties
    private int damage;
    private boolean playerOwned;

    // Animation properties
    private float animationTimer;
    private float pulseScale;
    private float rotationSpeed;
    private float alpha;
    private float trailTimer;

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

        // Initialize animation properties
        this.animationTimer = 0;
        this.pulseScale = 1.0f;
        this.rotationSpeed = 0; // No rotation - projectile should be static
        this.alpha = 1.0f;
        this.trailTimer = 0;
    }

    /**
     * Updates the projectile state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        // Update animation timer
        animationTimer += delta;

        // No pulse scale update - projectile should not blink
        pulseScale = 1.0f; // Constant scale instead of pulsing

        // No rotation update - projectile should be static
        // rotation += rotationSpeed * delta;

        // Update trail timer
        trailTimer += delta;

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
        if (active && textureRegion != null) {
            // Save original color
            float originalR = batch.getColor().r;
            float originalG = batch.getColor().g;
            float originalB = batch.getColor().b;
            float originalA = batch.getColor().a;

            // Draw trail effect (every 0.05 seconds)
            if (trailTimer >= 0.05f) {
                trailTimer = 0;
                // Draw fading trail with reduced alpha
                for (int i = 1; i <= 3; i++) {
                    float trailAlpha = originalA * (0.7f - 0.2f * i);
                    float trailScale = 0.9f - 0.1f * i;
                    float offsetX = playerOwned ? -i * 8 : i * 8;

                    batch.setColor(originalR, originalG, originalB, trailAlpha);
                    batch.draw(
                        textureRegion,
                        position.x + offsetX, position.y,
                        width / 2, height / 2,
                        width * trailScale, height * trailScale,
                        1, 1,
                        0 // Static rotation (no spinning)
                    );
                }
            }

            // Draw the projectile with pulse effect
            batch.setColor(originalR, originalG, originalB, originalA);
            batch.draw(
                textureRegion,
                position.x, position.y,
                width / 2, height / 2,
                width * pulseScale, height * pulseScale,
                1, 1,
                0 // Static rotation (no spinning)
            );

            // Draw glow effect
            batch.setColor(originalR, originalG, originalB, originalA * 0.5f);
            batch.draw(
                textureRegion,
                position.x - width * 0.1f, position.y - height * 0.1f,
                width / 2, height / 2,
                width * pulseScale * 1.2f, height * pulseScale * 1.2f,
                1, 1,
                0 // Static rotation (no spinning)
            );

            // Restore original color
            batch.setColor(originalR, originalG, originalB, originalA);
        }
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
