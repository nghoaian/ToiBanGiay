package tdtu.edu.vn.shoes_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.edu.vn.shoes_store.model.OrderDetail;
import tdtu.edu.vn.shoes_store.model.User;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
