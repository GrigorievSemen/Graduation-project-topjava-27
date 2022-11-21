package ru.grigoriev.graduationproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.grigoriev.graduationproject.model.Role;
import ru.grigoriev.graduationproject.security.jwt.JwtConfigurer;
import ru.grigoriev.graduationproject.security.jwt.JwtTokenProvider;

import static ru.grigoriev.graduationproject.web.user.constant.Constant.ROLE_PREFIX;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT,"/api/v1/auth/registered","/h2-console/**/").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole( "ADMIN")
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}