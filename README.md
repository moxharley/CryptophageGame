## About
This project was created for the COMP 2522 "Object-Oriented Programming" course at BCIT. 
It was created by Harlan Bullock and Finn Wylie

## Description
A fast-paced bullet-hell roguelike where you play as an individual known as a ‘Cryptophage’.
The goal is to make a game that emphasizes skill,

## Build instructions

These instructions assume the user is using the standard intelliJ setup for the COMP 2522 course.

1. After forking/cloning the repository to intelliJ open the Gradle panel on the right side of the screen.
2. Select \[term_project] > \[Tasks] > \[build] > \[build] to build the project.
3. After the project has built, in the same Gradle panel select \[term_project] > \[Tasks] > \[application] > \[run]
4. The game will open.

## Main Menu
- Click \[Play] to start a new game.
- Click \[Scores] to view the top 5 scores.
  - On death, your run's score will be saved to this leaderboard with a name of your choice.
- Click \[Dungeon] to view the experimental, unimplemented, dungeon generator.
- Click \[Quit] to exit the game.

## Gameplay
Move around the screen shooting enemies while avoiding being hit yourself. The game increases in difficulty as you clear 
more waves. On death, you will be shown your run's score \(number of kills you got) and a box to input your name.

### Controls:
| Action     | Button       |
|------------|--------------|
| Move Up    | W            |
| Move Down  | S            |
| Move Left  | A            |
| Move Right | D            |
| Aim        | Mouse Cursor |
| Shoot      | Left Click   |

## Future Improvements


## Bibliography
Tutorial used for basic project start guide: https://youtube.com/playlist?list=PLVNiGun9focYT2OVFUzL30wUtOToo6frD&si=FQgnzmftUZgB8cQ0

Referenced when making the dungeon generator: https://github.com/halftheopposite/bsp-dungeon-generator
