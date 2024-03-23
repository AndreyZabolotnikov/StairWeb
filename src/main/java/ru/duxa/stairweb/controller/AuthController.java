package ru.duxa.stairweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.List;
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

    @GetMapping("/reg")
    public String regWeb(Model model) {
        PersonRegistrationDto personRegistrationDto = new PersonRegistrationDto();
        model.addAttribute("person", personRegistrationDto);
        return "reg";
    }

    @PostMapping("/reg")
    public String regPerson(@Valid @ModelAttribute("person") PersonRegistrationDto registrationDto,
                            BindingResult result, Model model) {
        personValidator.validate(registrationDto, result);

        if (result.hasErrors()) {
            return "reg";
        }

        personService.saveUser(registrationDto);
        return "redirect:authorization";
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
        if (result.hasErrors()){
            return "forgot-password";
        }

        if (form.getEmail().isEmpty())
            result.rejectValue("email","required", "Email не должен быть пустым");

        else if (form.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") == false)
            result.rejectValue("email", "required", "Неверный формат email");

        Person person = personService.findByEmail(form.getEmail());
        if (person == null) {
            result.rejectValue("email", null, "Email не найден");
            return "forgot-password";
        }

//        PasswordResetToken token = new PasswordResetToken();
//        token.setToken(UUID.randomUUID().toString());
//        token.setPerson(person);
//        token.setExpiryDate(30);
//        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("89658572145@mail.ru");
        mail.setTo(person.getEmail());
        mail.setSubject("Password reset request");

//        Map<String, Object> model = new HashMap<>();
//        model.put("token", token);
//        model.put("user", person);
//        model.put("signature", "https://memorynotfound.com");
//        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
//        mail.setModel(model);
//        emailService.sendEmail(mail);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        emailService.sendSimpleMessage(mail);


        return "redirect:/forgot?success";
    }

    @GetMapping("/users")
//    @PreAuthorize("hasRole('USER')")
    public String listRegisteredUsers(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users1")
    public String listRegisteredUsers1(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

}
