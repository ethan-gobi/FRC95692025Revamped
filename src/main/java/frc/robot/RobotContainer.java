package frc.robot;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.algae.AlgaeEject;
import frc.robot.commands.algae.AlgaeIntake;
import frc.robot.commands.algae.AlgaePivotDown;
import frc.robot.commands.algae.AlgaePivotMiddle;
import frc.robot.commands.algae.AlgaePivotUp;
import frc.robot.commands.algae.AlgaePivotUpper;
import frc.robot.commands.auto.AutoAlign;
import frc.robot.commands.coral.CoralEjectCommand;
import frc.robot.commands.coral.CoralForceDiffEjectCommand;
import frc.robot.commands.coral.CoralIntakeCommand;
import frc.robot.commands.elevator.ElevatorCycle;
import frc.robot.commands.elevator.ElevatorL1Command;
import frc.robot.commands.elevator.ElevatorL2Command;
import frc.robot.commands.elevator.ElevatorL3Command;
import frc.robot.commands.elevator.ElevatorZeroCommand;
import frc.robot.commands.leds.FlashBang;
import frc.robot.constants.Constants.AutoAlignCoordinates;
import frc.robot.constants.Constants.OperatorConstants;
import frc.robot.subsystems.algae.AlgaeEndeffactorSubsystem;
import frc.robot.subsystems.coral.CoralEndeffactorSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import swervelib.SwerveInputStream;

/**
 * Central wiring hub for subsystems, commands, and operator controls.
 */
public class RobotContainer {
  private final SwerveSubsystem driveBase = new SwerveSubsystem();
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
  private final CoralEndeffactorSubsystem coralEndeffactorSubsystem = new CoralEndeffactorSubsystem();
  private final AlgaeEndeffactorSubsystem algaeEndeffactorSubsystem = new AlgaeEndeffactorSubsystem();
  private final LEDSubsystem ledSubsystem = new LEDSubsystem();

