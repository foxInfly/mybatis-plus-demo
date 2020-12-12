package com.lp.test.mybatisplusdemo.modules.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lp.test.mybatisplusdemo.modules.test.domain.po.User;
import com.lp.test.mybatisplusdemo.modules.test.service.UserService;
import com.lp.test.mybatisplusdemo.modules.test.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author lp
 * @since 2020-12-12 22:32:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}