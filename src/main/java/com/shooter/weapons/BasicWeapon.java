package com.shooter.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.shooter.entities.Entity;
import com.shooter.entities.Projectile;
import com.shooter.utils.Constants;

/**
 * Basic weapon implementation.
 * Fires single projectiles at a fixed rate.
 */
public class BasicWeapon implements Weapon {
    // Weapon properties
    private float cooldown;
    private float cooldownTimer;
    private int damage;
    private boolean playerOwned;

    // Projectile properties
    private TextureRegion projectileTexture;
    private float projectileWidth;
    private float projectileHeight;

    // Active projectiles
    private Array<Projectile> projectiles;

    // Muzzle flash effect
    private boolean showMuzzleFlash;
    private float muzzleFlashTimer;
    private float muzzleFlashX;
    private float muzzleFlashY;
    private float muzzleFlashScale;
    private float muzzleFlashRotation;
    private float muzzleFlashAlpha;
    private float[] muzzleFlashParticlesX;
    private float[] muzzleFlashParticlesY;
    private float[] muzzleFlashParticlesScale;
    private float[] muzzleFlashParticlesAlpha;
    private float[] muzzleFlashParticlesSpeed;

    // Explosion effects
    private static class ExplosionEffect {
        float x, y;
        float timer;
        float scale;
        float rotation;
        float alpha;

        public ExplosionEffect(float x, float y) {
            this.x = x;
            this.y = y;
            this.timer = 0.3f; // Duration in seconds
            this.scale = 1.0f;
            this.rotation = MathUtils.random(0, 360);
            this.alpha = 1.0f;
        }

        public void update(float delta) {
            timer -= delta;
            scale += delta * 3.0f; // Grow over time
            alpha = timer / 0.3f; // Fade out over duration
        }

        public boolean isActive() {
            return timer > 0;
        }
    }

    private Array<ExplosionEffect> explosions;

    /**
     * Constructor for the basic weapon.
     * @param cooldown The cooldown time in seconds
     * @param damage The damage each projectile deals
     * @param playerOwned Whether the weapon is owned by the player
     * @param projectileTexture The texture for projectiles
     * @param projectileWidth The width of projectiles
     * @param projectileHeight The height of projectiles
     */
    public BasicWeapon(float cooldown, int damage, boolean playerOwned, 
                       TextureRegion projectileTexture, float projectileWidth, float projectileHeight) {
        this.cooldown = cooldown;
        this.cooldownTimer = 0;
        this.damage = damage;
        this.playerOwned = playerOwned;
        this.projectileTexture = projectileTexture;
        this.projectileWidth = projectileWidth;
        this.projectileHeight = projectileHeight;
        this.projectiles = new Array<Projectile>();

        // Initialize muzzle flash properties
        this.showMuzzleFlash = false;
        this.muzzleFlashTimer = 0;
        this.muzzleFlashX = 0;
        this.muzzleFlashY = 0;
        this.muzzleFlashScale = 1.0f;
        this.muzzleFlashRotation = 0;
        this.muzzleFlashAlpha = 1.0f;

        // Initialize muzzle flash particles (8 particles)
        this.muzzleFlashParticlesX = new float[8];
        this.muzzleFlashParticlesY = new float[8];
        this.muzzleFlashParticlesScale = new float[8];
        this.muzzleFlashParticlesAlpha = new float[8];
        this.muzzleFlashParticlesSpeed = new float[8];

        // Initialize explosions array
        this.explosions = new Array<ExplosionEffect>();
    }

    /**
     * Fires the weapon from the specified position.
     * @param x The x position to fire from
     * @param y The y position to fire from
     * @return True if the weapon fired, false if it's on cooldown
     */
    @Override
    public boolean fire(float x, float y) {
        if (cooldownTimer <= 0) {
            // Create a new projectile
            Projectile projectile = new Projectile(
                x, y, 
                projectileWidth, projectileHeight, 
                damage, playerOwned
            );

            // Set the projectile texture
            projectile.setTextureRegion(projectileTexture);

            // Add the projectile to the active projectiles
            projectiles.add(projectile);

            // Reset the cooldown timer
            cooldownTimer = cooldown;

            // Show muzzle flash
            showMuzzleFlash = true;
            muzzleFlashTimer = 0.25f; // Duration of muzzle flash in seconds (increased for more animation)
            muzzleFlashX = x;
            muzzleFlashY = y;
            muzzleFlashScale = 10.0f; // Initial scale of muzzle flash (5 times bigger)
            muzzleFlashRotation = MathUtils.random(0, 360); // Random rotation for variety
            muzzleFlashAlpha = 1.0f;

            // Initialize muzzle flash particles
            for (int i = 0; i < muzzleFlashParticlesX.length; i++) {
                // Position particles at the muzzle
                muzzleFlashParticlesX[i] = x;
                muzzleFlashParticlesY[i] = y;

                // Random scale for each particle (2.5 to 7.5) - 5x bigger
                muzzleFlashParticlesScale[i] = 2.5f + MathUtils.random(5.0f);

                // Full opacity to start
                muzzleFlashParticlesAlpha[i] = 1.0f;

                // Random speed for each particle (6 to 16) - faster for more animation
                muzzleFlashParticlesSpeed[i] = 6.0f + MathUtils.random(10.0f);
            }

            return true;
        }
        return false;
    }

