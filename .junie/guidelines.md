# Junie Game Development Guidelines

## Project Overview
This document outlines the standard guidelines and best practices for developing games using the Junie framework. These guidelines ensure consistency, maintainability, and quality across all game development projects.

## Project work

- Check details for tasks in development-todo.md
- Update development-todo.md when progressing to show current state of project
- Use project-summery.md as context and keep it updated while developing
- Adhere to technical-design-document.md

## Code Style

### Naming Conventions
- **Classes**: Use PascalCase (e.g., `PlayerController`, `EnemySpawner`)
- **Methods/Functions**: Use camelCase (e.g., `movePlayer()`, `spawnEnemy()`)
- **Variables**: Use camelCase (e.g., `playerHealth`, `enemyCount`)
- **Constants**: Use UPPER_SNAKE_CASE (e.g., `MAX_PLAYER_HEALTH`, `DEFAULT_SPEED`)
- **Packages**: Use lowercase with dots (e.g., `com.yourgame.entities`)

### Formatting
- Use 4 spaces for indentation (not tabs)
- Maximum line length: 100 characters
- Use blank lines to separate logical blocks of code
- Always use braces for control structures, even for single-line statements

### Documentation
- All public classes and methods should have JavaDoc comments
- Document complex algorithms and non-obvious code with inline comments
- Keep comments up-to-date with code changes

## Project Structure

### Directory Organization
- Follow the standard Maven/Gradle project structure
- Organize code by feature or domain, not by type
- Keep resource files (images, sounds, etc.) in appropriate subdirectories

### Dependencies
- Minimize external dependencies
- Document all dependencies with version numbers
- Use dependency management tools (Maven/Gradle)

## Game Development Practices

### Performance
- Use object pooling for frequently created/destroyed objects
- Minimize garbage collection by reusing objects
- Profile your game regularly to identify bottlenecks
- Target 60 FPS minimum on target platforms

### Asset Management
- Use texture atlases for sprites
- Optimize image sizes and formats
- Use appropriate audio formats and compression
- Implement asset loading screens for large games

### Game Loop
- Separate update logic from rendering
- Use delta time for movement calculations
- Handle variable frame rates gracefully

### Input Handling
- Support multiple input methods (keyboard, gamepad, touch)
- Make controls configurable
- Implement input buffering for responsive controls

### Testing
- Write unit tests for game logic
- Implement automated testing for critical systems
- Conduct regular playtesting sessions
- Test on all target platforms

## Version Control

### Git Practices
- Use feature branches for development
- Write clear, descriptive commit messages
- Regularly merge from main/master to avoid conflicts
- Tag releases with version numbers

### Versioning
- Follow Semantic Versioning (MAJOR.MINOR.PATCH)
- Maintain a changelog for each version

## Deployment

### Build Process
- Automate the build process
- Create separate builds for different platforms
- Implement continuous integration

### Release Checklist
- Verify all features work as expected
- Check performance on target platforms
- Ensure all assets are properly licensed
- Update version numbers and changelogs

## Collaboration

### Communication
- Document design decisions
- Use issue tracking for bugs and features
- Conduct regular code reviews
- Maintain up-to-date documentation

### Knowledge Sharing
- Document common problems and solutions
- Create tutorials for new team members
- Share reusable components and utilities

## Game-Specific Guidelines

### 2D Sidescrolling Shooter
- Maintain consistent difficulty curve
- Ensure enemy patterns are learnable but challenging
- Balance weapon upgrades for progression
- Design levels with clear visual language
- Implement responsive controls with appropriate feedback
