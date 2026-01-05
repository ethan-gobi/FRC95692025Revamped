package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Provides AprilTag-based pose estimation from dual PhotonVision cameras.
 *
 * <p>Subsystem visuals: <a href="https://docs.photonvision.org/en/latest/_images/photonvision-camera.png">PhotonVision
 * camera and UI example</a>.
 */
public class Vision extends SubsystemBase {
  private static final AprilTagFieldLayout FIELD_LAYOUT =
      AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);

  private final PhotonCamera rightCamera = new PhotonCamera("End Effector Camera");
  private final PhotonCamera leftCamera = new PhotonCamera("left camera");

  private final Transform3d robotToRightCamera =
      new Transform3d(new Translation3d(-0.13172, 0.32659, 0.338), new Rotation3d(0, 0, Math.PI));
  private final Transform3d robotToLeftCamera =
      new Transform3d(new Translation3d(-0.13172, -0.32659, 0.338), new Rotation3d(0, 0, Math.PI));

  private final PhotonPoseEstimator rightPoseEstimator =
      new PhotonPoseEstimator(FIELD_LAYOUT, PoseStrategy.AVERAGE_BEST_TARGETS, robotToRightCamera);
  private final PhotonPoseEstimator leftPoseEstimator =
      new PhotonPoseEstimator(FIELD_LAYOUT, PoseStrategy.AVERAGE_BEST_TARGETS, robotToLeftCamera);

  private final Field2d field = new Field2d();

  private Optional<EstimatedRobotPose> latestEstimatedPoseRight = Optional.empty();
  private Optional<EstimatedRobotPose> latestEstimatedPoseLeft = Optional.empty();

  private PhotonPipelineResult latestResultRight = new PhotonPipelineResult();
  private PhotonPipelineResult latestResultLeft = new PhotonPipelineResult();
  private Pose3d referencePose = new Pose3d();
  private int targetId;

  public Vision() {
    SmartDashboard.putData("photon subsystem", field);
  }

  public Optional<EstimatedRobotPose> getRightPoseEstimate() {
    return latestEstimatedPoseRight;
  }

  public Optional<EstimatedRobotPose> getLeftPoseEstimate() {
    return latestEstimatedPoseLeft;
  }

  public Optional<Pose2d> getPose2dR() {
    return latestEstimatedPoseRight.map(pose -> pose.estimatedPose.toPose2d());
  }

  public Optional<Pose2d> getPose2dL() {
    return latestEstimatedPoseLeft.map(pose -> pose.estimatedPose.toPose2d());
  }

  public Optional<Double> getTimestampR() {
    return latestEstimatedPoseRight.map(pose -> pose.timestampSeconds);
  }

  public Optional<Double> getTimestampL() {
    return latestEstimatedPoseLeft.map(pose -> pose.timestampSeconds);
  }

  public List<PhotonTrackedTarget> getCurrentTargetsRight() {
    return latestResultRight.getTargets();
  }

  public List<PhotonTrackedTarget> getCurrentTargetsLeft() {
    return latestResultLeft.getTargets();
  }

  public void setReferencePose(Pose3d refPose3d) {
    referencePose = refPose3d;
  }

  @Override
  public void periodic() {
    latestResultRight = rightCamera.getLatestResult();
    latestResultLeft = leftCamera.getLatestResult();

    if (latestResultRight.hasTargets()) {
      latestEstimatedPoseRight = rightPoseEstimator.update(latestResultRight);
      targetId = latestResultRight.getBestTarget().fiducialId;
    } else {
      latestEstimatedPoseRight = Optional.empty();
    }

    if (latestResultLeft.hasTargets()) {
      latestEstimatedPoseLeft = leftPoseEstimator.update(latestResultLeft);
    } else {
      latestEstimatedPoseLeft = Optional.empty();
    }

    rightPoseEstimator.setReferencePose(referencePose);
    leftPoseEstimator.setReferencePose(referencePose);

    latestEstimatedPoseRight.ifPresent(pose -> field.setRobotPose(pose.estimatedPose.toPose2d()));

    SmartDashboard.putBoolean("has target R", latestResultRight.hasTargets());
    SmartDashboard.putBoolean("has target L", latestResultLeft.hasTargets());
    SmartDashboard.putNumber("id april", targetId);
  }
}
