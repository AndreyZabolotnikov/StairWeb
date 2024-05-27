package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
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

    private int maxClearanceGOST;
}
