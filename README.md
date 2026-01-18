# Hex Game - Java CLI Implementation

An object-oriented implementation of the classic strategy board game **Hex**, played via a Command Line Interface (CLI). This project was developed to Java concepts, design patterns, and algorithm implementation, including custom Artificial Intelligence agents.

##  Features

* **Robust Game Logic:** Fully functional Hex board mechanics with dynamic grid sizes.
* **Versatile Game Modes:**
    * **Human vs. Human:** Local multiplayer on the same terminal.
    * **Human vs. AI:** Challenge different levels of artificial intelligence.
* **Artificial Intelligence Agents:**
    * **BogoAI:** A beginner-level AI that utilizes random moves and basic blocking strategies.
    * **HeroAI:** An advanced AI agent implementing **Dijkstra-based pathfinding** to calculate the shortest winning path and block the opponent effectively.
* **Session Management:** A central `Hub` architecture allows creating, switching, and managing multiple concurrent game sessions.
* **Command-Based Interface:** User interactions are handled via a strict **Command Pattern** implementation, ensuring input validation and decoupling UI from logic.

##  Technologies & Architecture

This project showcases strong proficiency in **Object-Oriented Programming (OOP)** and software architecture.

* **Language:** Java (JDK 17+)
* **Design Patterns:**
    * **Command Pattern:** Decouples user requests (e.g., `Place`, `Swap`, `Undo`) from the execution logic.
    * **Strategy Pattern:** Used within the `Player` hierarchy to interchange behavior between `HumanPlayer` and `ArtificialPlayer`.
* **Algorithms:** Graph traversal (Pathfinding) for win-condition checking and AI logic.

## 📂 Project Structure

The project follows a modular package structure:

```text
src/hex
├── model/                  # Core domain logic (Board, Hexagon, Game Rules)
│   ├── entity/             # Player abstractions and concrete implementations
│   │   └── artificial/     # AI logic (HeroAI, BogoAI)
│   └── ...
└── ui/                     # Console interface and Input handling
    ├── commands/           # Individual command classes  
    └── ...

##  How to Run

### Prerequisites
* Java Development Kit (JDK) installed (Version 11 or higher recommended).
* Git.

### Installation & Execution

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/weddlelook/HexGame-Java.git
    cd HexGame-Java
    ```

2.  **Compile the source code:**
    ```bash
    javac -d bin -sourcepath src src/hex/ui/Application.java
    ```

3.  **Run the application:**
    The application accepts arguments for: `[Board Size] [Player1 Name] [Player2 Name/AI] [Optional: auto-print]`
    
    *Example: Start a game on an 11x11 board against the advanced AI:*
    ```bash
    java -cp bin hex.ui.Application 11 MyName HeroAI auto-print
    ```

##  Command Guide

Once the game starts, you can use the following commands:

* `place <x> <y>` : Place your token at the specified coordinates.
* `swap` : Swap tokens/sides (Available only on the second turn of the game).
* `print` : Display the current state of the board.
* `history <n>` : Show the last *n* moves made in the game.
* `new-game <name>` : Start a completely new game session with a given name.
* `switch-game <name>` : Switch context to another active game session.
* `list-games` : List all currently active game sessions.
* `quit` : Exit the application.

---
*This project is the final assignment for the 'Programming' module at the KIT Faculty of Informatics.