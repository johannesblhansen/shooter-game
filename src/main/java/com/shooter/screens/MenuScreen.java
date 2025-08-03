package com.shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.shooter.ShooterGame;
import com.shooter.utils.Constants;

/**
 * Menu screen for the game.
 * Displays the game title and instructions to start the game.
 */
public class MenuScreen extends BaseScreen {
    private BitmapFont font;
    private GlyphLayout layout;
    private final String titleText = Constants.GAME_TITLE;
    private final String startText = "Press ENTER to start";
    private final String exitText = "Press ESC to exit";
    
    /**
     * Constructor for the menu screen.
     * @param game The main game instance
     */
    public MenuScreen(ShooterGame game) {
        super(game);
        font = new BitmapFont();
        font.getData().setScale(2);
        layout = new GlyphLayout();
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
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera
        super.render(delta);
        
        // Handle input
        handleInput();
        
        // Draw the menu
        batch.begin();
        
        // Draw title
        layout.setText(font, titleText);
        float titleX = (viewport.getWorldWidth() - layout.width) / 2;
        float titleY = viewport.getWorldHeight() * 0.7f;
        font.draw(batch, titleText, titleX, titleY);
        
        // Draw start instruction
        layout.setText(font, startText);
        float startX = (viewport.getWorldWidth() - layout.width) / 2;
        float startY = viewport.getWorldHeight() * 0.4f;
        font.draw(batch, startText, startX, startY);
        
        // Draw exit instruction
        layout.setText(font, exitText);
        float exitX = (viewport.getWorldWidth() - layout.width) / 2;
        float exitY = viewport.getWorldHeight() * 0.3f;
        font.draw(batch, exitText, exitX, exitY);
        
        batch.end();
    }
    
    /**
     * Handles user input.
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Transition to the game screen
            transitionTo(new GameScreen(game));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // Exit the game
            Gdx.app.exit();
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