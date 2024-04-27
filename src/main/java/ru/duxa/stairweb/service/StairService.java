package ru.duxa.stairweb.service;

import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Stair;

import java.util.HashMap;
import java.util.Map;

@Service
public class StairService {

    public StairDto formToDto(StairDto form){
        StairDto stairDto = new StairDto();
        stairDto.setWidthStair(form.getWidthStair());
        stairDto.setUpperFloor(form.getUpperFloor());
        stairDto.setDownFloor(form.getDownFloor());
        stairDto.setStepLengths(mapFieldsToMapStair(form.getStepLengths()));
        stairDto.setStepHeights(mapFieldsToMapStair(form.getStepHeights()));
        return stairDto;
    }

    public Stair stairDtoToStair(StairDto form){
        Stair stair = new Stair();
        stair.setWidthStair(form.getWidthStair());
        stair.setUpperFloor(form.getUpperFloor());
        stair.setDownFloor(form.getDownFloor());
        stair.setStepLengths(mapFieldsToMapStair(form.getStepLengths()));
        stair.setStepHeights(mapFieldsToMapStair(form.getStepHeights()));
        return stair;
    }

    private Map<Integer, Integer> mapFieldsToMapStair(Map<Integer, Integer> map) {
        Map<Integer, Integer> mapBuffer = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) != null && i == 0) {
                mapBuffer.put(i, map.get(i));
            } else if (map.get(i) != null && map.get(i - 1) != null) {
                mapBuffer.put(i, map.get(i));
            } else {
                break;
            }
        }
        return mapBuffer;
    }

}
