package ru.grigoriev.graduationproject.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.grigoriev.graduationproject.model.User;

@Component
public class UserUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void setPasswordWithEncoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
