package com.hspedu.furns.test;

import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.OrderItemDAOImpl;
import com.hspedu.furns.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class OrderItemDAOTest {

    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Test
    public void saveOrderItem() {
        //北欧小沙发',200,2,400,'sn00002'
        OrderItem orderItem = new OrderItem(null, "北欧小沙发", new BigDecimal(200), 3,
                new BigDecimal(600), "sn00002");
        System.out.println(orderItemDAO.saveOrderItem(orderItem));

    }
}
