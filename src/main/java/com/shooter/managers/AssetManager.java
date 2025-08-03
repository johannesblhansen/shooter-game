package com.shooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.utils.Constants;

/**
 * Functional interface for drawing on a pixmap.
 * Used to create pixel art textures.
 */
@FunctionalInterface
interface PixmapDrawer {
    void draw(Pixmap pixmap);
}

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
     * Creates pixel art style textures for development.
     */
    private void createPlaceholderTextures() {
        // Create pixel art style player texture (spaceship)
        playerTexture = createPixelArtTexture(32, 32, pixmap -> {
            // Main body (blue)
            pixmap.setColor(0.2f, 0.4f, 1.0f, 1.0f);
            pixmap.fillRectangle(8, 8, 16, 16);

            // Cockpit (light blue)
            pixmap.setColor(0.5f, 0.8f, 1.0f, 1.0f);
            pixmap.fillRectangle(20, 12, 8, 8);

            // Wings (darker blue)
            pixmap.setColor(0.1f, 0.2f, 0.8f, 1.0f);
            pixmap.fillRectangle(4, 4, 8, 24);
            pixmap.fillRectangle(24, 8, 4, 16);

            // Engine flames (orange/yellow)
            pixmap.setColor(1.0f, 0.6f, 0.2f, 1.0f);
            pixmap.fillRectangle(2, 12, 6, 8);
        });

        // Create pixel art style enemy texture (alien ship)
        enemyTexture = createPixelArtTexture(32, 32, pixmap -> {
            // Main body (red)
            pixmap.setColor(0.9f, 0.2f, 0.2f, 1.0f);
            pixmap.fillRectangle(8, 8, 16, 16);

            // Cockpit (dark red)
            pixmap.setColor(0.6f, 0.1f, 0.1f, 1.0f);
            pixmap.fillRectangle(4, 12, 8, 8);

            // Wings (purple)
            pixmap.setColor(0.7f, 0.2f, 0.8f, 1.0f);
            pixmap.fillRectangle(16, 4, 8, 24);

            // Engine flames (green - alien tech)
            pixmap.setColor(0.2f, 0.9f, 0.4f, 1.0f);
            pixmap.fillRectangle(24, 12, 6, 8);
        });

        // Create pixel art style projectile texture (energy bolt)
        projectileTexture = createPixelArtTexture(16, 16, pixmap -> {
            // Core (bright yellow)
            pixmap.setColor(1.0f, 1.0f, 0.2f, 1.0f);
            pixmap.fillRectangle(4, 6, 8, 4);

            // Glow effect (orange)
            pixmap.setColor(1.0f, 0.7f, 0.0f, 0.7f);
            pixmap.fillRectangle(2, 5, 12, 6);

            // Trail (fading yellow)
            pixmap.setColor(1.0f, 1.0f, 0.5f, 0.5f);
            pixmap.fillRectangle(0, 7, 4, 2);
        });

        // Create pixel art style background textures
        backgroundTextures = new Texture[3];

        // Deep space background (dark blue with stars)
        backgroundTextures[0] = createPixelArtTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, pixmap -> {
            // Deep space (dark blue)
            pixmap.setColor(0.05f, 0.05f, 0.15f, 1.0f);
            pixmap.fill();

            // Add stars (random white dots)
            pixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            for (int i = 0; i < 200; i++) {
                int x = (int)(Math.random() * Constants.DEFAULT_WIDTH);
                int y = (int)(Math.random() * Constants.DEFAULT_HEIGHT);
                int size = (int)(Math.random() * 3) + 1;
                pixmap.fillRectangle(x, y, size, size);
            }
        });

        // Nebula layer (purple/blue clouds)
        backgroundTextures[1] = createPixelArtTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, pixmap -> {
            // Transparent base
            pixmap.setColor(0, 0, 0, 0);
            pixmap.fill();

            // Add nebula clouds (purple/blue with transparency)
            for (int i = 0; i < 10; i++) {
                int x = (int)(Math.random() * Constants.DEFAULT_WIDTH);
                int y = (int)(Math.random() * Constants.DEFAULT_HEIGHT);
                int width = (int)(Math.random() * 200) + 100;
                int height = (int)(Math.random() * 100) + 50;

                // Random purple/blue color
                float r = 0.2f + (float)Math.random() * 0.3f;
                float g = 0.1f + (float)Math.random() * 0.2f;
                float b = 0.4f + (float)Math.random() * 0.4f;
                pixmap.setColor(r, g, b, 0.3f);

                // Draw cloud-like shape
                pixmap.fillRectangle(x, y, width, height);
            }
        });

        // Foreground dust/particles (small bright particles)
        backgroundTextures[2] = createPixelArtTexture(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT, pixmap -> {
            // Transparent base
            pixmap.setColor(0, 0, 0, 0);
            pixmap.fill();

            // Add dust particles (small bright dots)
            for (int i = 0; i < 50; i++) {
                int x = (int)(Math.random() * Constants.DEFAULT_WIDTH);
                int y = (int)(Math.random() * Constants.DEFAULT_HEIGHT);

                // Random bright color
                float r = 0.7f + (float)Math.random() * 0.3f;
                float g = 0.7f + (float)Math.random() * 0.3f;
                float b = 0.7f + (float)Math.random() * 0.3f;
                pixmap.setColor(r, g, b, 0.2f);

                // Draw small particle
                pixmap.fillRectangle(x, y, 2, 2);
            }
        });

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
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

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
     * Creates a pixel art texture using the provided drawer.
     * @param width The width of the texture
     * @param height The height of the texture
     * @param drawer The drawer to use for creating the pixel art
     * @return The created texture
     */
    private Texture createPixelArtTexture(int width, int height, PixmapDrawer drawer) {
        // Create a pixmap
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Let the drawer draw on the pixmap
        drawer.draw(pixmap);

        // Create texture from pixmap
        Texture texture = new Texture(pixmap);

        // Set texture filtering to nearest for crisp pixel art
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

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
