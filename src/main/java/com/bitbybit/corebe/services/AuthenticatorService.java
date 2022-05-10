package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.LoginDto;
import com.bitbybit.corebe.dtos.RegisterDto;
import com.bitbybit.corebe.dtos.UserDataDto;

public class AuthenticatorService {
    public UserDataDto userData = null;

    public AuthenticatorService() {}

    public String register(RegisterDto registerForm) {
        this.userData = new UserDataDto(registerForm.getSeries(), registerForm.getGroup(), registerForm.getSemiGroup());
        if (!registerForm.getPassword().equals(registerForm.getMatchingPassword()))
            return "Fail";
        if (registerForm.getGroup().length() != 3)
            return "Fail";
        if (registerForm.getSeries().length() != 2)
            return "Fail";
        if (!registerForm.getSemiGroup().equals("A") && !registerForm.getSemiGroup().equals("B"))
            return "Fail";
        if (registerForm.getYear() > 4)
            return "Fail";
        if (registerForm.getSemester() > 2)
            return "Fail";

        return "Success";
    }

    public String login(LoginDto loginForm) {
        return "Succes";
    }
}
