package com.user.registrtn.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
//@Profile("local")
public class SpringSecurityConfig {

//    @Value("${spring.datasource.url}")
//    private String dbUrl ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests.anyRequest().authenticated()).
//        http.authorizeRequests(requests -> requests.anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf.disable()).
                sessionManagement(session ->session.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)).headers(headers->
                        headers.frameOptions(frame-> frame.sameOrigin()));
        return http.build();
    }


    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        //   Without Hashing
        //   UserDetails userDetails = User.withUsername("Yash").password("{noop}Yash").roles("ADMIN").build();
        UserDetails userDetails = User.withUsername("Yash").password("Yash").
                passwordEncoder(pass->bCryptPasswordEncoder().encode(pass)).roles("ADMIN").build();
        UserDetails userDetails1 = User.withUsername("user").password("user").
                passwordEncoder(pass->bCryptPasswordEncoder().encode(pass)).roles("USER").build();
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.createUser(userDetails);
    jdbcUserDetailsManager.createUser(userDetails1);
    return jdbcUserDetailsManager;

    }
    @Bean
    public DataSource dataSource() {
//        System.out.println("${spring.datasource.url}");
//        System.out.println(dbUrl);
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("localdb").addScript(
                JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();

    }

    //hash the password to be stored in DB
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


}
