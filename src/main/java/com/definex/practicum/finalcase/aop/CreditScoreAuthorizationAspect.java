package com.definex.practicum.finalcase.aop;

import com.definex.practicum.finalcase.exception.UnauthorizedException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.security.JwtAuthenticationFilter;
import com.definex.practicum.finalcase.security.JwtGenerator;
import com.definex.practicum.finalcase.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreditScoreAuthorizationAspect {

    private JwtGenerator jwtGenerator;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserService userService;

    @Autowired
    public CreditScoreAuthorizationAspect(JwtGenerator jwtGenerator, JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtGenerator = jwtGenerator;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }


    @Around("@annotation(com.definex.practicum.finalcase.aop.annotations.RequiresUserRolePermission)")
    public Object authorizeUserRoleAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        if (token != null && jwtGenerator.validation(token)) {
            String username = jwtGenerator.getJwtUsername(token);
            User user = userService.getUserByTckn(username);
            List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            if (authorities.contains("ROLE_USER") && !authorities.contains("ROLE_ADMIN")) {
                Object[] signatureArgs = joinPoint.getArgs();
                if (signatureArgs.length > 0 && signatureArgs[0] instanceof UUID) {
                    UUID userId = (UUID) signatureArgs[0];
                    if (!userService.getUserById(userId).getUsername().equals(username)) {
                        throw new UnauthorizedException("User is not authorized or token has expired");
                    }
                }
            }else if (!authorities.contains("ROLE_USER") && !authorities.contains("ROLE_ADMIN")) {
                throw new UnauthorizedException("User is not authorized or token has expired");
            }
        }
        return joinPoint.proceed();
    }
}
