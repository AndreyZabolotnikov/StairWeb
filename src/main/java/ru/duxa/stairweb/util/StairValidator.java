package ru.duxa.stairweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Stair;

@Component
public class StairValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Stair.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StairDto stairDto = (StairDto) target;

        if (stairDto.getDownFloor() <= 0) {
            errors.rejectValue("downFloor", null, "не заполнено");
        }
        if (stairDto.getWidthStair() <= 0) {
            errors.rejectValue("widthStair", null, "не заполнено");
        }
        if (stairDto.getUpperFloor() <= 0) {
            errors.rejectValue("upperFloor", null, "не заполнено");
        }
    }
}
