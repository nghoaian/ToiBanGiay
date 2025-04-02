package tdtu.edu.vn.shoes_store.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Brands;

import java.util.List;

@Service
public interface BrandsService {
    void saveBrands(Brands brands);
    void updateBrands(Brands brands);
    void deleteBrands(Long id);
    Brands findById(Long id);
    List<Brands> getAllBrands();
    Brands findByName(String name);
}
