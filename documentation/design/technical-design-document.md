# Technical Design Document: 2D Sidescrolling Shooter Game

## 1. Architecture Overview

### 1.1 High-Level Architecture

The game will follow a component-based architecture with clear separation of concerns. The main architectural components include:

- **Core Game Engine**: Manages the game loop, rendering, and input handling
- **Screen Management System**: Handles different game screens and transitions
- **Entity Component System**: Manages game entities and their behaviors
- **Resource Management**: Handles loading and unloading of assets
- **Physics and Collision System**: Manages collision detection and response
- **Audio System**: Handles sound effects and music
- **UI System**: Manages game UI elements

### 1.2 Architectural Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      ShooterGame (Main)                     │
└───────────────────────────────┬─────────────────────────────┘
                                │
        ┌─────────────────────────────────────────────┐
        │                                             │
┌───────▼──────────┐    ┌────────────────┐    ┌──────▼─────────┐
│  Screen Manager  │    │ Asset Manager  │    │  Input Handler │
└───────┬──────────┘    └────────┬───────┘    └────────────────┘
        │                        │
┌───────▼──────────┐    ┌────────▼───────┐
│  Game Screens    │    │ Resource Cache │
│  - MenuScreen    │    └────────────────┘
│  - GameScreen    │
│  - GameOverScreen│
└───────┬──────────┘
        │
┌───────▼──────────┐    ┌────────────────┐    ┌────────────────┐
│ Entity Manager   │───►│Collision System│───►│ Physics System │
└───────┬──────────┘    └────────────────┘    └────────────────┘
        │
        ├─────────────┬─────────────┬─────────────┐
        │             │             │             │
┌───────▼──────┐┌─────▼─────┐┌──────▼──────┐┌─────▼─────┐
│   Player     ││  Enemies  ││ Projectiles ││ Power-ups │
└──────────────┘└───────────┘└─────────────┘└───────────┘
```

### 1.3 Project Structure

The project follows the standard libGDX project structure with additional organization for game-specific components:

```
src/main/java/com/yourgame/
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

## 2. Design Patterns

### 2.1 Core Design Patterns

#### 2.1.1 Singleton Pattern
Used for managers that should have only one instance:
- AssetManager
- EntityManager
- CollisionManager
- AudioManager

```java
public class AssetManager {
    private static AssetManager instance;

    private AssetManager() {
        // Private constructor
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    // Methods for asset management
}
```

#### 2.1.2 Factory Pattern
Used for creating different types of entities:
- EnemyFactory
- ProjectileFactory
- PowerUpFactory

```java
public class EnemyFactory {
    public static Enemy createEnemy(EnemyType type, float x, float y) {
        switch (type) {
            case BASIC:
                return new BasicEnemy(x, y);
            case SHOOTER:
                return new ShooterEnemy(x, y);
            case BOMBER:
                return new BomberEnemy(x, y);
            default:
                return new BasicEnemy(x, y);
        }
    }
}
```

#### 2.1.3 Object Pool Pattern
Used for frequently created and destroyed objects:
- ProjectilePool
- ExplosionEffectPool

```java
public class ProjectilePool {
    private final Array<Projectile> activeProjectiles = new Array<>();
    private final Array<Projectile> pooledProjectiles = new Array<>();

    public Projectile obtain(float x, float y, float velocityX, float velocityY) {
        Projectile projectile;
        if (pooledProjectiles.size > 0) {
            projectile = pooledProjectiles.pop();
            projectile.reset(x, y, velocityX, velocityY);
        } else {
            projectile = new Projectile(x, y, velocityX, velocityY);
        }
        activeProjectiles.add(projectile);
        return projectile;
    }

    public void free(Projectile projectile) {
        if (activeProjectiles.removeValue(projectile, true)) {
            pooledProjectiles.add(projectile);
        }
    }

    public void update(float delta) {
        for (int i = activeProjectiles.size - 1; i >= 0; i--) {
            Projectile projectile = activeProjectiles.get(i);
            projectile.update(delta);
            if (!projectile.isActive()) {
                free(projectile);
            }
        }
    }
}
```

#### 2.1.4 State Pattern
Used for entities with different states:
- PlayerState (Normal, Invulnerable, Dead)
- BossState (Different attack phases)