  private final CommandPS5Controller driverController =
      new CommandPS5Controller(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController operatorController = new CommandXboxController(1);

  private final SendableChooser<Command> autoChooser;
  private final SendableChooser<Integer> poseChooser = new SendableChooser<>();

  private final Command driveFieldOrientedAngularVelocity;
  private final Command driveFieldOrientedDirectAngle;
  private final Command driveFieldOrientedDirectAngleKeyboard;

  /**
   * Creates a new {@link RobotContainer} and configures subsystem bindings.
   */
  public RobotContainer() {
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    populatePoseChooser();
    SmartDashboard.putData("pose chooser", poseChooser);

    driveFieldOrientedAngularVelocity = createAngularVelocityDrive();
    driveFieldOrientedDirectAngle = createDirectAngleDrive();
    driveFieldOrientedDirectAngleKeyboard = createKeyboardDrive();

    configureDefaultDriveCommand();
    configureBindings();
  }

  /**
   * Provides the autonomous routine selected on the dashboard.
   *
   * @return autonomous command choice
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  /**
   * Returns the pose that matches the dashboard selection.
   *
   * @return selected pose or the default option when no choice exists
   */
  public Pose2d getSelectedPose() {
    Integer selection = poseChooser.getSelected();
    Map<Integer, Pose2d> rightSidePoses = createRightSidePoses();
    if (selection == null || !rightSidePoses.containsKey(selection)) {
      return rightSidePoses.get(6);
    }
    return rightSidePoses.get(selection);
  }

  private void configureBindings() {
    configureDriveBindings();
    configureElevatorBindings();
    configureCoralBindings();
    configureAlgaeBindings();
    configureUtilityBindings();
  }

  private void configureDriveBindings() {
    Command zeroGyro = Commands.runOnce(driveBase::zeroGyro, driveBase);
    driverController.povUp().onTrue(zeroGyro);
    driverController.povDown().whileTrue(new FlashBang(ledSubsystem));
  }

  private void configureElevatorBindings() {
    driverController.R1().onTrue(new ElevatorCycle(elevatorSubsystem, 1));
    driverController.L1().onTrue(new ElevatorCycle(elevatorSubsystem, -1));
  }

  private void configureCoralBindings() {
    operatorController.leftBumper().onTrue(new CoralIntakeCommand(coralEndeffactorSubsystem, ledSubsystem));
    operatorController.rightBumper().onTrue(
        new CoralEjectCommand(coralEndeffactorSubsystem, elevatorSubsystem, ledSubsystem));
    NamedCommands.registerCommand(
        "EC", new CoralForceDiffEjectCommand(coralEndeffactorSubsystem, elevatorSubsystem, ledSubsystem));
    NamedCommands.registerCommand("IC", new CoralIntakeCommand(coralEndeffactorSubsystem, ledSubsystem));
  }

  private void configureAlgaeBindings() {
    operatorController.y().onTrue(new AlgaePivotUp(algaeEndeffactorSubsystem));
    operatorController.x().onTrue(new AlgaePivotMiddle(algaeEndeffactorSubsystem));
    operatorController.a().onTrue(new AlgaePivotDown(algaeEndeffactorSubsystem));
    operatorController.b().onTrue(new AlgaePivotUpper(algaeEndeffactorSubsystem));

    operatorController.rightTrigger().whileTrue(new AlgaeIntake(algaeEndeffactorSubsystem));
    operatorController.leftTrigger().whileTrue(new AlgaeEject(algaeEndeffactorSubsystem));

    operatorController.povLeft().onTrue(Commands.runOnce(coralEndeffactorSubsystem::diffRight));
    operatorController.povRight().onTrue(Commands.runOnce(coralEndeffactorSubsystem::diffLeft));

    NamedCommands.registerCommand("L0", new ElevatorZeroCommand(elevatorSubsystem));
    NamedCommands.registerCommand("L1", new ElevatorL1Command(elevatorSubsystem));
    NamedCommands.registerCommand("L2", new ElevatorL2Command(elevatorSubsystem));
    NamedCommands.registerCommand("L3", new ElevatorL3Command(elevatorSubsystem));
  }

  private void configureUtilityBindings() {
    driverController.povLeft().whileTrue(createAutoAlignCommand(-60));
    driverController.povRight().whileTrue(createAutoAlignCommand(60));
  }

  private Command createAutoAlignCommand(double headingDegrees) {
    Pose2d targetPose = new Pose2d(0, 0, Rotation2d.fromDegrees(headingDegrees));
    Command alignCommand = driveBase.driveToPose(targetPose);
    return new AutoAlign(driveBase, alignCommand);
  }

  private void populatePoseChooser() {
    List<Integer> validPositions = Arrays.asList(6, 7, 8, 9, 10, 11, 17, 18, 19, 20, 21, 22);
    boolean defaultSet = false;
    for (Integer option : validPositions) {
      if (!defaultSet) {
        poseChooser.setDefaultOption(option.toString(), option);
        defaultSet = true;
      } else {
        poseChooser.addOption(option.toString(), option);
      }
    }
  }

  private Map<Integer, Pose2d> createRightSidePoses() {
    return Map.ofEntries(
        Map.entry(6, AutoAlignCoordinates.R.pose6),
        Map.entry(7, AutoAlignCoordinates.R.pose7),
        Map.entry(8, AutoAlignCoordinates.R.pose8),
        Map.entry(9, AutoAlignCoordinates.R.pose9),
        Map.entry(10, AutoAlignCoordinates.R.pose10),
        Map.entry(11, AutoAlignCoordinates.R.pose11),
        Map.entry(17, AutoAlignCoordinates.R.pose17),
        Map.entry(18, AutoAlignCoordinates.R.pose18),
        Map.entry(19, AutoAlignCoordinates.R.pose19),
        Map.entry(20, AutoAlignCoordinates.R.pose20),
        Map.entry(21, AutoAlignCoordinates.R.pose21),
        Map.entry(22, AutoAlignCoordinates.R.pose22));
  }

  private void configureDefaultDriveCommand() {
    if (RobotBase.isSimulation()) {
      driveBase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else {
      driveBase.setDefaultCommand(driveFieldOrientedAngularVelocity);
    }
  }

  private Command createAngularVelocityDrive() {
    SwerveInputStream driveAngularVelocity =
        SwerveInputStream.of(
                driveBase.getSwerveDrive(),
                () -> driverController.getLeftY() * -1 * 0.05,
                () -> driverController.getLeftX() * -1 * 0.05)
            .withControllerRotationAxis(driverController::getRightX)
            .deadband(OperatorConstants.DEADBAND)
            .scaleTranslation(1)
            .allianceRelativeControl(true);
    return driveBase.driveFieldOriented(driveAngularVelocity);
  }

  private Command createDirectAngleDrive() {
    SwerveInputStream driveAngularVelocity =
        SwerveInputStream.of(
                driveBase.getSwerveDrive(),
                () -> driverController.getLeftY() * -1 * 0.05,
                () -> driverController.getLeftX() * -1 * 0.05)
            .withControllerRotationAxis(driverController::getRightX)
            .deadband(OperatorConstants.DEADBAND)
            .scaleTranslation(1)
            .allianceRelativeControl(true);

    SwerveInputStream driveDirectAngle =
        driveAngularVelocity
            .copy()
            .withControllerHeadingAxis(
                () -> driverController.getRightX() * 0.05, () -> driverController.getRightY() * 0.05)
            .headingWhile(true);

    return driveBase.driveFieldOriented(driveDirectAngle);
  }

  private Command createKeyboardDrive() {
    SwerveInputStream driveAngularVelocityKeyboard =
        SwerveInputStream.of(
                driveBase.getSwerveDrive(), () -> -driverController.getLeftY(), () -> -driverController.getLeftX())
            .withControllerRotationAxis(() -> -driverController.getRawAxis(2))
            .deadband(OperatorConstants.DEADBAND)
            .scaleTranslation(0.8)
            .allianceRelativeControl(true);

    SwerveInputStream driveDirectAngleKeyboard =
        driveAngularVelocityKeyboard
            .copy()
            .withControllerHeadingAxis(
                () -> Math.sin(driverController.getRawAxis(2) * Math.PI) * (Math.PI * 2),
                () -> Math.cos(driverController.getRawAxis(2) * Math.PI) * (Math.PI * 2))
            .headingWhile(true)
            .translationHeadingOffset(true)
            .translationHeadingOffset(Rotation2d.fromDegrees(0));

    return driveBase.driveFieldOriented(driveDirectAngleKeyboard);
  }
}
