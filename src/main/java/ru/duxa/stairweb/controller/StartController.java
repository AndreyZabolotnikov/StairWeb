package ru.duxa.stairweb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.RoleRepository;
import ru.duxa.stairweb.service.PersonService;
import ru.duxa.stairweb.service.StairService;
import ru.duxa.stairweb.util.StairValidator;

import java.util.List;

@Controller
public class StartController {

    private final PersonService personService;
    private final RoleRepository roleRepository;
    private final StairValidator stairValidator;
    private final StairService stairService;
    private StairDto stairDtoBuffer;
    private static boolean isErrStair;

    @Autowired
    public StartController(PersonService personService, RoleRepository roleRepository, StairValidator stairValidator, StairService stairService) {
        this.personService = personService;
        this.roleRepository = roleRepository;
        this.stairValidator = stairValidator;
        this.stairService = stairService;
    }

    @GetMapping("/")
    public String startWeb(@ModelAttribute("stair") StairDto stairDto, Authentication authentication, Model model) {

        if (authentication != null) {
            model.addAttribute("isAuth", true);
            model.addAttribute("user", authentication.getName());
        } else {
            model.addAttribute("isAuth", false);
        }

        if (stairDtoBuffer != null) {
            stairDto.setDownFloor(stairDtoBuffer.getDownFloor());
            stairDto.setUpperFloor(stairDtoBuffer.getUpperFloor());
            stairDto.setWidthStair(stairDtoBuffer.getWidthStair());
            stairDto.setStepHeights(stairDtoBuffer.getStepHeights());
            stairDto.setStepLengths(stairDtoBuffer.getStepLengths());
        }

        model.addAttribute("isErrStair", isErrStair);

        return "index";
    }

    @PostMapping("/")
    public String addStair(@ModelAttribute("stair") @Valid StairDto form, BindingResult result, Model model) {

        StairDto stairDto = stairService.formToDto(form);
        stairValidator.validate(stairDto, result);

        if (result.hasErrors()) {
            return "index";
        }

        if ((stairDto.getStepHeights().size() - stairDto.getStepLengths().size()) != 1) {
            isErrStair = true;
            stairDtoBuffer = stairDto;
            return "redirect:/";
        }

        isErrStair = false;
        stairDtoBuffer = stairDto;

        return "redirect:/result";
    }

    @GetMapping("/result")
    public String generalResult() {
        return "general-result";
    }

    @GetMapping("/users")
//    @PreAuthorize("hasRole('USER')")
    public String listRegisteredUsers(Model model) {
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin")
    public String listRegisteredUsers1(Model model) {
        List<PersonRegistrationDto> users = personService.findAllUsers();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin";
    }

}
