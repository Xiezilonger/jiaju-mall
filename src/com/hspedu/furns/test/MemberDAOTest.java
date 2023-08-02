package com.hspedu.furns.test;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import org.junit.jupiter.api.Test;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class MemberDAOTest {

    private MemberDAO memberDAO = new MemberDAOImpl();

    @Test
    public void queryMemberByUsername() {

        if(memberDAO.queryMemberByUsername("admin") == null) {
            System.out.println("该用户名不存在...");
        } else {
            System.out.println("该用户名存在...");
        }
    }

    @Test
    public void saveMember() {

        Member member =
                new Member(null, "king", "king", "king@sohu.com");
        if(memberDAO.saveMember(member) == 1) {
            System.out.println("添加OK");
        } else {
            System.out.println("添加失败...");
        }
    }

    @Test
    public void queryMemberByUsernameAndPassword() {
        //小技巧:
        Member member = memberDAO.queryMemberByUsernameAndPassword("admin", "admin");
        System.out.println("member= " + member);
    }
}
