package com.fashionrental.website.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fashionrental.website.service.CustomUserDetailService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	@Autowired
	CustomUserDetailService customUserDetailService; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .antMatchers("/", "/shop/**", "/register","/forgotpassword").permitAll()
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .permitAll()
                                .failureUrl("/login?error=true")
                                .defaultSuccessUrl("/")
                                .usernameParameter("username")
                                .passwordParameter("password")
                )
                .oauth2Login(oauth2Login -> oauth2Login
                                .loginPage("/login")
                                .successHandler(googleOAuth2SuccessHandler)
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(withDefaults())
                .csrf(withDefaults())
                .headers(headers -> headers.frameOptions().disable());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(customUserDetailService);
	}
	
	@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
	}
}

