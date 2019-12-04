package com.wwt.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwt.userservice.model.User;
import com.wwt.userservice.model.UserRepository;
import com.wwt.userservice.utils.Error;
import com.wwt.userservice.utils.Success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = { "/users", "/users/register" })
    public JSONObject insertUser(@RequestBody User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).size() > 0)
                return Error.errorResponse(1);
            User newUser = userRepository.insert(user);
            JSONObject content = new JSONObject();
            content.put("id", newUser.getId());
            content.put("username", newUser.getUserName());
            content.put("avatar", newUser.getPhoto());
            content.put("eid", newUser.getType() == null ? "" : newUser.getType());
            return Success.successResponse(content);
        } catch (Exception exception) {
            return Error.errorResponse(0);
        }
    }

    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(name = "email") String email) {
        return userRepository.findByEmail(email).get(0);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(name = "id") String id) {
        return userRepository.findById(id).get();
    }
}