    /**
     * Updates all active projectiles from this weapon.
     * @param delta The time in seconds since the last update
     */
    @Override
    public void update(float delta) {
        // Update cooldown timer
        if (cooldownTimer > 0) {
            cooldownTimer -= delta;
        }

        // Update muzzle flash
        if (showMuzzleFlash) {
            muzzleFlashTimer -= delta;
            if (muzzleFlashTimer <= 0) {
                showMuzzleFlash = false;
            } else {
                // Animate main muzzle flash (more dynamic animation)
                float progress = 1.0f - (muzzleFlashTimer / 0.25f); // 0.0 to 1.0 over duration
                // More dynamic scale animation - expand then contract
                if (progress < 0.3f) {
                    // First 30% of animation: expand from 10.0 to 12.0
                    muzzleFlashScale = 10.0f + (progress / 0.3f) * 2.0f;
                } else {
                    // Remaining 70%: contract from 12.0 to 5.0
                    muzzleFlashScale = 12.0f - ((progress - 0.3f) / 0.7f) * 7.0f;
                }
                // Slower fade for longer visibility
                muzzleFlashAlpha = 1.0f - progress * 0.6f; // Fade from 1.0 to 0.4

                // Animate muzzle flash particles
                for (int i = 0; i < muzzleFlashParticlesX.length; i++) {
                    // Calculate direction (spread in a cone)
                    float angle = i * 45f; // 8 particles spread in 360 degrees (45 degrees apart)

                    // Move particles outward
                    muzzleFlashParticlesX[i] += MathUtils.cosDeg(angle) * muzzleFlashParticlesSpeed[i] * delta;
                    muzzleFlashParticlesY[i] += MathUtils.sinDeg(angle) * muzzleFlashParticlesSpeed[i] * delta;

                    // Shrink particles
                    muzzleFlashParticlesScale[i] -= delta * 2.0f;
                    if (muzzleFlashParticlesScale[i] < 0.1f) {
                        muzzleFlashParticlesScale[i] = 0.1f;
                    }

                    // Fade particles
                    muzzleFlashParticlesAlpha[i] -= delta * 5.0f;
                    if (muzzleFlashParticlesAlpha[i] < 0) {
                        muzzleFlashParticlesAlpha[i] = 0;
                    }
                }
            }
        }

        // Update projectiles
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);

