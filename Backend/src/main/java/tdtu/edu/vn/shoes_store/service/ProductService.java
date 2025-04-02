package tdtu.edu.vn.shoes_store.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Product;

import java.util.List;

@Service
public interface ProductService {
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();

    List<Product> getAllProductByBrands(Long id);

    List<Product> getAllProductByCategories(Long id);

    List<Product> getAllProductByBrandsAndCategories(Long brand, Long category);
}
