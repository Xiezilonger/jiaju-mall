package com.hspedu.furns.test;

import com.hspedu.furns.dao.AdminDAO;
import com.hspedu.furns.dao.impl.AdminDAOImpl;
import org.junit.jupiter.api.Test;

public class AdminDAOTest {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Test
    public void queryAdminByUsername() {
        if (adminDAO.queryAdminByUsername("zilong") == null) {
            System.out.println("no");
        } else {
            System.out.println( " ok");
        }
    }

}
