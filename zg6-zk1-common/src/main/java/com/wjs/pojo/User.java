package com.wjs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:17
 */
@Data
@TableName("user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String username;

    private String loginName;

    private String password;

    private String name;

    private String sex;

    private String phone;

    //身份证
    private String identityCard;

}
