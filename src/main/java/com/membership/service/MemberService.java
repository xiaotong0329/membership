package com.membership.service;

import com.membership.pojo.MemberInfo;


public interface MemberService {

    void register(MemberInfo memberInfo);

    MemberInfo login(MemberInfo memberInfo);
}