```java
public interface PlayerState {
    void update(Player player, float delta);
    void handleInput(Player player);
    void onEnter(Player player);
    void onExit(Player player);
}

public class NormalPlayerState implements PlayerState {
    @Override
    public void update(Player player, float delta) {
        // Normal update logic
    }

    @Override
    public void handleInput(Player player) {
        // Normal input handling
    }

    @Override
    public void onEnter(Player player) {
        // Setup for normal state
    }

    @Override
    public void onExit(Player player) {
        // Cleanup for normal state
    }
}
```

#### 2.1.5 Observer Pattern
Used for event handling:
- Collision events
- Game state changes
- Score updates

```java
public interface CollisionObserver {
    void onCollision(Entity entity1, Entity entity2);
}

public class CollisionManager {
    private Array<CollisionObserver> observers = new Array<>();

    public void addObserver(CollisionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CollisionObserver observer) {
        observers.removeValue(observer, true);
    }

    private void notifyCollision(Entity entity1, Entity entity2) {
        for (CollisionObserver observer : observers) {
            observer.onCollision(entity1, entity2);
        }
    }

    public void checkCollisions() {
        // Collision detection logic
        // When collision detected:
        notifyCollision(entity1, entity2);
    }
}
```

#### 2.1.6 Command Pattern
Used for input handling and actions:
- MoveCommand
- ShootCommand
- TogglePodCommand

```java
public interface Command {
    void execute(Entity entity);
}

public class MoveCommand implements Command {
    private final float velocityX;
    private final float velocityY;

    public MoveCommand(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public void execute(Entity entity) {
        entity.setVelocity(velocityX, velocityY);
    }
}
```

## 3. Component Specifications

### 3.1 Core Game Engine

#### 3.1.1 Game Loop
The game loop will use libGDX's built-in loop with the following phases:
1. Process input
2. Update game state
3. Check collisions
4. Render

```java
@Override
public void render(float delta) {
    // Process input
    handleInput();

    // Update game state
    updateGameState(delta);

    // Check collisions
    collisionManager.checkCollisions();

    // Render
    clearScreen();
    renderBackground();
    renderEntities();
    renderUI();
}
```

#### 3.1.2 Screen Management
Screens will be managed using libGDX's Screen interface:

```java
public class GameScreen implements Screen {
    private final ShooterGame game;
    private EntityManager entityManager;
    private CollisionManager collisionManager;

    public GameScreen(ShooterGame game) {
        this.game = game;
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        // Initialize other components
    }

    @Override
    public void show() {
        // Screen initialization
    }

    @Override
    public void render(float delta) {
        // Game loop implementation
    }

    // Other Screen methods
}
```

### 3.2 Entity System

#### 3.2.1 Base Entity Class

```java
public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Rectangle bounds;
    protected boolean active;
    protected TextureRegion currentFrame;

    public Entity(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(x, y, 0, 0);
        active = true;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        updateBounds();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    protected void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }
}
```

#### 3.2.2 Player Class

The Player class will extend Entity and implement:
- Movement controls
- Weapon firing
- Companion pod management
- Health/lives system

#### 3.2.3 Enemy Class

The Enemy class will extend Entity and implement:
- Different movement patterns
- Health system
- Attack patterns
- Score value

### 3.3 Collision System

The collision system will use libGDX's Rectangle class for hitbox-based collision detection:

