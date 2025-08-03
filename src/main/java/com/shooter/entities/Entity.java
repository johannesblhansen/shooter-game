package com.shooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for all game entities.
 * Provides common properties and methods for all entities.
 */
public abstract class Entity {
    // Position and movement
    protected Vector2 position;
    protected Vector2 velocity;
    protected float rotation;
    
    // Dimensions
    protected float width;
    protected float height;
    
    // State
    protected boolean active;
    
    // Rendering
    protected TextureRegion textureRegion;
    
    // Collision
    protected Rectangle bounds;
    
    /**
     * Constructor for the entity.
     * @param x The x position
     * @param y The y position
     * @param width The width
     * @param height The height
     */
    public Entity(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.rotation = 0;
        this.width = width;
        this.height = height;
        this.active = true;
        this.bounds = new Rectangle(x, y, width, height);
    }
    
    /**
     * Updates the entity state.
     * @param delta The time in seconds since the last update
     */
    public void update(float delta) {
        // Update position based on velocity
        position.add(velocity.x * delta, velocity.y * delta);
        
        // Update collision bounds
        bounds.setPosition(position);
    }
    
    /**
     * Renders the entity.
     * @param batch The sprite batch to render with
     */
    public void render(SpriteBatch batch) {
        if (active && textureRegion != null) {
            batch.draw(
                textureRegion,
                position.x, position.y,
                width / 2, height / 2,
                width, height,
                1, 1,
                rotation
            );
        }
    }
    
    /**
     * Checks if this entity collides with another entity.
     * @param other The other entity
     * @return True if the entities collide, false otherwise
     */
    public boolean collidesWith(Entity other) {
        return this.bounds.overlaps(other.bounds);
    }
    
    // Getters and setters
    
    public Vector2 getPosition() {
        return position;
    }
    
    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.bounds.setPosition(position);
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }
    
    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
    
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
}