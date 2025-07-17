package com.membership.controller;

import com.membership.aop.annotation.AwardPoints;
import com.membership.exception.LoginFailedException;
import com.membership.pojo.MemberInfo;
import com.membership.pojo.Result;
import com.membership.service.MemberService;
import com.membership.utils.AliOSSUtils;
import com.membership.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/membership")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    AliOSSUtils aliOSSUtils;

    /**
     * @param memberInfo 注册会员信息
     * @return
     */
    @PostMapping("/v1/register")
    public Result register(@RequestBody MemberInfo memberInfo) {
        log.info("register:{}", memberInfo);
        memberService.register(memberInfo);

        return Result.success();
    }

    /**
     * @param memberInfo 传入rentid, userid, password
     * @return JWT令牌
     */
    @ResponseBody
    @AwardPoints(eventType = "login")
    @PostMapping("/v1/login")
    public Result login(@RequestBody MemberInfo memberInfo) {
        log.info("login:{}", memberInfo);
        MemberInfo mem = memberService.login(memberInfo);
        if (mem == null) {
            throw new LoginFailedException("用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", mem.getUserId());
        claims.put("rentId", mem.getRentId());
        log.info("login:{}", mem);

        String jwt = JWTUtils.createJWT(claims);
        return Result.success(jwt);
    }

    /**
     * 模拟消费触发事件 -> 增加积分
     *
     * @return
     */
    @AwardPoints(eventType = "purchase")
    @PostMapping("/v1/purchase")
    public Result purchase() {
        log.info("purchase");
        return Result.success();
    }

    /**
     * @param memberInfo 传入userid, rentid, 修改username
     * @param imageFile  传入头像
     * @return
     * @throws IOException
     */
    @PostMapping("/v1/update-info")
    public Result updateInfo(
            @RequestPart("memberInfo") MemberInfo memberInfo,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        log.info("updateInfo:{}", memberInfo);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = aliOSSUtils.upload(imageFile);
            memberInfo.setImageUrl(imageUrl);
        }
        memberService.updateInfo(memberInfo);
        return Result.success();
    }

}