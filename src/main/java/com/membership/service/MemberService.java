package com.membership.service;

import com.membership.pojo.MemberInfo;


public interface MemberService {

    void register(MemberInfo memberInfo);

    MemberInfo login(MemberInfo memberInfo);

    void addPoints(String userId, String rentId, Integer points);

    Integer getMinPoints(Integer level, String rentId);

    MemberInfo getMemberInfo(String userId, String rentId);

    void addLevel(String userId, String rentId);

    void updateInfo(MemberInfo memberInfo);
}

