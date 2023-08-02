package com.hspedu.furns.test;

import com.hspedu.furns.service.AdminService;
import com.hspedu.furns.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;

public class AdminServiceTest {
    private AdminService adminService = new AdminServiceImpl();

    @Test
    public void isExistsAdmin(){
        if (adminService.isExistsUsername("zilong")){
            System.out.println("ok");


        }else {
            System.out.println("no");

        }
    }
}
