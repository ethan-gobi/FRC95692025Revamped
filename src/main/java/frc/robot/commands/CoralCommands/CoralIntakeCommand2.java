// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.CoralCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralEndeffactorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CoralIntakeCommand2 extends Command {
  private final CoralEndeffactorSubsystem coralEndeffactorSubsystem;

  /** Creates a new CoralIntakeCommand2. */
  public CoralIntakeCommand2() {
    coralEndeffactorSubsystem = c;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(c);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    coralEndeffactorSubsystem.setVolt(7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coralEndeffactorSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !(coralEndeffactorSubsystem.beamBroken1() || coralEndeffactorSubsystem.beamBroken2());
  }
}
