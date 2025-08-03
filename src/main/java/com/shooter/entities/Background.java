package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.shooter.utils.Constants;

/**
 * Background class for managing scrolling background layers.
 * Implements parallax scrolling effect with multiple layers.
 */
public class Background {
    // Background layers
    private BackgroundLayer[] layers;
    
    /**
     * Constructor for the background.
     * @param textures The texture regions for each layer (from back to front)
     * @param parallaxFactors The parallax factors for each layer (from back to front)
     */
    public Background(TextureRegion[] textures, float[] parallaxFactors) {
        if (textures.length != parallaxFactors.length) {
            throw new IllegalArgumentException("Number of textures must match number of parallax factors");
        }
        
        layers = new BackgroundLayer[textures.length];
        for (int i = 0; i < textures.length; i++) {
            layers[i] = new BackgroundLayer(textures[i], parallaxFactors[i]);
        }
    }
    
    /**
     * Updates the background layers.
     * @param delta The time in seconds since the last update
     */
    public void update(float delta) {
        for (BackgroundLayer layer : layers) {
            layer.update(delta);
        }
    }
    
    /**
     * Renders the background layers.
     * @param batch The sprite batch to render with
     */
    public void render(SpriteBatch batch) {
        for (BackgroundLayer layer : layers) {
            layer.render(batch);
        }
    }
    
    /**
     * Inner class representing a single background layer.
     */
    private class BackgroundLayer {
        private TextureRegion texture;
        private float parallaxFactor;
        private float x1, x2;
        private float width;
        private float height;
        
        /**
         * Constructor for a background layer.
         * @param texture The texture region for the layer
         * @param parallaxFactor The parallax factor for the layer
         */
        public BackgroundLayer(TextureRegion texture, float parallaxFactor) {
            this.texture = texture;
            this.parallaxFactor = parallaxFactor;
            this.width = Constants.DEFAULT_WIDTH;
            this.height = Constants.DEFAULT_HEIGHT;
            this.x1 = 0;
            this.x2 = width;
        }
        
        /**
         * Updates the layer position.
         * @param delta The time in seconds since the last update
         */
        public void update(float delta) {
            // Move the layer based on the scroll speed and parallax factor
            float scrollAmount = Constants.WORLD_SCROLL_SPEED * delta * parallaxFactor;
            
            x1 -= scrollAmount;
            x2 -= scrollAmount;
            
            // If a texture has moved completely off screen, move it to the right
            if (x1 + width < 0) {
                x1 = x2 + width;
            }
            if (x2 + width < 0) {
                x2 = x1 + width;
            }
        }
        
        /**
         * Renders the layer.
         * @param batch The sprite batch to render with
         */
        public void render(SpriteBatch batch) {
            batch.draw(texture, x1, 0, width, height);
            batch.draw(texture, x2, 0, width, height);
        }
    }
}