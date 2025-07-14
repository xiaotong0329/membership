package com.membership.controller;

import com.membership.pojo.MemberInfo;
import com.membership.pojo.Result;
import com.membership.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/v1/login")
    public Result login(String userId, String password){
        log.info("login:{}", userId);
        memberService.login(userId, password);
        return Result.success();
    }

}
