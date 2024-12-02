# ReMind - Pattern Memory Game

**ReMind** is an interactive Android game designed to boost memory and pattern recognition skills. Players are challenged to observe, memorize, and reproduce complex patterns on a grid-based interface as they progress through increasingly difficult levels. The game is both engaging and educational, leveraging smooth animations, intuitive controls, and a sleek Material Design interface.

## Features

ReMind offers a dynamic and engaging experience with:
- **Interactive Grid Gameplay**: A 6x7 grid of touch-sensitive nodes.
- **Memory-Based Challenge**: Observe and reproduce patterns, starting with 5 nodes and increasing in complexity every two levels.
- **Score Tracking**: Historical records of gameplay performance, displayed in a score history log.
- **Real-Time Feedback**: Animations for path tracing and color-coded feedback to help players track progress.
- **Adaptive Difficulty**: Three attempts per level with dynamic path complexity as levels increase.

Technical features include:
- **Custom Path Animation**: Smooth, interactive path drawing with corner effects.
- **Room Database**: Persistent local storage for score history.
- **MVVM Architecture**: Separation of concerns for a clean and maintainable codebase.
- **Material Design Components**: A visually appealing and intuitive interface.
- **Asynchronous Performance**: Efficient operations powered by Kotlin coroutines.

## Gameplay Mechanics

### Observation Phase:
Players observe a visually displayed path on the grid:
- **Start Point**: Highlighted in green.
- **End Point**: Marked in red.
- **Path**: Drawn in blue to indicate the sequence.

### Reproduction Phase:
- Players use touch and drag gestures to recreate the displayed path.
- Selected nodes will turn Blue
- Movement is valid in all 8 directions (horizontal, vertical, and diagonal).

### Scoring:
- Players earn scores based on completion time and level progression.
- Each level allows **three attempts** to complete the path.

## Screenshots

1. **Gameplay**:  
 ![image](https://github.com/user-attachments/assets/160a97fe-60e8-4292-ba7d-548f558fe87d)


2. **Main Menu**:  
   ![image](https://github.com/user-attachments/assets/e3246126-84f1-46c5-8cba-a209cc614bd1)


3. **Score History**:  
 ![image](https://github.com/user-attachments/assets/fef306bd-1515-4eb3-a399-23a48e98084c)


## Technical Details

The game’s implementation includes:
- **Grid Management**: A 6x7 grid is modeled using a `Graph` class, supporting 8-directional connections between nodes.
- **Dynamic Path Generation**: Randomized paths are generated with increasing length based on the level, ensuring unique and challenging gameplay.
- **UI Components**: Custom button designs with color-coded states:
  - Grey: Default/unselected nodes.
  - Green: Start node.
  - Red: End node.
  - Blue: Active path nodes.
 
## Database Schema
PathScore Entity:
- attemptNo (Primary Key)
- dateTime (String)
- score (Int)
- timeTaken (Long)

## Installation 
- **Requirements**:
- Android 8.1(API 27) or higher
- Target SDK: Android 14(API 34)

## Development
- **Key Dependancies**:
- - AndroidX Core KTX
- Room Database (v2.6.1)
- Material Design Components
- ViewBinding
- Coroutines

## Future Enhancements:
- Additional Mini-games
- Difficulty modes
- Enhanced scoring system
- Multiplayer support

## Contributions
- Contributions are welcome! Please feel free to submit pull requests
