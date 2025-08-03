package com.shooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.utils.Constants;

/**
 * Asset manager class.
 * Handles loading and managing game assets.
 */
public class AssetManager {
    // Textures
    private Texture playerTexture;
    private Texture enemyTexture;
    private Texture projectileTexture;
    private Texture[] backgroundTextures;
    
    // Texture regions
    private TextureRegion playerRegion;
    private TextureRegion enemyRegion;
    private TextureRegion projectileRegion;
    private TextureRegion[] backgroundRegions;
    
    // Singleton instance
    private static AssetManager instance;
    
    /**
     * Private constructor for the asset manager.
     */
    private AssetManager() {
        // Initialize with placeholder textures
        createPlaceholderTextures();
    }
    
    /**
     * Gets the singleton instance of the asset manager.
     * @return The asset manager instance
     */
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }
    
    /**
     * Creates placeholder textures for development.
     */
    private void createPlaceholderTextures() {
        // Create placeholder textures
        playerTexture = createColorTexture(32, 32, 0, 0, 1, 1); // Blue
        enemyTexture = createColorTexture(32, 32, 1, 0, 0, 1); // Red
        projectileTexture = createColorTexture(16, 16, 1, 1, 0, 1); // Yellow
        
        // Create placeholder background textures
        backgroundTextures = new Texture[3];
        backgroundTextures[0] = createColorTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, 0.1f, 0.1f, 0.3f, 1); // Dark blue
        backgroundTextures[1] = createColorTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, 0.2f, 0.2f, 0.4f, 0.5f); // Medium blue with transparency
        backgroundTextures[2] = createColorTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, 0.3f, 0.3f, 0.5f, 0.3f); // Light blue with more transparency
        
        // Create texture regions
        playerRegion = new TextureRegion(playerTexture);
        enemyRegion = new TextureRegion(enemyTexture);
        projectileRegion = new TextureRegion(projectileTexture);
        
        // Create background texture regions
        backgroundRegions = new TextureRegion[backgroundTextures.length];
        for (int i = 0; i < backgroundTextures.length; i++) {
            backgroundRegions[i] = new TextureRegion(backgroundTextures[i]);
        }
    }
    
    /**
     * Creates a texture with the specified color.
     * @param width The width of the texture
     * @param height The height of the texture
     * @param r The red component (0-1)
     * @param g The green component (0-1)
     * @param b The blue component (0-1)
     * @param a The alpha component (0-1)
     * @return The created texture
     */
    private Texture createColorTexture(int width, int height, float r, float g, float b, float a) {
        // Create a pixmap
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(width, height, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        
        // Fill with color
        pixmap.setColor(r, g, b, a);
        pixmap.fill();
        
        // Create texture from pixmap
        Texture texture = new Texture(pixmap);
        
        // Dispose pixmap
        pixmap.dispose();
        
        return texture;
    }
    
    /**
     * Disposes all assets.
     */
    public void dispose() {
        playerTexture.dispose();
        enemyTexture.dispose();
        projectileTexture.dispose();
        
        for (Texture texture : backgroundTextures) {
            texture.dispose();
        }
    }
    
    // Getters for texture regions
    
    public TextureRegion getPlayerRegion() {
        return playerRegion;
    }
    
    public TextureRegion getEnemyRegion() {
        return enemyRegion;
    }
    
    public TextureRegion getProjectileRegion() {
        return projectileRegion;
    }
    
    public TextureRegion[] getBackgroundRegions() {
        return backgroundRegions;
    }
}