package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;

import java.util.List;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class FurnServiceImpl implements FurnService {

    //定义属性 FurnDAO对象
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryFurns();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurnById(int id) {
        return furnDAO.deleteFurnById(id);
    }

    @Override
    public Furn queryFurnById(int id) {
        return furnDAO.queryFurnById(id);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {

        //先创建一个Page对象，然后根据实际情况填充属性
        Page<Furn> page = new Page<>();

        ////因为每页显示多少条记录，是其它地方也可以使用
        ////ctrl+shift+u => 切换大小写
        //public static  final  Integer PAGE_SIZE = 3;
        //
        ////表示显示当前页[显示第几页]
        ////前端页面来的
        //private Integer pageNo;
        ////表示每页显示几条记录
        //private Integer pageSize = PAGE_SIZE;
        ////表示共有多少页, 他是计算得到
        //private Integer pageTotalCount;
        ////表示的是共有多少条记录 , 通过totalRow和pageSize
        ////计算得到pageTotalCount
        ////是可以同数据库DB来的->DAO
        //private Integer totalRow;
        ////表示当前页，要显示的数据
        ////从DB来的->DAO
        //private List<T> items;
        ////分页导航的字符串
        //private String url;
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int totalRow = furnDAO.getTotalRow();
        page.setTotalRow(totalRow);
        //pageTotalCount 是计算得到-> 一个小小的算法
        //老师分析
        //比如 6 2  =》  6 / 2 = 3
        //比如 5 2  =》  5 / 2 = 2
        //验证 7 3 =>
        //验证 0 2 =>
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount += 1;
        }

        page.setPageTotalCount(pageTotalCount);

        //private List<T> items
        //老师开始计算begin-> 小小算法
        //验证: pageNo = 1 pageSize = 3 => begin =0
        //验证: pageNo = 3 pageSize = 2 => begin =4
        //OK => 但是注意这里隐藏一个坑, 现在你看不到, 后面会暴露
        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItems(begin, pageSize);
        page.setItems(pageItems);
        //还差一个url => 分页导航，先放一放
        return page;
    }

    @Override
    public Page<Furn> pageByName(int pageNo, int pageSize, String name) {

        //先创建一个Page对象，然后根据实际情况填充属性
        Page<Furn> page = new Page<>();

        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        //根据名字来返回总的记录数
        int totalRow = furnDAO.getTotalRowByName(name);
        page.setTotalRow(totalRow);

        //计算得到总的页数
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount += 1;
        }

        page.setPageTotalCount(pageTotalCount);

        //private List<T> items
        //老师开始计算begin-> 小小算法
        //验证: pageNo = 1 pageSize = 3 => begin =0
        //验证: pageNo = 3 pageSize = 2 => begin =4
        //OK => 但是注意这里隐藏一个坑, 现在你看不到, 后面会暴露
        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItemsByName(begin, pageSize, name);
        page.setItems(pageItems);
        //还差一个url => 分页导航，先放一放
        return page;
    }
}
