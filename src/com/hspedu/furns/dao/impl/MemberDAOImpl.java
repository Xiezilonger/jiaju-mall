package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.entity.Member;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO {

    /**
     * 通过用户名返回对应的Member
     *
     * @param username 用户名
     * @return 对应的Member, 如果没有该Member, 返回 null
     */
    @Override
    public Member queryMemberByUsername(String username) {
        //老师提示，sql 先在sqlyog 测试，然后再拿到程序中
        //这里可以提高我们的开发效率，减少不必要的bug
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member` " +
                " WHERE `username`=?";
        return querySingle(sql, Member.class, username);
    }

    /**
     * 保存一个会员
     *
     * @param member 传入Member对象
     * @return 返回-1 就是失败，返回其它的数字就是受影响的行数
     */
    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO `member`(`username`,`password`,`email`) " +
                " VALUES(?,MD5(?), ?)";
        return update(sql, member.getUsername(),
                member.getPassword(), member.getEmail());
    }


    @Override
    public Member queryMemberByUsernameAndPassword(String username, String password) {

        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member` " +
                " WHERE `username`=? and `password`=md5(?)";
        return querySingle(sql, Member.class, username, password);
    }


}
