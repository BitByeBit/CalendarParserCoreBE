package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.LoginDto;
import com.bitbybit.corebe.dtos.RegisterDto;
import com.bitbybit.corebe.dtos.UserDataDto;
import com.bitbybit.corebe.models.User;
import com.bitbybit.corebe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String register(RegisterDto registerForm) {
        User user = new User(registerForm.username, registerForm.password, registerForm.name,
                            registerForm.series, registerForm.group, registerForm.semiGroup, null);

        if (!registerForm.password.equals(registerForm.matchingPassword))
            return "Fail";
        if (registerForm.group.length() != 3)
            return "Fail";
        if (registerForm.series.length() != 2)
            return "Fail";
        if (!registerForm.semiGroup.equals("A") && !registerForm.semiGroup.equals("B"))
            return "Fail";
        if (registerForm.year > 4)
            return "Fail";
        if (registerForm.semester > 2)
            return "Fail";

        userRepository.save(user);

        return "Success";
    }

    public String login(LoginDto loginForm) {
        return "Succes";
    }

    public User getUser(String username) {
        return userRepository.getUserByUsername(username);
    }
}