package com.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shooter.screens.MenuScreen;

/**
 * Main game class for the 2D Sidescrolling Shooter Game.
 * This class serves as the entry point for the game and handles screen management.
 */
public class ShooterGame extends Game {
    // SpriteBatch for rendering
    private SpriteBatch batch;
    
    // Game dimensions
    private int width;
    private int height;
    
    /**
     * Called when the game is created.
     * Initializes the SpriteBatch and sets the initial screen.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        
        // Set the initial screen to the menu screen
        setScreen(new MenuScreen(this));
    }
    
    /**
     * Called when the game should render itself.
     * Clears the screen and delegates rendering to the current screen.
     */
    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Let the screen render itself
        super.render();
    }
    
    /**
     * Called when the game is disposed.
     * Disposes of the SpriteBatch and the current screen.
     */
    @Override
    public void dispose() {
        batch.dispose();
        getScreen().dispose();
    }
    
    /**
     * Returns the SpriteBatch for rendering.
     * @return The SpriteBatch
     */
    public SpriteBatch getBatch() {
        return batch;
    }
    
    /**
     * Returns the width of the game window.
     * @return The width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the game window.
     * @return The height
     */
    public int getHeight() {
        return height;
    }
}