package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.shooter.utils.Constants;

import java.util.HashMap;
import java.util.Map;
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
    private int currentLevel;
    private SpawnPattern currentPattern;

    // Enemy properties
    private TextureRegion enemyTexture;
    private float enemyWidth;
    private float enemyHeight;

    // Enemy definitions
    private Map<EnemyType, EnemyDefinition> enemyDefinitions;

    // Active enemies
    private Array<Enemy> enemies;

    // Random number generator
    private Random random;

    /**
     * Enum for different spawn patterns.
     */
    public enum SpawnPattern {
        RANDOM,
        WAVE,
        ALTERNATING
    }

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
        this.currentLevel = 1;
        this.currentPattern = SpawnPattern.RANDOM;

        // Initialize enemy definitions
        initializeEnemyDefinitions();
    }

    /**
     * Initializes the enemy definitions.
     */
    private void initializeEnemyDefinitions() {
        enemyDefinitions = new HashMap<>();

        // Basic enemy
        enemyDefinitions.put(EnemyType.BASIC, new EnemyDefinition(
            EnemyType.BASIC,
            Constants.BASIC_ENEMY_HEALTH,
            Constants.BASIC_ENEMY_SCORE,
            Constants.BASIC_ENEMY_SPEED,
            2.0f, // Shoot interval
            enemyTexture,
            enemyWidth,
            enemyHeight
        ));

        // Sine wave enemy
        enemyDefinitions.put(EnemyType.SINE_WAVE, new EnemyDefinition(
            EnemyType.SINE_WAVE,
            Constants.BASIC_ENEMY_HEALTH,
            Constants.BASIC_ENEMY_SCORE * 2,
            Constants.BASIC_ENEMY_SPEED,
            2.0f, // Shoot interval
            enemyTexture,
            enemyWidth,
            enemyHeight
        ));

        // Shooter enemy
        enemyDefinitions.put(EnemyType.SHOOTER, new EnemyDefinition(
            EnemyType.SHOOTER,
            Constants.BASIC_ENEMY_HEALTH,
            Constants.BASIC_ENEMY_SCORE * 3,
            Constants.BASIC_ENEMY_SPEED * 0.7f,
            1.5f, // Shoot interval
            enemyTexture,
            enemyWidth,
            enemyHeight
        ));

        // Fast enemy
        enemyDefinitions.put(EnemyType.FAST, new EnemyDefinition(
            EnemyType.FAST,
            Constants.BASIC_ENEMY_HEALTH,
            Constants.BASIC_ENEMY_SCORE * 2,
            Constants.BASIC_ENEMY_SPEED * 1.5f,
            2.0f, // Shoot interval
            enemyTexture,
            enemyWidth,
            enemyHeight
        ));

        // Tank enemy
        enemyDefinitions.put(EnemyType.TANK, new EnemyDefinition(
            EnemyType.TANK,
            Constants.BASIC_ENEMY_HEALTH * 3,
            Constants.BASIC_ENEMY_SCORE * 4,
            Constants.BASIC_ENEMY_SPEED * 0.5f,
            2.0f, // Shoot interval
            enemyTexture,
            enemyWidth,
            enemyHeight
        ));
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

        // Select enemy type based on spawn pattern and current level
        EnemyType enemyType = selectEnemyType();

        // Get enemy definition
        EnemyDefinition definition = enemyDefinitions.get(enemyType);

        // Create a new enemy
        Enemy enemy = new Enemy(Constants.DEFAULT_WIDTH, y, definition);

        // Add the enemy to the active enemies
        enemies.add(enemy);
    }

    /**
     * Selects an enemy type based on the current spawn pattern and level.
     * @return The selected enemy type
     */
    private EnemyType selectEnemyType() {
        // Determine available enemy types based on current level
        Array<EnemyType> availableTypes = new Array<>();

        // Basic enemies are always available
        availableTypes.add(EnemyType.BASIC);

        // Add more enemy types as level increases
        if (currentLevel >= 2) {
            availableTypes.add(EnemyType.SINE_WAVE);
        }
        if (currentLevel >= 3) {
            availableTypes.add(EnemyType.FAST);
        }
        if (currentLevel >= 4) {
            availableTypes.add(EnemyType.SHOOTER);
        }
        if (currentLevel >= 5) {
            availableTypes.add(EnemyType.TANK);
        }

        // Select enemy type based on spawn pattern
        switch (currentPattern) {
            case WAVE:
                // In wave pattern, return the same type for a series of enemies
                // Use the current time to determine which wave we're in
                int waveIndex = (int)(System.currentTimeMillis() / 5000) % availableTypes.size;
                return availableTypes.get(waveIndex);

            case ALTERNATING:
                // In alternating pattern, alternate between different types
                // Use the enemy count to determine which type to spawn
                return availableTypes.get(enemies.size % availableTypes.size);

            case RANDOM:
            default:
                // In random pattern, select a random type
                return availableTypes.get(random.nextInt(availableTypes.size));
        }
    }

    /**
     * Increases the difficulty by decreasing the spawn interval and potentially increasing the level.
     */
    private void increaseDifficulty() {
        // Decrease spawn interval
        spawnInterval = Math.max(spawnInterval * 0.9f, minSpawnInterval);

        // Every 3 difficulty increases, increase the level (up to level 5)
        if (difficultyTimer == 0 && currentLevel < 5 && (int)(difficultyInterval / 10) % 3 == 0) {
            currentLevel++;

            // Change spawn pattern periodically
            switch (currentLevel) {
                case 2:
                    currentPattern = SpawnPattern.ALTERNATING;
                    break;
                case 3:
                    currentPattern = SpawnPattern.WAVE;
                    break;
                case 4:
                    currentPattern = SpawnPattern.RANDOM;
                    break;
                case 5:
                    // Cycle through patterns more quickly at max level
                    // This will be handled by the update method
                    break;
            }
        }
    }

    /**
     * Sets the current level.
     * @param level The level to set
     */
    public void setLevel(int level) {
        this.currentLevel = Math.max(1, Math.min(level, 5));
    }

    /**
     * Gets the current level.
     * @return The current level
     */
    public int getLevel() {
        return currentLevel;
    }

    /**
     * Sets the current spawn pattern.
     * @param pattern The pattern to set
     */
    public void setSpawnPattern(SpawnPattern pattern) {
        this.currentPattern = pattern;
    }

    /**
     * Gets the current spawn pattern.
     * @return The current spawn pattern
     */
    public SpawnPattern getSpawnPattern() {
        return currentPattern;
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
