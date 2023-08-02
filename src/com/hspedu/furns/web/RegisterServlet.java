//package com.hspedu.furns.web;
//
//import com.hspedu.furns.entity.Member;
//import com.hspedu.furns.service.MemberService;
//import com.hspedu.furns.service.impl.MemberServiceImpl;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author 韩顺平
// * @version 1.0
// */
//public class RegisterServlet extends HttpServlet {
//
//    //定义一个属性MemberService
//    private MemberService memberService = new MemberServiceImpl();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //接收用户注册信息-> 一定要去看前端页面字段..
//        //用户名
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//
//        //判断这个用户名是不是可用
//        if(!memberService.isExistsUsername(username)) {
//            //注册
//            //System.out.println("用户名 " + username + " 不存在, 可以注册");
//            //构建一个Member对象
//            Member member = new Member(null, username, password, email);
//            if(memberService.registerMember(member)) {
//                //请求转发
//                request.getRequestDispatcher("/views/member/register_ok.jsp")
//                        .forward(request,response);
//            } else  {
//                //请求转发
//                request.getRequestDispatcher("/views/member/register_fail.jsp")
//                        .forward(request,response);
//            }
//
//        } else  {
//            //返回注册页面
//            //后面可以加入提示信息...
//            request.getRequestDispatcher("/views/member/login.jsp")
//                    .forward(request,response);
//        }
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
//}
