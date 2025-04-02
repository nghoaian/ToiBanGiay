package tdtu.edu.vn.shoes_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdtu.edu.vn.shoes_store.model.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query("SELECT c FROM Categories c WHERE c.name = :name")
    Categories findByName(@Param("name") String name);
}
