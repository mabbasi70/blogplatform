//package com.mohdeveloper.blogplatform.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
//import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
//@Configuration
//@EnableJdbcHttpSession
//public class SessionConfig {
//    @Bean
//    public JdbcOperationsSessionRepository sessionRepository(JdbcOperations jdbcOperations, PlatformTransactionManager transactionManager) {
//        JdbcOperationsSessionRepository repository = new JdbcOperationsSessionRepository(jdbcOperations, transactionManager);
//        // Using a JSON-based serializer
//        repository.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        return repository;
//    }
//
//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver() {
//        // Use header-based session ID resolution
//        return HeaderHttpSessionIdResolver.xAuthToken();
//    }
//}
