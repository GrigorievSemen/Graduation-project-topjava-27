package ru.grigoriev.graduationproject.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.security.jwt.JwtUserFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockSecurity {

    public static void addMockToken(User user) {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(JwtUserFactory.create(user));
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }
}
