package tdtu.edu.vn.shoes_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.BrandsDto;
import tdtu.edu.vn.shoes_store.model.Brands;
import tdtu.edu.vn.shoes_store.service.BrandsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
    @Autowired
    private BrandsService brandsService;

    @GetMapping
    public List<BrandsDto> getAllBrands() {
        return getListBrands(brandsService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<Object> addBrands(@RequestBody Brands brands) {
        Map<String, Object> result = new HashMap<>();
        Brands existingBrand = brandsService.findByName(brands.getName());
        if (existingBrand != null) {
            result.put("statusCode", HttpStatus.BAD_REQUEST.value());
            result.put("message", "Brand already exists!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        brandsService.saveBrands(brands);
        result.put("statusCode", HttpStatus.OK.value());
        result.put("message", "Create new brand successfully!");
        result.put("content", getBrandsDtoBody(brands));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public BrandsDto getBrandById(@PathVariable(name = "id") Long id) {
        return getBrandsDtoBody(brandsService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBrandById(@PathVariable(name = "id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Brands brand = brandsService.findById(id);
        if (brand != null) {
            brandsService.deleteBrands(id);
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Delete brand successfully!");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Brand not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBrandById(@PathVariable(name = "id") Long id,
                                                  @RequestBody Brands brands) {
        Map<String, Object> result = new HashMap<>();
        Brands brand = brandsService.findById(id);
        if (brand != null) {
            if(brands.getName() != null) {
                Brands existingBrand = brandsService.findByName(brands.getName());
                if (existingBrand != null && !existingBrand.getId(). equals(id)) {
                    result.put("statusCode", HttpStatus.BAD_REQUEST.value());
                    result.put("message", "Brand already exists!");
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
                brand.setName(brands.getName());
            }
            brandsService.updateBrands(brand);

            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Update brand successfully!");
            result.put("content", getBrandsDtoBody(brand));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Brand not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    private BrandsDto getBrandsDtoBody(Brands brands) {
        BrandsDto brandsDto = new BrandsDto();
        if(brands.getId() != null) brandsDto.setId(brands.getId());
        if(brands.getName() != null) brandsDto.setName(brands.getName());
        return brandsDto;
    }
    private List<BrandsDto> getListBrands(List<Brands> brands) {
        List<BrandsDto> listBrands = new ArrayList<>();
        for (Brands brand : brands) {
            BrandsDto brandDto = new BrandsDto();
            brandDto.setId(brand.getId());
            brandDto.setName(brand.getName());
            listBrands.add(brandDto);
        }
        return listBrands;
    }
}
