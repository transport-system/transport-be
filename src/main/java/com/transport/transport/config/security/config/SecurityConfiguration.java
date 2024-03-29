package com.transport.transport.config.security.config;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.common.RoleEnum;
import com.transport.transport.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.applyPermitDefaultValues();
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
            return configuration;
        });

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(EndpointConstant.Authentication.AUTHENTICATION_ENDPOINT + "/**").permitAll() //Cho phép tất cả các role, nhưng mà không đăng nhập vẫn được
                .antMatchers(EndpointConstant.Booking.BOOKING_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Seat.SEAT_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Account.ACCOUNT_ENDPOINT + "/**").authenticated() //cho phép tất cả các role, nhưng phải login
                .antMatchers(EndpointConstant.Company.COMPANY_ENDPOINT + "/**").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.COMPANY.name())
                .antMatchers(EndpointConstant.Vehicle.VEHICLE_ENDPOINT + "/**").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.COMPANY.name())
                .antMatchers(EndpointConstant.Voucher.VOUCHER_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Route.ROUTE_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Feedback.FEEDBACK_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Trip.TRIP_ENDPOINT + "/**").permitAll()
                .antMatchers(EndpointConstant.Payment.PAYMENT_ENDPOINT + "/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl(EndpointConstant.Authentication.LOGOUT_ENDPOINT)
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/webjars/**",
                "/csrf",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/error",
                "/postman"
        );
    }
}
