package com.hspedu.furns.filter;

import com.google.gson.Gson;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 这是用于权限验证的过滤器, 对指定的url进行验证
 * 如果登录过, 就放行, 如果没有登录，就回到登录页面
 *
 * @author 韩顺平
 * @version 1.0
 */
public class AuthFilter implements Filter {

    //后面我们把要排除的url放入到excludedUrls
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取到配置excludedUrls
        String strExcludedUrls = filterConfig.getInitParameter("excludedUrls");
        String[] splitUrl = strExcludedUrls.split(",");
        //将 splitUrl 转成 List
        //Java基础常用类 Arrays
        excludedUrls = Arrays.asList(splitUrl);
        System.out.println("excludedUrls=" + excludedUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //得到请求的url
        String url = request.getServletPath();
//        System.out.println("url=" + url);

        //判断是否要验证
        if(!excludedUrls.contains(url)) {

            //得到session中的member对象
            Member member = (Member) request.getSession().getAttribute("member");


            //如果member不为null, 还应当判断该member是不是admin, 在根据获取到的url
            //进行相应的处理, 如果该用户不是admin, 但是他访问了后台, 就转到首页即可
            //前面听过+Java基础=>独立完成==> 自己动脑筋

            //说明该用户没有登录
            if (member == null) {

                //判断是不是Ajax请求
                if(!WebUtils.isAjaxRequest(request)) {//如果不是ajax
                    //转发到登录页面, 转发不走过滤器
                    request.getRequestDispatcher("/views/member/login.jsp")
                            .forward(servletRequest, servletResponse);
                } else {//如果是ajax

                    //返回一个url=> 按照json格式
                    HashMap<String, Object> resultMap = new HashMap<>();

                    resultMap.put("url", "views/member/login.jsp");
                    String resultJson = new Gson().toJson(resultMap);
                    servletResponse.getWriter().write(resultJson);
                }

                //返回
                return;
            }
        }
        //继续访问
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
