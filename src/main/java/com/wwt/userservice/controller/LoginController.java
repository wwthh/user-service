package com.wwt.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwt.userservice.model.User;
import com.wwt.userservice.model.UserRepository;
import com.wwt.userservice.utils.Error;
import com.wwt.userservice.utils.Success;
import com.wwt.userservice.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 */
@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/login")
    public JSONObject login(@RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "cipher", required = true) String password) {
        try {
            if (email == null)
                return Error.errorResponse(1);
            User user;
            try {
                user = userRepository.findByEmail(email).get(0);
            } catch (Exception exception) {
                return Error.errorResponse(1);
            }
            if (password == null || !password.equals(user.getPassword())) {
                return Error.errorResponse(2);
            }
            String token = JWTUtils.generateJWT(user.getId());
            JSONObject content = new JSONObject();
            content.put("id", user.getId());
            content.put("token", token);
            content.put("username", user.getUserName());
            content.put("email", user.getEmail());
            content.put("points", user.getPoint());
            content.put("avatar", user.getPhoto());
            content.put("eid", user.getType() == null ? "" : user.getType());
            return Success.successResponse(content);
        } catch (Exception e) {
            return Error.errorResponse(0);
        }
    }
}