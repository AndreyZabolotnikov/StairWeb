package ru.duxa.stairweb.dto;

import jakarta.persistence.ElementCollection;
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
    @Min(value = 1, message = "не заполнено")
    private Integer upperFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "не заполнено")
    private Integer downFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "не заполнено")
    private Integer widthStair;
    @ElementCollection
    private Map<Integer, Integer> stepHeights;
    @ElementCollection
    private Map<Integer, Integer> stepLengths;
}
