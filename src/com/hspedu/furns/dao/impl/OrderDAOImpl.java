package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.web.BasicServlet;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO `order`(`id`,`create_time`,`price`,`status`,`member_id`) " +
                "VALUES(?,?,?,?,?)";
        return update(sql, order.getId(),order.getCreateTime(),
                order.getPrice(),order.getStatus(),order.getMemberId());
    }
}
