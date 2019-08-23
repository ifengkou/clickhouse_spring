package cn.ifengkou.db.timeseries.service;

import cn.ifengkou.db.timeseries.dao.mysql.UserDao;
import cn.ifengkou.db.timeseries.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/23
 */
@Service
public class UserService {

    @Resource
    UserDao userDao;

    void dropUserTable(){
        userDao.dropUserTable();
    }

    void createUserTable(){
        userDao.createUserTable();
    }

    void insertUser(User user){
        userDao.insertUser(user);
    }

    List<User> findAllUser(){
        return userDao.findAllUser();
    }

    /**
     * 通过名字查询用户信息
     */
    User findUserByName(String name){
        return userDao.findUserByName(name);
    }
}