```java
public class CollisionManager {
    private EntityManager entityManager;

    public CollisionManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void checkCollisions() {
        // Check player-enemy collisions
        Player player = entityManager.getPlayer();
        if (player.isActive() && !player.isInvulnerable()) {
            for (Enemy enemy : entityManager.getEnemies()) {
                if (enemy.isActive() && player.getBounds().overlaps(enemy.getBounds())) {
                    handlePlayerEnemyCollision(player, enemy);
                }
            }
        }

        // Check projectile-enemy collisions
        for (Projectile projectile : entityManager.getPlayerProjectiles()) {
            if (projectile.isActive()) {
                for (Enemy enemy : entityManager.getEnemies()) {
                    if (enemy.isActive() && projectile.getBounds().overlaps(enemy.getBounds())) {
                        handleProjectileEnemyCollision(projectile, enemy);
                    }
                }
            }
        }

        // Check player-powerup collisions
        for (PowerUp powerUp : entityManager.getPowerUps()) {
            if (powerUp.isActive() && player.isActive() && player.getBounds().overlaps(powerUp.getBounds())) {
                handlePlayerPowerUpCollision(player, powerUp);
            }
        }

        // Check enemy projectile-player collisions
        // ...
    }

    private void handlePlayerEnemyCollision(Player player, Enemy enemy) {
        player.takeDamage(1);
        enemy.takeDamage(1);
    }

    private void handleProjectileEnemyCollision(Projectile projectile, Enemy enemy) {
        projectile.setActive(false);
        enemy.takeDamage(projectile.getDamage());
    }

    private void handlePlayerPowerUpCollision(Player player, PowerUp powerUp) {
        powerUp.applyEffect(player);
        powerUp.setActive(false);
    }
}
```

### 3.4 Asset Management

The asset management system will handle loading and unloading of game resources:

```java
public class AssetLoader {
    private static AssetManager manager = new AssetManager();

    public static void load() {
        // Load textures
        manager.load("sprites/player.png", Texture.class);
        manager.load("sprites/enemies.png", Texture.class);
        manager.load("sprites/projectiles.png", Texture.class);
        manager.load("sprites/powerups.png", Texture.class);
        manager.load("sprites/background.png", Texture.class);

        // Load sounds
        manager.load("audio/shoot.wav", Sound.class);
        manager.load("audio/explosion.wav", Sound.class);
        manager.load("audio/powerup.wav", Sound.class);

        // Load music
        manager.load("audio/game_music.mp3", Music.class);
        manager.load("audio/menu_music.mp3", Music.class);

        // Wait for all assets to load
        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }

    public static <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }
}
```

## 4. Data Structures and Algorithms

### 4.1 Key Data Structures

#### 4.1.1 Entity Management
- Array/ArrayList for active entities
- QuadTree for spatial partitioning and efficient collision detection

#### 4.1.2 Level Data
- JSON format for level definitions
- Array for enemy spawn patterns

#### 4.1.3 Particle Effects
- Particle pool for explosion effects
- Particle emitter configurations

### 4.2 Key Algorithms

#### 4.2.1 Collision Detection
- Broad phase: Spatial partitioning with QuadTree
- Narrow phase: Rectangle overlap tests

#### 4.2.2 Enemy Movement Patterns
- Sine wave movement
- Bezier curves for complex paths
- Homing algorithm for tracking player

#### 4.2.3 Scrolling Background
- Parallax scrolling algorithm with multiple layers

## 5. Performance Considerations

### 5.1 Memory Management
- Use object pooling for frequently created/destroyed objects
- Implement texture atlases to reduce draw calls
- Unload assets when not needed

### 5.2 Rendering Optimization
- Implement view culling for off-screen entities
- Batch similar rendering operations
- Use sprite sheets and texture atlases

### 5.3 CPU Optimization
- Optimize collision detection with spatial partitioning
- Limit update frequency for distant objects
- Use efficient data structures for entity management

## 6. Cross-Platform Considerations

### 6.1 Desktop
- Support keyboard and gamepad input
- Configurable resolution and graphics settings
- Performance profiling for different hardware configurations

### 6.2 Mobile (Future Expansion)
- Touch controls adaptation
- Performance optimization for mobile devices
- UI scaling for different screen sizes

## 7. Testing Strategy

### 7.1 Unit Testing
- Test core game logic components
- Test collision detection accuracy
- Test entity behavior

### 7.2 Integration Testing
- Test interaction between different systems
- Test screen transitions
- Test game state persistence

### 7.3 Performance Testing
- FPS monitoring
- Memory usage tracking
- Load testing with many entities

## 8. Future Expansion Considerations

### 8.1 Multiplayer Support
- Client-server architecture
- Synchronization of game state
- Lobby system

### 8.2 Content Expansion
- Level editor
- Additional enemy types
- New weapon systems
- Achievement system

### 8.3 Platform Expansion
- Mobile port
- Web version using GWT
- Console platforms
