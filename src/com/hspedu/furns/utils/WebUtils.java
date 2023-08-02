package com.hspedu.furns.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class WebUtils {


    //定义一个文件上传的路径
    public static String  FURN_IMG_DIRECTORY = "assets/images/product-image";

    /**
     * 判断请求是不是一个ajax请求=>http知识=> 基础越扎实， 解决问题思路就会开阔
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
