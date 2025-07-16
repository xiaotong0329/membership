package com.membership.controller;

import com.membership.aop.annotation.AwardPoints;
import com.membership.aop.annotation.Log;
import com.membership.pojo.MemberInfo;
import com.membership.pojo.Result;
import com.membership.service.MemberService;
import com.membership.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/membership")
public class MemberController {
    @Autowired
    MemberService memberService;

    @PostMapping("/v1/register")
    public Result register(@RequestBody MemberInfo memberInfo){
        log.info("register:{}", memberInfo);
        memberService.register(memberInfo);

        return Result.success();
    }

    @Log(eventType = "login")
    @AwardPoints(eventType = "login")
    @PostMapping("/v1/login")
    public Result login(@RequestBody MemberInfo memberInfo){
        log.info("login:{}", memberInfo);
        MemberInfo mem = memberService.login(memberInfo);

        if (mem != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", mem.getUserId());
            claims.put("rentId", mem.getRentId());
            log.info("login:{}", mem);

            String jwt = JWTUtils.createJWT(claims);
            return Result.success(jwt);
        }
        return Result.success();
    }

    @Log(eventType = "purchase")
    @AwardPoints(eventType = "purchase")
    @PostMapping("/v1/purchase")
    public Result purchase(){
        log.info("purchase");
        return Result.success();
    }

}
