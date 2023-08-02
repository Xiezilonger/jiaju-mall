package com.hspedu.furns.service;

import com.hspedu.furns.entity.Cart;

/**
 * @author 韩顺平
 * @version 1.0
 */
public interface OrderService {

    //分析
    //1. 生成订单
    //2. 订单是根据cart来生成, cart对象在session,通过web层，传入saveOrder
    //3. 订单是和一个会员关联
    public String saveOrder(Cart cart, int memberId);
}
