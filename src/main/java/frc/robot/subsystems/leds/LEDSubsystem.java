package frc.robot.subsystems.leds;

import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Second;

/**
 * Manages addressable LED animations that reflect robot status.
 */
public class LEDSubsystem extends SubsystemBase {
  private final AddressableLED led = new AddressableLED(9);
  private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(210);
  private LEDPattern pattern;

  public LEDSubsystem() {
    led.setLength(ledBuffer.getLength());
    led.setData(ledBuffer);
    led.start();
    runDefaultColor();
  }

  @Override
  public void periodic() {
    pattern.applyTo(ledBuffer);
    led.setData(ledBuffer);
  }

  public void runDefaultColor() {
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent()) {
      if (alliance.get() == DriverStation.Alliance.Red) {
        setContinuousGradientScrolling(Color.kDarkRed, Color.kOrangeRed, 0.6);
      } else {
        setContinuousGradientScrolling(Color.kCyan, Color.kDarkBlue, 0.6);
      }
    } else {
      setRainbowScrolling();
    }
  }

  public void setRainbow() {
    pattern = LEDPattern.rainbow(255, 120);
  }

  public void setRainbowScrolling() {
    pattern = LEDPattern.rainbow(255, 120).scrollAtAbsoluteSpeed(MetersPerSecond.of(0.8), Meters.of(1 / 120.0));
  }

  public void setSolidColor(Color color) {
    pattern = LEDPattern.solid(color);
  }

  public void setBlink(Color color, double timeSeconds) {
    pattern = LEDPattern.solid(color).blink(Second.of(timeSeconds));
  }

  public void setBreathe(Color color, double timeSeconds) {
    pattern = LEDPattern.solid(color).breathe(Second.of(timeSeconds));
  }

  public void progressMask(Color color) {
    pattern = LEDPattern.solid(color).breathe(Second.of(1));
  }

  public void setContinuousGradientScrolling(Color color1, Color color2, double speed) {
    pattern =
        LEDPattern.gradient(LEDPattern.GradientType.kContinuous, color1, color2)
            .scrollAtAbsoluteSpeed(MetersPerSecond.of(speed), Meters.of(1 / 120.0));
  }

  public void setOff() {
    pattern = LEDPattern.solid(Color.kBlack);
  }
}
