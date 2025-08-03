package com.shooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shooter.ShooterGame;
import com.shooter.utils.Constants;

/**
 * Base screen class that all game screens will extend.
 * Provides common functionality for all screens.
 */
public abstract class BaseScreen implements Screen {
    protected final ShooterGame game;
    protected final SpriteBatch batch;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    
    /**
     * Constructor for the base screen.
     * @param game The main game instance
     */
    public BaseScreen(ShooterGame game) {
        this.game = game;
        this.batch = game.getBatch();
        
        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }
    
    /**
     * Called when the screen becomes the current screen.
     */
    @Override
    public void show() {
        // Default implementation does nothing
    }
    
    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
    
    /**
     * Called when the screen is resized.
     * @param width The new width
     * @param height The new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }
    
    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {
        // Default implementation does nothing
    }
    
    /**
     * Called when the screen is resumed.
     */
    @Override
    public void resume() {
        // Default implementation does nothing
    }
    
    /**
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {
        // Default implementation does nothing
    }
    
    /**
     * Called when the screen is disposed.
     */
    @Override
    public void dispose() {
        // Default implementation does nothing
    }
    
    /**
     * Transitions to another screen.
     * @param screen The screen to transition to
     */
    protected void transitionTo(Screen screen) {
        game.setScreen(screen);
    }
}