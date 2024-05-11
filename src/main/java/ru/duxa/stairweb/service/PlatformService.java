package ru.duxa.stairweb.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.PlatformDto;
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

}