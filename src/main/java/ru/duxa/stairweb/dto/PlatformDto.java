package ru.duxa.stairweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlatformDto {

    private String name;

    private int angleMax;

    private int lengthWayMax;

    private int clearanceOnWall;

    private int clearanceOnStepSupports;

    private int clearanceOnStep;

    private int overlapRamp;

    private int lengthRamp;

    private int lengthPassing;

    private int lengthSide;

    private int widthOnWallPassing;

    private int widthOnWallSide;

    private int widthOnWallLowerSide;

    private int widthOnSupportsPassing;

    private int widthOnSupportsLowerSide;

    private int widthOnSupportsSide;

    private int foldedSupports;

    private int foldedWall;

    private int catcher;

    private int firePassageWay;

    private int lengthTrackAdd;

    private double currentAngle;

    private int lengthWay;

}