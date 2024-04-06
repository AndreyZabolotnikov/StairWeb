package ru.duxa.stairweb.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/img/**").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/reg").permitAll()
                                .requestMatchers("/forgot").permitAll()
                                .requestMatchers("/send-email").permitAll()
                                .requestMatchers("/reset-password").permitAll()
                                .requestMatchers("/confirmation").permitAll()
                                .requestMatchers("/inst-admin").permitAll()
                                .requestMatchers("/stair").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/users").hasRole("USER")
                                .requestMatchers("/admin").hasRole("ADMIN")

                ).formLogin(
                        (form) -> form
                                .loginPage("/authorization").permitAll()
                                .loginProcessingUrl("/authorization")
                                .defaultSuccessUrl("/", true).permitAll()
                                .failureUrl("/authorization?error").permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/authorization")
                                .permitAll()
                ).rememberMe(
                        (rememberMe) -> rememberMe.key("uniqueAndSecret")
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}