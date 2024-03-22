package com.wjs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjs.Vo.UserVo;
import com.wjs.mapper.UserMapper;
import com.wjs.pojo.User;
import com.wjs.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:40
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(UserVo userVo) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getLoginName,userVo.getLoginName());

        User one = userMapper.selectOne(wrapper);

        return one;
    }
}
