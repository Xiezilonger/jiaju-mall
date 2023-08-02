package com.hspedu.furns.web;

import com.google.gson.Gson;
import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class CartServlet extends BasicServlet {

    //增加一个属性
    private FurnService furnService = new FurnServiceImpl();


    /**
     * 清空购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取session中的购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.clear();
        }

        //返回清空购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    protected void delItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到cart购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.delItem(id);
        }
        //返回到请求删除购物车的页面
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 更新某个CartItem的数量, 即更新购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int id = DataUtils.parseInt(req.getParameter("id"), 0);
//        //这里可以根据业务来处理
//        int count = DataUtils.parseInt(req.getParameter("count"), 1);
//
//        //获取session中购物车
//        Cart cart = (Cart) req.getSession().getAttribute("cart");
//        if (null != cart) {
//            cart.updateCount(id, count);
//        }
//        //回到请求更新购物车的页面
//        resp.sendRedirect(req.getHeader("Referer"));

        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        int count = DataUtils.parseInt(req.getParameter("count"), 1);

        Cart cart =(Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            cart.updateCount(id,count);
        }

        resp.sendRedirect(req.getHeader("Referer"));


    }


    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        

        //得到添加家居的id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到到id对应的Furn
        Furn furn = furnService.queryFurnById(id);

        //判断
        //老师，先把正常的逻辑走完，再处理异常情况
        //todo

        //根据furn 构建 CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        //从session中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (null == cart) {//说明当前用户的session没有cart
            cart = new Cart();//创建了一个cart对象
            req.getSession().setAttribute("cart", cart);
        }

        //将cartItem 加入到cart
        cart.addItem(item);


        //添加完毕后，返回json数据, 给前端
        //前端得到json数据后，进行局部刷新即可
        //老师思路
        //1. 规定json格式 {"cartTotalCount", 3}
        //2. 创建Map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cartTotalCount", cart.getTotalCount());
        //3. 转成json
        String resultJson = new Gson().toJson(resultMap);
        //4. 返回
        resp.getWriter().write(resultJson);


    }

    //增加一个添加家居数据到购物车的方法

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //得到添加家居的id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到到id对应的Furn
        Furn furn = furnService.queryFurnById(id);

        //判断
        //老师，先把正常的逻辑走完，再处理异常情况
        //todo

        //根据furn 构建 CartItem
        CartItem item =
                new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        //从session中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (null == cart) {//说明当前用户的session没有cart
            cart = new Cart();//创建了一个cart对象
            req.getSession().setAttribute("cart", cart);
        }

        //将cartItem 加入到cart
        cart.addItem(item);
        System.out.println("cart=" + cart);

        //添加完毕后，需要返回到添加家居的页面
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);

    }
}
