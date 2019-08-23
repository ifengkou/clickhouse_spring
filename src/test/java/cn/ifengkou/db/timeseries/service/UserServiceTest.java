package cn.ifengkou.db.timeseries.service;

import cn.ifengkou.db.timeseries.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Resource
    UserService userService;

    @Test
    public void testCreateTable(){
        userService.dropUserTable();
        userService.createUserTable();
        User user = new User();
        user.setName("test");
        user.setAge(1);
        user.setMoney(2.0);
        userService.insertUser(user);

        List<User> users = userService.findAllUser();

        Assert.assertEquals(1,users.size());
        userService.dropUserTable();
    }
}
