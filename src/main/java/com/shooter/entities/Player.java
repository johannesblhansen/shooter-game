package com.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        this.shootCooldown = Constants.BASIC_WEAPON_COOLDOWN;
        this.shootTimer = 0;
    }

    /**
     * Updates the player state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
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

    /**
     * Renders the player.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
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
        if (!invulnerable) {
            lives--;
            if (lives <= 0) {
                active = false;
                return true;
            } else {
                invulnerable = true;
                invulnerabilityTimer = Constants.PLAYER_INVULNERABILITY_TIME;
            }
        }
        return false;
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
}
