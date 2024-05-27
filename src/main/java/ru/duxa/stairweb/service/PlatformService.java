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
    }

    public PlatformDto getPlatformDto(String platformName) {

        Platform platform = getPlatform(platformName);
        PlatformDto platformDto = new PlatformDto();

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

        return platformDto;
    }

    public void optimiizeParametersPlatform(StairDto stairDto, PlatformDto platformDto) {
        boolean checkET = false;
        int countET = 0;
        do {
//            lengthWayET = lengthWay(angleET);
//
//            if (countET == 0) {
//                lengthWayOnLowerPlatsET = lengthWayOnLowerPlats(ETPlatform.clearanceOnStep, angleET, stepNumY);
//            } else {
//                lengthWayOnLowerPlatsET = lengthWayOnLowerPlats(ETPlatform.clearanceOnStep, angleET, countClearanceMin);
//            }
//            lengthClearanceRampET = lengthClearanceRamp(lengthWayOnLowerPlatsET, angleET);
//
//            if(clearanceMax < maxClearanceGOST && clearanceMin > ETPlatform.clearanceOnStep - 1 && lengthClearanceRampET < 10){
//                for(int i = 1; i <= maxClearanceGOST - ETPlatform.clearanceOnStep; i++) {
//                    lengthWayOnLowerPlatsET = lengthWayOnLowerPlats(ETPlatform.clearanceOnStep + i, angleET, countClearanceMin);
//                    lengthClearanceRampET = lengthClearanceRamp(lengthWayOnLowerPlatsET, angleET);
//                    if(lengthClearanceRampET > 10){
//                        break;
//                    }
//                }
//            }
//
//            findNumberMinClearanceStep(angleET, lengthWayOnLowerPlatsET);
//
            if (platformDto.getLengthClearanceRamp() >= (platformDto.getLengthRamp() - platformDto.getOverlapRamp()) && platformDto.getCurrentAngle() > 0) {
                platformDto.setCurrentAngle(platformDto.getCurrentAngle() - 1);
                checkET = true;
            } else if ((platformDto.getLengthClearanceRamp() >= (platformDto.getLengthRamp() - platformDto.getOverlapRamp()) && platformDto.getCurrentAngle() < 1) || (platformDto.getLengthClearanceRamp() < 0 && platformDto.getCurrentAngle() < 1)) {
                platformDto.setCurrentAngle(0);
                platformDto.setLengthWay(0);
                platformDto.setLengthWayOnLowerPlats(0);
                platformDto.setLengthClearanceRamp(0);
                break;
            } else if (platformDto.getClearanceMin() <= platformDto.getClearanceOnStep() - 1 && countET == 0) {
                checkET = true;
            } else
                break;
            countET++;

        }
        while (checkET);
//        if(clearanceMax > maxClearanceGOST) {
//            angleET = 0;
//            lengthWayET = 0;
//            lengthWayOnLowerPlatsET = 0;
//            lengthClearanceRampET = 0;
//        }
//
//        clearanceMaxET = clearanceMax;
//        clearanceNumberMaxET = countClearanceMax;
    }

    public void searchParametersPlatform(StairDto stairDto, PlatformDto platformDto) {

        PlatformDto platformDtoByName = getPlatformDto(platformDto.getName());

        if (platformDto.getName().equals("et")) {
            platformDto.setCurrentAngle((int) stairDto.getAngle());
        } else {
            platformDto.setCurrentAngle(stairDto.getAngle());
        }

        platformDto.setLengthWay(lengthWay(stairDto, platformDto));
        platformDto.setClearanceOnStep(platformDtoByName.getClearanceOnStep());
        platformDto.setLengthWayOnLowerPlats(lengthWayOnLowerPlats(stairDto, platformDto));
        findNumberMinAndMaxClearanceStep(platformDto, stairDto, platformDto.getLengthWayOnLowerPlats());
        platformDto.setLengthClearanceRamp(lengthClearanceRamp(platformDto, stairDto, platformDto.getLengthWayOnLowerPlats()));

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
        int maxClearanceGOST = 500;
        int clearanceMax = 0;
        int countClearanceMin = 0;
        int countClearanceMax = 0;
        int clearanceMin = maxClearanceGOST;

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