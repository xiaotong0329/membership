package com.membership.service;

import com.membership.pojo.PointRule;
import org.springframework.stereotype.Service;


public interface RuleService {
    PointRule getRule(String eventType, String rentId);

}
