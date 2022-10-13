package com.dev.demo.config;


import com.dev.demo.security.JwtAuthenticationEntryPoint;
import com.dev.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {


    private JwtAuthenticationEntryPoint unAuthHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(unAuthHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeHttpRequests().antMatchers("/",
                        "/favicon.ico","/**/*.png","/**/*.gif","/**/*.svg","/**/*.jpg",
                        "/**/*.html","/**/*.css", "/**/*.js").permitAll()
                        .antMatchers("/user/**").permitAll()
                        .antMatchers("/auth/**").authenticated().and().formLogin();
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}
