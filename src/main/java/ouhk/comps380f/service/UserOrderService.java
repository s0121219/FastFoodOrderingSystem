package ouhk.comps380f.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.UserOrderRepository;
import ouhk.comps380f.exception.UserOrderNotFound;
import ouhk.comps380f.model.UserOrder;

@Service
public class UserOrderService {

    @Resource
    private UserOrderRepository userOrderRepo;

    @Transactional
    public List<UserOrder> getUserOrders() {
        return userOrderRepo.findAll();
    }

    @Transactional
    public UserOrder getUserOrder(int id) {
        return userOrderRepo.findById(id).orElse(null);
    }

    @Transactional
    public List<UserOrder> getUserOrderByUsername(String username) {
        List<UserOrder> order_list = new ArrayList<UserOrder>();
        for (UserOrder userOrder : userOrderRepo.findAll()) {
            if (userOrder.getUsername().equals(username)) {
                order_list.add(userOrder);
            }
        }
        return order_list;
    }

    @Transactional(rollbackFor = UserOrderNotFound.class)
    public void deleteUserOrder(int id) throws UserOrderNotFound {
        UserOrder deletedUserOrder = userOrderRepo.findById(id).orElse(null);
        if (deletedUserOrder == null) {
            throw new UserOrderNotFound();
        }
        userOrderRepo.delete(deletedUserOrder);
    }
}
