package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "platform")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Platform {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "angle_max")
    private int angleMax;

    @Column(name = "length_way_max")
    private int lengthWayMax;

    @Column(name = "clearance_on_wall")
    private int clearanceOnWall;

    @Column(name = "clearance_on_step_supports")
    private int clearanceOnStepSupports;

    @Column(name = "clearance_on_step")
    private int clearanceOnStep;

    @Column(name = "overlap_ramp")
    private int overlapRamp;

    @Column(name = "length_ramp")
    private int lengthRamp;

    @Column(name = "length_passing")
    private int lengthPassing;

    @Column(name = "length_side")
    private int lengthSide;

    @Column(name = "width_on_wall_passing")
    private int widthOnWallPassing;

    @Column(name = "width_on_wall_side")
    private int widthOnWallSide;

    @Column(name = "width_on_wall_lower_side")
    private int widthOnWallLowerSide;

    @Column(name = "width_on_supports_passing")
    private int widthOnSupportsPassing;

    @Column(name = "width_on_supports_side")
    private int widthOnSupportsLowerSide;

    @Column(name = "width_on_supports_lower_side")
    private int widthOnSupportsSide;

    @Column(name = "folded_supports")
    private int foldedSupports;

    @Column(name = "folded_wall")
    private int foldedWall;

    @Column(name = "catcher")
    private int catcher;
}
