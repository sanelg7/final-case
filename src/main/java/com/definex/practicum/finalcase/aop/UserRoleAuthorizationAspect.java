package com.definex.practicum.finalcase.aop;

import com.definex.practicum.finalcase.exception.UnauthorizedException;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.security.JwtAuthenticationFilter;
import com.definex.practicum.finalcase.security.JwtGenerator;
import com.definex.practicum.finalcase.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Aspect
@Component
public class UserRoleAuthorizationAspect {
    private final JwtGenerator jwtGenerator;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    // Checks incoming authorized requests and restricts role based access based on user

    @Autowired
    public UserRoleAuthorizationAspect(JwtGenerator jwtGenerator, JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtGenerator = jwtGenerator;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }

    // Checks if the received bearer token payload is carrying the same user that called the method.
    // ONLY checks for users with USER role. Does not apply to admins.
    @Around("@annotation(com.definex.practicum.finalcase.aop.annotations.RequiresUserRolePermission)")
    public Object authorizeUserRoleAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        if (token != null && jwtGenerator.validation(token)) {
            String username = jwtGenerator.getJwtUsername(token);
            User user = userService.getUserByTckn(username);
            List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
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
