# ReMind - Pattern Memory Game

**ReMind** is an interactive Android game designed to boost memory and pattern recognition skills. Players are challenged to observe, memorize, and reproduce complex patterns on a grid-based interface as they progress through increasingly difficult levels. The game is both engaging and educational, leveraging smooth animations, intuitive controls, and a sleek Material Design interface.

## Features

ReMind offers a dynamic and engaging experience with:
- **Interactive Grid Gameplay**: A 6x7 grid of touch-sensitive nodes.
- **Memory-Based Challenge**: Observe and reproduce patterns, starting with 5 nodes and increasing in complexity every two levels.
- **Score Tracking**: Historical records of gameplay performance, displayed in a score history log.
- **Real-Time Feedback**: Animations for path tracing and lines drawn as nodes are selected to show path taken.
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
- **Start Point**: Highlighted by a Pulsating blue node
- **End Point**: Marked by where the sequence ends.
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
![image](https://github.com/user-attachments/assets/e0490e61-bcea-4f58-a18e-5f85c243764b)



2. **Main Menu**:  
  ![image](https://github.com/user-attachments/assets/1a52dfe5-d949-4392-913f-6c193c4162b9)



3. **Score History**:  

![image](https://github.com/user-attachments/assets/f28523f8-fb80-4a24-9ffa-42c0f6d7eb37)


## Technical Details

The game’s implementation includes:
- **Grid Management**: A 6x7 grid is modeled using a `Graph` class, supporting 8-directional connections between nodes.
- **Dynamic Path Generation**: Randomized paths are generated with increasing length based on the level, ensuring unique and challenging gameplay.
- **UI Components**: Custom button designs with color-coded states:
  - Grey: Default/unselected nodes.
  - Blue: Active path nodes.
  - Pulsating Blue node: Indicates the start point of the sequence
 
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
