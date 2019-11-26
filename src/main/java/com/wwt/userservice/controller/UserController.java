package com.wwt.userservice.controller;

import com.wwt.userservice.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public Map<String, Object> insertUser(@RequestBody String requestBody) {

        return null;
    }

    @GetMapping("/user")
    public String getUser(@RequestParam(name = "loheagn", required = true) String name,
                          @RequestHeader(name = "token", required = true) String token) {
        return name;
    }
}
