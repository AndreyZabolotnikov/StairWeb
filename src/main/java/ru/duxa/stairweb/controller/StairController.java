package ru.duxa.stairweb.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.dto.PlatformDto;
import ru.duxa.stairweb.dto.StairDto;
import ru.duxa.stairweb.service.ExportPdfService;
import ru.duxa.stairweb.service.PersonService;
import ru.duxa.stairweb.service.PlatformService;
import ru.duxa.stairweb.service.StairService;
import ru.duxa.stairweb.util.PlatformValidator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("session")
public class StairController {

    private final PersonService personService;
    private final StairService stairService;
    private final PlatformService platformService;
    private final PlatformValidator platformValidator;
    private StairDto stairDto;
    private PlatformDto et;
    private PlatformDto npu;
    private ExportPdfService exportPdfService;

    @Autowired
    public StairController(PersonService personService, StairService stairService, PlatformService platformService, PlatformValidator platformValidator, ExportPdfService exportPdfService) {
        this.personService = personService;
        this.stairService = stairService;
        this.platformService = platformService;
        this.platformValidator = platformValidator;
        this.exportPdfService = exportPdfService;
    }

    @ModelAttribute("stair")
    private StairDto getStairDto() {
        if (stairDto == null) {
            stairDto = new StairDto();
        }
        return stairDto;
    }

    @ModelAttribute("et")
    private PlatformDto getEt() {
        if (et == null) {
            et = new PlatformDto();
        }
        return et;
    }

    @ModelAttribute("npu")
    private PlatformDto getNpu() {
        if (npu == null) {
            npu = new PlatformDto();
        }
        return npu;
    }


    @GetMapping("/")
    public String startWeb(Authentication authentication, Model model) {

        if (authentication != null) {
            model.addAttribute("isAuth", true);
            model.addAttribute("user", authentication.getName());
        } else {
            model.addAttribute("isAuth", false);
        }

        return "index";
    }

    @GetMapping("/reset")
    public String reset() {
        stairDto = new StairDto();
        et = new PlatformDto();
        npu = new PlatformDto();
        return "redirect:/";
    }

    @GetMapping("/stair")
    public String addStair(@ModelAttribute("stair") @Valid StairDto form, BindingResult result,
                           RedirectAttributes redirectAttributes, Authentication authentication, Model model) {

        stairDto = stairService.formToDto(form);

        if ((stairDto.getStepHeights().size() - stairDto.getStepLengths().size()) != 1
                || form.getStepHeights().size() <= form.getStepLengths().size()
                || isErrorMapStair(form.getStepHeights(), stairDto.getStepHeights())
                || isErrorMapStair(form.getStepLengths(), stairDto.getStepLengths())
                || result.hasErrors()
        ) {
            redirectAttributes.addFlashAttribute("stair", stairDto);
            model.addAttribute("isAuth", true);
            model.addAttribute("user", authentication.getName());
            result.rejectValue("stepHeights", "error.stair", "Заполните правильно лестницу!");
            return "index";
        }

        redirectAttributes.addFlashAttribute("stair", stairDto);
        return "redirect:/result";
    }

    private boolean isErrorMapStair(Map<Integer, Integer> map, Map<Integer, Integer> mapSize) {
        int counter = 0;
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) != null) {
                counter++;
            }
        }
        return counter != mapSize.size();
    }

    @GetMapping("/result")
    public String generalResult() {

        stairService.searchParametersStair(stairDto);

        et.setName("et");
        platformService.optimizeAddSearchParametersPlatform(stairDto, et);
        platformValidator.setParam(stairDto, et);

        npu.setName("npu");
        platformService.optimizeAddSearchParametersPlatform(stairDto, npu);
        double scale = Math.pow(10, 1);
        npu.setCurrentAngle(Math.ceil(npu.getCurrentAngle() * scale) / scale);

        platformValidator.setParam(stairDto, npu);

        return "general-result";
    }

    @GetMapping("/et_passage_to_wall")
    public String etPassageToWall() {
        return "et/et-passage-to-wall";
    }

    @GetMapping("/et_passage_on_supports")
    public String etPassageOnSupports() {
        return "et/et-passage-on-supports";
    }

    @GetMapping("/et_side_to_wall")
    public String etSideToWall() {
        return "et/et-side-to-wall";
    }

    @GetMapping("/et_side_on_supports")
    public String etSideOnSupports() {
        return "et/et-side-on-supports";
    }

    @GetMapping("/et_3side_to_wall")
    public String et3SideToWall() {
        return "et/et-3side-to-wall";
    }

    @GetMapping("/et_3side_on_supports")
    public String et3SideOnSupports() {
        return "et/et-3side-on-supports";
    }

    @GetMapping("/npu_passage_to_wall")
    public String npuPassageToWall() {
        return "npu/npu-passage-to-wall";
    }

    @GetMapping("/npu_passage_on_supports")
    public String npuPassageOnSupports() {
        return "npu/npu-passage-on-supports";
    }

    @GetMapping("/npu_side_to_wall")
    public String npuSideToWall() {
        return "npu/npu-side-to-wall";
    }

    @GetMapping("/npu_side_on_supports")
    public String npuSideOnSupports() {
        return "npu/npu-side-on-supports";
    }

    @GetMapping("/npu_3side_to_wall")
    public String npu3SideToWall() {
        return "npu/npu-3side-to-wall";
    }

    @GetMapping("/npu_3side_on_supports")
    public String npu3SideOnSupports() {
        return "npu/npu-3side-on-supports";
    }

    @GetMapping("/download_stair")
    public void downloadStair(HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("stair", stairDto);
        ByteArrayInputStream exportedData = exportPdfService.exportPdf("stair", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=stair.pdf");
        IOUtils.copy(exportedData, response.getOutputStream());

    }
}
