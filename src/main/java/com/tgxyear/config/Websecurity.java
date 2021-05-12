package com.tgxyear.config;

import com.tgxyear.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Websecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    UserLoginService userService;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }


    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.logout()
//                    .logoutUrl()这个默认值为"/logout"
                .logoutSuccessUrl("/tologinn");

        http.formLogin().loginPage("/tologinn")
                .loginProcessingUrl("/kkp")//loginProcessingUrl得内容可以瞎写，但必须要和form表格中得action保持一致
//                    .failureForwardUrl("/hello").permitAll()
                .defaultSuccessUrl("/toblogdindex").permitAll()
                .and().authorizeRequests()
                .antMatchers("/static/layui/**").permitAll()//css，js等静态资源放行
                .antMatchers("/layui/**").permitAll()
                .antMatchers("/bootstrap-3.3.7-dist/**").permitAll()
                .antMatchers("/ad").hasRole("admin")
                .antMatchers("/").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/toregisterpage").permitAll()
                .antMatchers("/webfrontadduser").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
        ;
    }

}
