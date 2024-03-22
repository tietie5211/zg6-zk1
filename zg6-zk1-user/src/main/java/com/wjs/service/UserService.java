package com.wjs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjs.Vo.UserVo;
import com.wjs.pojo.User;

/**
 * description
 *
 * @author yuanguangbin
 * @date 2023/11/16 9:40
 */
public interface UserService extends IService<User> {
    User login(UserVo userVo);
}
