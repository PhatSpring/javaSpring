/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.service;

import com.dev.com.document.IdDto;
import com.dev.com.document.role;
import com.dev.com.document.userModel;
import com.dev.com.dto.RoleDto;
import com.dev.com.dto.UserDto;
import com.dev.com.repository.roleRepository;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author Admin
 */
@Service
public class roleService {

    @Autowired
    private roleRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<role> findListRole(String keyword, Pageable pageable) {

        Query query = new Query();
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdDate"));
        if (keyword != null) {
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("role_name").regex(keyword, "i"));
            query.addCriteria(criteria);
        }
        List<role> listDocument = mongoTemplate.find(query, role.class);
        Page<role> page = PageableExecutionUtils.getPage(listDocument, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), role.class));
        return page;

    }

    public role findRoleId(ObjectId id) {
        role newRole = repository.getFindRoleId(id);

        if (newRole == null) {
            throw new DigoHttpException(11001, HttpServletResponse.SC_BAD_REQUEST);
        }
        return newRole;
    }

    public IdDto createRole(RoleDto role) {
        IdDto newIdDto = new IdDto();
        role newRole = new role();
        newRole.setRole_name(role.getRole_name());
        newRole.setCreatedDate(new Date());
        role newroleDto = repository.save(newRole);
        newIdDto.setId(newroleDto.getId());
        return newIdDto;
    }
   
}
