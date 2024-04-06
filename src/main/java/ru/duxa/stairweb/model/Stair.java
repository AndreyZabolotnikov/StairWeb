package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "stair")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stair{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numberStair;
    private int upperFloor;
    private int downFloor;
    private int widthStair;
    @ElementCollection
    private Map<Integer, Integer> stepHeights;
    @ElementCollection
    private Map<Integer, Integer> stepLengths;


}
