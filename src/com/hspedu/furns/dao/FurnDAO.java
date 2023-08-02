package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Furn;

import java.util.List;

/**
 * @author 韩顺平
 * @version 1.0
 */
public interface FurnDAO {

    /**
     * 返回所有的家居信息集合: 后面我们再考虑分页
     * @return
     */
    public List<Furn> queryFurns();

    /**
     * 将传入的Furn对象，保存到DB
     * @param furn
     * @return
     */
    public int addFurn(Furn furn);

    /**
     * 根据传入id, 删除DB中对应的家居
     * @param id
     * @return 是受影响的行数
     */
    public int deleteFurnById(int id);

    /**
     * 根据id, 返回对应的furn
     * @param id
     * @return
     */
    public Furn queryFurnById(int id);

    /**
     * 将传入的furn对象，更新到数据库, 根据id
     * @param furn
     * @return
     */
    public int updateFurn(Furn furn);

    //老师分析：Page的哪些属性是可以直接从数据库中获取
    //就把这个填充任务防在DAO层.
    public int getTotalRow();

    // 获取当前页要显示的数据
    // begin : 表示从第几条记录开始获取， 从0开始计算
    // pageSize : 表示取出多少条记录
    // Mysql基础..
    public List<Furn> getPageItems(int begin, int pageSize);

    /**
     * 根据名字来获取总记录数
     * @param name
     * @return
     */
    public int getTotalRowByName(String name);

    /**
     * 根据begin, pageSize, 和name来获取要显示的家居信息
     * @param begin
     * @param pageSize
     * @param name
     * @return
     */
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name);
}
