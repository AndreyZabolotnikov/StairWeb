package ru.duxa.stairweb.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.PlatformDto;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Platform;
import ru.duxa.stairweb.repository.PlatformRepository;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    @Autowired
    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public Platform getPlatform(String name) {
        return platformRepository.findByName(name);
    }


    @Transactional
    public void savePlatform(PlatformDto platformDto) {
        Platform platform = getPlatform(platformDto.getName());
        System.out.println(platform.getName());

        platform.setAngleMax(platformDto.getAngleMax());
        platform.setLengthWayMax(platformDto.getLengthWayMax());
        platform.setClearanceOnWall(platformDto.getClearanceOnWall());
        platform.setClearanceOnStepSupports(platformDto.getClearanceOnStepSupports());
        platform.setClearanceOnStep(platformDto.getClearanceOnStep());
        platform.setOverlapRamp(platformDto.getOverlapRamp());
        platform.setLengthRamp(platformDto.getLengthRamp());
        platform.setLengthPassing(platformDto.getLengthPassing());
        platform.setLengthSide(platformDto.getLengthSide());
        platform.setWidthOnWallPassing(platformDto.getWidthOnWallPassing());
        platform.setWidthOnWallSide(platformDto.getWidthOnWallSide());
        platform.setWidthOnWallLowerSide(platformDto.getWidthOnWallLowerSide());
        platform.setWidthOnSupportsPassing(platformDto.getWidthOnSupportsPassing());
        platform.setWidthOnSupportsLowerSide(platformDto.getWidthOnSupportsLowerSide());
        platform.setWidthOnSupportsSide(platformDto.getWidthOnSupportsSide());
        platform.setFoldedSupports(platformDto.getFoldedSupports());
        platform.setFoldedWall(platformDto.getFoldedWall());
        platform.setCatcher(platformDto.getCatcher());
        platform.setFirePassageWay(platformDto.getFirePassageWay());
        platform.setLengthTrackAdd(platformDto.getLengthTrackAdd());
        platform.setMaxClearanceGOST(platformDto.getMaxClearanceGOST());
    }


    public PlatformDto getPlatformDto(String platformName, PlatformDto platformDto) {

        Platform platform = getPlatform(platformName);

        platformDto.setName(platform.getName());
        platformDto.setAngleMax(platform.getAngleMax());
        platformDto.setLengthWayMax(platform.getLengthWayMax());
        platformDto.setClearanceOnWall(platform.getClearanceOnWall());
        platformDto.setClearanceOnStepSupports(platform.getClearanceOnStepSupports());
        platformDto.setClearanceOnStep(platform.getClearanceOnStep());
        platformDto.setOverlapRamp(platform.getOverlapRamp());
        platformDto.setLengthRamp(platform.getLengthRamp());
        platformDto.setLengthPassing(platform.getLengthPassing());
        platformDto.setLengthSide(platform.getLengthSide());
        platformDto.setWidthOnWallPassing(platform.getWidthOnWallPassing());
        platformDto.setWidthOnWallSide(platform.getWidthOnWallSide());
        platformDto.setWidthOnWallLowerSide(platform.getWidthOnWallLowerSide());
        platformDto.setWidthOnSupportsPassing(platform.getWidthOnSupportsPassing());
        platformDto.setWidthOnSupportsLowerSide(platform.getWidthOnSupportsLowerSide());
        platformDto.setWidthOnSupportsSide(platform.getWidthOnSupportsSide());
        platformDto.setFoldedSupports(platform.getFoldedSupports());
        platformDto.setFoldedWall(platform.getFoldedWall());
        platformDto.setCatcher(platform.getCatcher());
        platformDto.setFirePassageWay(platform.getFirePassageWay());
        platformDto.setLengthTrackAdd(platform.getLengthTrackAdd());
        platformDto.setMaxClearanceGOST(platform.getMaxClearanceGOST());

        return platformDto;
    }

    public void optimizeAddSearchParametersPlatform(StairDto stairDto, PlatformDto platformDto) {

        platformDto = getPlatformDto(platformDto.getName(), platformDto);

        if (platformDto.getName().equals("et")) {
            platformDto.setCurrentAngle((int) stairDto.getAngle());
        } else {
            platformDto.setCurrentAngle(stairDto.getAngle());
        }

        boolean check;
        int count = 0;

        do {
            platformDto.setLengthWay(lengthWay(stairDto, platformDto));

            if (count == 0) {
                platformDto.setLengthWayOnLowerPlats(lengthWayOnLowerPlats(stairDto, platformDto));
            } else {
                stairDto.setStepNumber(platformDto.getCountClearanceMin());
                platformDto.setLengthWayOnLowerPlats(lengthWayOnLowerPlats(stairDto, platformDto));
            }

            platformDto.setLengthClearanceRamp(lengthClearanceRamp(platformDto, stairDto, platformDto.getLengthWayOnLowerPlats()));

            if (platformDto.getClearanceMax() < platformDto.getMaxClearanceGOST() && platformDto.getClearanceMin() > platformDto.getClearanceOnStep() - 1
                    && platformDto.getLengthClearanceRamp() < 10) {
                for (int i = 1; i <= platformDto.getMaxClearanceGOST() - platformDto.getClearanceOnStep(); i++) {
                    platformDto.setClearanceOnStep(platformDto.getClearanceOnStep() + i);
                    stairDto.setStepNumber(platformDto.getCountClearanceMin());
                    platformDto.setLengthWayOnLowerPlats(lengthWayOnLowerPlats(stairDto, platformDto));
                    platformDto.setLengthClearanceRamp(lengthClearanceRamp(platformDto, stairDto, platformDto.getLengthWayOnLowerPlats()));
                    if (platformDto.getLengthClearanceRamp() > 10) {
                        break;
                    }
                }
            }

            findNumberMinAndMaxClearanceStep(platformDto, stairDto, platformDto.getLengthWayOnLowerPlats());

            if (platformDto.getLengthClearanceRamp() >= (platformDto.getLengthRamp() - platformDto.getOverlapRamp()) && platformDto.getCurrentAngle() > 0) {
                platformDto.setCurrentAngle(platformDto.getCurrentAngle() - 1);
                check = true;
            } else if ((platformDto.getLengthClearanceRamp() >= (platformDto.getLengthRamp() - platformDto.getOverlapRamp()) && platformDto.getCurrentAngle() < 1) || (platformDto.getLengthClearanceRamp() < 0 && platformDto.getCurrentAngle() < 1)) {
                platformDto.setCurrentAngle(0);
                platformDto.setLengthWay(0);
                platformDto.setLengthWayOnLowerPlats(0);
                platformDto.setLengthClearanceRamp(0);
                break;
            } else if (platformDto.getClearanceMin() <= platformDto.getClearanceOnStep() - 1 && count == 0) {
                check = true;
            } else
                break;
            count++;

        }
        while (check);

        if (platformDto.getClearanceMax() > platformDto.getMaxClearanceGOST()) {
            platformDto.setCurrentAngle(0);
            platformDto.setLengthWay(0);
            platformDto.setLengthWayOnLowerPlats(0);
            platformDto.setLengthClearanceRamp(0);
        }

        platformDto.setLengthLowerPlatformMin(platformDto.getLengthWayOnLowerPlats() + platformDto.getLengthPassing() + platformDto.getClearanceOnWall());
        platformDto.setLengthLowerPlatformSideMin(platformDto.getLengthWayOnLowerPlats() + platformDto.getLengthSide() + platformDto.getClearanceOnWall());

        if (stairDto.getStepLengthsCoordinates().size() > 0) {
            platformDto.setCountStepClearanceMax(platformDto.getCountClearanceMax() + 1);
        } else
            platformDto.setCountStepClearanceMax(0);

        if(platformDto.getCurrentAngle() <  1) {
            platformDto.setLengthLowerPlatformMin(0);
            platformDto.setLengthLowerPlatformSideMin(0);
            platformDto.setLengthWay(0);
            platformDto.setLengthTrackAdd(0);
            platformDto.setCountClearanceMax(0);
            platformDto.setClearanceMax(0);
            platformDto.setWidthOnWallPassing(0);
            platformDto.setFoldedWall(0);
            platformDto.setClearanceOnWall(0);
            platformDto.setWidthOnWallSide(0);
            platformDto.setWidthOnWallLowerSide(0);
            platformDto.setWidthOnSupportsPassing(0);
            platformDto.setWidthOnSupportsSide(0);
            platformDto.setWidthOnSupportsLowerSide(0);
            platformDto.setCountStepClearanceMax(0);
            platformDto.setClearanceOnStepSupports(0);
        }


    }

    private int lengthWay(StairDto stairDto, PlatformDto platformDto) {
        int ye = stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size() - 1);
        int xe = (int) Math.round(ye / Math.tan(Math.toRadians(platformDto.getCurrentAngle())));
        return (int) Math.sqrt(Math.pow(xe, 2) + Math.pow(ye, 2));
    }

    public int lengthWayOnLowerPlats(StairDto stairDto, PlatformDto platformDto) {
        int y;
        int x;
        int lengthWayOnLowerPlats;

        y = stairDto.getStepHeightsCoordinates().get(stairDto.getStepNumber()) + platformDto.getClearanceOnStep();
        x = (int) Math.round(y / Math.tan(Math.toRadians(platformDto.getCurrentAngle())));

        if (stairDto.getStepNumber() > 0) {
            lengthWayOnLowerPlats = x - stairDto.getStepLengthsCoordinates().get(stairDto.getStepNumber() - 1);
        } else {
            lengthWayOnLowerPlats = x;
        }
        return lengthWayOnLowerPlats;
    }

    private void findNumberMinAndMaxClearanceStep(PlatformDto platformDto, StairDto stairDto, int lengthWayOnLowerPlats) {
        int clearanceMax = 0;
        int countClearanceMin = 0;
        int countClearanceMax = 0;
        int clearanceMin = platformDto.getMaxClearanceGOST();

        int clearance;
        int x;
        int y;
        int stairY;
        for (int i = 0; i < stairDto.getStepHeightsCoordinates().size() - 1; i++) {
            if (i == 0) {
                x = lengthWayOnLowerPlats;
            } else {
                x = lengthWayOnLowerPlats + stairDto.getStepLengthsCoordinates().get(i - 1);
            }
            y = (int) (Math.tan(Math.toRadians(platformDto.getCurrentAngle())) * x);
            stairY = stairDto.getStepHeightsCoordinates().get(i);
            clearance = y - stairY;
            if (clearance > clearanceMax) {
                clearanceMax = clearance;
                countClearanceMax = i;
            }
            if (clearance < clearanceMin) {
                clearanceMin = clearance;
                countClearanceMin = i;
            }
        }
        platformDto.setClearanceMax(clearanceMax);
        platformDto.setClearanceMin(clearanceMin);
        platformDto.setCountClearanceMax(countClearanceMax);
        platformDto.setCountClearanceMin(countClearanceMin);
    }

    private int lengthClearanceRamp(PlatformDto platformDto, StairDto stairDto, int lengthWayOnLowerPlats) {
        int lengthClearanceRamp;
        int xStair;
        if (stairDto.getStepLengthsCoordinates().size() >= 1)
            xStair = stairDto.getStepLengthsCoordinates().get(stairDto.getStepLengthsCoordinates().size() - 1);
        else xStair = 0;
        int xWay;
        if (stairDto.getStepHeightsCoordinates().size() > 1) {
            xWay = (int) ((stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size() - 1)) / Math.tan(Math.toRadians(platformDto.getCurrentAngle()))) - lengthWayOnLowerPlats;
        } else {
            xWay = 0;
        }
        if (stairDto.getStepHeightsCoordinates().size() > 1) {
            lengthClearanceRamp = xStair - xWay;
        } else {
            lengthClearanceRamp = lengthWayOnLowerPlats - stairDto.getFirstPoint();
        }
        return lengthClearanceRamp;
    }
}