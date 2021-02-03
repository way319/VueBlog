package com.vueblog.Shiro;

import cn.hutool.core.bean.BeanUtil;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import com.vueblog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author m
 * @version 1.0
 * @className AccountRealm
 * @description 授权
 * @date 2021/1/31
 */
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 判断是否JwtToken的实例
        return token instanceof JwtToken;
    }

    /**
     * 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        return null;
    }

    /**
     * 登录认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        // 获取令牌
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(Long.valueOf(userId));
        if(user ==null){
            // 未知帐户例外
            throw new UnknownAccountException("账户不存在");
        }

        if(user.getStatus() == -1){
            // 锁定帐户例外
            throw new LockedAccountException("账户已被锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user,profile);

        System.out.println("----------");
        // 返回 一部分公开的信息
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
