package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Positions the algae pivot at the middle setpoint.
 */
public class AlgaePivotMiddle extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaePivotMiddle(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.algaeSetPoint(-0.11);
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
