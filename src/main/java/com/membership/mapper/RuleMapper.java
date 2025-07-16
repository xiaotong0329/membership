package com.membership.mapper;

import com.membership.pojo.PointRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RuleMapper {

    @Select("select * from point_rule where event_type = #{eventType} and rent_id = #{rentId}")
    PointRule getRule(String eventType, String rentId);
}
