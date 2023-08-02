package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.entity.Furn;

import java.util.List;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {

    @Override
    public List<Furn> queryFurns() {
        String sql = "SELECT `id`, `name` , `maker`, `price`, `sales`, `stock`, `img_path` imgPath FROM furn";
        return queryMulti(sql, Furn.class);
    }

    @Override
    public int addFurn(Furn furn) {
        //把sql先在SQLYOG测试
        String sql = "INSERT INTO furn(`id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path`) " +
                "VALUES(NULL , ? , ? , ? , ? , ? , ?)";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurnById(int id) {
        String sql = "DELETE FROM `furn` WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(int id) {
        String sql = "SELECT `id`, `name` , `maker`, `price`, `sales`, `stock`, `img_path` imgPath FROM furn WHERE id = ?";
        return querySingle(sql, Furn.class, id);
    }

    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATE `furn` SET `name` = ? , `maker` = ?, `price` = ? , " +
                "`sales` = ? , `stock` = ? , `img_path` = ? " +
                "WHERE id = ? ";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath(), furn.getId());
    }

    //首页、后台分页的家居总行数
    @Override
    public int getTotalRow() {
        String sql = "SELECT COUNT(*) FROM `furn`";
        //return (Integer) queryScalar(sql); //=> cast异常
        //java基础的包装类讲过 => 自己回去瞜一眼
        return ((Number) queryScalar(sql)).intValue();
    }

    //首页、后台分页的当页家居
    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {

        String sql = "SELECT `id`, `name` , `maker`, `price`, `sales`, `stock`, " +
                "`img_path` imgPath FROM furn LIMIT ?, ?";

        return queryMulti(sql, Furn.class, begin, pageSize);
    }

    //首页搜索商品的总行数
    @Override
    public int getTotalRowByName(String name) {
        String sql = "SELECT COUNT(*) FROM `furn` WHERE `name` LIKE ?";
        return ((Number) queryScalar(sql, "%" + name + "%")).intValue();
    }

    //首页搜索单页的商品
    @Override
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name) {
        String sql = "SELECT `id`, `name` , `maker`, `price`, `sales`, `stock`, " +
                "`img_path` imgPath FROM furn WHERE `name` LIKE ? LIMIT ?, ?";
        return queryMulti(sql, Furn.class, "%" + name + "%", begin, pageSize);
    }

}
