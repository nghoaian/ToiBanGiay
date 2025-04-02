package tdtu.edu.vn.shoes_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdtu.edu.vn.shoes_store.model.Brands;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long> {
    @Query("SELECT b FROM Brands b WHERE b.name = :name")
    Brands findByName(@Param("name") String name);
}
