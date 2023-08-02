package com.hspedu.furns.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Set;

/**
 * Cart 就是购物车, 包含多个CartItem
 *
 * @author 韩顺平
 * @version 1.0
 */
public class Cart {

    //定义属性
    //包含多个CartItem, 使用HashMap来保存
    private HashMap<Integer, CartItem> items = new HashMap<>();


    public boolean isEmpty() {
        return  items.size() == 0;
    }

    public void clear() {
        items.clear();
    }

    /**
     * 根据传入的id ,删除指定的购物车项
     *
     * @param id
     */
    public void delItem(int id) {
        items.remove(id);
    }

    /**
     * 修改指定的CartItem的数量和总价, 根据传入的id 和 count
     *
     * @param id
     * @param count
     */
    public void updateCount(int id, int count) {

        CartItem item = items.get(id);
        if (item != null){
            item.setCount(count);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }

    public HashMap<Integer, CartItem> getItems() {
        return items;
    }


    /**
     * 返回购物车的总价
     *
     * @return
     */
    public BigDecimal getCartTotalPrice() {

        BigDecimal cartTotalPrice = new BigDecimal(0);
        //遍历我们的items
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem item = items.get(id);
            //老师提醒, 一定要包add后的值, 重新赋给 cartTotalPrice, 这样才是累加.
            cartTotalPrice = cartTotalPrice.add(item.getTotalPrice());
        }

        return cartTotalPrice;
    }

    public int getTotalCount() {
        //购物车商品的总数量
        int totalCount = 0;
        //遍历items 统计totalCount
        //java 基础 -> 如何遍历HashMap  集合
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            totalCount += ((CartItem) items.get(id)).getCount();
        }
        return totalCount;
    }

    //添加家居[CartItem]到Cart
    public void addItem(CartItem cartItem) {

        //画一个 内存图帮助理解
        CartItem item = items.get(cartItem.getId());
        if (null == item) {//说明当前购物车，还没有这个/cartitem
            items.put(cartItem.getId(), cartItem);
        } else {//购物车有这个cartItem
            item.setCount(item.getCount() + 1);//数量增加1
            //修改总价
            //item.getPrice()=> BigDecimal * Integer
            //java基础
            //item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
            item.setTotalPrice(item.getTotalPrice().add(item.getPrice()));
        }

    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
