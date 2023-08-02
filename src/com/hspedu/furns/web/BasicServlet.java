package com.hspedu.furns.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 韩顺平
 * @version 1.0
 */
public abstract class BasicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //解决接收到的数据中文乱码问题
        req.setCharacterEncoding("utf-8");

        //System.out.println("BasicServlet doPost()");
        //获取到action的值
        //老韩提示：如果我们使用了模板模式+反射+动态绑定，要满足action的value 和 方法名一致!!!
        String action = req.getParameter("action");
        //System.out.println("action=" + action);

        //使用反射,获取当前对象的方法
        //老韩解读
        //1.this 就是请求的Servlet
        //2.declaredMethod 方法对象就是当前请求的servlet对应的"action名字" 的方法, 该方法对象(declaredMethod)
        //  是变化的,根据用户请求
        //3. 老韩的体会：使用模板模式+反射+动态机制===> 简化多个 if--else if---..
        try {
            Method declaredMethod =
                    this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println("declaredMethod=" + declaredMethod);
            //使用方法对象，进行反射调用
            declaredMethod.invoke(this, req, resp);

        } catch (Exception e) {
            //java基础->异常机制
            //将发生的异常,继续throw
            //老师心得体会: 异常机制是可以参与业务逻辑
            //老师把这个问题，暴露出来的，让你们看到. 让小伙伴学到东西.
            throw new RuntimeException(e);

        }

    }

    //在BasicServlet中, 增加处理Get请求

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
