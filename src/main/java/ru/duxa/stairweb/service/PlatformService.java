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

    public void searchParametersPlatform(StairDto stairDto, PlatformDto platformDto) {

        if (platformDto.getName().equals("et")) {
            platformDto.setCurrentAngle((int) stairDto.getAngle());
        } else {
            platformDto.setCurrentAngle(stairDto.getAngle());
        }

        platformDto.setLengthWay(lengthWay(stairDto, platformDto));
        System.out.println(lengthWayOnLowerPlats(stairDto, platformDto));
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

        if (stairDto.getStepNumber() > 1) {
            y = stairDto.getStepHeightsCoordinates().get(stairDto.getStepNumber()) + platformDto.getClearanceOnStep();
        } else {
            y = stairDto.getStepHeightsCoordinates().get(1) + platformDto.getClearanceOnStep();
        }

        x = (int) Math.round(y / Math.tan(Math.toRadians(platformDto.getCurrentAngle())));

        if (stairDto.getStepNumber() > 1) {
            lengthWayOnLowerPlats = x - stairDto.getStepLengthsCoordinates().get(stairDto.getStepNumber() - 1);
        } else {
            lengthWayOnLowerPlats = x;
        }
        return lengthWayOnLowerPlats;
    }

//    private void findNumberMinClearanceStep(double angle, int lengthWayOnLowerPlats) {
//        clearanceMax = 0;
//        countClearanceMin = 0;
//        countClearanceMax = 0;
//        clearanceMin = maxClearanceGOST;
//
//        int clearance;
//        int x;
//        int y;
//        int stairY;
//        for (int i = 1; i < lengthStepsY.size(); i++) {
//            if (i == 1) {
//                x = lengthWayOnLowerPlats;
//            } else {
//                x = lengthWayOnLowerPlats + lengthStepsX.get(i - 1);
//            }
//            y = (int) (Math.tan(Math.toRadians(angle)) * x);
//            stairY = lengthStepsY.get(i);
//            clearance = y - stairY;
//            if (clearance > clearanceMax) {
//                clearanceMax = clearance;
//                countClearanceMax = i;
//            }
//            if (clearance < clearanceMin) {
//                clearanceMin = clearance;
//                countClearanceMin = i;
//            }
//        }
//    }

//    private int lengthClearanceRamp(int lengthWayOnLowerPlats, double angle) {
//        int l;
//        int xStair;
//        if (lengthStepsX.size() >= 1)
//            xStair = lengthStepsX.get(lengthStepsX.size());
//        else xStair = 0;
//        int xWay;
//        if (lengthStepsY.size() > 1) {
//            xWay = (int) ((lengthStepsY.get(lengthStepsY.size())) / Math.tan(Math.toRadians(angle))) - lengthWayOnLowerPlats;
//        } else {
//            xWay = 0;
//        }
//        if (lengthStepsY.size() > 1) {
//            l = xStair - xWay;
//        } else {
//            l = lengthWayOnLowerPlats - firstPoint;
//        }
//        return l;
//    }

}