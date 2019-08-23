package cn.ifengkou.db.timeseries.dao.clickhouse;

import cn.ifengkou.db.timeseries.model.TestModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/22
 */
@Mapper
@Component
public interface EventDao {

    void createTestTable();

    void dropTestTable();

    int insertBatch(List<TestModel> list);

    List<TestModel> getAll();
}
