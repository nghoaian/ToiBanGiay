package tdtu.edu.vn.shoes_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Categories;
import tdtu.edu.vn.shoes_store.repository.CategoriesRepository;

import java.util.List;

@Service
@Transient
public class CategoriesServiceImpl implements CategoriesService{
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public void saveCategories(Categories categories) {
        categoriesRepository.save(categories);
    }

    @Override
    public void updateCategories(Categories categories) {
        categoriesRepository.saveAndFlush(categories);
    }

    @Override
    public void deleteCategories(Long id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    public Categories findById(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories findByName(String name) {
        return categoriesRepository.findByName(name);
    }
}
