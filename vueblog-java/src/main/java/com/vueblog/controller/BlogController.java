package com.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Blog;
import com.vueblog.service.BlogService;
import com.vueblog.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-31
 */
@Slf4j
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 列表
     * @param currentPage 当前页
     * @return pageData
     * */
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        log.info("currentPage->" + currentPage);
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    /**
     * 详情
     * @param id 博客id
     * @return blog
     */
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");

        return Result.success(blog);
    }

    /**
     * 编辑
     * @param blog 参数
     * @return
     */
    @RequiresAuthentication
    @PostMapping("blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            log.info("文章id->" + ShiroUtil.getProfile().getId());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }

}
