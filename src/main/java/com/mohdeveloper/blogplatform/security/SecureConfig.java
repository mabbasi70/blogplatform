package com.mohdeveloper.blogplatform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecureConfig {

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        String usersByUsernameQuery =
//                "select username,password, enabled from `blog_platform`.user where username = ?";
//        String authorByUserQuery =
//                "select username, authority from `blog_platform`.authorities where username=?";
//        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
//        userDetailsManager.setAuthoritiesByUsernameQuery(authorByUserQuery);
//        return userDetailsManager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
