package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.shooter.utils.Constants;

import java.util.Random;

/**
 * Enemy spawner class.
 * Handles spawning enemies at regular intervals.
 */
public class EnemySpawner {
    // Spawner properties
    private float spawnTimer;
    private float spawnInterval;
    private float difficultyTimer;
    private float difficultyInterval;
    private float minSpawnInterval;
    
    // Enemy properties
    private TextureRegion enemyTexture;
    private float enemyWidth;
    private float enemyHeight;
    
    // Active enemies
    private Array<Enemy> enemies;
    
    // Random number generator
    private Random random;
    
    /**
     * Constructor for the enemy spawner.
     * @param spawnInterval The initial spawn interval in seconds
     * @param difficultyInterval The interval at which difficulty increases in seconds
     * @param minSpawnInterval The minimum spawn interval in seconds
     * @param enemyTexture The texture for enemies
     * @param enemyWidth The width of enemies
     * @param enemyHeight The height of enemies
     */
    public EnemySpawner(float spawnInterval, float difficultyInterval, float minSpawnInterval,
                        TextureRegion enemyTexture, float enemyWidth, float enemyHeight) {
        this.spawnTimer = 0;
        this.spawnInterval = spawnInterval;
        this.difficultyTimer = 0;
        this.difficultyInterval = difficultyInterval;
        this.minSpawnInterval = minSpawnInterval;
        this.enemyTexture = enemyTexture;
        this.enemyWidth = enemyWidth;
        this.enemyHeight = enemyHeight;
        this.enemies = new Array<Enemy>();
        this.random = new Random();
    }
    
    /**
     * Updates the enemy spawner and all active enemies.
     * @param delta The time in seconds since the last update
     */
    public void update(float delta) {
        // Update spawn timer
        spawnTimer -= delta;
        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = spawnInterval;
        }
        
        // Update difficulty timer
        difficultyTimer += delta;
        if (difficultyTimer >= difficultyInterval) {
            increaseDifficulty();
            difficultyTimer = 0;
        }
        
        // Update enemies
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(delta);
            
            // Remove inactive enemies
            if (!enemy.isActive()) {
                enemies.removeIndex(i);
                i--;
            }
        }
    }
    
    /**
     * Renders all active enemies.
     * @param batch The sprite batch to render with
     */
    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }
    
    /**
     * Spawns a new enemy.
     */
    private void spawnEnemy() {
        // Calculate random y position
        float y = random.nextFloat() * (Constants.DEFAULT_HEIGHT - enemyHeight);
        
        // Create a new enemy
        Enemy enemy = new Enemy(
            Constants.DEFAULT_WIDTH, y,
            enemyWidth, enemyHeight,
            Constants.BASIC_ENEMY_HEALTH,
            Constants.BASIC_ENEMY_SCORE
        );
        
        // Set the enemy texture
        enemy.setTextureRegion(enemyTexture);
        
        // Add the enemy to the active enemies
        enemies.add(enemy);
    }
    
    /**
     * Increases the difficulty by decreasing the spawn interval.
     */
    private void increaseDifficulty() {
        spawnInterval = Math.max(spawnInterval * 0.9f, minSpawnInterval);
    }
    
    /**
     * Gets the active enemies.
     * @return The active enemies
     */
    public Array<Enemy> getEnemies() {
        return enemies;
    }
    
    /**
     * Gets the number of active enemies.
     * @return The number of active enemies
     */
    public int getActiveEnemyCount() {
        return enemies.size;
    }
}