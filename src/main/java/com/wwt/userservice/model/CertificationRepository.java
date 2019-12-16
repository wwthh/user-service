package com.wwt.userservice.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CertificationRepository extends MongoRepository<Certification, String> {
    Integer removeByUserId(String userId);
    List<Certification> findByUserId(String userId);
}
