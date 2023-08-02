package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class CustomerFurnServlet extends BasicServlet {

    //定义一个FurnService属性
    private FurnService furnService = new FurnServiceImpl();

    /**
     * 这里仍然是一个分页请求家居信息的API/方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //这里的业务逻辑和后台分页显示家居信息非常相似
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //调用service方法, 获取Page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        //将page放入到request域
        req.setAttribute("page", page);
        //请求转发到furn_manage.jsp
        req.getRequestDispatcher("/views/customer/index.jsp")
                .forward(req, resp);
    }

    protected void pageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //这里的业务逻辑和后台分页显示家居信息非常相似
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //老师解读
        //1.如果参数有name 带上没有值, 接收到的是""
        //2.如果name参数都没有, 接收到的null
        //3.也就是说我们把 "" 和 null 业务逻辑合并在一起..
        String name = req.getParameter("name");
        if (null == name) {
            name = "";
        }
        //调用service方法, 获取Page对象
        Page<Furn> page = furnService.pageByName(pageNo, pageSize, name);

        StringBuilder url =
                new StringBuilder("customerFurnServlet?action=pageByName");
        if (!"".equals(name)) {//如name不为"", 则在拼接 name参数
            url.append("&name=").append(name);
        }

        page.setUrl(url.toString());
        //将page放入到request域
        req.setAttribute("page", page);
        //请求转发到furn_manage.jsp
        req.getRequestDispatcher("/views/customer/index.jsp")
                .forward(req, resp);
    }
}
