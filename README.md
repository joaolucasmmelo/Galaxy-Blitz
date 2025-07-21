---
title: Galaxy Blitz
description: A space shooter in pure Java with clean design, intense gameplay, and modular structure.
authors:
  - name: João Lucas Melo
    url: https://github.com/joaolucasmmelo
---

# 🚀 Galaxy Blitz

**Galaxy Blitz** is a **space shooter** game developed entirely in **pure Java**, focusing on performance, smooth gameplay, and a modular code structure. Ideal for those who want to understand how to build a 2D game from scratch using only the Java language.

---

## 🎮 Gameplay

- Move the ship using: `W`, `A`, `S`, `D`
- Boost with: `SHIFT`
- Shoot with: `P` and `SPACE`
- Face waves of enemies, dodge bullets, collect power-ups, and score points

---

## 🧰 Technologies Used

- **Java AWT/Swing** — for graphical rendering
- **Threads** — real-time game loop control
- **KeyListener** — keyboard input detection
- **Canvas & Graphics2D** — 2D rendering
- **Collision management** — using `Rectangle.intersects(...)`
- **Visual and sound resources** — loaded with `getResourceAsStream()`

---

## 📁 Project Structure

```
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── meujogo.Modelo/
│ │ │ ├── Container.java
│ │ │ ├── Enemy1.java
│ │ │ ├── Heart.java
│ │ │ ├── Phase.java
│ │ │ ├── Player.java
│ │ │ ├── Shot.java
│ │ │ ├── SoundPlayer.java
│ │ │ └── SpecialShot.java
│ │ └── resources/
│ │   └── Media/
├── GalaxyBlitz.jar
├── README.md
├── pom.xml
└── start.bat
```

---

## ⚙️ How to Run
### Run via terminal
This method runs the complete game with sound.

---

### Run `start.bat`
This is the simplest way to launch the game, but it was not fully developed,
causing the game to run without sound.

---

### Manual Compilation

```bash
javac -d bin src/meujogo/**/*.java
java -cp bin meujogo.principalmain.Main
```

---

## 📦 Creating the `.jar` Executable

### 1. Create the Manifest

File `manifest.txt`:

```
Main-Class: meujogo.principalmain.Main
Class-Path: .
```

(*a blank line at the end is required*)

### 2. Generate the JAR

```bash
jar cfm GalaxyBlitz.jar manifest.txt -C bin .
```

### 3. Run the Game

```bash
java -jar GalaxyBlitz.jar
```

---

## 🌠 Planned Features

- Background music
- Sound effects
- Multiple levels
- Enemy attacks
- Score system

---

## 👤 Author

**João Lucas Melo**  
GitHub: [@joaolucasmmelo](https://github.com/joaolucasmmelo)

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for more details.

---

## ⭐ Contribute

If you enjoyed the project:

- Leave a ⭐ on the repository!
- Report bugs and submit improvements via Pull Request
- Share with fellow developers 🚀

---

**Good battles in space!** 🛸👾