            // Remove inactive projectiles and create explosion effects
            if (!projectile.isActive()) {
                // Create explosion effect at projectile position
                explosions.add(new ExplosionEffect(
                    projectile.getPosition().x + projectile.getWidth() / 2,
                    projectile.getPosition().y + projectile.getHeight() / 2
                ));

                projectiles.removeIndex(i);
                i--;
            }
        }

        // Update explosion effects
        for (int i = 0; i < explosions.size; i++) {
            ExplosionEffect explosion = explosions.get(i);
            explosion.update(delta);

            // Remove inactive explosions
            if (!explosion.isActive()) {
                explosions.removeIndex(i);
                i--;
            }
        }
    }

    /**
     * Renders all active projectiles from this weapon.
     * @param batch The sprite batch to render with
     */
    @Override
    public void render(SpriteBatch batch) {
        // Save original color
        float originalR = batch.getColor().r;
        float originalG = batch.getColor().g;
        float originalB = batch.getColor().b;
        float originalA = batch.getColor().a;

        // Render explosion effects (behind everything else)
        if (projectileTexture != null) {
            for (ExplosionEffect explosion : explosions) {
                // Calculate colors based on explosion progress
                float progress = 1.0f - (explosion.timer / 0.3f); // 0.0 to 1.0 over duration

                // Outer explosion (orange-red)
                batch.setColor(1.0f, 0.5f - progress * 0.3f, 0.2f - progress * 0.2f, explosion.alpha * 0.7f);
                batch.draw(
                    projectileTexture,
                    explosion.x - explosion.scale * 1.5f, explosion.y - explosion.scale * 1.5f,
                    explosion.scale * 1.5f, explosion.scale * 1.5f,
                    explosion.scale * 3.0f, explosion.scale * 3.0f,
                    1, 1,
                    explosion.rotation
                );

                // Inner explosion (yellow-white)
                batch.setColor(1.0f, 0.9f - progress * 0.3f, 0.5f - progress * 0.5f, explosion.alpha);
                batch.draw(
                    projectileTexture,
                    explosion.x - explosion.scale * 0.75f, explosion.y - explosion.scale * 0.75f,
                    explosion.scale * 0.75f, explosion.scale * 0.75f,
                    explosion.scale * 1.5f, explosion.scale * 1.5f,
                    1, 1,
                    explosion.rotation + 30 // Offset rotation for variety
                );
            }
        }

        // Render projectiles
        for (Projectile projectile : projectiles) {
            projectile.render(batch);
        }

        // Render muzzle flash (on top of projectiles)
        if (showMuzzleFlash && projectileTexture != null) {
            // Render muzzle flash particles first (behind the main flash)
            for (int i = 0; i < muzzleFlashParticlesX.length; i++) {
                if (muzzleFlashParticlesAlpha[i] > 0) {
                    // Determine color based on particle index (create a gradient from yellow to red)
                    float red = 1.0f;
                    float green = 0.8f - (i / (float)muzzleFlashParticlesX.length) * 0.6f;
                    float blue = 0.2f - (i / (float)muzzleFlashParticlesX.length) * 0.2f;

                    batch.setColor(red, green, blue, muzzleFlashParticlesAlpha[i]);
                    batch.draw(
                        projectileTexture,
                        muzzleFlashParticlesX[i] - muzzleFlashParticlesScale[i], 
                        muzzleFlashParticlesY[i] - muzzleFlashParticlesScale[i],
                        muzzleFlashParticlesScale[i], muzzleFlashParticlesScale[i],
                        muzzleFlashParticlesScale[i] * 2, muzzleFlashParticlesScale[i] * 2,
                        1, 1,
                        i * 45 // Each particle has a different rotation
                    );
                }
            }

            // Draw outer glow (larger, more transparent) - enhanced for bigger flash
            batch.setColor(1.0f, 0.8f, 0.2f, muzzleFlashAlpha * 0.5f); // Yellow-orange glow
            batch.draw(
                projectileTexture,
                muzzleFlashX - muzzleFlashScale * 1.8f, muzzleFlashY - muzzleFlashScale * 1.8f,
                muzzleFlashScale * 1.8f, muzzleFlashScale * 1.8f,
                muzzleFlashScale * 3.6f, muzzleFlashScale * 3.6f,
                1, 1,
                muzzleFlashRotation + MathUtils.sin(muzzleFlashTimer * 20) * 10 // Animated rotation
            );

            // Draw middle layer (new layer for more dynamic effect)
            batch.setColor(1.0f, 0.9f, 0.4f, muzzleFlashAlpha * 0.7f); // Yellow-orange
            batch.draw(
                projectileTexture,
                muzzleFlashX - muzzleFlashScale * 1.2f, muzzleFlashY - muzzleFlashScale * 1.2f,
                muzzleFlashScale * 1.2f, muzzleFlashScale * 1.2f,
                muzzleFlashScale * 2.4f, muzzleFlashScale * 2.4f,
                1, 1,
                muzzleFlashRotation - MathUtils.sin(muzzleFlashTimer * 15) * 15 // Animated rotation in opposite direction
            );

            // Draw inner flash (smaller, brighter)
            batch.setColor(1.0f, 1.0f, 0.8f, muzzleFlashAlpha * 0.8f); // Bright yellow-white
            batch.draw(
                projectileTexture,
                muzzleFlashX - muzzleFlashScale * 0.75f, muzzleFlashY - muzzleFlashScale * 0.75f,
                muzzleFlashScale * 0.75f, muzzleFlashScale * 0.75f,
                muzzleFlashScale * 1.5f, muzzleFlashScale * 1.5f,
                1, 1,
                muzzleFlashRotation + 45 + MathUtils.cos(muzzleFlashTimer * 25) * 20 // Animated rotation
            );

            // Draw a bright center point for extra flash effect
            batch.setColor(1.0f, 1.0f, 1.0f, muzzleFlashAlpha);
            batch.draw(
                projectileTexture,
                muzzleFlashX - muzzleFlashScale * 0.3f, muzzleFlashY - muzzleFlashScale * 0.3f,
                muzzleFlashScale * 0.3f, muzzleFlashScale * 0.3f,
                muzzleFlashScale * 0.6f, muzzleFlashScale * 0.6f,
                1, 1,
                muzzleFlashRotation + 90 // Different rotation for variety
            );
        }

        // Restore original color
        batch.setColor(originalR, originalG, originalB, originalA);
    }

    /**
     * Checks if any projectiles from this weapon collide with the specified entity.
     * @param entity The entity to check collision with
     * @return True if a collision occurred, false otherwise
     */
    @Override
    public boolean checkCollision(Entity entity) {
        for (int i = 0; i < projectiles.size; i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.isActive() && projectile.collidesWith(entity)) {
                if (projectile.handleCollision(entity)) {
                    // Create explosion effect at collision point
                    explosions.add(new ExplosionEffect(
                        projectile.getPosition().x + projectile.getWidth() / 2,
                        projectile.getPosition().y + projectile.getHeight() / 2
                    ));

                    projectile.setActive(false);
                    projectiles.removeIndex(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the cooldown time of the weapon.
     * @return The cooldown time in seconds
     */
    @Override
    public float getCooldown() {
        return cooldown;
    }

    /**
     * Sets the cooldown time of the weapon.
     * @param cooldown The cooldown time in seconds
     */
    @Override
    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Gets the number of active projectiles.
     * @return The number of active projectiles
     */
    public int getActiveProjectileCount() {
        return projectiles.size;
    }
}
