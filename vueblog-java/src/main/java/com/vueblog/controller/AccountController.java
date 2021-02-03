package com.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.vueblog.common.dto.LoginDto;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import com.vueblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author m
 * @version 1.0
 * @className AccountController
 * @description AccountController
 * @date 2021/2/1
 */
@Slf4j
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto,
                        HttpServletResponse response)
    {
        User user = userService.getOne(new QueryWrapper<User>().eq("username",loginDto.getUsername()));
        Assert.notNull(user,"用户不存在");

        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        // 创建token
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result loginOut(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
