package com.shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.shooter.ShooterGame;

/**
 * Game over screen displayed when the player loses all lives or completes the game.
 * This is a placeholder implementation for Milestone 1.
 */
public class GameOverScreen extends BaseScreen {
    private BitmapFont font;
    private GlyphLayout layout;
    private final int score;
    private final String gameOverText = "GAME OVER";
    private final String scoreText;
    private final String restartText = "Press ENTER to play again";
    private final String menuText = "Press ESC to return to menu";
    
    /**
     * Constructor for the game over screen.
     * @param game The main game instance
     * @param score The player's final score
     */
    public GameOverScreen(ShooterGame game, int score) {
        super(game);
        this.score = score;
        this.scoreText = "Final Score: " + score;
        font = new BitmapFont();
        font.getData().setScale(2);
        layout = new GlyphLayout();
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
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera
        super.render(delta);
        
        // Handle input
        handleInput();
        
        // Draw the game over screen
        batch.begin();
        
        // Draw game over text
        layout.setText(font, gameOverText);
        float gameOverX = (viewport.getWorldWidth() - layout.width) / 2;
        float gameOverY = viewport.getWorldHeight() * 0.7f;
        font.draw(batch, gameOverText, gameOverX, gameOverY);
        
        // Draw score text
        layout.setText(font, scoreText);
        float scoreX = (viewport.getWorldWidth() - layout.width) / 2;
        float scoreY = viewport.getWorldHeight() * 0.5f;
        font.draw(batch, scoreText, scoreX, scoreY);
        
        // Draw restart instruction
        font.getData().setScale(1.5f);
        layout.setText(font, restartText);
        float restartX = (viewport.getWorldWidth() - layout.width) / 2;
        float restartY = viewport.getWorldHeight() * 0.3f;
        font.draw(batch, restartText, restartX, restartY);
        
        // Draw menu instruction
        layout.setText(font, menuText);
        float menuX = (viewport.getWorldWidth() - layout.width) / 2;
        float menuY = viewport.getWorldHeight() * 0.2f;
        font.draw(batch, menuText, menuX, menuY);
        
        batch.end();
    }
    
    /**
     * Handles user input.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Start a new game
            transitionTo(new GameScreen(game));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Return to menu screen
            transitionTo(new MenuScreen(game));
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