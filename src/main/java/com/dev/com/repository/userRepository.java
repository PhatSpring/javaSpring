/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.dev.com.document.userModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author ASUS
 */
@Repository
public interface userRepository extends MongoRepository<userModel, ObjectId> {

    @Query(value = "{'id': ?0}")
    userModel getFindUserId(ObjectId id);
}
