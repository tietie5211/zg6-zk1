package com.wjs.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:58
 */
@Data
public class UserVo implements Serializable {

    private String loginName;
    private String password;


}
