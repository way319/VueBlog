package com.vueblog.util;

import com.vueblog.Shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author m
 * @version 1.0
 * @className ShiroUtil
 * @description ShiroUtil
 * @date 2021/2/1
 */
public class ShiroUtil {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
