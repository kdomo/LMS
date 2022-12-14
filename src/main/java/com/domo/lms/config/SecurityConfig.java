package com.domo.lms.config;

import com.domo.lms.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService memberService;

    AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailurlHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    UserAuthenticationSuccessHandler getSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
//                "member/register"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(memberService);
        authenticationManager = authenticationManagerBuilder.build();


        http
                .cors().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/member/register",
                        "/member/email-auth",
                        "/member/find/password",
                        "/member/reset/password").permitAll()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureHandler(getFailurlHandler()).permitAll()
                .successHandler(getSuccessHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/denied");
                ;
        return http.build();
    }

//    @Bean
//    public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//        return auth
//                .userDetailsService(memberService)
//                .passwordEncoder(getPasswordEncoder()).and().build();
//    }
}
