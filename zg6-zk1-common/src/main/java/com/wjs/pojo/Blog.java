package com.wjs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:17
 */
@Data
@TableName("blog")
public class Blog implements Serializable {

    // @TableId(type = IdType.ASSIGN_ID)
    @TableId(type = IdType.AUTO)
    private Integer blogId;


    private String title;

    private String content;

    // 点赞数
    private Integer upvoteNum;

    // 转发数
    private Integer transpondNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private Integer userId;

    private String picture;

}
