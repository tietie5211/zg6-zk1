package com.wjs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjs.Vo.BlogVo;
import com.wjs.param.BlogParam;
import com.wjs.pojo.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * description
 *
 * @author yuanguangbin
 * @date 2023/11/16 10:04
 */
public interface BlogService extends IService<Blog> {
    Map<String, Object> blogList(BlogVo blogVo);

    int add(Blog blog);

    Blog selectOne(Blog blog);

    int updateUpvote(BlogParam blogParam);

    String upload(MultipartFile file);
}
