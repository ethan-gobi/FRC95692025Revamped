package frc.robot.commands.leds;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.leds.LEDSubsystem;

/**
 * Command that rapidly flashes the LEDs white while active, then returns to the default rainbow.
 */
public class FlashBang extends Command {
  private final LEDSubsystem ledSubsystem;

  /**
   * Creates a flashing LED command.
   *
   * @param ledSubsystem LED controller dependency
   */
  public FlashBang(LEDSubsystem ledSubsystem) {
    this.ledSubsystem = ledSubsystem;
    addRequirements(ledSubsystem);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  @Override
  public void initialize() {
    ledSubsystem.setBlink(Color.kWhite, 0.05);
  }

  @Override
  public void end(boolean interrupted) {
    ledSubsystem.setRainbowScrolling();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
