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

