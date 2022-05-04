package com.blank.ecommerce.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String contextPath;

    public SecurityConfig(AdminServerProperties adminServerProperties) {
        this.contextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler
                = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(contextPath + "/");

        http.authorizeRequests()
                .antMatchers(contextPath + "/assets").permitAll()
                .antMatchers(contextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(contextPath + "/login").successHandler(successHandler)
                .and()
                .logout().logoutUrl(contextPath + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        contextPath + "/instances",
                        contextPath + "/actuator/**"
                )
        ;

    }
}
