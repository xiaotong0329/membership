package com.membership.aop.aspect;

import com.membership.aop.annotation.Log;
import com.membership.mapper.MemberMapper;
import com.membership.pojo.MemberInfo;
import com.membership.pojo.OperationLog;
import com.membership.pojo.PointRule;
import com.membership.service.MemberService;
import com.membership.service.RuleService;
import com.membership.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Order(20)
@Component
public class LogAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;


    @Around("@annotation(logAnnotation)")
    public Object recordLog(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        Object result = null;
        LocalDateTime now = LocalDateTime.now();

            String operatorType = logAnnotation.eventType();
            String userId = null;
            String rentId = null;
            Integer beforeOperation = 0;
            Integer afterOperation = 0;
            Integer change = 0;

            if("login".equals(logAnnotation.eventType())) {

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
            log.info("userid:{} rentId:{}", userId, rentId);
            MemberInfo m = memberService.getMemberInfo(userId, rentId);

            //get before points
            beforeOperation = m.getTotalPoints();

            //execute the operation
            result = joinPoint.proceed();

            //get after points
            entityManager.clear();
            MemberInfo afterstate = memberService.getMemberInfo(userId, rentId);
            afterOperation = afterstate.getTotalPoints();

            //get point change
            PointRule rule = ruleService.getRule(operatorType, rentId);
            change = rule.getPoints();

            log.info("operationType:{}, beforeOperation:{}, afterOperation:{}, change:{}", operatorType, beforeOperation, afterOperation, change);

            OperationLog log = new OperationLog();
            log.setOperationType(operatorType);
            log.setOperationTime(now);
            log.setBeforeOperation(beforeOperation);
            log.setAfterOperation(afterOperation);
            log.setPointChange(change);
            log.setUserId(userId);
            log.setRentId(rentId);

        memberMapper.addLog(log);
        return result;

    }

}
