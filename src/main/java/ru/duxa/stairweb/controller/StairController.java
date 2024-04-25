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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.RoleRepository;
import ru.duxa.stairweb.service.PersonService;
import ru.duxa.stairweb.service.StairService;

import java.util.List;

@Controller
public class StairController {

    private final PersonService personService;
    private final RoleRepository roleRepository;
    private final StairService stairService;
    private static boolean isErrStair;

    @Autowired
    public StairController(PersonService personService, RoleRepository roleRepository, StairService stairService) {
        this.personService = personService;
        this.roleRepository = roleRepository;
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

        model.addAttribute("isErrStair", isErrStair);

        return "index";
    }

    @PostMapping("/")
    public String addStair(@ModelAttribute("stair") @Valid StairDto form, BindingResult result, RedirectAttributes redirectAttributes) {

        StairDto stairDto = stairService.formToDto(form);

        if ((stairDto.getStepHeights().size() - stairDto.getStepLengths().size()) != 1) {
            isErrStair = true;
            redirectAttributes.addFlashAttribute("stair", form);
            return "redirect:/";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("stair", form);
            return "index";
        }

        isErrStair = false;

        return "redirect:/result";
    }

    @GetMapping("/result")
    public String generalResult() {
        return "general-result";
    }

    @GetMapping("/et_passage_to_wall")
    public String etPassageToWall(){
        return "et/et-passage-to-wall";
    }

    @GetMapping("/et_passage_on_supports")
    public String etPassageOnSupports(){
        return "et/et-passage-on-supports";
    }

    @GetMapping("/et_side_to_wall")
    public String etSideToWall(){
        return "et/et-side-to-wall";
    }

    @GetMapping("/et_side_on_supports")
    public String etSideOnSupports(){
        return "et/et-side-on-supports";
    }

    @GetMapping("/et_3side_to_wall")
    public String et3SideToWall(){
        return "et/et-3side-to-wall";
    }

    @GetMapping("/et_3side_on_supports")
    public String et3SideOnSupports(){
        return "et/et-3side-on-supports";
    }

    @GetMapping("/npu_passage_to_wall")
    public String npuPassageToWall(){
        return "npu/npu-passage-to-wall";
    }

    @GetMapping("/npu_passage_on_supports")
    public String npuPassageOnSupports(){
        return "npu/npu-passage-on-supports";
    }

    @GetMapping("/npu_side_to_wall")
    public String npuSideToWall(){
        return "npu/npu-side-to-wall";
    }

    @GetMapping("/npu_side_on_supports")
    public String npuSideOnSupports(){
        return "npu/npu-side-on-supports";
    }

    @GetMapping("/npu_3side_to_wall")
    public String npu3SideToWall(){
        return "npu/npu-3side-to-wall";
    }

    @GetMapping("/npu_3side_on_supports")
    public String npu3SideOnSupports(){
        return "npu/npu-3side-on-supports";
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
