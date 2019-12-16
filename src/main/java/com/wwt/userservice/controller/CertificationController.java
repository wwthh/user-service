package com.wwt.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwt.userservice.model.Certification;
import com.wwt.userservice.model.CertificationRepository;
import com.wwt.userservice.model.User;
import com.wwt.userservice.model.UserRepository;
import com.wwt.userservice.utils.Error;
import com.wwt.userservice.utils.Success;
import com.wwt.userservice.utils.UserIdCertification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CertificationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CertificationRepository certificationRepository;

    @PostMapping("/users/{id}/certification")
    public JSONObject createCertification(@RequestHeader("userId") String userId, @PathVariable(name = "id")String id, @RequestBody Certification certification){
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            if(certification.getName()==null || certification.getOrg()==null || certification.getTags()==null || certification.getTags().size()==0)
                return Error.errorResponse(1);
            User oldUser = userRepository.findById(id).get();
            oldUser.setType("-1");
            userRepository.save(oldUser);
            certification.setUserId(id);
            certification.setApplyDate(System.currentTimeMillis());
            certification.setState("ing");
            certificationRepository.insert(certification);
            return Success.successResponse(new JSONObject());
        } catch (Exception e){
            return Error.errorResponse(0);
        }
    }

    @DeleteMapping("/users/{id}/certification")
    public JSONObject deleteCertification(@RequestHeader("userId") String userId, @PathVariable(name = "id")String id) {
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            certificationRepository.removeByUserId(userId);
            return Success.successResponse(new JSONObject());
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }

    @GetMapping("/users/{id}/certification")
    public JSONObject getCertification(@RequestHeader("userId") String userId, @PathVariable("id")String id) {
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            Certification certification = certificationRepository.findByUserId(userId).get(0);
            return Success.successResponse(JSONObject.parseObject(JSONObject.toJSONString(certification)));
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }
}
