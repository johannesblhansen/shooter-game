# 2D Sidescrolling Shooter Game - Project Summary

## Project Overview

This project aims to create a classic 2D sidescrolling shooter game using Java 21 and the libGDX framework. The game will feature a spacecraft fighting against an alien invasion with continuous rightward scrolling, various enemy types, power-ups, boss battles, and a special companion pod mechanic.

## Key Features

- Vibrant pixel art visual style with crisp, detailed sprites
- Smooth sidescrolling gameplay with parallax backgrounds
- Player spacecraft with customizable weapons
- Variety of enemy types with unique movement and attack patterns
- Special companion pod that can attach to the player ship or be launched as a weapon
- Power-up system with weapon upgrades, shields, and special abilities
- Multiple levels with increasing difficulty
- Boss battles at the end of each level
- High score system and game progression

## Technology Stack

- **Programming Language**: Java 21
- **Game Framework**: libGDX
- **Build System**: Maven
- **Version Control**: Git
- **IDE**: IntelliJ IDEA or Eclipse

## Dependency Management

The project uses Maven to manage dependencies and libGDX requirements. Maven provides:

- Centralized dependency management for all libGDX components
- Consistent build process across different environments
- Simplified integration of additional libraries
- Automated handling of transitive dependencies
- Clear separation of compile, runtime, and test dependencies

The project's `pom.xml` file will include all necessary libGDX artifacts and their appropriate versions, ensuring compatibility and easy updates.


## Technical Design

For detailed information about the technical design of this project, please refer to the [Technical Design Document](design/technical-design-document.md).

## Implementation Recommendations

For practical guidance on implementing this project, please refer to the [Implementation Recommendations](implementation-plan/implementation-recommendations.md).

## Current Progress

### Milestone 1: Project Foundation ✓
The first milestone has been successfully completed, establishing the foundation for the game:

- **Project Setup**: The libGDX project has been set up with the necessary dependencies, including Core libGDX, Box2D for physics, and Freetype for font rendering.
- **Project Structure**: The basic package structure has been created, organizing code into logical components (screens, entities, managers, utils).
- **Core Framework**: The main game class and screen management system have been implemented, allowing for smooth transitions between different game states.
- **Screen System**: Placeholder screens have been created for the menu, game, and game over states, with proper navigation between them.
- **Asset Management**: A basic asset management system has been implemented, with placeholder pixel art textures for the player, enemies, projectiles, and background.

### Progress on Milestone 2: Playable Prototype
Some aspects of Milestone 2 have also been implemented:

- **Asset Management**: The AssetManager class provides centralized asset handling with pixel art style textures.
- **Entity System**: Basic entity classes have been created for the player, enemies, and projectiles.
- **Scrolling Background**: A parallax scrolling background has been implemented with multiple layers.
- **Basic Shooting Mechanics**: A simple weapon system has been implemented with projectile firing and movement.
- **Simple Enemies**: Basic enemy spawning and movement have been implemented.
- **Collision Detection**: Simple collision detection between player projectiles and enemies has been added.

## Conclusion

This project creates a classic arcade-style game using modern Java and the libGDX framework. The game features engaging gameplay with various enemies, power-ups, and boss battles.

The modular architecture and clear separation of concerns make the codebase maintainable and extensible, allowing for future enhancements and features.
