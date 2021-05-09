package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

}
