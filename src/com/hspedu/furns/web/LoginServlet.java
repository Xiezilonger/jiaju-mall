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
//public class LoginServlet extends HttpServlet {
//    //定义一个MemberService属性
//    private MemberService memberService = new MemberServiceImpl();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //System.out.println("LoginServlet 被调用..");
//        //老韩说明:如果在登录页面, 用户没有输入内容，就直接提交，后台接收到的是""
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        Member member = memberService.login(new Member(null, username, password, null));
//        if (member == null) { //用户没有在DB
//            //System.out.println(member + " 登录失败...");
//            //把登录错误信息,放入到request域 => 如果忘了，可以看servlet / jsp
//            request.setAttribute("msg", "用户名或者密码错误");
//            request.setAttribute("username", username);
//            //页面转发
//            request.getRequestDispatcher("/views/member/login.jsp")
//                    .forward(request, response);
//        } else { //用户在DB
//            //System.out.println(member + " 登录成功~...");
//            request.getRequestDispatcher("/views/member/login_ok.jsp")
//                    .forward(request, response);
//        }
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
//}
