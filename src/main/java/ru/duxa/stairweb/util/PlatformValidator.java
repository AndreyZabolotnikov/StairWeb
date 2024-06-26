package ru.duxa.stairweb.util;

import org.springframework.stereotype.Component;
import ru.duxa.stairweb.dto.PlatformDto;
import ru.duxa.stairweb.dto.StairDto;

@Component
public class PlatformValidator {
    private static StairDto stairDto;
    private static PlatformDto platformDto;

    public PlatformValidator() {
    }

    public void setParam(StairDto stairDto, PlatformDto platformDto) {
        PlatformValidator.platformDto = platformDto;
        PlatformValidator.stairDto = stairDto;

        platformDto.setPassingToWall(isPassingToWall());
        platformDto.setPassingOnSupports(isPassingOnSupports());
        platformDto.setSideToWall(isSideToWall());
        platformDto.setSideOnSupports(isSideOnSupports());
        platformDto.setThreeSideToWall(isThreeSideToWall());
        platformDto.setThreeSideOnSupports(isThreeSideOnSupports());
        platformDto.setMaxAngle(isAngle());
        platformDto.setLengthLowerPassing(isLengthLowerPassing());
        platformDto.setLengthLowerSide(isLengthLowerSide());
        platformDto.setRampClearance(isRampClearance());
        platformDto.setWidthPassingOnWall(isWidthPassingOnWall());
        platformDto.setWidthPassingOnSupports(isWidthPassingOnSupports());
        platformDto.setWidthSideToWall(isWidthSideToWall());
        platformDto.setWidthSideOnSupports(isWidthSideOnSupports());
        platformDto.setCheckFirePassageWay(isFirePassageWay());
        platformDto.setCheckFirePassageSupports(isFirePassageSupports());
        platformDto.setCheckCatcher(checkCatcher());
        platformDto.setCheckLengthWay(checkLengthWay());

    }

    private boolean isPassingToWall() {
        return isAngle() && isRampClearance() && isLengthLowerPassing() && isWidthPassingOnWall() && !checkLengthWay();
    }

    private boolean isPassingOnSupports() {
        return isAngle() && isRampClearance() && isLengthLowerPassing() && isWidthPassingOnSupports() && !checkLengthWay();
    }

    private boolean isSideToWall() {
        return isAngle() && isRampClearance() && isLengthLowerSide() && isWidthSideToWall() && !checkLengthWay();
    }

    private boolean isSideOnSupports() {
        return isAngle() && isRampClearance() && isLengthLowerSide() && isWidthSideOnSupports() && !checkLengthWay();
    }

    private boolean isThreeSideToWall() {
        return isAngle() && isRampClearance() && isLengthLowerPassing() && isWidthSideToWall() && !checkLengthWay();
    }

    private boolean isThreeSideOnSupports() {
        return isAngle() && isRampClearance() && isLengthLowerPassing() && isWidthSideOnSupports() && !checkLengthWay();
    }

    private boolean isAngle() {
        return platformDto.getAngleMax() >= platformDto.getCurrentAngle() && platformDto.getCurrentAngle() > 0;
    }

    private boolean isRampClearance() {
        return platformDto.getLengthRamp() - platformDto.getLengthClearanceRamp() <= stairDto.getUpperFloor();
    }

    private boolean isLengthLowerPassing() {
        return platformDto.getLengthLowerPlatformMin() <= stairDto.getDownFloor();
    }

    private boolean isLengthLowerSide() {
        return platformDto.getLengthLowerPlatformSideMin() <= stairDto.getDownFloor();
    }

    private boolean isWidthPassingOnWall() {
        return platformDto.getWidthOnWallPassing() + platformDto.getClearanceOnWall() <= stairDto.getWidthStair();
    }

    private boolean isWidthPassingOnSupports() {
        return platformDto.getWidthOnSupportsPassing() + platformDto.getClearanceOnWall() + platformDto.getClearanceOnStepSupports() <= stairDto.getWidthStair();
    }

    private boolean isWidthSideToWall() {
        return platformDto.getWidthOnWallSide() + platformDto.getClearanceOnWall() <= stairDto.getWidthStair();
    }

    private boolean isWidthSideOnSupports() {
        return platformDto.getWidthOnSupportsSide() + platformDto.getClearanceOnWall() + platformDto.getClearanceOnStepSupports() <= stairDto.getWidthStair();
    }

    private boolean isFirePassageWay() {
        return (platformDto.getFirePassageWay() + platformDto.getFoldedWall()) <= stairDto.getWidthStair();
    }

    private boolean isFirePassageSupports() {
        return (platformDto.getFirePassageWay() + platformDto.getFoldedWall() + platformDto.getClearanceOnStepSupports()) <= stairDto.getWidthStair();
    }

    private boolean checkCatcher() {
        return platformDto.getCurrentAngle() >= platformDto.getCatcher();
    }

    private boolean checkLengthWay() {
        return platformDto.getLengthWay() > platformDto.getLengthWayMax();
    }

}
