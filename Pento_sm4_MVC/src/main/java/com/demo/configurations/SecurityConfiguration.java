package com.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.demo.service.AccountService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	private AccountService accountService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors(cor -> cor.disable())
					.csrf(cs -> cs.disable())
					.authorizeHttpRequests(auth -> {
						auth
							.requestMatchers(
									"/", 
									"/admin/css/**", "/admin/js/**",
									"/admin/login", "/admin/forgetPassword", "/admin/process-login", "/admin/logout",
									"/admin",
									"/home/**", "/aboutus/**", "/accessDenied",
									"/user/**", "/users/**"
									).permitAll()
							.requestMatchers("/superadmin/**").hasAnyRole("SUPER_ADMIN")
							.requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
							.requestMatchers("/user/**", "/").hasAnyRole("SUPER_ADMIN", "ADMIN", "MEMBER");
					})
					.formLogin(formLogin -> {
						formLogin.loginPage("/admin/login")
								.loginProcessingUrl("/admin/process-login")
								.usernameParameter("email")
								.passwordParameter("password")
								.defaultSuccessUrl("/")
								.failureUrl("/admin/login?error")
								;
					})
					.logout(logout -> {
						logout.logoutUrl("/logout")
								.logoutSuccessUrl("/admin/login?logout");
					})
					.exceptionHandling(ex -> {
						ex.accessDeniedPage("/accessDenied");
					})
					.build();
					
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) 
			throws Exception {
		builder.userDetailsService(accountService);
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
