package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Admin;
import com.hspedu.furns.entity.Member;

public interface AdminDAO {
    //提供一个通过用户名返回对应的admin
    public Admin queryAdminByUsername(String username);
}
