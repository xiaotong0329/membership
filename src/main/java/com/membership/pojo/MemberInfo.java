package com.membership.pojo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String rentId;
    private String memberNo;
    private String userName;
    private String password;
    private String phone;
    private String imageUrl;
    private Integer currentLevel;
    private Integer totalPoints;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;

}
