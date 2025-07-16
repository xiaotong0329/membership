package com.membership.service.impl;

import com.membership.mapper.RuleMapper;
import com.membership.pojo.PointRule;
import com.membership.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public PointRule getRule(String eventType, String rentId) {
        PointRule p = ruleMapper.getRule(eventType, rentId);
        return p;

    }
}
