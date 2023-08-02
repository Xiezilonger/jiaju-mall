package com.hspedu.furns.test;

import com.hspedu.furns.utils.JDBCUtilsByDruid;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class JDBCUtilsByDruidTest {

    @Test
    public void getConnection() throws SQLException {

        //如果这里你看不懂, 回去看mysql ,jdbc, 数据库连接池 , 满汉楼
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println("connection= " + connection);
        JDBCUtilsByDruid.close(null, null, connection);

    }
}
