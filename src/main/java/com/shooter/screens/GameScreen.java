package com.shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.shooter.ShooterGame;

/**
 * Main game screen where gameplay takes place.
 * This is a placeholder implementation for Milestone 1.
 */
public class GameScreen extends BaseScreen {
    private BitmapFont font;
    private boolean gamePaused;
    
    /**
     * Constructor for the game screen.
     * @param game The main game instance
     */
    public GameScreen(ShooterGame game) {
        super(game);
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        gamePaused = false;
    }
    
    /**
     * Called when the screen becomes the current screen.
     */
    @Override
    public void show() {
        // Nothing special to do here for Milestone 1
    }
    
    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0.1f, 0.2f, 1);
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
        
        // For Milestone 1, just display a placeholder message
        String message = "Game Screen - Placeholder";
        font.draw(batch, message, 
                  (viewport.getWorldWidth() - font.getScaleX() * message.length() * 8) / 2, 
                  viewport.getWorldHeight() / 2);
        
        String instructions = "Press ESC to return to menu, P to pause/unpause";
        font.draw(batch, instructions, 
                  (viewport.getWorldWidth() - font.getScaleX() * instructions.length() * 4) / 2, 
                  viewport.getWorldHeight() / 2 - 30);
        
        if (gamePaused) {
            String pausedText = "GAME PAUSED";
            font.draw(batch, pausedText, 
                      (viewport.getWorldWidth() - font.getScaleX() * pausedText.length() * 8) / 2, 
                      viewport.getWorldHeight() * 0.75f);
        }
        
        batch.end();
    }
    
    /**
     * Updates the game state.
     * @param delta The time in seconds since the last update
     */
    private void update(float delta) {
        // In Milestone 1, there's no actual game logic to update
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
            transitionTo(new GameOverScreen(game, 0));
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