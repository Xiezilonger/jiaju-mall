package com.hspedu.furns.entity;

import java.util.List;

/**
 * Page是一个Javabean 是一个分页的数据模型(包含了分页的各种信息)
 * @author 韩顺平
 * @version 1.0
 */
//T表示泛型，因为将来分页模型对应的数据类型是不确定的
public class Page<T> {

    //因为每页显示多少条记录，是其它地方也可以使用
    //ctrl+shift+u => 切换大小写
    public static  final  Integer PAGE_SIZE = 4;

    //表示显示当前页[显示第几页]
    //前端页面来的
    private Integer pageNo;
    //表示每页显示几条记录
    private Integer pageSize = PAGE_SIZE;
    //表示共有多少页, 他是计算得到
    private Integer pageTotalCount;
    //表示的是共有多少条记录 , 通过totalRow和pageSize
    //计算得到pageTotalCount
    //是可以同数据库DB来的->DAO
    private Integer totalRow;
    //表示当前页，要显示的数据
    //从DB来的->DAO
    private List<T> items;
    //分页导航的字符串
    private String url;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
