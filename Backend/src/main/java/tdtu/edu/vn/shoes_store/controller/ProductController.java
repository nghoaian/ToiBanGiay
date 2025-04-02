package tdtu.edu.vn.shoes_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.ProductDto;
import tdtu.edu.vn.shoes_store.model.Product;
import tdtu.edu.vn.shoes_store.service.BrandsService;
import tdtu.edu.vn.shoes_store.service.CategoriesService;
import tdtu.edu.vn.shoes_store.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private BrandsService brandsService;;

    @Autowired
    private CategoriesService categoriesService;


    @GetMapping
    public List<ProductDto> getAllProduct() {
        return getListProduct(productService.getAllProducts());
    }


    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody ProductDto productDto) {
        Map<String, Object> result = new HashMap<>();
        Product product = getProductBody(productDto);
        
        productService.saveProduct(product);
        result.put("statusCode", HttpStatus.OK.value());
        result.put("message", "Create new product successfully!");
        result.put("content", getProductDtoBody(product));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable(name = "id") Long id) {
        return getProductDtoBody(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(name = "id") Long id) {
        Map<String, Object> result = new HashMap<>();
        Product product = productService.getProductById(id);
        if(product != null) {
            productService.deleteProduct(product.getId());
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Delete product successfully!");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else  {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Product not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "id") Long id,
                                                @RequestBody ProductDto productDto) {
        Map<String, Object> result = new HashMap<>();
        Product product = productService.getProductById(id);
        if(product != null) {
            product = getProductBody(productDto);
            product.setId(id);
            productService.updateProduct(product);
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Update product successfully!");
            result.put("content", getProductDtoBody(product));
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND);
            result.put("message", "Product not found!");
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getByBrands")
    public List<ProductDto> getAllProductByBrands(@RequestParam Long id) {
        return getListProduct(productService.getAllProductByBrands(id));
    }

    @GetMapping("/getByCategories")
    public List<ProductDto> getAllProductByCategories(@RequestParam Long id) {
        return getListProduct(productService.getAllProductByCategories(id));
    }

     private Product getProductBody(ProductDto productDto) {
        Product product = new Product();

        if(productDto.getName() != null) product.setName(productDto.getName());
        if(productDto.getDescription() != null) product.setDescription(productDto.getDescription());
        if(productDto.getImage() != null) product.setImage(productDto.getImage());
        if(productDto.getBrands() != null) {
            product.setBrands(brandsService.findById(productDto.getBrands()));
        }
        if(productDto.getCategories() != null) {
            product.setCategories(categoriesService.findById(productDto.getCategories()));
        }

        if(productDto.getSize() != null) product.setSize(productDto.getSize());
        if(productDto.getRelatedProducts() != null) product.setRelatedProducts(productDto.getRelatedProducts());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return product;
    }

    private ProductDto getProductDtoBody(Product product) {
        ProductDto productDto = new ProductDto();

        if (product.getId() != null) productDto.setId(product.getId());
        if(product.getName() != null) productDto.setName(product.getName());
        if(product.getDescription() != null) productDto.setDescription(product.getDescription());
        if(product.getImage() != null) productDto.setImage(product.getImage());
        if(product.getBrands() != null) productDto.setBrands(product.getBrands().getId());
        if(product.getCategories() != null) productDto.setCategories(product.getCategories().getId());
        if(product.getSize() != null) productDto.setSize(product.getSize());
        if(product.getRelatedProducts() != null) productDto.setRelatedProducts(product.getRelatedProducts());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());

        return productDto;
    }

    private List<ProductDto> getListProduct(List<Product> products) {
        List<ProductDto> listProduct = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = getProductDtoBody(product);
            listProduct.add(productDto);
        }
        return listProduct;
    }
}
