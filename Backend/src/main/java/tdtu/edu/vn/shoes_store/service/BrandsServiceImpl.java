package tdtu.edu.vn.shoes_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.model.Brands;
import tdtu.edu.vn.shoes_store.repository.BrandsRepository;

import java.util.List;

@Service
@Transient
public class BrandsServiceImpl implements BrandsService {
    @Autowired
    private BrandsRepository brandsRepository;

    @Override
    public void saveBrands(Brands brands) {
        brandsRepository.save(brands);
    }

    @Override
    public void updateBrands(Brands brands) {
        brandsRepository.saveAndFlush(brands);
    }

    @Override
    public void deleteBrands(Long id) {
        brandsRepository.deleteById(id);
    }

    @Override
    public Brands findById(Long id) {
        return brandsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsRepository.findAll();
    }

    @Override
    public Brands findByName(String name) {
        return brandsRepository.findByName(name);
    }
}
