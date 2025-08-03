# Planning a 2D Sidescrolling Shooter Game with Java 21 and libGDX

Creating a classic horizontal scrolling shooter game is an exciting project! Here's a comprehensive plan to help you develop your 2D sidescrolling shooter using Java 21 and libGDX.

## Game Overview

This game will be a classic horizontal scrolling shooter where the player controls a spacecraft fighting against an alien invasion. Key elements include:
- Continuous rightward scrolling
- Various enemy types with different movement patterns
- Power-ups and weapon upgrades
- Boss battles
- Distinctive "Force" pod that can attach to your ship or be launched as a weapon

## Technical Setup

### Development Environment
1. **Java 21** - Install the latest JDK
2. **libGDX** - Set up using the [libGDX project generator](https://libgdx.com/dev/project-generation/)
3. **IDE** - IntelliJ IDEA or Eclipse with Gradle support
4. **Version Control** - Git for source control

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/yourgame/
│   │       ├── ShooterGame.java (Main game class)
│   │       ├── screens/
│   │       │   ├── GameScreen.java
│   │       │   ├── MenuScreen.java
│   │       │   └── GameOverScreen.java
│   │       ├── entities/
│   │       │   ├── Entity.java (Base class)
│   │       │   ├── Player.java
│   │       │   ├── Enemy.java
│   │       │   ├── Projectile.java
│   │       │   ├── PowerUp.java
│   │       │   └── CompanionPod.java (The special attachable weapon pod)
│   │       ├── managers/
│   │       │   ├── EntityManager.java
│   │       │   ├── CollisionManager.java
│   │       │   ├── LevelManager.java
│   │       │   └── AudioManager.java
│   │       └── utils/
│   │           ├── Constants.java
│   │           └── AssetLoader.java
│   └── resources/
│       ├── sprites/
│       ├── audio/
│       └── levels/
└── test/
    └── java/
        └── com/yourgame/
            └── (Test classes)
```

## Core Game Components

### 1. Game Loop
Use libGDX's built-in game loop with the following phases:
- Input handling
- Update game state
- Collision detection
- Rendering

### 2. Entity System
- **Player Aircraft**: Controls, health, lives, weapon systems
- **Enemies**: Different types with unique behaviors and attack patterns
- **Projectiles**: Both player and enemy projectiles
- **Companion Pod**: The special attachable/detachable weapon pod
- **Power-ups**: Weapon upgrades, shields, speed boosts

### 3. Collision System
- Implement a collision detection system using libGDX's Rectangle or Circle classes
- Handle different collision types (player-enemy, projectile-enemy, player-powerup)

### 4. Level Design
- Scrolling background with parallax effect
- Enemy spawn patterns
- Boss encounters
- Level progression

### 5. Graphics
- Sprite sheets for animations
- Particle effects for explosions and weapon effects
- HUD for displaying score, lives, and power-up status

### 6. Audio
- Background music
- Sound effects for weapons, explosions, power-ups

## Implementation Plan

### Phase 1: Core Mechanics
1. Set up the libGDX project
2. Implement basic player movement
3. Create the scrolling background
4. Add simple shooting mechanics
5. Implement basic enemy spawning

### Phase 2: Expanded Gameplay
1. Add collision detection
2. Implement health/lives system
3. Create different enemy types
4. Add basic power-ups
5. Implement the Companion pod mechanic

### Phase 3: Polish and Content
1. Design multiple levels
2. Add boss battles
3. Implement scoring system
4. Create menu and game over screens
5. Add sound effects and music
6. Implement particle effects

### Phase 4: Refinement
1. Balance gameplay difficulty
2. Optimize performance
3. Add save/load functionality
4. Implement high scores
5. Add options menu (controls, volume, etc.)

## Code Examples

### Main Game Class
```java
public class ShooterGame extends Game {
    @Override
    public void create() {
        // Load assets
        Assets.load();
        // Set the initial screen
        setScreen(new MenuScreen(this));
    }
}
```

### Player Class
```java
public class Player extends Entity {
    private int lives;
    private int score;
    private Weapon currentWeapon;
    private CompanionPod companionPod;

    public Player(float x, float y) {
        super(x, y);
        lives = 3;
        score = 0;
        currentWeapon = new BasicWeapon();
        // Initialize other properties
    }

    @Override
    public void update(float delta) {
        // Handle input
        handleInput();
        // Update position
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        // Keep player within screen bounds
        keepInBounds();
        // Update weapon cooldown
        currentWeapon.update(delta);
        // Update companion pod if attached
        if (companionPod != null && companionPod.isAttached()) {
            companionPod.setPosition(position.x + 30, position.y);
        }
    }

    private void handleInput() {
        // Process keyboard/gamepad input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -200;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = 200;
        } else {
            velocity.x = 0;
        }

        // Similar for up/down

        // Shooting
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            shoot();
        }

        // Companion pod control
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            toggleCompanionPod();
        }
    }

    private void shoot() {
        if (currentWeapon.canFire()) {
            currentWeapon.fire(position.x, position.y);
        }
    }

    private void toggleCompanionPod() {
        if (companionPod != null) {
            companionPod.toggle();
        }
    }
}
```

### Enemy Spawning System
```java
public class EnemySpawner {
    private float spawnTimer;
    private float spawnInterval;
    private Array<EnemyDefinition> enemyTypes;
    private EntityManager entityManager;

    public EnemySpawner(EntityManager entityManager) {
        this.entityManager = entityManager;
        spawnTimer = 0;
        spawnInterval = 1.5f; // Spawn every 1.5 seconds initially
        enemyTypes = new Array<>();
        // Initialize enemy types
        loadEnemyTypes();
    }

    public void update(float delta, float scrollSpeed) {
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            spawnEnemy(scrollSpeed);
        }
    }

    private void spawnEnemy(float scrollSpeed) {
        // Choose a random enemy type
        EnemyDefinition def = enemyTypes.random();
        // Create enemy at the right side of the screen with random y position
        float x = Gdx.graphics.getWidth();
        float y = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
        Enemy enemy = new Enemy(x, y, def.getType(), def.getHealth(), def.getSpeed());
        // Add to entity manager
        entityManager.addEntity(enemy);
    }
}
```

## Resources

### libGDX Documentation
- [Official libGDX Wiki](https://github.com/libgdx/libgdx/wiki)
- [libGDX API Documentation](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/)

### Game Design References
- Study classic horizontal shooter gameplay videos to understand enemy patterns and level design
- Research classic arcade shooters for inspiration on level design and enemy patterns

### Art Resources
- [OpenGameArt](https://opengameart.org/) for free game assets
- [Kenney](https://kenney.nl/) for free game assets
- Consider hiring an artist for custom sprites or creating your own

### Sound Resources
- [Freesound](https://freesound.org/) for sound effects
- [incompetech](https://incompetech.com/) for royalty-free music

## Next Steps

1. Set up your development environment with Java 21 and libGDX
2. Create a basic prototype with player movement and scrolling background
3. Implement shooting mechanics
4. Add simple enemies
5. Build from there following the implementation plan
