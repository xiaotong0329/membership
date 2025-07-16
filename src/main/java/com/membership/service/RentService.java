package com.membership.service;

import com.membership.pojo.LevelRule;
import com.membership.pojo.RentInfo;

public interface RentService {
    void add(RentInfo rentInfo);
    void addLevelRule(LevelRule levelRule);

}
