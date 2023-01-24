package com.bknote71.ajouportal.config;

import com.bknote71.ajouportal.repository.ResourceRepository;
import com.bknote71.ajouportal.repository.UserRepository;
import com.bknote71.ajouportal.security.PermitAllFilter;
import com.bknote71.ajouportal.security.UrlFilterInvocationSecurityMetadataSource;
import com.bknote71.ajouportal.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final String[] permitAllResources = {"/users/**", "/login/**", "/courses/**"};
    @Bean
    public AuthenticationManager manager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new PrincipalDetailsService(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .formLogin();

//        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);

        return http.build();
    }

    @Bean
    public FilterSecurityInterceptor interceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new PermitAllFilter(permitAllResources);
        filterSecurityInterceptor.setSecurityMetadataSource(new UrlFilterInvocationSecurityMetadataSource(resourceRepository));
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        filterSecurityInterceptor.setAuthenticationManager(manager());
        return filterSecurityInterceptor;
    }

    private AccessDecisionManager accessDecisionManager() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(voters());
        return affirmativeBased;
    }

    private List<AccessDecisionVoter<?>> voters() {
        List<AccessDecisionVoter<?>> list = new ArrayList<>();
        list.add(roleVoter());
        return list;
    }

    private AccessDecisionVoter<?> roleVoter() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_STUDENT > ROLE_DEFAULT");
        return new RoleHierarchyVoter(roleHierarchy);
    }
}
