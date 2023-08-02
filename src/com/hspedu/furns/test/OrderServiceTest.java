package com.hspedu.furns.test;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class OrderServiceTest {

    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void saveOrder() {

        //构建一个Cart对象
        Cart cart = new Cart();
        cart.addItem(new CartItem(10,"北欧风格小桌子",new BigDecimal(200.00), 2, new BigDecimal(400.00)));
        cart.addItem(new CartItem(2,"简约风格小椅子",new BigDecimal(180.00), 1, new BigDecimal(180.00)));

        //验证3张表是否变化正确.
        System.out.println(orderService.saveOrder(cart, 2));
    }
}
