package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Drives the algae pivot slightly above the upper setpoint.
 */
public class AlgaePivotUpper extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaePivotUpper(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.algaeSetPoint(-0.36);
  }

  @Override
  public void end(boolean interrupted) {
    algaeEndeffactorSubsystem.algaePivotStop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
