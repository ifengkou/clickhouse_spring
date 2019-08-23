package cn.ifengkou.db.timeseries.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/23
 */
@Data
public class TestModel implements Serializable {
    private Date day;
    private String name;
    private int age;
}
