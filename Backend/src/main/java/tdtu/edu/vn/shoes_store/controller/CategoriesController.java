package tdtu.edu.vn.shoes_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.CategoriesDto;
import tdtu.edu.vn.shoes_store.model.Categories;
import tdtu.edu.vn.shoes_store.service.CategoriesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public List<CategoriesDto> getAllCategories() {
        return getListCategories(categoriesService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Object> addCategories(@RequestBody Categories categories) {
        Map<String, Object> result = new HashMap<>();
        Categories existingCategories = categoriesService.findByName(categories.getName());
        if (existingCategories != null) {
            result.put("statusCode", HttpStatus.BAD_REQUEST.value());
            result.put("message", "Category already exists!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        categoriesService.saveCategories(categories);
        result.put("statusCode", HttpStatus.OK.value());
        result.put("message", "Create new category successfully!");
        result.put("content", getCategoriesDtoBody(categories));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public CategoriesDto getCategoriesById(@PathVariable(name = "id") Long id) {
        return getCategoriesDtoBody(categoriesService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoriesById(@PathVariable(name = "id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Categories Categories = categoriesService.findById(id);
        if (Categories != null) {
            categoriesService.deleteCategories(id);
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Delete category successfully!");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Category not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoriesById(@PathVariable(name = "id") Long id,
                                                  @RequestBody Categories categories) {
        Map<String, Object> result = new HashMap<>();
        Categories category = categoriesService.findById(id);
        if (category != null) {
            if(categories.getName() != null) {
                Categories existingCategories = categoriesService.findByName(categories.getName());
                if (existingCategories != null && !existingCategories.getId().equals(id)) {
                    result.put("statusCode", HttpStatus.BAD_REQUEST.value());
                    result.put("message", "Category already exists!");
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
                category.setName(categories.getName());
            }
            categoriesService.updateCategories(category);

            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Update category successfully!");
            result.put("content", getCategoriesDtoBody(category));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Category not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    private CategoriesDto getCategoriesDtoBody(Categories categories) {
        CategoriesDto categoriesDto = new CategoriesDto();
        if(categories.getId() != null) categoriesDto.setId(categories.getId());
        if(categories.getName() != null) categoriesDto.setName(categories.getName());
        return categoriesDto;
    }
    private List<CategoriesDto> getListCategories(List<Categories> categories) {
        List<CategoriesDto> listCategories = new ArrayList<>();
        for (Categories category : categories) {
            CategoriesDto categoriesDto = new CategoriesDto();
            categoriesDto.setId(category.getId());
            categoriesDto.setName(category.getName());
            listCategories.add(categoriesDto);
        }
        return listCategories;
    }
}
