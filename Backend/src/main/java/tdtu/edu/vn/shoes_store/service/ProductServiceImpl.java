package tdtu.edu.vn.shoes_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Product;
import tdtu.edu.vn.shoes_store.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transient
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductByBrands(Long id) {
        List<Product> productsByBrands = new ArrayList<>();
        for (Product product : this.getAllProducts()) {
            if (product.getBrands().getId().equals(id)) {
                productsByBrands.add(product);
            }
        }

        return productsByBrands;
    }

    @Override
    public List<Product> getAllProductByCategories(Long id) {
        List<Product> productsByCategories = new ArrayList<>();
        for (Product product : this.getAllProducts()) {
            if (product.getCategories().getId().equals(id)) {
                productsByCategories.add(product);
            }
        }
        return productsByCategories;
    }

    @Override
    public List<Product> getAllProductByBrandsAndCategories(Long brand, Long category) {
        return this.getAllProductByBrands(brand)
                .stream().filter(product -> product.getCategories().getId().equals(category))
                .toList();
    }
}
