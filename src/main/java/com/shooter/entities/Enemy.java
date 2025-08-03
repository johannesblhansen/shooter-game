package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    private EnemyType type;

    // Movement pattern properties
    private float initialY;
    private float time;
    private float amplitude;
    private float frequency;

    /**
     * Constructor for the enemy.
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     * @param health The health of the enemy
     * @param scoreValue The score value of the enemy
     * @param type The enemy type
     */
    public Enemy(float x, float y, float width, float height, int health, int scoreValue, EnemyType type) {
        super(x, y, width, height);
        this.health = health;
        this.scoreValue = scoreValue;
        this.shootTimer = 0;
        this.shootInterval = 2.0f; // Default shoot interval
        this.type = type;
        this.initialY = y;
        this.time = 0;
        this.amplitude = 50; // Default amplitude for sine wave movement
        this.frequency = 2; // Default frequency for sine wave movement

        // Set velocity based on enemy type
        switch (type) {
            case BASIC:
                velocity.x = -Constants.BASIC_ENEMY_SPEED;
                break;
            case SINE_WAVE:
                velocity.x = -Constants.BASIC_ENEMY_SPEED;
                break;
            case SHOOTER:
                velocity.x = -Constants.BASIC_ENEMY_SPEED * 0.7f;
                shootInterval = 1.5f;
                break;
            case FAST:
                velocity.x = -Constants.BASIC_ENEMY_SPEED * 1.5f;
                break;
            case TANK:
                velocity.x = -Constants.BASIC_ENEMY_SPEED * 0.5f;
                break;
        }
    }

    /**
     * Constructor for the enemy using an enemy definition.
     * @param x The x position
     * @param y The y position
     * @param definition The enemy definition
     */
    public Enemy(float x, float y, EnemyDefinition definition) {
        this(x, y, definition.getWidth(), definition.getHeight(), 
             definition.getHealth(), definition.getScoreValue(), definition.getType());
        this.shootInterval = definition.getShootInterval();
        this.textureRegion = definition.getTextureRegion();
    }

    /**
     * Updates the enemy state.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        // Update time for movement patterns
        time += delta;

        // Update movement based on enemy type
        switch (type) {
            case SINE_WAVE:
                // Calculate new y position based on sine wave
                position.y = initialY + amplitude * MathUtils.sin(frequency * time);
                break;
            case SHOOTER:
                // Shooters move slower but can shoot
                // They will be handled by the weapon system
                break;
            case FAST:
                // Fast enemies just move faster (already set in constructor)
                break;
            case TANK:
                // Tank enemies move slower but have more health (already set in constructor)
                break;
            case BASIC:
            default:
                // Basic enemies just move in a straight line (already set in constructor)
                break;
        }

        // Update position based on velocity
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
