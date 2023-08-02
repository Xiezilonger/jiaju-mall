package com.hspedu.furns.test;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class FurnServiceTest {
    private FurnService furnService = new FurnServiceImpl();

    public void queryFurns() {
        List<Furn> furns = furnService.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void addFurn() {

        Furn furn = new Furn(null, "可爱的沙发~", "顺平家居", new BigDecimal(999.99), 100, 10, "assets/images/product-image/default.jpg");
        furnService.addFurn(furn);
    }

    public void deleteFurnById() {

        System.out.println(furnService.deleteFurnById(17));
    }

    public void queryFurnById() {
        Furn furn = furnService.queryFurnById(100);
        System.out.println("furn=>" + furn);
    }

    public void updateFurn() {

        Furn furn = new Furn(13, "可爱的沙发~", "顺平家居", new BigDecimal(999.99), 100, 10, "assets/images/product-image/default.jpg");
        System.out.println(furnService.updateFurn(furn));
    }

    public void page() {
        //老韩小技巧，如果我们需要看一个对象(复杂), 可以debug看对象
        Page<Furn> page = furnService.page(2, 2);
        System.out.println("page=" + page);
    }

    public void pageByName() {
        Page<Furn> page = furnService.pageByName(2, 3, "沙发");
        System.out.println("page=" + page);
    }
}
