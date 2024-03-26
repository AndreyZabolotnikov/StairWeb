package ru.duxa.stairweb.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.duxa.stairweb.dto.PasswordResetDto;
import ru.duxa.stairweb.model.PasswordResetToken;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.repository.PasswordResetTokenRepository;
import ru.duxa.stairweb.service.PersonService;

@Controller
public class PasswordResetController {

    @Autowired private PersonService personService;
    @Autowired private PasswordResetTokenRepository tokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordResetDto() {
        return new PasswordResetDto();
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(required = false) String token, Model model) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if(resetToken == null) {
           model.addAttribute("error", "Неверная ссылка, запросите заново сбросить пароль");
        } else if (resetToken.isExpired()) {
            model.addAttribute("error", "Срок действия ссылки истекло, запросите заново сбросить пароль");

        }else {
            model.addAttribute("token", resetToken.getToken());
        }
        return "reset-password";
    }

    @PostMapping("/reset-password")
    @Transactional
    public String handleResetPassword(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
                                      BindingResult result, RedirectAttributes redirectAttributes) {

        if (form.getPassword().isEmpty()){
            result.rejectValue("password", null, "Пароль не может быть пустым");
        }

        else if(!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.passwordResetForm", "Пароли не совпадают");
        }

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return "redirect:/reset-password?token=" + form.getToken();
        }


        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        Person person = token.getPerson();
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        person.setPassword(updatedPassword);
        person.setEnabled(true);
        tokenRepository.delete(token);

        return "redirect:/login?resetSuccess";
    }
}
