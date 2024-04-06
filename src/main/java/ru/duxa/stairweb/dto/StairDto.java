package ru.duxa.stairweb.dto;

import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StairDto {
    private int numberStair;
    private int upperFloor;
    private int downFloor;
    private int widthStair;
    @ElementCollection
    private Map<Integer, Integer> stepHeights;
    @ElementCollection
    private Map<Integer, Integer> stepLengths;
}
