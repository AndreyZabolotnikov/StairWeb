package ru.duxa.stairweb.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StairDto {
    private int numberStair;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "значение не может быть меньше 1")
    @Max(value = 99999, message = "значение не может быть больше 99999")
    private Integer upperFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "значение не может быть меньше 1")
    @Max(value = 99999, message = "значение не может быть больше 99999")
    private Integer downFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "значение не может быть меньше 1")
    @Max(value = 99999, message = "значение не может быть больше 99999")
    private Integer widthStair;
    @ElementCollection
    private Map<Integer, Integer> stepHeights;
    @ElementCollection
    private Map<Integer, Integer> stepLengths;

    private double angle;

    private int heightStair;

    private int lengthStair;

    private Map<Integer, Integer> stepLengthsCoordinates;

    private Map<Integer, Integer> stepHeightsCoordinates;

    private int stepNumber;

    private int countMatchingPoints;

    private int firstPoint;

    private int horizontalLength;
}
