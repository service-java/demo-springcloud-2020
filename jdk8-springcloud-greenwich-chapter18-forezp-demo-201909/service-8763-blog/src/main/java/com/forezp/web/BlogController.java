package com.forezp.web;

import com.forezp.annotation.SysLogger;
import com.forezp.dto.ResponseVO;
import com.forezp.entity.Blog;
import com.forezp.exception.CommonException;
import com.forezp.exception.ErrorCode;
import com.forezp.service.BlogService;
import com.forezp.util.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fangzhipeng on 2017/7/10.
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @ApiOperation(value = "发布博客", notes = "发布博客")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    @SysLogger("postBlog")
    public ResponseVO postBlog(@RequestBody Blog blog){
        //字段判读省略
       Blog blog1= blogService.postBlog(blog);
       return ResponseVO.onSuccess(blog1);
    }

    @ApiOperation(value = "根据用户id获取所有的blog", notes = "根据用户id获取所有的blog")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{username}")
    @SysLogger("getBlogs")
    public ResponseVO getBlogs(@PathVariable String  username){
        //字段判读省略
        if(UserUtils.isMyself(username)) {
            List<Blog> blogs = blogService.findBlogs(username);
            return ResponseVO.onSuccess(blogs);
        }else {
            throw new CommonException(ErrorCode.TOKEN_IS_NOT_MATCH_USER);
        }
    }

    @ApiOperation(value = "获取博文的详细信息", notes = "获取博文的详细信息")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}/detail")
    @SysLogger("getBlogDetail")
    public ResponseVO getBlogDetail(@PathVariable Long id){
        return ResponseVO.onSuccess(blogService.findBlogDetail(id));
    }
}
