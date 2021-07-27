package com.yl.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * 配置一个springsecurity配置类
 */
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;
    //认证
//    @Bean
//    public UserDetailsService userDetailsService() throws Exception {
//        // ensure the passwords are encoded properly  从内存中取值
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(users.username("admin").password("123456").roles("vip1","vip2","vip3").build());
//        manager.createUser(users.username("yanglun").password("123456").roles("vip1").build());
//        manager.createUser(users.username("zhangsan").password("123456").roles("vip2").build());
//        manager.createUser(users.username("lisi").password("123456").roles("vip3").build());
//        return manager;
//
//    }
    @Bean
    UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("vip1")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("vip1", "vip2","vip3")
//                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        users.createUser(admin);
        return users;
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests(authorize -> authorize
                            .anyRequest().hasRole("ADMIN")
                    )
                    .httpBasic();
        }
    }
//    权限分配
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers((requestMatchers) ->
                            requestMatchers
                                    .antMatchers("/**").anyRequest()
                    )
                    .authorizeRequests((authorizeRequests) ->
                            authorizeRequests
                                    .antMatchers("/level1/**").hasRole("vip1")
                    )
                    .authorizeHttpRequests((authorizeRequests) ->
                            authorizeRequests.antMatchers("/level2/**").hasRole("vip2"))
                    .authorizeHttpRequests((authorizeRequests) ->
                            authorizeRequests.antMatchers("/level3/**").hasRole("vip3"))
                    .formLogin();


            http.logout().logoutSuccessUrl("/login");
        }
    }
}
