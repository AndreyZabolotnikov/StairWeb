package ru.duxa.stairweb.service;

import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.StairDto;

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

    private Map<Integer, Integer> mapFieldsToMapStair(Map<Integer, Integer> map) {
        Map<Integer, Integer> mapBuffer = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) != null && i == 0) {
                mapBuffer.put(i, map.get(i));
            } else if (map.get(i) != null && map.get(i - 1) != null) {
                mapBuffer.put(i, map.get(i));
                break;
            } else {
                break;
            }
        }
        return mapBuffer;
    }

}
