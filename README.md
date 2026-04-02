🏛️ Theseus & Minotaur - Advanced Labyrinth Game 👹
A sophisticated Java implementation of the classic Theseus and Minotaur labyrinth game with advanced AI players, modern GUI, and comprehensive gameplay features.

� Game Overview
In this strategic game, Theseus must collect all supplies in a randomly generated labyrinth while avoiding the Minotaur. The Minotaur's goal is to catch Theseus before he completes his mission. Both characters move through a maze with walls that block certain paths, creating a challenging tactical environment.

🎯 Objective
Theseus: Collect all 4 supplies scattered throughout the labyrinth to win
Minotaur: Catch Theseus by moving to the same tile to win
Tie Condition: If 100 moves are completed without a winner
🚀 Features
🤖 Advanced AI Players
Random Player: Makes completely random valid moves (baseline difficulty)
Heuristic Player: Uses sophisticated evaluation functions considering supply proximity and opponent distance
MinMax Player: Implements MinMax algorithm with 2-level lookahead for optimal strategic play
🎨 Modern GUI Enhancements
Enhanced Visual Design: Color-coded player themes, professional styling, and modern layout
Image Support: Toggle between text and graphical representations with custom character sprites
Real-time Statistics: Live display of move scores, total scores, and game progress
Interactive Controls: Dropdown menus with info buttons explaining each AI strategy
Responsive Layout: Organized panels with proper spacing and no component overlap
⚡ Gameplay Modes
Manual Play: Click to advance each move step-by-step
Automated Play: Watch the game unfold automatically with customizable speed (800ms intervals)
Mix & Match: Combine different AI types for varied gameplay experiences
📊 Statistics & Analysis
Detailed Player Statistics: Comprehensive analysis for Heuristic and MinMax players
Move Evaluation: Real-time display of move scores and decision-making process
Game History: Round-by-round progression tracking
Performance Metrics: Win/loss statistics and strategic insights
🏗️ Project Structure
Theseus - Minotaur/
├── Game.java              # Main game controller and GUI management
├── MyPanel.java            # Board visualization and rendering
├── Board.java              # Game board logic and maze generation
├── Tile.java              # Individual tile properties and wall management
├── Supply.java            # Supply item management
├── Player.java            # Base player class with random movement
├── HeuristicPlayer.java   # AI player using evaluation functions
├── MinMaxPlayer.java      # AI player using MinMax algorithm
├── create_images.java     # Utility to generate sample game sprites
├── images/                # Directory for character and item sprites
│   ├── theseus.png       # Hero character image (32x32)
│   ├── minotaur.png      # Monster character image (32x32)
│   └── supply.png        # Treasure/supply image (32x32)
└── README.md             # This documentation file
🎯 AI Strategy Details
🎲 Random Player
Strategy: Completely random valid moves
Use Case: Baseline for testing and learning game mechanics
Difficulty: Beginner
🧠 Heuristic Player
Strategy: Sophisticated evaluation functions
Factors Considered:
Distance to nearest supplies
Distance to opponent
Strategic positioning
Evaluation Formula:
Theseus: nearSupplies × 0.46 - opponentDistance × 0.54
Minotaur: nearSupplies × 0.46 + opponentDistance × 0.54
Difficulty: Intermediate
🎯 MinMax Player
Strategy: MinMax algorithm with 2-level lookahead
Features:
Game tree exploration
Considers opponent's optimal responses
Maximizes own advantage while minimizing opponent's
Difficulty: Advanced/Expert
🚀 Getting Started
Prerequisites
Java Development Kit (JDK) 8 or higher
Java Swing support (included in most JDK distributions)
Installation & Setup
Clone or download the project files to your local machine

Navigate to the project directory:

cd "c:\Users\chatzc01\Projects\java\Theseus - Minotaur"
Compile all Java files:

javac *.java
(Optional) Create sample images:

mkdir images
javac create_images.java
java create_images
Run the game:

