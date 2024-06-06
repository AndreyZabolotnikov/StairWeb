package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.duxa.stairweb.dto.PlatformDto;
import ru.duxa.stairweb.service.PlatformService;

@Controller
@RequestMapping("/settings_platform")
public class SettingPlatformController {

    private final PlatformService platformService;
    private PlatformDto platformDto;

    @Autowired
    public SettingPlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public String setPlatformData(Model model) {
        if (platformDto == null)
            platformDto = new PlatformDto();
        model.addAttribute("platform", platformDto);
        return "settings_platform";
    }

    @GetMapping("/{var}")
    public String setPlatformData(@PathVariable("var") String var) {
        platformDto = platformService.getPlatformDto(var, new PlatformDto());
        return "redirect:/settings_platform";
    }

    @PostMapping("/save")
    public String savePlatformData(@ModelAttribute("platform") PlatformDto form) {
        if (platformDto.getName() == null){
            return "redirect:/settings_platform";
        }
        form.setName(platformDto.getName());
        platformService.savePlatform(form);
        return "redirect:/settings_platform/" + form.getName();
    }

}