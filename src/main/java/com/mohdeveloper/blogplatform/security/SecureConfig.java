package com.mohdeveloper.blogplatform.security;


import com.mohdeveloper.blogplatform.service.impl.OAuth2UserServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecureConfig {


    private final CustomSuccessHandler successHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final UserServiceImpl userService;
    private final OAuth2UserServiceImpl oAuth2UserService;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

//    @PostConstruct
//    public void configureSecurityContextHolder() {
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/styles/**", "/js/**",
                                "/images/**", "/signup/**","/login/**",
                                "/blog-images/**","/logout-success",
                                "/oauth2/authorization/google","/password-reset/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated())

                .oauth2Login(auth2-> auth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .loginPage("/login")
                        .successHandler(successHandler))

                .formLogin(formLogin->formLogin
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))

//                .csrf(csrf -> csrf.ignoringRequestMatchers("/ws/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true"))
//                .authenticationProvider(authenticationProvider())
                .build();

    }


}
