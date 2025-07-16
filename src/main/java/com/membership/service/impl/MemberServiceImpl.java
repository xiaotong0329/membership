package com.membership.service.impl;


import com.membership.mapper.MemberMapper;
import com.membership.pojo.MemberInfo;
import com.membership.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void register(MemberInfo memberInfo) {
        memberInfo.setUserId(UUID.randomUUID().toString());
        memberInfo.setCreateTime(LocalDateTime.now());
        memberInfo.setUpdateTime(LocalDateTime.now());
        memberInfo.setMemberNo(UUID.randomUUID().toString());
        memberInfo.setTotalPoints(0);
        memberInfo.setCurrentLevel(0);
        memberInfo.setDeleted(0);
        log.info("service register memberInfo: {}", memberInfo);
        memberMapper.register(memberInfo);

    }

    @Override
    public MemberInfo login(MemberInfo memberInfo) {
        log.info("service login memberInfo: {}", memberInfo);
        MemberInfo m =  memberMapper.login(memberInfo);
        return m;
    }

    @Override
    public void addPoints(String userId, String rentId, Integer points) {
        log.info("service addPoint userId: {}, rentId: {}", userId, rentId);
        memberMapper.addPoints(userId, rentId, points);
    }

    @Override
    public Integer getMinPoints(Integer level, String rentId) {
        log.info("service getMinPoints level: {}", level);
        return memberMapper.getMinPoints(level, rentId);
    }

    @Override
    public void addLevel(String userId, String rentId) {
        log.info("service addLevel userId: {}, rentId: {}", userId, rentId);
        memberMapper.addLevel(userId, rentId);
    }

    @Override
    public MemberInfo getMemberInfo(String userId, String rentId) {
        log.info("service getMemberInfo userId: {}, rentId: {}", userId, rentId);
        return memberMapper.getMemberInfo(userId, rentId);
    }

}
