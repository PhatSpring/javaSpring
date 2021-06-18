/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.service;

import com.cloudinary.utils.ObjectUtils;
import com.dev.com.document.cartitem;
import com.dev.com.document.image;
import com.dev.com.dto.ImageDto;
import com.dev.com.repository.ImageRepository;
import com.dev.com.repository.cartitemRepository;
import com.dev.com.untils.Singleton;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<image> findListImage(String keyword, Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdDate"));
        if (keyword != null) {
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("quanlity").regex(keyword, "i"));
            query.addCriteria(criteria);
        }
        List<image> listDocument = mongoTemplate.find(query, image.class);
        Page<image> page = PageableExecutionUtils.getPage(listDocument, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Image.class));
        return page;

    }

    public image uploadFile(MultipartFile file) {
        try {
            Map<image, Object> uploadResult = Singleton.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            image newCloudinary = new image(url, 0);
            repository.save(newCloudinary);
            return newCloudinary;

        } catch (Exception ex) {
            return null;
        }
    }
    public image findImageId(ObjectId id)
    {
        image newImage=repository.getFindImageId(id);
          if (newImage == null) {
            throw new DigoHttpException(11001, HttpServletResponse.SC_BAD_REQUEST);
        }
        return newImage;
    }
    

}
