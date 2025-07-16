package com.membership.aop.aspect;

import com.membership.aop.annotation.AwardPoints;
import com.membership.mapper.MemberMapper;
import com.membership.pojo.MemberInfo;
import com.membership.pojo.OperationLog;
import com.membership.pojo.PointRule;
import com.membership.service.MemberService;
import com.membership.service.RuleService;
import com.membership.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@Order(10)
public class PointAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;


    @Around("@annotation(awardPoints)")
    public void awardPoints(JoinPoint joinPoint, AwardPoints awardPoints) {
        String userId = null;
        String rentId = null;
        log.info(awardPoints.toString());
        Integer beforeOperation = 0;
        Integer afterOperation = 0;
        Integer change = 0;
        if("login".equals(awardPoints.eventType())) {

            Object[] args = joinPoint.getArgs();
            log.info("args: {}", args);
            MemberInfo mem = (MemberInfo) args[0];
            userId = mem.getUserId();
            rentId = mem.getRentId();
            log.info("userId:{} rentId:{}", userId, rentId);

        } else {

            String jwt = request.getHeader("token");
            Claims claims = JWTUtils.parseJWT(jwt);
            userId = (String) claims.get("userId");
            rentId = (String) claims.get("rentId");

        }
            String eventType = awardPoints.eventType();


        PointRule rule = ruleService.getRule(eventType, rentId);
        MemberInfo before = memberService.getMemberInfo(userId, rentId);
        beforeOperation = before.getTotalPoints();

        memberService.addPoints(userId, rentId, rule.getPoints());
        MemberInfo m = memberService.getMemberInfo(userId, rentId);
        afterOperation = m.getTotalPoints();
        change = rule.getPoints();
        Integer clevel = m.getCurrentLevel();
        Integer cpoints = m.getTotalPoints();
        //check if can increase level
        if(cpoints >= memberService.getMinPoints(clevel + 1, rentId))
        {
            memberService.addLevel(userId, rentId);
        }

        OperationLog log = new OperationLog();
        log.setOperationType(eventType);
        log.setOperationTime(LocalDateTime.now());
        log.setBeforeOperation(beforeOperation);
        log.setAfterOperation(afterOperation);
        log.setPointChange(change);
        log.setUserId(userId);
        log.setRentId(rentId);
        memberMapper.addLog(log);

    }
}
