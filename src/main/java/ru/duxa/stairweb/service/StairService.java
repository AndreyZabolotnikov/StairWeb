package ru.duxa.stairweb.service;

import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Stair;

import java.util.HashMap;
import java.util.Map;

@Service
public class StairService {

    public StairDto formToDto(StairDto form) {
        StairDto stairDto = new StairDto();
        stairDto.setWidthStair(form.getWidthStair());
        stairDto.setUpperFloor(form.getUpperFloor());
        stairDto.setDownFloor(form.getDownFloor());
        stairDto.setStepLengths(mapFieldsToMapStair(form.getStepLengths()));
        stairDto.setStepHeights(mapFieldsToMapStair(form.getStepHeights()));
        return stairDto;
    }

//    public Stair stairDtoToStair(StairDto form){
//        Stair stair = new Stair();
//        stair.setWidthStair(form.getWidthStair());
//        stair.setUpperFloor(form.getUpperFloor());
//        stair.setDownFloor(form.getDownFloor());
//        stair.setStepLengths(mapFieldsToMapStair(form.getStepLengths()));
//        stair.setStepHeights(mapFieldsToMapStair(form.getStepHeights()));
//        return stair;
//    }

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

    public void searchParametersStair(StairDto stairDto) {

        stairDto.setStepHeightsCoordinates(stepTransformToCoordinates(stairDto.getStepHeights()));
        stairDto.setStepLengthsCoordinates(stepTransformToCoordinates(stairDto.getStepLengths()));

        double angle = 90;
        int stepNumY = 0;
        int countMatchingPoints = 0;

        if (stairDto.getStepHeightsCoordinates().size() == 1)
            angle = 30;
        else {
            for (int i = 0; i < stairDto.getStepHeightsCoordinates().size() - 1; i++) {
                double lengthStep;
                if (i == 0)
                    lengthStep = stairDto.getStepLengthsCoordinates().get(stairDto.getStepLengthsCoordinates().size() - 1);
                else {
                    lengthStep = stairDto.getStepLengthsCoordinates().get(stairDto.getStepLengthsCoordinates().size() - 1) - stairDto.getStepLengthsCoordinates().get(i - 1);
                }
                double heightStep = stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size() - 1) - stairDto.getStepHeightsCoordinates().get(i);
                double angleCurrent = Math.toDegrees(Math.atan(heightStep / lengthStep));
                if (angle == angleCurrent)
                    countMatchingPoints++;
                if (angle >= angleCurrent) {
                    angle = angleCurrent;
                    stepNumY = i;
                }
            }
        }
        stairDto.setCountMatchingPoints(countMatchingPoints);
        stairDto.setNumberStair(stepNumY);
        stairDto.setAngle(Math.round(angle * 10.0) / 10.0);
        stairDto.setLengthStair(lengthStair(stairDto));
        stairDto.setHeightStair(stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size() - 1));
        stairDto.setFirstPoint(findFirstPoint(stairDto));
    }

    private Map<Integer, Integer> stepTransformToCoordinates(Map<Integer, Integer> stepSize) {

        Map<Integer, Integer> stepCoordinates = new HashMap<>();
        if (stepSize.isEmpty())
            return stepCoordinates;

        int size = stepSize.get(0);
        for (int i = 0; i <= stepSize.size() - 1; i++) {
            if (stepSize.get(i) != 0)
                stepCoordinates.put(i, size);
            else break;
            if (i != stepSize.size() - 1)
                size += stepSize.get(i + 1);
        }
        return stepCoordinates;
    }

    private int lengthStair(StairDto stairDto) {
        int lengthStair = 0;
        int length;
        int height;
        if (stairDto.getStepHeightsCoordinates().size() > 1) {
            length = stairDto.getStepLengthsCoordinates().get(stairDto.getStepLengthsCoordinates().size() - 1);
            height = stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size() - 1) - stairDto.getStepHeightsCoordinates().get(0);
            lengthStair = (int) Math.sqrt(Math.pow(length, 2) + Math.pow(height, 2));
        }
        return lengthStair;
    }

    private int findFirstPoint(StairDto stairDto) {
        int x0;
        if (stairDto.getStepHeightsCoordinates().size() > 1) {
            int y0 = 0;
            int xe = stairDto.getStepLengthsCoordinates().get(stairDto.getStepLengthsCoordinates().size()-1);
            int ye = stairDto.getStepHeightsCoordinates().get(stairDto.getStepHeightsCoordinates().size()-1);
            int ym = stairDto.getStepHeightsCoordinates().get(stairDto.getStepNumber());
            int xm;
            if (stairDto.getStepNumber() <= 1)
                xm = 0;
            else
                xm = stairDto.getStepLengthsCoordinates().get(stairDto.getStepNumber() - 1);

            x0 = (((y0 - ym) * (xe - xm)) / (ye - ym)) + xm;
        } else {
            x0 = (int) (stairDto.getStepHeightsCoordinates().get(0) / Math.tan(Math.toRadians(stairDto.getAngle())));
        }
        return Math.abs(x0);
    }

}
