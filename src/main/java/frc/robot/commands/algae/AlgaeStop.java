package frc.robot.commands.algae;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;

/**
 * Stops algae intake motor output.
 */
public class AlgaeStop extends Command {

  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem;

  public AlgaeStop(AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem) {
    this.algaeEndeffactorSubsystem = algaeEndeffactorSubsystem;
    addRequirements(algaeEndeffactorSubsystem);
  }

  @Override
  public void execute() {
    algaeEndeffactorSubsystem.setSpeed(0);
  }

  @Override
  public void end(boolean interrupted) {
    algaeEndeffactorSubsystem.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
