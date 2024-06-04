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
        if (platformDto.getAngleMax() >= platformDto.getCurrentAngle()
                && platformDto.getLengthLowerPlatformMin() <= stairDto.getDownFloor()
                && platformDto.getWidthOnWallPassing() + platformDto.getClearanceOnWall() <= stairDto.getWidthStair()
                && platformDto.getLengthRamp() - platformDto.getLengthClearanceRamp() <= stairDto.getUpperFloor()
                && platformDto.getCurrentAngle() > 0) {
            flag = true;
        }
        return flag;
    }

    private boolean isPassingOnSupports() {
        return true;
    }

    private boolean isSideToWall() {
        return true;
    }

    private boolean isSideOnSupports() {
        return true;
    }

    private boolean isThreeSideToWall() {
        return true;
    }

    private boolean isThreeSideOnSupports() {
        return true;
    }
}
