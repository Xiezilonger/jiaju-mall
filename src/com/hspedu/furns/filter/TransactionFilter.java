package com.hspedu.furns.filter;

import com.hspedu.furns.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

/**
 * 管理事务的过滤器
 * @author 韩顺平
 * @version 1.0
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            //放行.
            filterChain.doFilter(servletRequest,servletResponse);
            JDBCUtilsByDruid.commit();//统一提交, 出现了异常
        } catch (Exception e) {
            //老师解读: 只有在try{} 中出现了异常,才会进行catch{} 执行
            //, 才会进行回滚.
            JDBCUtilsByDruid.rollback();//回滚
            //抛出异常, 给tomcat, tomcat会根据errorpage 来显示对应
            //这里请体会: 异常机制是可以参与业务逻辑
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
