package com.membership.controller;

import com.membership.pojo.LevelRule;
import com.membership.pojo.RentInfo;
import com.membership.pojo.Result;
import com.membership.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/membership")
public class RentController {
    @Autowired
    private RentService rentService;

    /**
     * @param rentInfo 增加商铺
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody RentInfo rentInfo) {
        log.info("add rent info :{}", rentInfo);
        rentService.add(rentInfo);
        return Result.success(rentInfo);
    }

    /**
     * @param levelRule 增加等级规则
     * @return
     */
    @PostMapping("/add-level-rule")
    public Result addLevelRule(@RequestBody LevelRule levelRule) {
        log.info("add level rule :{}", levelRule);
        rentService.addLevelRule(levelRule);
        return Result.success();

    }


}
