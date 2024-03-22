package com.wjs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjs.param.BlogParam;
import com.wjs.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description
 *
 * @author yuanguangbin
 * @date 2023/11/16 10:04
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {


    int updateUpvote(BlogParam blogParam);

    Blog selectTitle(Blog blog);

    List<Blog> selectListByTime(long last);
}
