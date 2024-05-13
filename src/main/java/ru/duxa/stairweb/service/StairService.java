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

    public double searchAngle(StairDto stairDto) {
        Map<Integer, Integer> stepLengths = stepTransformToCoordinates( stairDto.getStepLengths());
        Map<Integer, Integer> stepHeights = stepTransformToCoordinates( stairDto.getStepHeights());

        double angle = 90;
        int stepNumY = 0;
        int countMatchingPoints = 0;

        if (stepHeights.size() == 1)
            return 30;
        else {
            for (int i = 0; i < stepHeights.size()-1; i++) {
                double lengthStep;
                if (i == 0)
                    lengthStep = stepLengths.get(stepLengths.size()-1);
                else {
                    lengthStep = stepLengths.get(stepLengths.size()-1) - stepLengths.get(i - 1);
                }
                double heightStep = stepHeights.get(stepHeights.size()-1) - stepHeights.get(i);
                double angleCurrent = Math.toDegrees(Math.atan(heightStep / lengthStep));
                if (angle == angleCurrent)
                    countMatchingPoints++;
                if (angle >= angleCurrent) {
                    angle = angleCurrent;
                    stepNumY = i;
                }
            }
        }
        return angle;
    }

    public Map<Integer, Integer> stepTransformToCoordinates(Map<Integer, Integer> stepSize) {

        Map<Integer, Integer> stepCoordinates = new HashMap<>();
        if (stepSize.isEmpty())
            return stepCoordinates;

        int size = stepSize.get(0);
        for (int i = 0; i <= stepSize.size()-1; i++) {
            if (stepSize.get(i) != 0)
                stepCoordinates.put(i, size);
            else break;
            if (i != stepSize.size()-1)
                size += stepSize.get(i + 1);
        }
        return stepCoordinates;
    }

    public int lengthStair(Map<Integer, Integer> coordinatesHeightsStep, Map<Integer, Integer> coordinatesLengthsStep) {
        int lengthStair = 0;
        int length;
        int height;
        if (coordinatesHeightsStep.size() > 1) {
            length = coordinatesLengthsStep.get(coordinatesLengthsStep.size() - 1);
            height = coordinatesHeightsStep.get(coordinatesHeightsStep.size() - 1) - coordinatesHeightsStep.get(0);
            lengthStair = (int) Math.sqrt(Math.pow(length, 2) + Math.pow(height, 2));
        }
        return lengthStair;
    }

    public int findFirstPoint(Map<Integer, Integer> coordinatesHeightsStep, Map<Integer, Integer> coordinatesLengthsStep,
                              int stepNumY, double angle) {
        int x0;
        if (coordinatesHeightsStep.size() > 1) {
            int y0 = 0;
            int xe = coordinatesLengthsStep.get(coordinatesLengthsStep.size());
            int ye = coordinatesHeightsStep.get(coordinatesHeightsStep.size());
            int ym = coordinatesHeightsStep.get(stepNumY);
            int xm;
            if (stepNumY <= 1)
                xm = 0;
            else
                xm = coordinatesLengthsStep.get(stepNumY - 1);

            x0 = (((y0 - ym) * (xe - xm)) / (ye - ym)) + xm;
        } else {
            x0 = (int) (coordinatesHeightsStep.get(1) / Math.tan(Math.toRadians(angle)));
        }
        return Math.abs(x0);
    }

}
