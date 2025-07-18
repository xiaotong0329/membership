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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
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
    public Object awardPoints(ProceedingJoinPoint joinPoint, AwardPoints awardPoints) throws Throwable {
        String userId = null;
        String rentId = null;
        log.info(awardPoints.toString());
        Integer beforeOperation = 0;
        Integer afterOperation = 0;
        Integer change = 0;
        //如果是登录操作，从参数中获取rentid和userid
        if ("login".equals(awardPoints.eventType())) {

            Object[] args = joinPoint.getArgs();
            log.info("args: {}", args);
            MemberInfo mem = (MemberInfo) args[0];
            userId = mem.getUserId();
            rentId = mem.getRentId();
            log.info("userId:{} rentId:{}", userId, rentId);

        } else {
            //如果不是登录操作，从jwt令牌中获取userid和rentid
            String jwt = request.getHeader("token");
            Claims claims = JWTUtils.parseJWT(jwt);
            userId = (String) claims.get("userId");
            rentId = (String) claims.get("rentId");

        }
        String eventType = awardPoints.eventType();


        //获取触发事件对应的积分
        PointRule rule = ruleService.getRule(eventType, rentId);
        //获取积分变更前的积分
        MemberInfo before = memberService.getMemberInfo(userId, rentId);
        beforeOperation = before.getTotalPoints();


        Object result = joinPoint.proceed();

        //增加积分
        memberService.addPoints(userId, rentId, rule.getPoints());
        MemberInfo m = memberService.getMemberInfo(userId, rentId);
        //记录增加积分后的积分
        afterOperation = m.getTotalPoints();
        //记录更改的积分
        change = rule.getPoints();
        Integer clevel = m.getCurrentLevel();
        Integer cpoints = m.getTotalPoints();
        //检查能否升级
        if (cpoints >= memberService.getMinPoints(clevel + 1, rentId)) {
            memberService.addLevel(userId, rentId);
        }

        //记录此次积分操作的日志
        OperationLog log = new OperationLog();
        log.setOperationType(eventType);
        log.setOperationTime(LocalDateTime.now());
        log.setBeforeOperation(beforeOperation);
        log.setAfterOperation(afterOperation);
        log.setPointChange(change);
        log.setUserId(userId);
        log.setRentId(rentId);
        memberMapper.addLog(log);

        return result;

    }
}
