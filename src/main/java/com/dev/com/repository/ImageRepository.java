/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.repository;

import com.dev.com.document.cartitem;
import com.dev.com.document.image;
import com.dev.com.dto.ImageDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface ImageRepository extends MongoRepository<image, ObjectId> {
     @Query(value = "{'id': ?0}")
    image getFindImageId(ObjectId id);
}
