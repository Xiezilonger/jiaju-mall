package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Order;

/**
 * @author 韩顺平
 * @version 1.0
 */
public interface OrderDAO {

    /**
     * 将传入的order对象保存到数据表order表
     * @param order
     * @return
     */
    public int saveOrder(Order order);
}
