package com.membership.mapper;

import com.membership.pojo.RentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentMapper {
    void add(RentInfo rentInfo);
}
