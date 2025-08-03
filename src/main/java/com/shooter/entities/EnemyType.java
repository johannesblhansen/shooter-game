package com.shooter.entities;

/**
 * Enum for different enemy types.
 * Each type has different behavior and properties.
 */
public enum EnemyType {
    /**
     * Basic enemy that moves in a straight line.
     */
    BASIC,
    
    /**
     * Enemy that moves in a sine wave pattern.
     */
    SINE_WAVE,
    
    /**
     * Enemy that can shoot projectiles.
     */
    SHOOTER,
    
    /**
     * Enemy that moves faster than basic enemies.
     */
    FAST,
    
    /**
     * Enemy with more health than basic enemies.
     */
    TANK
}