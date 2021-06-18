/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.controller;

import com.dev.com.document.IdDto;
import com.dev.com.document.category;
import com.dev.com.document.role;
import com.dev.com.dto.AffectedRowsDto;
import com.dev.com.service.categoryService;
import com.dev.com.service.roleService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> getAllClasses(@RequestParam(required = false) String keyword, Pageable pageable) {
        Page<category> res = categoryService.findListCategory(keyword, pageable);
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<Object> postCategory(HttpServletRequest request,
            @RequestBody category category) {
        IdDto idCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(idCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(value = "id", required = true) ObjectId id) {
        category newCategory = categoryService.findCategoryId(id);
        return ResponseEntity.ok(newCategory);
    }
    //ham get theo ten
//    @GetMapping("/category_name")
//   public ResponseEntity<Object> getCategoryByCategoryName(@PathVariable(value = "category_name", required = true) String category_name) {
//        category newCategory = categoryService.findCategoryByName(category_name);
//        return ResponseEntity.ok(newCategory);
//    }
     

    @PutMapping("/{id}")
    public ResponseEntity<Object> putCategory(HttpServletRequest request,
            @PathVariable(value = "id", required = true) ObjectId id,
            @RequestBody category category) {
        AffectedRowsDto idCategory = categoryService.putCategory(id, category);
        return ResponseEntity.ok(idCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(HttpServletRequest request,
            @PathVariable(value = "id", required = true) ObjectId id) {
        AffectedRowsDto idAffectedRowsDto = categoryService.deleteOne(id);
        return ResponseEntity.ok(idAffectedRowsDto);
    }

    @DeleteMapping("/all-category")
    public ResponseEntity<Object> deleteAllCategory(HttpServletRequest request,
            @RequestParam(value = "id", required = true) List<ObjectId> listId) {
        AffectedRowsDto idAffectedRowsDto = categoryService.deleteAll(listId);
        return ResponseEntity.ok(idAffectedRowsDto);
    }
}
