# 2D Sidescrolling Shooter Game

This is a classic 2D sidescrolling shooter game built with Java 21 and libGDX. For detailed information about the project, please refer to the [Project Summary](documentation/project-summary.md).

## Quick Start

To run the game:

1. Ensure you have Java 21 JDK installed
2. Clone this repository
3. Build the project using Maven: `mvn clean package`
4. Run the game using: `java -jar target/shooter-game-1.0-SNAPSHOT.jar`

Alternatively, you can run the game directly from your IDE by executing the main method in `src/main/java/com/shooter/desktop/DesktopLauncher.java`.

## Project Structure

```
src/main/java/com/shooter/
├── ShooterGame.java (Main game class)
├── screens/
│   ├── GameScreen.java
│   ├── MenuScreen.java
│   └── GameOverScreen.java
├── entities/
│   ├── Entity.java (Base class)
│   ├── Player.java
│   ├── Enemy.java
│   ├── Projectile.java
│   ├── PowerUp.java
│   └── CompanionPod.java
├── managers/
│   ├── EntityManager.java
│   ├── CollisionManager.java
│   ├── LevelManager.java
│   └── AudioManager.java
└── utils/
    ├── Constants.java
    └── AssetLoader.java
```

## Development Status

This project is currently in the initial development phase. Milestone 1 (Project Foundation) has been completed, which includes:
- Project setup with libGDX
- Basic project structure
- Core game framework
- Screen management system

## Documentation

For more detailed information about the project, please refer to the following documentation:

- [Project Summary](documentation/project-summary.md) - Overview of the project, key features, and technology stack
- [Technical Design Document](documentation/design/technical-design-document.md) - Detailed technical design, architecture, and component specifications
- [Implementation Recommendations](documentation/implementation-plan/implementation-recommendations.md) - Practical recommendations for implementing the game
- [Detailed Implementation Tasks](documentation/implementation-plan/detailed-implementation-tasks.md) - Comprehensive breakdown of implementation tasks
- [Development Todo](documentation/implementation-plan/development-todo.md) - Task list with milestones and deliverables
