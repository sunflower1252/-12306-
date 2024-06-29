package com.sanzuniao.business.Controller;

import com.sanzuniao.business.service.StationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang
 */
@RequestMapping("/station")
@RestController
public class StationController {

    @Resource
    private StationService stationService;

    public void test() {
        System.out.println("admin的测试样例");
    }
}
