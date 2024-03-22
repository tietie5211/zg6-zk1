package com.wjs.controller;

import com.wjs.Vo.UserVo;
import com.wjs.mapper.UserMapper;
import com.wjs.pojo.User;
import com.wjs.service.UserService;
import com.wjs.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:39
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public R login(@RequestBody UserVo userVo){
       User one = userService.login(userVo);

       return R.ok().message("查询成功").data("one",one);
    }

}
