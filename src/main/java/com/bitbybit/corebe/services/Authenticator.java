package com.bitbybit.corebe.services;

import com.bitbybit.corebe.DTOS.LoginForm;
import com.bitbybit.corebe.DTOS.RegisterForm;
import com.bitbybit.corebe.DTOS.UserData;

public class Authenticator {
    public UserData userData = null;

    public Authenticator() {}

    public String register(RegisterForm registerForm) {
        this.userData = new UserData(registerForm.getSeries(), registerForm.getGroup(), registerForm.getSemiGroup());
        if (!registerForm.getPassword().equals(registerForm.getMatchingPassword()))
            return "Fail";
        if (registerForm.getGroup().length() != 3)
            return "Fail";
        if (registerForm.getSeries().length() != 2)
            return "Fail";
        if (!registerForm.getSemiGroup().equals("a") && !registerForm.getSemiGroup().equals("b"))
            return "Fail";
        if (registerForm.getYear() > 4)
            return "Fail";
        if (registerForm.getSemester() > 2)
            return "Fail";

        return "Succes";
    }

    public String login(LoginForm loginForm) {
        return "Succes";
    }
}
