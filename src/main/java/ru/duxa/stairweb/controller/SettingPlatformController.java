package ru.duxa.stairweb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingPlatformController {

    @GetMapping("/settings_platform")
    String setPlatformData() {
        return "settings_platform";
    }
}
