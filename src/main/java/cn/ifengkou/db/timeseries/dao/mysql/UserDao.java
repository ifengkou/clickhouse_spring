package cn.ifengkou.db.timeseries.dao.mysql;

import cn.ifengkou.db.timeseries.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@Component
@Mapper
public interface UserDao {

    @Update({"CREATE TABLE `user` (" +
            "  `id` int(13) NOT NULL AUTO_INCREMENT COMMENT '主键'," +
            "  `name` varchar(33) DEFAULT NULL COMMENT '姓名'," +
            "  `age` int(3) DEFAULT NULL COMMENT '年龄'," +
            "  `money` double DEFAULT NULL COMMENT '账户余额'," +
            "  PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8"})
    void createUserTable();

    @Insert("INSERT INTO user(name, age,money) VALUES(#{name}, #{age}, #{money})")
    void insertUser(User user);

    @Select("SELECT * FROM user")
    List<User> findAllUser();

    /**
     * 通过名字查询用户信息
     */
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(@Param("name") String name);

    @Update({"drop table if exists user"})
    void dropUserTable();
}