java Game
🎮 How to Play
Game Setup
Launch the application
Choose game mode: Manual Play or Automated Play
Select AI types for both Theseus and Minotaur using dropdown menus
Click info buttons (i) to learn about different AI strategies
Generate Board to create a new random labyrinth
Gameplay Controls
▶ Play/Auto Play: Advance the game (manual or automated mode)
🎲 Generate Board: Create a new random maze
🖼️ Images/📝 Text: Toggle between graphical and text display
❌ Quit: Exit the application
Visual Elements
Blue Theme: Theseus (Hero) elements
Red Theme: Minotaur (Monster) elements
Green Background: Labyrinth environment
Supply Icons: Treasures scattered throughout the maze
Real-time Stats: Live updates of scores and move evaluations
🎨 Visual Features
Enhanced Graphics
Modern Color Scheme: Professional blue/red theme with green labyrinth
Custom Sprites: 32x32 pixel character and item images
Smooth Transitions: Anti-aliased rendering for professional appearance
Responsive Design: Scalable UI elements with proper spacing
Image System
Automatic Fallback: Uses text if images aren't available
Smart Positioning: Perfect alignment within game tiles
Collision Handling: Proper display when characters occupy the same tile
Clean Supply Display: Image-only mode removes text overlays
🔧 Configuration
Game Parameters
Board Size: 15×15 grid (225 tiles)
Supply Count: 4 treasures
Move Limit: 100 moves (tie condition)
Auto-Play Speed: 800ms between moves
Customization Options
Player Types: Mix any combination of Random, Heuristic, MinMax
Visual Mode: Switch between images and text at any time
Game Mode: Choose manual clicking or automated viewing
📈 Statistics & Analytics
Real-time Information
Round Counter: Current game round
Move Scores: AI evaluation for each move
Total Scores: Supply collection progress
Strategy Display: Current AI type for each player
End-game Analysis
Detailed Statistics: Comprehensive AI performance data
Move History: Complete game progression
Strategic Insights: Decision-making analysis for AI players
�️ Technical Implementation
Core Technologies
Java Swing: GUI framework
Object-Oriented Design: Modular architecture
AI Algorithms: Heuristic evaluation and MinMax tree search
Image Processing: BufferedImage for sprite handling
Key Classes
Game: Main controller, GUI management, event handling
MyPanel: Custom JPanel for board rendering and image display
Board: Maze generation, wall management, supply placement
Player Classes: Polymorphic AI implementation with different strategies
Performance Features
Efficient Rendering: Optimized paint methods with anti-aliasing
Smart Updates: Component updating without full recreation
Memory Management: Proper image loading and disposal
🎯 Gameplay Tips
For Theseus Players
Heuristic Strategy: Good balance of supply collection and evasion
MinMax Strategy: Optimal play but may be predictable
Random Strategy: Unpredictable but inefficient
For Minotaur Players
Heuristic Strategy: Aggressive pursuit with strategic positioning
MinMax Strategy: Calculated hunting with move prediction
Random Strategy: Chaotic but sometimes surprisingly effective
Strategic Combinations
Balanced: Heuristic vs Heuristic for competitive games
Learning: Random vs Heuristic to understand AI differences
Challenge: Any player vs MinMax for maximum difficulty
Analysis: MinMax vs MinMax for optimal strategic play
� Future Enhancements
Potential Improvements
Save/Load Games: Persistent game state management
Replay System: Record and playback game sessions
Custom Board Sizes: Variable labyrinth dimensions
Network Play: Multiplayer over network connections
Advanced Analytics: Heat maps and move prediction visualization
🤝 Contributing
This project welcomes improvements and contributions. Key areas for enhancement include:

Additional AI algorithms (A*, genetic algorithms)
Enhanced graphics and animations
Sound effects and music
Performance optimizations
User interface improvements
� License
This project is an educational implementation of the classic Theseus and Minotaur game. Feel free to use, modify, and distribute for educational purposes.

🏛️ About the Game
The Theseus and Minotaur game is based on the ancient Greek myth where the hero Theseus must navigate the labyrinth of Crete to defeat the Minotaur. This implementation transforms the myth into an engaging strategic game with modern AI opponents and sophisticated gameplay mechanics.

Enjoy your journey through the labyrinth! 🎮✨

