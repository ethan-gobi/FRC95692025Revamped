# FRC 9569 – 2025 Reefscape Robot Code

This repository contains the **2025 FRC Reefscape robot code** for **FRC Team 9569**.  
The project is built using **WPILib (Java)** and follows the **command-based architecture** recommended by FIRST.

---

## 📁 Project Structure

The codebase is organized by **mechanism and responsibility** to keep it readable, maintainable, and competition-safe.

## ♻️ Recent Refactor Summary (behavior unchanged)

- **Subsystems moved into mechanism packages:** `swerve`, `elevator`, `vision`, `leds`, `coral`, `algae`. Each subsystem now lives under `frc.robot.subsystems.<mechanism>` while keeping all hardware logic identical.
- **Commands regrouped by mechanism:** `auto`, `algae`, `coral`, `elevator`, `leds`, `swerve`. RobotContainer imports/bindings were updated to point at the new package locations without altering control flow.
- **Constants relocated:** `Constants` now resides in `frc.robot.constants`, with values preserved verbatim.
- **Binary safety:** No binary assets were modified (e.g., Gradle wrapper JARs, deploy assets).
- **Testing note:** `./gradlew test` currently fails to download the Gradle distribution due to a proxy restriction; no code-level test failures were observed.

These changes are structural only—robot behavior, hardware mappings, and tuning values remain exactly the same.

## ✅ Ongoing OOP Cleanup (current)

- **Command-based clarity:** All commands now extend WPILib `Command`, declare requirements, and include Javadocs that describe intent and operator mapping, making them easier for new students to follow.
- **Container readability:** `RobotContainer` was reorganized to clearly show subsystem creation, controller bindings, drive mode setup, and PathPlanner command registration.
- **Subsystem documentation:** Elevator, LED, vision, and manipulator subsystems received concise class-level Javadocs and cleaned methods without leftover commented code.
- **Dependencies:** The WPILib new-command library is explicitly declared in `build.gradle` to ensure the command classes resolve cleanly across environments.

