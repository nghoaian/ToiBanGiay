package tdtu.edu.vn.shoes_store.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Categories;

import java.util.List;

@Service
public interface CategoriesService {
    void saveCategories(Categories categories);
    void updateCategories(Categories categories);
    void deleteCategories(Long id);
    Categories findById(Long id);
    List<Categories> getAllCategories();

    Categories findByName(String name);
}
