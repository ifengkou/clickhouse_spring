package cn.ifengkou.db.timeseries.service;

import cn.ifengkou.db.timeseries.dao.clickhouse.EventDao;
import cn.ifengkou.db.timeseries.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@Service
public class EventService {
    @Autowired
    EventDao eventDao;

    @Autowired
    @Qualifier("clickHouseDataSource")
    DataSource clickHouseDataSource;

    @Autowired
    @Qualifier("clickhouseJdbcTemplate")
    JdbcTemplate clickhouseJdbcTemplate;

    List<TestModel> getAllByMybatis(){
        return eventDao.getAll();
    }

    List<TestModel> getAllByDataSource() throws SQLException {
        Connection connection = clickHouseDataSource.getConnection();
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT day,name,age FROM test_jdbc_example limit 20");

        List<TestModel> list = new ArrayList<>();
        while (rs.next()) {
            TestModel testModel = new TestModel();
            testModel.setDay(rs.getDate(1));
            testModel.setName(rs.getString(2));
            testModel.setAge(rs.getInt(3));
            list.add(testModel);
        }
        return list;
    }

    List<TestModel> getAllBySpringData(){
        String sql = "SELECT day,name,age FROM test_jdbc_example limit 20";
        RowMapper<TestModel> rowMapper = new BeanPropertyRowMapper<>(TestModel.class);
        List<TestModel> list = clickhouseJdbcTemplate.query(sql,rowMapper);
        return list;
    }
}
