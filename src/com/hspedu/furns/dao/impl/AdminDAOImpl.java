package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.AdminDAO;
import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.entity.Admin;

public class AdminDAOImpl extends BasicDAO<Admin> implements AdminDAO {
    @Override
    public Admin queryAdminByUsername(String username) {
        String sql = "select `id`,`username`,`password` from manager where username = ?";
        return querySingle(sql,Admin.class,username);
    }
}
