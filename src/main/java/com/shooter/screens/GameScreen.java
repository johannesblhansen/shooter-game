package com.shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.ShooterGame;
import com.shooter.entities.Background;
import com.shooter.entities.Enemy;
import com.shooter.entities.EnemySpawner;
import com.shooter.entities.Player;
import com.shooter.managers.AssetManager;
import com.shooter.managers.CollisionManager;
import com.shooter.utils.Constants;
import com.shooter.weapons.BasicWeapon;
import com.shooter.weapons.Weapon;

/**
 * Main game screen where gameplay takes place.
 * Implements the core gameplay for Milestone 2.
 */
public class GameScreen extends BaseScreen {
    // UI elements
    private BitmapFont font;
    private boolean gamePaused;

    // Game entities
    private Player player;
    private Background background;
    private EnemySpawner enemySpawner;

    // Weapons
    private Weapon playerWeapon;

    // Managers
    private CollisionManager collisionManager;

    // Asset manager
    private AssetManager assetManager;

    /**
     * Constructor for the game screen.
     * @param game The main game instance
     */
    public GameScreen(ShooterGame game) {
        super(game);
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        gamePaused = false;

        // Get the asset manager
        assetManager = AssetManager.getInstance();

        // Initialize game entities
        initializeEntities();
    }

    /**
     * Initializes game entities.
     */
    private void initializeEntities() {
        // Create player
        player = new Player(50, Constants.DEFAULT_HEIGHT / 2 - 16, 32, 32);
        player.setTextureRegion(assetManager.getPlayerRegion());

        // Create player weapon
        playerWeapon = new BasicWeapon(
            Constants.BASIC_WEAPON_COOLDOWN,
            1, // Damage
            true, // Player owned
            assetManager.getProjectileRegion(),
            16, 16 // Projectile dimensions
        );

        // Create background
        TextureRegion[] backgroundTextures = assetManager.getBackgroundRegions();
        float[] parallaxFactors = {0.2f, 0.5f, 0.8f}; // Different speeds for each layer
        background = new Background(backgroundTextures, parallaxFactors);

        // Create enemy spawner
        enemySpawner = new EnemySpawner(
            2.0f, // Initial spawn interval
            10.0f, // Difficulty increase interval
            0.5f, // Minimum spawn interval
            assetManager.getEnemyRegion(),
            32, 32 // Enemy dimensions
        );

        // Create collision manager
        collisionManager = new CollisionManager(player, enemySpawner.getEnemies(), playerWeapon);
    }

    /**
     * Called when the screen becomes the current screen.
     */
    @Override
    public void show() {
        // Nothing special to do here
    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        super.render(delta);

        // Handle input
        handleInput();

        // Update game state if not paused
        if (!gamePaused) {
            update(delta);
        }

        // Draw the game
        batch.begin();

        // Draw background
        background.render(batch);

        // Draw enemies
        enemySpawner.render(batch);

        // Draw player
        player.render(batch);

        // Draw projectiles
        playerWeapon.render(batch);

        // Draw UI
        drawUI();

        batch.end();
    }

    /**
     * Draws the UI elements.
     */
    private void drawUI() {
        // Draw score and lives
        font.draw(batch, "Score: " + player.getScore(), 10, Constants.DEFAULT_HEIGHT - 10);
        font.draw(batch, "Lives: " + player.getLives(), 10, Constants.DEFAULT_HEIGHT - 30);

        // Draw pause text if paused
        if (gamePaused) {
            String pausedText = "GAME PAUSED";
            font.draw(batch, pausedText, 
                      (viewport.getWorldWidth() - font.getScaleX() * pausedText.length() * 8) / 2, 
                      viewport.getWorldHeight() * 0.75f);
        }
    }

    /**
     * Updates the game state.
     * @param delta The time in seconds since the last update
     */
    private void update(float delta) {
        // Update background
        background.update(delta);

        // Update player
        player.update(delta);

        // Update player weapon
        playerWeapon.update(delta);

        // Update enemies
        enemySpawner.update(delta);

        // Check for player shooting
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            playerWeapon.fire(player.getPosition().x + player.getWidth(), player.getPosition().y + player.getHeight() / 2);
        }

        // Check for collisions
        boolean playerDied = collisionManager.checkCollisions();

        // Check for game over
        if (playerDied || !player.isActive()) {
            transitionTo(new GameOverScreen(game, player.getScore()));
        }
    }


    /**
     * Handles user input.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Return to menu screen
            transitionTo(new MenuScreen(game));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            // Toggle pause state
            gamePaused = !gamePaused;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            // For testing: transition to game over screen
            transitionTo(new GameOverScreen(game, player.getScore()));
        }
    }

    /**
     * Called when the screen is disposed.
     */
    @Override
    public void dispose() {
        font.dispose();
    }
}
