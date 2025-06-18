package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.model.Category;
import com.dailycodework.buynowdotcom.response.ApiResponse;
import com.dailycodework.buynowdotcom.service.category.ICategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error: ",e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category theCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("success",theCategory));
        } catch (EntityExistsException e) {
            return ResponseEntity.status
                    (CONFLICT).body(new ApiResponse("Error: ",e.getMessage()));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("id") Long categoryId){
        try {
            Category theCategory = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("success",theCategory));
        } catch (EntityExistsException e) {
            return ResponseEntity.status
                    (NOT_FOUND).body(new ApiResponse("Error: ",e.getMessage()));
        }
    }

    @DeleteMapping("/category/{categoryId}/category")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("Deleted SuccessFully!!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status
                    (NOT_FOUND).body(new ApiResponse("Error: ",e.getMessage()));
        }
    }

    @GetMapping("/category/{name}/getByName")
    public ResponseEntity<ApiResponse> findCategoryByName(@PathVariable("name") String categoryName){
        try {
            Category theCategory = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("success",theCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status
                    (NOT_FOUND).body(new ApiResponse("Error: ",e.getMessage()));
        }
    }


    @PutMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(Category category, @PathVariable("id") Long categoryId){
        try {
            Category theCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("success",theCategory));
        } catch (EntityExistsException e) {
            return ResponseEntity.status
                    (NOT_FOUND).body(new ApiResponse("Error: ",e.getMessage()));
        }
    }

}
