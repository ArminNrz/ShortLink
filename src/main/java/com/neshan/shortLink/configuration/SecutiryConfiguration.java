package com.neshan.shortLink.configuration;

import com.neshan.shortLink.constant.Constant;
import com.neshan.shortLink.exception.RestAccessDeniedHandler;
import com.neshan.shortLink.security.SecurityUtil;
import com.neshan.shortLink.security.filter.AuthenticationFilter;
import com.neshan.shortLink.security.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecutiryConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SecurityUtil securityUtil;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationFilter customAuthenticationFilter = new AuthenticationFilter(authenticationManagerBean(), securityUtil);
        customAuthenticationFilter.setFilterProcessesUrl(Constant.BASE_URL + "/login");

        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(Constant.BASE_URL + "/login/**").permitAll()
                .antMatchers(Constant.BASE_URL + Constant.VERSION + "/customers/register").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new AuthorizationFilter(securityUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler);
    }
}
