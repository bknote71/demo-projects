package com.bknote71.ajouportal.security;

import com.bknote71.ajouportal.domain.Resource;
import com.bknote71.ajouportal.repository.ResourceRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, List<ConfigAttribute>> requestMap;
    private final ResourceRepository resourceRepository;

    public UrlFilterInvocationSecurityMetadataSource(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
        requestMap = getRequestMap();
    }

    private Map<RequestMatcher, List<ConfigAttribute>> getRequestMap() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resultMap = new LinkedHashMap<>();
        List<Resource> resources = resourceRepository.findAllByType("url");

        // test code
        if (resources.isEmpty())
            resources.add(new Resource(1L, "url", "/", "ROLE_STUDENT"));

        // url resource 에 연관된 Role get
        resources.stream()
                .forEach(resource -> {
                    List<ConfigAttribute> attributes = new ArrayList<>();
//                    resource.getRoles().stream().forEach(r -> attributes.add(new SecurityConfig(r.getName())));
                    attributes.add(new SecurityConfig(resource.getRole()));
                    resultMap.put(new AntPathRequestMatcher(resource.getContent()), attributes);
                });
        return resultMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        // ROLE_DEFAULT << 최하위 권한, 인증만 받으면 생기는 권한
        List<ConfigAttribute> list = Arrays.asList(new SecurityConfig("ROLE_DEFAULT"));

        return list;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
