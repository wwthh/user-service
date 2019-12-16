package com.wwt.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwt.userservice.model.Paper;
import com.wwt.userservice.model.User;
import com.wwt.userservice.model.UserRepository;
import com.wwt.userservice.utils.Error;
import com.wwt.userservice.utils.Success;
import com.wwt.userservice.utils.UserIdCertification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FavouriteController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/{id}/favourite")
    public JSONObject addFavourite(@RequestBody List<Paper> papers, @PathVariable(name= "id") String id, @RequestHeader("userId") String userId) {
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            if(papers == null || papers.size() ==0) return Error.errorResponse(1);
            User user = userRepository.findById(userId).get();
            for(int i = 0;i<papers.size();i++){
                papers.get(i).setAddTime(new Date());
            }
            Set<Paper> oldPapers = user.getFavourites();
            if(oldPapers==null)
                oldPapers = new HashSet<Paper>();
            oldPapers.addAll(papers);
            user.setFavourites(oldPapers);
            userRepository.save(user);
            return Success.successResponse(new JSONObject());
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }

    @DeleteMapping("/users/{id}/favourite")
    public JSONObject removeFavourite(@RequestBody List<String> papers, @PathVariable(name= "id") String id, @RequestHeader("userId") String userId) {
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            User user = userRepository.findById(userId).get();
            Set<Paper> oldPapers = user.getFavourites();
            oldPapers.removeIf(paper -> papers.contains(paper.getId()));
            user.setFavourites(oldPapers);
            userRepository.save(user);
            return Success.successResponse(new JSONObject());
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }

    @GetMapping("/users/{id}/favourite")
    public JSONObject getFavourite(@RequestHeader("userId") String userId, @PathVariable("id")String id) {
        try {
            JSONObject tmp = UserIdCertification.run(userId, id);
            if(tmp!=null) return tmp;
            Set<Paper> papers = userRepository.findById(userId).get().getFavourites();
            if(papers == null) papers = new HashSet<Paper>();
            return Success.successResponse(papers);
        }catch(Exception e) {
            return Error.errorResponse(0);
        }
    }
}
