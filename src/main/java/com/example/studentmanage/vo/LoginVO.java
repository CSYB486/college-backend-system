package com.example.studentmanage.vo;

import lombok.Data;

/**
 * 登录成功后返回给前端的信息
 */
@Data
public class LoginVO {

    /** JWT token，后续请求放到请求头 Authorization 中 */
    private String token;

    private Long id;

    private String username;

    private String nickname;
}
