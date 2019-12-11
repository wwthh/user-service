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
            if(user.getPhoto()==null)
                 user.setPhoto("https://s2.ax1x.com/2019/12/10/QD6MUH.png");
            user.setPoint("0");
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

    @PatchMapping(value="/users/{id}")
    public JSONObject updateUser(@RequestBody User user, @RequestHeader("userId") String userId, @PathVariable(name = "id")String id) {
        try {
            if(userId==null || userId.equals(""))
                return Error.errorResponse(3);
            if(!userId.equals(id))
                return Error.errorResponse(2);
            User oldUser = userRepository.findById(id).get();
            if(user.getEmail()!=null) {
                int tmpn = userRepository.findByEmail(user.getEmail()).size();
                if(tmpn>0)
                    return Error.errorResponse(1);
                oldUser.setEmail(user.getEmail());
            }
            if(user.getUserName()!=null) {
                oldUser.setUserName(user.getUserName());
            }
            if(user.getPhoto()!=null) {
                oldUser.setPhoto(user.getPhoto());
            }
            if(user.getPoint()!=null) {
                oldUser.setPoint(user.getPoint());
            }
            userRepository.save(oldUser);
            return Success.successResponse(JSONObject.parseObject(JSONObject.toJSONString(oldUser)));
        } catch (Exception e){
            return Error.errorResponse(0);
        }
    }

    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(name = "email") String email) {
        try{
            return userRepository.findByEmail(email).get(0);
        } catch (Exception e){
            return new User();
        }
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(name = "id") String id) {
        return userRepository.findById(id).get();
    }
}
