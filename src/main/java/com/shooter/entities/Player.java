package com.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.shooter.utils.Constants;

/**
 * Player entity class.
 * Handles player-specific properties and behaviors.
 */
public class Player extends Entity {
    // Player properties
    private int lives;
    private int score;
    private boolean invulnerable;
    private float invulnerabilityTimer;
    private boolean respawning;
    private float respawnTimer;
    private Vector2 respawnPosition;
    private boolean dying;
    private float deathAnimationTimer;

    // Weapon properties
    private float shootCooldown;
    private float shootTimer;

    /**
     * Constructor for the player.
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     */
    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.lives = Constants.PLAYER_INITIAL_LIVES;
        this.score = 0;
        this.invulnerable = false;
        this.invulnerabilityTimer = 0;
        this.respawning = false;
        this.respawnTimer = 0;
        this.respawnPosition = new Vector2(x, y);
        this.dying = false;
        this.deathAnimationTimer = 0;
        this.shootCooldown = Constants.BASIC_WEAPON_COOLDOWN;
        this.shootTimer = 0;
    }

    /**
     * Updates the player state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        // Handle death animation
        if (dying) {
            deathAnimationTimer -= delta;
            if (deathAnimationTimer <= 0) {
                dying = false;

                // Check if player has lives left
                if (lives > 0) {
                    // Start respawning
                    respawning = true;
                    respawnTimer = Constants.PLAYER_RESPAWN_TIME;

                    // Hide player during respawn
                    active = false;
                } else {
                    // Game over
                    active = false;
                }
            }
            return; // Skip other updates during death animation
        }

        // Handle respawning
        if (respawning) {
            respawnTimer -= delta;
            if (respawnTimer <= 0) {
                // Respawn complete
                respawning = false;
                active = true;
                position.set(respawnPosition);

                // Set invulnerability after respawn
                invulnerable = true;
                invulnerabilityTimer = Constants.PLAYER_INVULNERABILITY_TIME;
            }
            return; // Skip other updates during respawn
        }

        // Only handle input and movement if active
        if (active) {
            // Handle input
            handleInput(delta);

            // Update invulnerability
            if (invulnerable) {
                invulnerabilityTimer -= delta;
                if (invulnerabilityTimer <= 0) {
                    invulnerable = false;
                }
            }

            // Update shoot timer
            if (shootTimer > 0) {
                shootTimer -= delta;
            }

            // Update position based on velocity
            super.update(delta);

            // Ensure player stays within screen bounds
            keepInBounds();
        }
    }

    /**
     * Renders the player.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
        // Don't render if not active and not dying
        if (!active && !dying) {
            return;
        }

        // Handle death animation
        if (dying) {
            // For death animation, we could rotate the player, scale it, or make it flash
            // Here we'll make it rotate and fade out
            float progress = deathAnimationTimer / Constants.PLAYER_RESPAWN_TIME;
            float alpha = progress; // Fade out as animation progresses
            float rotation = (1 - progress) * 720; // Rotate more as animation progresses

            // Save batch color and alpha
            float oldAlpha = batch.getColor().a;

            // Set new alpha for fading
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha);

            // Draw with rotation
            if (textureRegion != null) {
                batch.draw(
                    textureRegion,
                    position.x, position.y,
                    width / 2, height / 2,
                    width, height,
                    1, 1,
                    rotation
                );
            }

            // Restore batch alpha
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, oldAlpha);

            return;
        }

        // If invulnerable, flash the player
        if (invulnerable) {
            if ((int)(invulnerabilityTimer * 10) % 2 == 0) {
                super.render(batch);
            }
        } else {
            super.render(batch);
        }
    }

    /**
     * Handles player input.
     * @param delta The time in seconds since the last update
     */
    private void handleInput(float delta) {
        // Reset velocity
        velocity.set(0, 0);

        // Movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -Constants.PLAYER_SPEED;
        }

        // Shooting
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer <= 0) {
            shoot();
            shootTimer = shootCooldown;
        }
    }

    /**
     * Keeps the player within the screen bounds.
     */
    private void keepInBounds() {
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > Constants.DEFAULT_WIDTH - width) {
            position.x = Constants.DEFAULT_WIDTH - width;
        }
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y > Constants.DEFAULT_HEIGHT - height) {
            position.y = Constants.DEFAULT_HEIGHT - height;
        }

        // Update bounds position
        bounds.setPosition(position);
    }

    /**
     * Shoots a projectile.
     * This method is called when the player presses the shoot button.
     * The actual shooting logic is handled by the weapon system in GameScreen.
     */
    private void shoot() {
        // The actual shooting logic is handled by the weapon system in GameScreen
        // This method is just a placeholder for any additional logic that might be needed
        // when the player shoots (e.g., playing a sound, showing a muzzle flash, etc.)
    }

    /**
     * Damages the player.
     * @return True if the player died, false otherwise
     */
    public boolean damage() {
        if (!invulnerable && !dying && !respawning) {
            lives--;
            if (lives <= 0) {
                // Start death animation
                dying = true;
                deathAnimationTimer = Constants.PLAYER_RESPAWN_TIME;
                return true;
            } else {
                // Make player invulnerable
                invulnerable = true;
                invulnerabilityTimer = Constants.PLAYER_INVULNERABILITY_TIME;
            }
        }
        return false;
    }

    /**
     * Respawns the player at the specified position.
     * @param x The x position
     * @param y The y position
     */
    public void respawn(float x, float y) {
        // Only respawn if player has lives left and is not already respawning
        if (lives > 0 && !respawning) {
            respawnPosition.set(x, y);
            respawning = true;
            respawnTimer = Constants.PLAYER_RESPAWN_TIME;
            active = false;
        }
    }

    /**
     * Adds score to the player.
     * @param amount The amount of score to add
     */
    public void addScore(int amount) {
        score += amount;
    }

    // Getters and setters

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public void setInvulnerable(boolean invulnerable, float duration) {
        this.invulnerable = invulnerable;
        this.invulnerabilityTimer = duration;
    }

    public boolean isDying() {
        return dying;
    }

    public boolean isRespawning() {
        return respawning;
    }

    public float getDeathAnimationTimer() {
        return deathAnimationTimer;
    }

    public float getRespawnTimer() {
        return respawnTimer;
    }
}
