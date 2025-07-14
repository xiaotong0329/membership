package com.membership.mapper;

import com.membership.pojo.MemberInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void register(MemberInfo memberInfo);
}
