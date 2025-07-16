package com.membership.service.impl;

import com.membership.mapper.RentMapper;
import com.membership.pojo.LevelRule;
import com.membership.pojo.RentInfo;
import com.membership.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class RentServiceImpl implements RentService {
    @Autowired
    private RentMapper rentMapper;

    @Override
    public void add(RentInfo rentInfo) {
        log.info("service add rent");
        rentInfo.setRentId(UUID.randomUUID().toString());
        rentInfo.setCreateTime(LocalDateTime.now());
        rentInfo.setUpdateTime(LocalDateTime.now());
        rentInfo.setDeleted(0);
        rentMapper.add(rentInfo);

    }

    @Override
    public void addLevelRule(LevelRule levelRule) {
        log.info("service add level rule");
        rentMapper.addLevelRule(levelRule);
    }
}
