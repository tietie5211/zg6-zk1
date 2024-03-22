package com.wjs.controller;

import com.wjs.Vo.BlogVo;
import com.wjs.param.BlogParam;
import com.wjs.pojo.Blog;
import com.wjs.service.BlogService;
import com.wjs.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 10:04
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping("blogList")
    public R blogList(@RequestBody BlogVo blogVo) {

        Map<String, Object> list = blogService.blogList(blogVo);

        return R.ok().message("查询成功").data("list", list);
    }

    /**
     * 添加方法
     * @param blog
     * @return
     */
    @PostMapping("add")
    public R add(@RequestBody Blog blog){

        if(StringUtils.isBlank(blog.getTitle())){
            return R.error().message("请输入标题，标题为空不能添加");
        }
        Blog blog1 = blogService.selectOne(blog);
        if(blog1 != null){
            return R.error().message("标题已经存了,不能添加相同的标题");
        }
        int i = blogService.add(blog);
        if(i>0){
            return R.ok().message("添加成功");
        }

        return R.error().message("添加失败");
    }

    @PostMapping("updateUpvote")
    public R updateUpvote(@RequestBody BlogParam blogParam){

        int i = blogService.updateUpvote(blogParam);

        if(i>0){
            return R.ok().message("点赞成功");
        }

        return R.error().message("点赞失败");
    }

    /**
     * 图片上传 fastdfs
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R upload(MultipartFile file){

        String fullPath = blogService.upload(file);

        return R.ok().message("上传成功").data("fullPath",fullPath);
    }

}
