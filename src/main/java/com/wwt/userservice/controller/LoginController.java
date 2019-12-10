package com.wwt.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwt.userservice.model.User;
import com.wwt.userservice.model.UserRepository;
import com.wwt.userservice.utils.Error;
import com.wwt.userservice.utils.Success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

class RequestData {
    private String email;
    private String cipher;

    /**
     * @return the cipher
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * @param cipher the cipher to set
     */
    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}

/**
 * LoginController
 */
@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public JSONObject login(@RequestParam(name = "email") String email, @RequestHeader(name = "token", defaultValue = "", required = false) String token) {
        if(token==null || token.equals("")){
            return Error.errorResponse(0);
        } else if (token.equals("1")) {
            return Error.errorResponse(1);
        } else if (token.equals("2")) {
            return Error.errorResponse(2);
        }
        if (email == null)
            return Error.errorResponse(1);
        User user;
        try {
            user = userRepository.findByEmail(email).get(0);
        } catch (Exception exception) {
            return Error.errorResponse(1);
        }
        JSONObject content = new JSONObject();
        content.put("id", user.getId());
        content.put("token", token);
        content.put("username", user.getUserName());
        content.put("email", user.getEmail());
        content.put("points", user.getPoint());
        content.put("avatar", user.getPhoto());
        content.put("eid", user.getType() == null ? "" : user.getType());
        return Success.successResponse(content);
    }
}