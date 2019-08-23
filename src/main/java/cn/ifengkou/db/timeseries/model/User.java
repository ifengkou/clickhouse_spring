package cn.ifengkou.db.timeseries.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/8/23
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private Integer age;
    private Double money;
}
