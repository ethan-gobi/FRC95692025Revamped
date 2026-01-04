package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.CoralEndeffactorSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem;

/**
 * Intakes coral while providing LED feedback for operator awareness.
 */
public class CoralIntakeCommand extends Command {
  private final CoralEndeffactorSubsystem coralEndeffactorSubsystem;
  private final LEDSubsystem ledSubsystem;

  public CoralIntakeCommand(CoralEndeffactorSubsystem coralEndeffactorSubsystem, LEDSubsystem ledSubsystem) {
    this.coralEndeffactorSubsystem = coralEndeffactorSubsystem;
    this.ledSubsystem = ledSubsystem;
    addRequirements(coralEndeffactorSubsystem, ledSubsystem);
  }

  @Override
  public void initialize() {
    ledSubsystem.setSolidColor(Color.kYellow);
  }

  @Override
  public void execute() {
    if (!coralEndeffactorSubsystem.beamBroken1()) {
      coralEndeffactorSubsystem.setVolt(4);
    } else {
      coralEndeffactorSubsystem.setVolt(12);
    }
  }

  @Override
  public void end(boolean interrupted) {
    coralEndeffactorSubsystem.stop();
    ledSubsystem.setSolidColor(Color.kGreen);
  }

  @Override
  public boolean isFinished() {
    return coralEndeffactorSubsystem.isObjectIn();
  }
}
