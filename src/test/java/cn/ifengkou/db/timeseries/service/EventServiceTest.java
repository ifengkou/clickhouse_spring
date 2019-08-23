package cn.ifengkou.db.timeseries.service;

import cn.ifengkou.db.timeseries.model.TestModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

    @Autowired
    EventService eventService;


    @Test
    public void testGetByMybatis() throws SQLException {
        List<TestModel> list = eventService.getAllByMybatis();
        Assert.assertTrue(list.size()==20);
    }

    @Test
    public void testGetByDataSource() throws SQLException {
        List<TestModel> list = eventService.getAllByDataSource();
        Assert.assertTrue(list.size()==20);
    }


    @Test
    public void testGetBySpringData() throws SQLException {
        List<TestModel> list = eventService.getAllBySpringData();
        Assert.assertTrue(list.size()==20);
    }

}
