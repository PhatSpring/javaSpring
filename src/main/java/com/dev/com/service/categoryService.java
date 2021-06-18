/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.service;

import com.dev.com.document.IdDto;
import com.dev.com.document.category;
import com.dev.com.document.role;
import com.dev.com.dto.AffectedRowsDto;
import com.dev.com.repository.categoryRepository;
import com.dev.com.repository.roleRepository;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class categoryService {

    @Autowired
    private categoryRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<category> findListCategory(String keyword, Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdDate"));
        if (keyword != null) {
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("category_name").regex(keyword, "i"));
            query.addCriteria(criteria);
        }
        List<category> listDocument = mongoTemplate.find(query, category.class);
        Page<category> page = PageableExecutionUtils.getPage(listDocument, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), category.class));
        return page;

    }

    public IdDto createCategory(category category) {
        IdDto newIdDto = new IdDto();
        category newCategory = new category();
        newCategory.setCategory_name(category.getCategory_name());
        newCategory.setCreatedDate(new Date());
        category newCategoryDto = repository.save(newCategory);
        newIdDto.setId(newCategory.getId());
        return newIdDto;
    }

    public category findCategoryId(ObjectId id) {
        category newCategory = repository.findById(id).orElse(null);
        if (newCategory == null) {
            throw new DigoHttpException(11001, HttpServletResponse.SC_BAD_REQUEST);
        }
        return newCategory;
    }
    //hàm tìm ki?m tìm ki?m theo tên
//    public category findCategoryByName(String categoryname)
//    {
//        Example<category> example = Example.of(new category(categoryname));
//         category newcategory=(category) repository.findAll(example);
//        return newcategory;
//    }
   

    public AffectedRowsDto putCategory(ObjectId id, category category) {
        AffectedRowsDto newAffectedRowsDto = new AffectedRowsDto();
        try {
            category oldCategory = repository.findById(id).orElse(null);
            if (Objects.nonNull(category.getCategory_name())) {
                oldCategory.setCategory_name(category.getCategory_name());
            }
            newAffectedRowsDto.setAffectedRows(1);
            repository.save(oldCategory);
        } catch (Exception e) {
        }
        return newAffectedRowsDto;
    }

    public AffectedRowsDto deleteOne(ObjectId id) {
        AffectedRowsDto newAffectedRowsDto = new AffectedRowsDto();
        try {
            category oldCategory = repository.findById(id).orElse(null);
            if (oldCategory != null && oldCategory.getUseCount() < 1) {
                repository.deleteById(id);
                newAffectedRowsDto.setAffectedRows(1);
            }
        } catch (Exception e) {
            throw new DigoHttpException(11001, HttpServletResponse.SC_NOT_FOUND);
        }
        return newAffectedRowsDto;
    }

    public AffectedRowsDto deleteAll(List<ObjectId> ListId) {
        AffectedRowsDto affectedRows = new AffectedRowsDto();
        try {
            if (Objects.nonNull(ListId) && ListId.size() > 0) {
                for (ObjectId objectId : ListId) {
                    category oldCategory = repository.findById(objectId).orElse(null);
                    if (oldCategory != null) {
                        repository.deleteById(objectId);
                        affectedRows.setAffectedRows(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new DigoHttpException(11001, HttpServletResponse.SC_NOT_FOUND);
        }
        return affectedRows;
    }

}
