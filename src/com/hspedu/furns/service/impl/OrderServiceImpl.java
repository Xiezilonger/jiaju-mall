package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.dao.impl.OrderDAOImpl;
import com.hspedu.furns.dao.impl.OrderItemDAOImpl;
import com.hspedu.furns.entity.*;
import com.hspedu.furns.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private FurnDAO furnDAO = new FurnDAOImpl();


    //老师说明: 这里同学们应该感受javaee分层的好处 , 在service层, 通过组合多个dao的方法
    //完成某个业务, 慢慢体会好处.

    @Override
    public String saveOrder(Cart cart, int memberId) {

        //这里的业务逻辑相对综合
        //完成任务时将 cart购物车的数据->以order和 orderItem形式保存到db

        //老师说明：因为生成订单会操作多表，因此会涉及到多表事务的问题 ThreadLocal+Mysql事务机制+过滤器
        //关于事务的处理，考虑的点比较多，老师后面专门处理

        //1. 通过cart对象, 构建对应的Order对象
        //先生成一个UUID, 表示当前的订单号, 订单号要保证是唯一
        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order =
                new Order(orderId, new Date(), cart.getCartTotalPrice(), 0, memberId);
        //保存order到数据表.
        orderDAO.saveOrder(order);

//        2.通过cart对象 ,遍历出CartItem, 构建OrderItem对象， 并保存到对应的表order_item

        HashMap<Integer, CartItem> items = cart.getItems();
        // java基础遍历hashmap
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem item = items.get(id);
            //通过cartItem对象构建了OrderItem
            OrderItem orderItem = new OrderItem(null,item.getName(),item.getPrice(),
                    item.getCount(),item.getTotalPrice(),orderId);

            //保存
            orderItemDAO.saveOrderItem(orderItem);

            //更新一把furn表的sales销量, stock存量
            //(1) 获取到furn对象
            Furn furn = furnDAO.queryFurnById(id);
            //(2) 更新一下这个furn的sales销量, stock存量
            furn.setSales(furn.getSales() + item.getCount());
            furn.setStock(furn.getStock() - item.getCount());
            //(3) 更新到数据表
            furnDAO.updateFurn(furn);

        }

        //=======通过entryset的方式遍历items 取出CartItem===

//        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
//            CartItem item = entry.getValue();
//            //通过cartItem对象构建了OrderItem
//            OrderItem orderItem = new OrderItem(null, item.getName(), item.getPrice(),
//                    item.getCount(), item.getTotalPrice(), orderId);
//
//            //保存
//            orderItemDAO.saveOrderItem(orderItem);
//
//            //更新一把furn表的sales销量, stock存量
//            //(1) 获取到furn对象
//            Furn furn = furnDAO.queryFurnById(item.getId());
//            //(2) 更新一下这个furn的sales销量, stock存量
//            furn.setSales(furn.getSales() + item.getCount());
//            furn.setStock(furn.getStock() - item.getCount());
//            //(3) 更新到数据表
//            furnDAO.updateFurn(furn);
//
//        }


        //清空购物车
        cart.clear();
        return orderId;
    }
}
