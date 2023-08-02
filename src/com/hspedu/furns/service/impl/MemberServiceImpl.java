package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class MemberServiceImpl implements MemberService {
    //定义一个MemberDao属性
    private MemberDAO memberDAO = new MemberDAOImpl();

    @Override
    public boolean registerMember(Member member) {

        return memberDAO.saveMember(member) == 1 ? true : false;

    }

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 如果存在返回true, 否则返回false
     */
    @Override
    public boolean isExistsUsername(String username) {

        //老韩的小技巧
        //如果看某个方法 ctrl + b  => 定位到memberDAO 遍历类型的方法
        //如果使用 ctrl+alt+ b=> 实现类的方法
        //如果有多个类，实现了该方法 会弹出选择的对话框.
        return memberDAO.queryMemberByUsername(username) == null ? false : true;

    }

    @Override
    public Member login(Member member) {
        //返回对象
        return memberDAO.queryMemberByUsernameAndPassword
                (member.getUsername(), member.getPassword());
    }
}
