package com.lp.test.mybatisplusdemo;

import com.lp.test.mybatisplusdemo.modules.test.domain.po.User;
import com.lp.test.mybatisplusdemo.modules.test.service.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusDemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        for(User user:userList) {
            System.out.println(user);
        }
    }

}
