package com.membership.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
