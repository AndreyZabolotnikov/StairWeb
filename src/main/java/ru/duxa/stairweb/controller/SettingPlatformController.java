package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.duxa.stairweb.model.Platform;
import ru.duxa.stairweb.service.PlatformService;

@Controller
@RequestMapping("/settings_platform")
public class SettingPlatformController {

    private final PlatformService platformService;
    private Platform platform;

    @Autowired
    public SettingPlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    String setPlatformData(Model model) {
        if(platform == null)
            platform = new Platform();
        model.addAttribute("platform", platform);
        return "settings_platform";
    }

    @GetMapping("/{var}")
    String setPlatformData(@PathVariable("var") String var) {
        platform = platformService.getPlatform(var);
        return "redirect:/settings_platform";
    }
}
