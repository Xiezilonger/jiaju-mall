package com.hspedu.furns.service;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;

import java.util.List;

/**
 * @author 韩顺平
 * @version 1.0
 */
public interface FurnService {

    /**
     * 返回家居信息
     * @return
     */
    public List<Furn> queryFurns();

    /**
     * 添加Furn对象到数据库
     * @param furn
     */
    public int addFurn(Furn furn);

    /**
     * 根据id 删除家居信息
     * @param id
     * @return
     */
    public int deleteFurnById(int id);

    /**
     * 根据id,返回家居信息
     * @param id
     * @return
     */
    public Furn queryFurnById(int id);

    /**
     * 根据传入的furn对象, 进行修改
     * @param furn
     * @return
     */
    public int updateFurn(Furn furn);

    /**
     * 根据传入的pageNo和 pageSize , 返回对应的page对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Furn> page(int pageNo, int pageSize);

    /**
     * 根据传入的pageNo和 pageSize 和Name, 返回对应的page对象
     * @param pageNo
     * @param pageSize
     * @param name
     * @return
     */
    public Page<Furn> pageByName(int pageNo, int pageSize, String name);
}
