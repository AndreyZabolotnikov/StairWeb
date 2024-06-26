package ru.duxa.stairweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.duxa.stairweb.dto.PasswordForgotDto;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Mail;
import ru.duxa.stairweb.model.PasswordResetToken;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.repository.PasswordResetTokenRepository;
import ru.duxa.stairweb.service.EmailService;
import ru.duxa.stairweb.service.PersonService;
import ru.duxa.stairweb.util.PersonValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class AuthController {

    private final PersonService personService;
    private final PersonValidator personValidator;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    @Autowired
    public AuthController(PersonService personService, PersonValidator personValidator,
                          PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @GetMapping("/authorization")
    public String authorizationWeb() {
        return "authorization";
    }

    @GetMapping("/inst-admin")
    public String authorizationAdminWeb(@RequestParam("admin") String admin) {
        String adminEmail = "admin";
        String adminPassword = "admin";
        if (admin.equals("reset") && personService.findByEmail(adminEmail) == null) {
            personService.saveAdmin(adminEmail, adminPassword);
        } else if (admin.equals("reset") && personService.findByEmail(adminEmail) != null) {
            personService.resetAdmin(adminEmail, adminPassword);
        }
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String regWeb(Model model) {
        PersonRegistrationDto personRegistrationDto = new PersonRegistrationDto();
        model.addAttribute("person", personRegistrationDto);
        return "reg";
    }

    @PostMapping("/reg")
    public String regPerson(@ModelAttribute("person") @Valid PersonRegistrationDto registrationDto,
                            BindingResult result, HttpServletRequest request) {

        personValidator.validate(registrationDto, result);

        if (result.hasErrors()) {
            return "reg";
        }

        personService.saveUser(registrationDto);

        PasswordResetToken token = new PasswordResetToken();
        Person person = personService.findByEmail(registrationDto.getEmail());
        token.setPerson(person);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("89658572145@mail.ru");
        mail.setTo(person.getEmail());
        mail.setSubject("Подтверждения регистрации");

        Map<String, Object> modelMail = new HashMap<>();
        modelMail.put("token", token);
        modelMail.put("user", person);
        modelMail.put("signature", "https://veara.ru");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        modelMail.put("resetUrl", url + "/confirmation?token=" + token.getToken());
        modelMail.put("forgotUrl", url + "/forgot");
        mail.setModel(modelMail);

        Runnable runnable = () -> {
            emailService.sendEmailConfirmation(mail);
        };

        new Thread(runnable).start();

        return "redirect:/send-email";
    }

    @GetMapping("/confirmation")
    @Transactional
    public String emailConfirmation(@RequestParam(required = false) String token, Model model) {

        if (tokenRepository.findByToken(token) == null) {
            model.addAttribute("error", "Неверная ссылка, зарегестрируйтесь заново");
        } else {
            PasswordResetToken confirmationToken = tokenRepository.findByToken(token);

            if (confirmationToken.isExpired()) {
                model.addAttribute("error", "Срок действия ссылки истек, запросите сбросить пароль");

            } else {
                model.addAttribute("token", confirmationToken.getToken());
                Person person = confirmationToken.getPerson();
                person.setEnabled(true);
                tokenRepository.delete(confirmationToken);
            }
        }

        return "confirmation-person";
    }

    @GetMapping("/forgot")
    public String forgot(Model model) {
        PasswordForgotDto passwordForgotDto = new PasswordForgotDto();
        model.addAttribute("forgotPasswordForm", passwordForgotDto);
        return "forgot-password";
    }

    @PostMapping("/forgot")
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {
        if (result.hasErrors()) {
            return "forgot-password";
        }

        if (form.getEmail().isEmpty()) {
            result.rejectValue("email", "required", "Email не должен быть пустым");
            return "forgot-password";
        } else if (form.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") == false
                || form.getEmail().equals("admin")) {
            result.rejectValue("email", "required", "Неверный формат email");
            return "forgot-password";
        }

        Person person = personService.findByEmail(form.getEmail());
        if (person == null) {
            result.rejectValue("email", null, "Email не найден");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();

        if (tokenRepository.findByPersonId(person.getId()) != null) {
            token = tokenRepository.findByPersonId(person.getId());
        } else
            token.setPerson(person);

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("89658572145@mail.ru");
        mail.setTo(person.getEmail());
        mail.setSubject("Сброс пароля");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", person);
        model.put("signature", "https://veara.ru");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);

        Runnable runnable = () -> {
            emailService.sendEmailReset(mail);
        };

        new Thread(runnable).start();


        return "redirect:/send-email";
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return "send-email";
    }

    @GetMapping("/change_account")
    public String changeAccount(Authentication authentication, Model model) {
        Person person = personService.findByEmail(authentication.getName());
        PersonRegistrationDto personRegistrationDto = personService.convertEntityToDto(person);
        model.addAttribute("person", personRegistrationDto);
        return "change_account";
    }

    @PostMapping("/change_account")
    public String updateAccount(@ModelAttribute("person") @Valid PersonRegistrationDto form, BindingResult result) {

        if (result.hasErrors()) {
            return "change_account";

        } else if (!personService.isPasswordMatch(form)) {
            result.rejectValue("password", "passwords", "Пароли не совпадают");
            return "change_account";
        }

        personService.updatePerson(form);

        return "redirect:/";
    }
}
