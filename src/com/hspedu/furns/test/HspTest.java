package com.hspedu.furns.test;

import java.util.HashMap;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class HspTest {
    public static void main(String[] args) {
        //老韩给大家推荐的一个实用编程小技巧=> 不要小看它
        HashMap<Object, Object> objectObjectHashMap =
                new HashMap<>();
        objectObjectHashMap.put("k1", "v1");
        System.out.println(objectObjectHashMap);
        objectObjectHashMap.clear(); //objectObjectHashMap = null
        System.out.println(objectObjectHashMap.size() == 0);
    }
}
