package com.vueblog.Shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author m
 * @version 1.0
 * @className AccountProfile
 * @description 帐户资料
 * @date 2021/1/31
 */

@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;
}
