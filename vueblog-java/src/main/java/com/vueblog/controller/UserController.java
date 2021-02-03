package com.vueblog.controller;


import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Object index(){
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.success(user);
    }
}
