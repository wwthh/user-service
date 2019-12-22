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
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
public class CertificationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CertificationRepository certificationRepository;

    @PostMapping("/users/{id}/certification")
    public JSONObject createCertification(@RequestHeader("userId") String userId, @PathVariable(name = "id")String id, @RequestBody CerRequestBody  cerRequestBody){
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            if(cerRequestBody==null || cerRequestBody.getEid()==null)
                return Error.errorResponse(1);
            User oldUser = userRepository.findById(id).get();
            if(oldUser.getType()!=null && !oldUser.getType().equals("")) return Error.errorResponse(4);
            oldUser.setType(cerRequestBody.getEid());
            // 修改专家信息
            String updateString = "{\n" +
                    "     \n" +
                    "     \"doc\":{ \"isCertification\" : \"%s\"}\n" +
                    "}\n";
            JSONObject requestBody = JSONObject.parseObject(String.format(updateString, id));
            String response = new RestTemplate().postForObject("http://localhost:9200/expert3/expert3/" + cerRequestBody.getEid() + "/_update", requestBody, String.class);
            userRepository.save(oldUser);
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
            User user = userRepository.findById(userId).get();
            user.setType("");
            userRepository.save(user);
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
            try {
                Certification certification = certificationRepository.findByUserId(userId).get(0);
                return Success.successResponse(JSONObject.parseObject(JSONObject.toJSONString(certification)));
            } catch (Exception e) {
                return Error.errorResponse(1);
            }
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }
}

class CerRequestBody{
    private String eid;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
