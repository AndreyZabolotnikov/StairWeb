package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.duxa.stairweb.model.Platform;
import ru.duxa.stairweb.service.PlatformService;

@Controller
@RequestMapping("/settings_platform")
public class SettingPlatformController {

    private final PlatformService platformService;

    @Autowired
    public SettingPlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }


    @GetMapping
    String setPlatformData(Model model) {
        Platform platform = new Platform();
        model.addAttribute("platform", platform);
        return "settings_platform";
    }

    @GetMapping("/et")
    String setPlatformDataET(Model model) {
        Platform platform = platformService.getPlatform("et");
        model.addAttribute("platform", platform);
        return "settings_platform";
    }

    @GetMapping("/npu")
    String setPlatformDataNPU(Model model) {
        Platform platform = platformService.getPlatform("npu");
        model.addAttribute("platform", platform);
        return "settings_platform";
    }
}
