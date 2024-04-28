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
    @Max(value = 10000, message = "значение не может быть больше 10000")
    private Integer upperFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "значение не может быть меньше 1")
    @Max(value = 10000, message = "значение не может быть больше 10000")
    private Integer downFloor;
    @NotNull(message = "не заполнено")
    @Min(value = 1, message = "значение не может быть меньше 1")
    @Max(value = 10000, message = "значение не может быть больше 10000")
    private Integer widthStair;
    @ElementCollection
    private Map<Integer, Integer> stepHeights;
    @ElementCollection
    private Map<Integer, Integer> stepLengths;
}
