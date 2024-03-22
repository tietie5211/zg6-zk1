package com.wjs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 10:06
 */
@Data
@Document(indexName = "blog")
public class BlogEntity implements Serializable {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer blogId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smary")
    private String title;

    @Field(type = FieldType.Keyword)
    private String content;

    //点赞数
    @Field(type = FieldType.Integer)
    private Integer upvoteNum;

    //转发数
    @Field(type = FieldType.Integer)
    private Integer transpondNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Field(type = FieldType.Date,format = DateFormat.date_time)
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date,format = DateFormat.date_time)
    private LocalDateTime updateTime;

    @Field(type = FieldType.Integer)
    private Integer userId;


    @Field(type = FieldType.Keyword)
    private String picture;

}
