package com.membership.mapper;

import com.membership.pojo.MemberInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void register(MemberInfo memberInfo);

    MemberInfo login (MemberInfo memberInfo);

    void addPoints(String userId, String rentId, Integer points);

    Integer getMinPoints(Integer level, String rentId);

    void addLevel(String userId, String rentId);

    MemberInfo getMemberInfo(String userId, String rentId);
}
