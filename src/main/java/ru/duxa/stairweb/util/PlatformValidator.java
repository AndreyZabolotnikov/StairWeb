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

    }

    private boolean isPassingToWall() {
        boolean flag = false;
        if (isAngle() && isRampClearance() && isLengthLowerPassing()
                && platformDto.getWidthOnWallPassing() + platformDto.getClearanceOnWall() <= stairDto.getWidthStair()
        ) {
            flag = true;
        }
        return flag;
    }

    private boolean isPassingOnSupports() {
        boolean flag = false;
        if (isAngle() && isRampClearance() && isLengthLowerPassing()
                && platformDto.getWidthOnSupportsPassing() + platformDto.getClearanceOnStepSupports()
                + platformDto.getClearanceOnWall() <= stairDto.getWidthStair()
        ) {
            flag = true;
        }
        return flag;
    }

    private boolean isSideToWall() {
        boolean flag = false;
        if (isAngle() && isRampClearance() && isLengthLowerSide()
                && platformDto.getWidthOnWallSide() + platformDto.getClearanceOnWall() <= stairDto.getWidthStair()
        ) {
            flag = true;
        }
        return flag;
    }

    private boolean isSideOnSupports() {
        boolean flag = false;
        if (isAngle() && isRampClearance() && isLengthLowerSide()
                && platformDto.getWidthOnSupportsSide() + platformDto.getClearanceOnStepSupports()
                + platformDto.getClearanceOnWall() <= stairDto.getWidthStair()
        ) {
            flag = true;
        }
        return flag;
    }

    private boolean isThreeSideToWall() {
        return true;
    }

    private boolean isThreeSideOnSupports() {
        return true;
    }

    private boolean isAngle() {
        return platformDto.getAngleMax() >= platformDto.getCurrentAngle()
                && platformDto.getCurrentAngle() > 0;
    }

    private boolean isRampClearance() {
        return  platformDto.getLengthRamp() - platformDto.getLengthClearanceRamp() <= stairDto.getUpperFloor();
    }

    private boolean isLengthLowerPassing() {
        return platformDto.getLengthLowerPlatformMin() <= stairDto.getDownFloor();
    }

    private boolean isLengthLowerSide() {
        return platformDto.getLengthLowerPlatformSideMin() <= stairDto.getDownFloor() ;
    }
}
