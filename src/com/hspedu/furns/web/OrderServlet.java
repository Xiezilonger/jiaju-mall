package com.hspedu.furns.web;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import com.hspedu.furns.utils.JDBCUtilsByDruid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class OrderServlet extends BasicServlet {


    //定义属性
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //业务代码
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //如果cart == null , 说明会员没有购买任何家居, 转发到首页
        //这里我们需要补充一个逻辑, 购物车在sessoin, 但是没有家居数据
        if (null == cart || cart.isEmpty()) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        //获取当前登录的memberId
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {//说明用户没有登录, 转发到登录页面

            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            return;//直接返回
        }
        //就可以生成订单
        //老韩分析
        //1. 如果我们只是希望对orderService.saveOrder()进行事务控制
        //2. 我们可以不使用过滤器, 我们直接在这个位置，进行提交和回滚即可
        //String orderId = null;
        //try {
        //    orderId = orderService.saveOrder(cart, member.getId());
        //    JDBCUtilsByDruid.commit();//提交
        //} catch (Exception e) {
        //    JDBCUtilsByDruid.rollback();
        //    e.printStackTrace();
        //}
        String orderId = orderService.saveOrder(cart, member.getId());
        req.getSession().setAttribute("orderId", orderId);

        //使用重定向方式到 checkout.jsp
        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");
    }
}
